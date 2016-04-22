package nl.intratuin.testmarket.service.implementation;

import nl.intratuin.testmarket.dao.contract.AccessKeyDao;
import nl.intratuin.testmarket.dao.contract.CustomerDao;
import nl.intratuin.testmarket.dto.ResponseAccessToken;
import nl.intratuin.testmarket.entity.AccessKey;
import nl.intratuin.testmarket.entity.Customer;
import nl.intratuin.testmarket.service.contract.CustomerService;
import org.springframework.social.facebook.api.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.Random;

@Named
public class CustomerServiceImpl implements CustomerService {
    private int expire;
    @Inject
    private CustomerDao customerDao;
    @Inject
    private AccessKeyDao accessKeyDao;

    public CustomerServiceImpl(){
        Properties prop = new Properties();
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
            expire=Integer.parseInt(prop.getProperty("expire"));
        } catch (IOException e) {
            expire=3600;
        }
    }

    public Customer findById(int id) {
        return customerDao.findById(id);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public boolean addCustomer(Customer customer) {
        //Check whether is email already registered or not
        String emailToRegister = customer.getEmail().toLowerCase();
        Integer existedCustomerId = customerDao.findByEmail(emailToRegister);

        if (existedCustomerId != null) {
            return false;
        } else {
            customerDao.save(customer);
            return true;
        }
    }

    @Transactional
    public ResponseAccessToken login(MultiValueMap<String, String> header) {
        ResponseAccessToken token=headerFormatCheck(header);
        if(token.getToken_type().equals("error"))
            return token;
        String emailToLogin = header.getFirst("client_id");
        Integer foundCustomerId = customerDao.findByEmail(emailToLogin);
        if (foundCustomerId != null) {
            Customer customerToLogin = customerDao.findById(foundCustomerId);
            if (customerToLogin.getPassword()==null){
                token.setToken_type("error");
                token.setAccess_token("Your profile does not contain password. " +
                        "Log in using social networks and set password in private page");
                return token;
            } else if (customerToLogin.getPassword().equals(header.getFirst("client_secret"))) {
                AccessKey accessKeyEntity = new AccessKey();

                String accessToken = randomString(40);
                LocalDateTime expireLocalDate = LocalDateTime.now().plusSeconds(expire);

                accessKeyEntity.setCustomerId(customerToLogin.getId());
                accessKeyEntity.setAccessKey(accessToken);
                accessKeyEntity.setExpireDate(new java.util.Date(expireLocalDate.getYear()-1900,expireLocalDate.getMonthValue()-1,expireLocalDate.getDayOfMonth(),
                        expireLocalDate.getHour(),expireLocalDate.getMinute(),expireLocalDate.getSecond()));

                accessKeyDao.save(accessKeyEntity);

                token.setAccess_token(accessToken);
                return token;
            } else {
                token.setToken_type("error");
                token.setAccess_token("Wrong email or password.");
                return token;
            }
        }else{
            token.setToken_type("error");
            token.setAccess_token("Wrong email or password.");
            return token;
        }
    }

    private ResponseAccessToken headerFormatCheck(MultiValueMap<String, String> header){
        ResponseAccessToken token=new ResponseAccessToken();
        token.setToken_type("error");
        String[] par_list={"grant_type","client_id","client_secret","username","password"};
        for(String par:par_list){
            if(!header.containsKey(par)){
                token.setAccess_token(par+" parameter missed");
                return token;
            }
        }
        if(!header.getFirst("grant_type").equals("password")){
            token.setAccess_token("Wrong grant_type");
            return token;
        }
        if(!header.getFirst("client_id").equals(header.getFirst("username"))){
            token.setAccess_token("client_id and username mismatch");
            return token;
        }
        if(!header.getFirst("client_secret").equals(header.getFirst("password"))){
            token.setAccess_token("client_secret and password mismatch");
            return token;
        }
        token.setToken_type("bearer");
        return token;
    }

    private String randomString(int lenght){
        Random rnd=new Random();
        String charset="0123456789abcdefghijklmnopqrstuvwxyz";
        String res="";
        for(int i=0; i<lenght; i++)
            res+=charset.charAt(rnd.nextInt(charset.length()));
        return res;
    }

    @Transactional
    public ResponseAccessToken loginTwitter (String email){
        Integer foundCustomerId = customerDao.findByEmail(email);
        if (foundCustomerId == null) {
            Customer newCustomer = new Customer();
            newCustomer.setEmail(email);
            customerDao.save(newCustomer);
            foundCustomerId=customerDao.findByEmail(email);
        }
        ResponseAccessToken token=new ResponseAccessToken();
        token.setToken_type("bearer");
        AccessKey accessKeyEntity = new AccessKey();

        String accessToken = randomString(40);
        LocalDateTime expireLocalDate = LocalDateTime.now().plusSeconds(expire);

        accessKeyEntity.setCustomerId(foundCustomerId);
        accessKeyEntity.setAccessKey(accessToken);
        accessKeyEntity.setExpireDate(new java.util.Date(expireLocalDate.getYear()-1900,expireLocalDate.getMonthValue()-1,expireLocalDate.getDayOfMonth(),
                expireLocalDate.getHour(),expireLocalDate.getMinute(),expireLocalDate.getSecond()));

        accessKeyDao.save(accessKeyEntity);

        token.setAccess_token(accessToken);
        return token;
    }

    @Transactional
    public ResponseAccessToken loginWithFacebook (User profile){
        String emailToLoginWithFacebook = profile.getEmail();
        if (emailToLoginWithFacebook != null) {
            Integer existedCustomerId = customerDao.findByEmail(emailToLoginWithFacebook);

            ResponseAccessToken token=new ResponseAccessToken();
            token.setToken_type("bearer");
            AccessKey accessKeyEntity = new AccessKey();

            String accessToken = randomString(40);
            LocalDateTime expireLocalDate = LocalDateTime.now().plusSeconds(expire);

            if(existedCustomerId == null)
                existedCustomerId=addWithFacebook(profile);
            accessKeyEntity.setCustomerId(existedCustomerId);
            accessKeyEntity.setAccessKey(accessToken);
            accessKeyEntity.setExpireDate(new java.util.Date(expireLocalDate.getYear()-1900,expireLocalDate.getMonthValue()-1,expireLocalDate.getDayOfMonth(),
                    expireLocalDate.getHour(),expireLocalDate.getMinute(),expireLocalDate.getSecond()));

            accessKeyDao.save(accessKeyEntity);

            token.setAccess_token(accessToken);
            return token;
        } else {
            ResponseAccessToken token=new ResponseAccessToken();
            token.setToken_type("error");
            token.setAccess_token("Unable to retrieve email");
            return token;
        }
    }

    public Integer getCustomerIdByFacebookProfile(User profile){
            String emailToLoginWithFacebook = profile.getEmail();
            if (emailToLoginWithFacebook != null) {
                Integer existedCustomerId = customerDao.findByEmail(emailToLoginWithFacebook);

                return existedCustomerId != null
                        ? existedCustomerId
                        : -1;
            } else {
                return null;
            }
        }

    @Transactional
    private int addWithFacebook (User profile){
        Customer customer = new Customer();

        customer.setFirstName(profile.getFirstName());
        customer.setLastName(profile.getLastName());
        customer.setEmail(profile.getEmail().toLowerCase());

        if (profile.getLocation() != null) {
            customer.setCity(profile.getLocation().getName().toString());
        }
        String birthdayFromFacebook = profile.getBirthday();
        if (birthdayFromFacebook != null) {
            String[] arrForBirthday = birthdayFromFacebook.split("/");
            LocalDate birthdayLocalDate = LocalDate.of(Integer.parseInt(arrForBirthday[2]),
                    Integer.parseInt(arrForBirthday[0]), Integer.parseInt(arrForBirthday[1]));
            java.sql.Date birthday = java.sql.Date.valueOf(birthdayLocalDate);
            customer.setBirthday(birthday);
        }

        String genderFromFacebook = profile.getGender();
        if (genderFromFacebook != null) {
            customer.setGender(genderFromFacebook.equals("male") ? 1 : 0);
        }
        customerDao.save(customer);
        return customerDao.findByEmail(profile.getEmail().toLowerCase());
    }
}
