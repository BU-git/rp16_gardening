package nl.intratuin.testmarket.service.implementation;

import nl.intratuin.testmarket.dao.contract.AccessKeyDao;
import nl.intratuin.testmarket.dao.contract.CustomerDao;
import nl.intratuin.testmarket.dto.DTOEmail;
import nl.intratuin.testmarket.dto.DTOFingerprint;
import nl.intratuin.testmarket.dto.DTOPassword;
import nl.intratuin.testmarket.entity.AccessKey;
import nl.intratuin.testmarket.entity.Customer;
import nl.intratuin.testmarket.service.contract.CustomerService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

    public CustomerServiceImpl() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
            expire = Integer.parseInt(prop.getProperty("expire"));
        } catch (IOException e) {
            expire = 3600;
        }
    }

    public Customer findById(int id) {
        return customerDao.findById(id);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public JSONObject addCustomer(String header) {
        JSONObject request;
        try {
            request = (JSONObject) new JSONParser().parse(header);
        } catch (ParseException e) {
            JSONObject response = new JSONObject();
            response.put("code", "400");
            response.put("error", "bad_request");
            response.put("error_description", "Invalid json format");
            return response;
        }
        JSONObject response = registerHeaderFormatCheck(request);
        if (response.containsKey("error"))
            return response;
        //Check whether is email already registered or not
        String emailToRegister = request.get("email").toString().toLowerCase();
        Integer existedCustomerId = customerDao.findByEmail(emailToRegister);

        if (existedCustomerId != null) {
            response.put("code", "400");
            response.put("error", "user_exist");
            response.put("error_description", "User with this email already exists");
            return response;
        } else {
            Customer customer = new Customer();
            customer.setEmail(emailToRegister);
            String[] name = request.get("name").toString().split(" ");
            customer.setFirstName(name[0]);
            if (name.length == 2)
                customer.setLastName(name[1]);
            else {
                customer.setTussen(name[1]);
                customer.setLastName(name[2]);
            }
            //TODO: get gender
            //customer.setGender(header.getFirst("client_gender").equals("1")?1:0);
            customer.setPassword(request.get("password").toString());
            save(customer);
            response.put("id", "" + customer.getId());
            response.put("client_id", customer.getEmail());
            response.put("email", customer.getEmail());
            return response;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(Customer customer) {
        customerDao.save(customer);
    }

    private JSONObject registerHeaderFormatCheck(JSONObject header) {
        JSONObject response = new JSONObject();
        String[] par_list = {"email", "password", "name"};//"client_gender",
        for (String par : par_list) {
            if (!header.containsKey(par)) {
                response.put("code", "400");
                response.put("error", "invalid_registration_request");
                response.put("error_description", "Missing " + par + " parameter");
                return response;
            }
        }
        return response;
    }

    @Transactional
    public JSONObject login(MultiValueMap<String, String> header) {
        JSONObject response = loginHeaderFormatCheck(header);
        if (response.containsKey("error"))
            return response;
        String emailToLogin = header.getFirst("client_id");
        Integer foundCustomerId = customerDao.findByEmail(emailToLogin);
        if (foundCustomerId != null) {
            Customer customerToLogin = customerDao.findById(foundCustomerId);
            if (customerToLogin.getPassword() == null) {
                response.put("code", "400");
                response.put("error", "no_pass");
                response.put("error_description", "Your profile does not contain password. " +
                        "Log in using social networks and set password in private page");
                return response;
            } else if (customerToLogin.getPassword().equals(header.getFirst("client_secret"))) {
                AccessKey accessKeyEntity = new AccessKey();

                String accessToken = randomString(40);
                LocalDateTime expireLocalDate = LocalDateTime.now().plusSeconds(expire);

                accessKeyEntity.setCustomerId(customerToLogin.getId());
                accessKeyEntity.setAccessKey(accessToken);
                accessKeyEntity.setExpireDate(expireLocalDate);

                accessKeyDao.save(accessKeyEntity);

                response.put("token_type", "bearer");
                response.put("access_token", accessToken);
                response.put("expires_in", "" + expire);
                return response;
            } else {
                response.put("code", "400");
                response.put("error", "invalid_client");
                response.put("error_description", "Client credentials are invalid");
                return response;
            }
        } else {
            response.put("code", "400");
            response.put("error", "invalid_client");
            response.put("error_description", "Client credentials are invalid");
            return response;
        }
    }

    private JSONObject loginHeaderFormatCheck(MultiValueMap<String, String> header) {
        JSONObject response = new JSONObject();
        String[] par_list = {"grant_type", "client_id", "client_secret", "username", "password"};
        for (String par : par_list) {
            if (!header.containsKey(par)) {
                response.put("code", "400");
                response.put("error", "invalid_request");
                response.put("error_description", "Missing " + par + " parameter");
                return response;
            }
        }
        if (!header.getFirst("grant_type").equals("password")) {
            response.put("code", "400");
            response.put("error", "invalid_request");
            response.put("error_description", "Invalid grant_type parameter");
            return response;
        }
        if (!header.getFirst("client_id").equals(header.getFirst("username"))) {
            response.put("code", "400");
            response.put("error", "invalid_request");
            response.put("error_description", "client_id and username mismatch");
            return response;
        }
        if (!header.getFirst("client_secret").equals(header.getFirst("password"))) {
            response.put("code", "400");
            response.put("error", "invalid_request");
            response.put("error_description", "client_secret and password mismatch");
            return response;
        }
        return response;
    }

    private String randomString(int length) {
        Random rnd = new Random();
        String charset = "0123456789abcdefghijklmnopqrstuvwxyz";
        String res = "";
        for (int i = 0; i < length; i++)
            res += charset.charAt(rnd.nextInt(charset.length()));
        return res;
    }

    @Transactional
    public JSONObject loginTwitter(String email) {
        Integer foundCustomerId = customerDao.findByEmail(email);
        if (foundCustomerId == null) {
            Customer newCustomer = new Customer();
            newCustomer.setEmail(email);
            customerDao.save(newCustomer);
            foundCustomerId = customerDao.findByEmail(email);
        }
        JSONObject respond = new JSONObject();
        respond.put("token_type", "bearer");
        AccessKey accessKeyEntity = new AccessKey();

        String accessToken = randomString(40);
        LocalDateTime expireLocalDate = LocalDateTime.now().plusSeconds(expire);

        accessKeyEntity.setCustomerId(foundCustomerId);
        accessKeyEntity.setAccessKey(accessToken);
        accessKeyEntity.setExpireDate(expireLocalDate);

        accessKeyDao.save(accessKeyEntity);

        respond.put("access_token", accessToken);
        respond.put("expires_in", expire);
        return respond;
    }

    @Transactional
    public JSONObject loginWithFacebook(User profile) {
        String emailToLoginWithFacebook = profile.getEmail();
        if (emailToLoginWithFacebook != null) {
            Integer existedCustomerId = customerDao.findByEmail(emailToLoginWithFacebook);

            JSONObject response = new JSONObject();
            AccessKey accessKeyEntity = new AccessKey();

            String accessToken = randomString(40);
            LocalDateTime expireLocalDate = LocalDateTime.now().plusSeconds(expire);

            if (existedCustomerId == null)
                existedCustomerId = addWithFacebook(profile);
            accessKeyEntity.setCustomerId(existedCustomerId);
            accessKeyEntity.setAccessKey(accessToken);
            accessKeyEntity.setExpireDate(expireLocalDate);

            accessKeyDao.save(accessKeyEntity);

            response.put("token_type", "bearer");
            response.put("access_token", accessToken);
            response.put("expires_in", "" + expire);
            return response;
        } else {
            JSONObject response = new JSONObject();
            response.put("code", "400");
            response.put("error", "invalid_request");
            response.put("error_description", "Can't get email from facebook");
            return response;
        }
    }

    @Transactional
    private int addWithFacebook(User profile) {
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

    public Customer getCustomerByAccessKey(String accessKey) {
        AccessKey foundAccessKeyEntity = accessKeyDao.getNonDeprecatedKey(accessKey);
        return foundAccessKeyEntity != null
                ? customerDao.findById(foundAccessKeyEntity.getCustomerId())
                : null;
    }

    @Transactional
    public boolean updateCustomer(Customer customer) {
        return customerDao.updateCustomer(customer);
    }

    @Transactional
    public JSONObject updateEmail(DTOEmail dtoNewEmail) {
        JSONObject response = new JSONObject();
        if (customerDao.findByEmail(dtoNewEmail.getEmail()) == null) {
            if(customerDao.updateEmail(dtoNewEmail)){
                response.put("success", "your email has been changed");
            }else {
                response.put("code", "500");
                response.put("error", "internal server error");
                response.put("error_description", "Sorry, error saving, try again");
            }
        } else {
            response.put("code", "400");
            response.put("error", "invalid_request");
            response.put("error_description", "The email is already taken");
        }
        return response;
    }

    @Transactional
    public boolean updatePassword(DTOPassword dtoNewPassword) {
        return customerDao.updatePassword(dtoNewPassword);
    }

    @Transactional
    public boolean saveFingerprint(DTOFingerprint dtoFingerprint){
        return customerDao.saveFingerprint(dtoFingerprint);
    }

}
