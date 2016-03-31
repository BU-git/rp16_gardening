package nl.intratuin.testmarket.service.implementation;

import nl.intratuin.testmarket.Credentials;
import nl.intratuin.testmarket.Message;
import nl.intratuin.testmarket.Settings;
import nl.intratuin.testmarket.dao.contract.CustomerDao;
import nl.intratuin.testmarket.service.contract.CustomerService;
import nl.intratuin.testmarket.entity.Customer;
import org.springframework.social.facebook.api.User;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.security.SignatureException;
import java.time.LocalDate;
import java.util.List;

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
    public Message addCustomer(Customer customer) {
        //Check whether is email already registered or not
        String emailToRegister = customer.getEmail().toLowerCase();
        Integer existedCustomerId = customerDao.findByEmail(emailToRegister);

        if (existedCustomerId != null) {
            return new Message("Sorry, this email address is already registered, choose another.");
        } else {
            customerDao.save(customer);
            return new Message("Registration is successful");
        }
    }

    @Override
    public Message login(Credentials credentials) {
        String emailToLogin = credentials.getEmail();
        Integer foundCustomerId = customerDao.findByEmail(emailToLogin);
        if (foundCustomerId != null) {
            Customer customerToLogin = customerDao.findById(foundCustomerId);
            return (customerToLogin.getPassword().equals(credentials.getPassword()))
                    ? new Message("Login is successful")
                    : new Message("Sorry, your username and password are incorrect - please try again.");
        } else {
            return new Message("Sorry, your username and password are incorrect - please try again.");
        }
    }

    @Transactional
    public Message loginTwitter(Credentials credentials) {
        String twitterKey = credentials.getPassword();
        String emailToLogin = credentials.getEmail();
        try {
            if (!twitterKey.equals(Settings.getEncryptedTwitterKey(emailToLogin)))
                return new Message("Wrong Twitter key.");
        } catch(SignatureException e){
            return new Message("Server encryption error");
        }
        Integer foundCustomerId = customerDao.findByEmail(emailToLogin);
        if (foundCustomerId != null) {
            return new Message("Login is successful");
        } else {
            Customer newCustomer = new Customer();
            newCustomer.setEmail(emailToLogin);
            customerDao.save(newCustomer);
            return new Message("Registration and login is successful.");
        }
    }

    @Transactional
    public Message loginWithFacebook(User profile) {
        String emailToLoginWithFacebook = profile.getEmail();
        if(emailToLoginWithFacebook != null) {
            Integer existedCustomerId = customerDao.findByEmail(emailToLoginWithFacebook);

            return existedCustomerId != null
                    ? new Message("Login is successful")
                    : addWithFacebook(profile);
        } else {
            return new Message("to successfully login we need your email");
        }
    }

    @Transactional
    private Message addWithFacebook(User profile) {
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
        return new Message("Registration with Facebook is successful");
    }
}
