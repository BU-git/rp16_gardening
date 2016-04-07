package nl.intratuin.testmarket.service.implementation;

import nl.intratuin.testmarket.controller.CustomerController;
import nl.intratuin.testmarket.dto.Credentials;
import nl.intratuin.testmarket.dto.RecoveryRecord;
import nl.intratuin.testmarket.dto.TransferMessage;
import nl.intratuin.testmarket.Settings;
import nl.intratuin.testmarket.dao.contract.CustomerDao;
import nl.intratuin.testmarket.service.contract.CustomerService;
import nl.intratuin.testmarket.entity.Customer;
import org.springframework.social.facebook.api.User;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SignatureException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

@Named
public class CustomerServiceImpl implements CustomerService {
    @Inject
    private CustomerDao customerDao;

    public Customer findById(int id) {
        return customerDao.findById(id);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Transactional
    public TransferMessage addCustomer(Customer customer) {
        //Check whether is email already registered or not
        String emailToRegister = customer.getEmail().toLowerCase();
        Integer existedCustomerId = customerDao.findByEmail(emailToRegister);

        if (existedCustomerId != null) {
            return new TransferMessage("Sorry, this email address is already registered, choose another.");
        } else {
            customerDao.save(customer);
            return new TransferMessage("Registration is successful");
        }
    }

    @Override
    public TransferMessage login(Credentials credentials) {
        String emailToLogin = credentials.getEmail();
        Integer foundCustomerId = customerDao.findByEmail(emailToLogin);
        if (foundCustomerId != null) {
            Customer customerToLogin = customerDao.findById(foundCustomerId);
            return (customerToLogin.getPassword().equals(credentials.getPassword()))
                    ? new TransferMessage("Login is successful")
                    : new TransferMessage("Sorry, your username and password are incorrect - please try again.");
        } else {
            return new TransferMessage("Sorry, your username and password are incorrect - please try again.");
        }
    }

    @Transactional
    public TransferMessage loginTwitter(Credentials credentials) {
        String twitterKey = credentials.getPassword();
        String emailToLogin = credentials.getEmail();
        try {
            if (!twitterKey.equals(Settings.getEncryptedTwitterKey(emailToLogin)))
                return new TransferMessage("Wrong Twitter key.");
        } catch(SignatureException e){
            return new TransferMessage("Server encryption error");
        }
        Integer foundCustomerId = customerDao.findByEmail(emailToLogin);
        if (foundCustomerId != null) {
            return new TransferMessage("Login is successful");
        } else {
            Customer newCustomer = new Customer();
            newCustomer.setEmail(emailToLogin);
            customerDao.save(newCustomer);
            return new TransferMessage("Registration and login is successful.");
        }
    }

    @Transactional
    public TransferMessage loginWithFacebook(User profile) {
        String emailToLoginWithFacebook = profile.getEmail();
        if(emailToLoginWithFacebook != null) {
            Integer existedCustomerId = customerDao.findByEmail(emailToLoginWithFacebook);

            return existedCustomerId != null
                    ? new TransferMessage("Login is successful")
                    : addWithFacebook(profile);
        } else {
            return new TransferMessage("to successfully login we need your email");
        }
    }

    @Transactional
    private TransferMessage addWithFacebook(User profile) {
        Customer customer = new Customer();

        customer.setFirstName(profile.getFirstName());
        customer.setLastName(profile.getLastName());
        customer.setEmail(profile.getEmail().toLowerCase());

        if(profile.getLocation() != null) {
            customer.setCity( profile.getLocation().getName().toString());
        }
        String birthdayFromFacebook = profile.getBirthday();
        if(birthdayFromFacebook != null) {
            String[] arrForBirthday = birthdayFromFacebook.split("/");
            LocalDate birthdayLocalDate = LocalDate.of(Integer.parseInt(arrForBirthday[2]),
                    Integer.parseInt(arrForBirthday[0]), Integer.parseInt(arrForBirthday[1]));
            java.sql.Date birthday = java.sql.Date.valueOf(birthdayLocalDate);
            customer.setBirthday(birthday);
        }

        String genderFromFacebook = profile.getGender();
        if(genderFromFacebook != null) {
            customer.setGender(genderFromFacebook.equals("male") ? 1 : 0);
        }
        customerDao.save(customer);
        return new TransferMessage("Registration with Facebook is successful");
    }

    @Override
    public TransferMessage forgot(String email, Properties fMailServerConfig) {
        Integer id=customerDao.findByEmail(email);
        if(id==null)
            return new TransferMessage("Recovery failed: no such email in user list.");
        Session session = Session.getDefaultInstance(fMailServerConfig, null);
        MimeMessage message = new MimeMessage(session);
        String key=id.toString();
        LocalDateTime time=LocalDateTime.now();
        String link="";
        try {
            link=Settings.sha1(time.toString(),key);
            message.setFrom(new InternetAddress("recovery@intratuin.nl"));
            message.addRecipient(
                    Message.RecipientType.TO, new InternetAddress(email)
            );
            message.setSubject("Intratuin: password recovery");
            message.setText("Follow this link to change your password in Intrauin:\n"+link);
            Transport.send(message);
        }
        catch (MessagingException | SignatureException ex){
            //System.err.println("Cannot send email. " + ex);
            return new TransferMessage("Recovery failed");
        }
        CustomerController.recoveryRecords.add(new RecoveryRecord(email,link,time));
        return new TransferMessage("Recovery successfull");
    }

    @Override
    public void recovery(String link, Properties fMailServerConfig) {
        RecoveryRecord recoveryRecord=findRecord(link);
        try {
            if(recoveryRecord!=null){
                Session session = Session.getDefaultInstance(fMailServerConfig, null);
                MimeMessage message = new MimeMessage(session);

                message.setFrom(new InternetAddress("recovery@intratuin.nl"));
                message.addRecipient(
                        Message.RecipientType.TO, new InternetAddress(recoveryRecord.getEmail())
                );

                if(recoveryRecord.isDeprecated()) {
                    message.setSubject("Intratuin: error!");
                    message.setText("Link is too old. Try again.");
                } else {
                    String pass=generatePass();
                    String passEncrypted=Settings.sha1(pass,recoveryRecord.getEmail());

                    int customerId=customerDao.findByEmail(recoveryRecord.getEmail());
                    Customer c=customerDao.findById(customerId);
                    c.setPassword(passEncrypted);
                    customerDao.save(c);

                    message.setSubject("Intratuin: password successfuly changed");
                    message.setText("Password changed. Your new password:\n"+pass);
                }
                Transport.send(message);
            }
        }
        catch (MessagingException | SignatureException ex){
            //System.err.println("Cannot send email. " + ex);
        }
    }

    private RecoveryRecord findRecord(String link){
        for(RecoveryRecord e:CustomerController.recoveryRecords)
            if(e.getLink().equals(link)) return e;
        return null;
    }

    private String generatePass(){
        String res="";
        for(int i=0; i<3; i++){
            res+=randomLetter(true);
            res+=randomLetter(false);
            res+=new Random().nextInt(9);
        }
        return res;
    }
    private char randomLetter(boolean capital){
        String alphabet="abcdefghijklmnopqrstuvwxyz";
        String alphabetCap="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if(capital)
            return alphabetCap.charAt(new Random().nextInt(alphabetCap.length()));
        else return alphabet.charAt(new Random().nextInt(alphabet.length()));
    }
}
