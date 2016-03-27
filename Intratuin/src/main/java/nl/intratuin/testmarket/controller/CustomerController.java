package nl.intratuin.testmarket.controller;

import nl.intratuin.testmarket.Credentials;
import nl.intratuin.testmarket.Settings;
import nl.intratuin.testmarket.TransferAccessToken;
import nl.intratuin.testmarket.service.contract.CustomerService;
import nl.intratuin.testmarket.Message;
import nl.intratuin.testmarket.entity.Customer;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.SignatureException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/customer/")
public class CustomerController {

    @Inject
    CustomerService service;

    @RequestMapping("all")
    public List<Customer> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "{id}")
    public Customer getById(@PathVariable int id) {
        return service.findById(id);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public @ResponseBody Message add(@RequestBody Customer newCustomer) {
        //Check whether is email already registered or not
        String emailToRegister = newCustomer.getEmail().toLowerCase();
        Integer existedCustomerId = service.findByEmail(emailToRegister);

        if (existedCustomerId != null) {
            return new Message("Sorry, this email address is already registered, choose another.");
        } else {
            service.save(newCustomer);
            return new Message("Registration is successful");
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody Message login(@RequestBody Credentials credentials) {
        String emailToLogin = credentials.getEmail();
        Integer foundCustomerId = service.findByEmail(emailToLogin);
        if (foundCustomerId != null) {
            Customer customerToLogin = service.findById(foundCustomerId);
            return (customerToLogin.getPassword().equals(credentials.getPassword()))
                    ? new Message("Login is successful")
                    : new Message("Sorry, your username and password are incorrect - please try again.");
        } else {
            return new Message("Sorry, your username and password are incorrect - please try again.");
        }
    }

    @RequestMapping(value = "loginTwitter", method = RequestMethod.POST)
    public @ResponseBody Message loginTwitter(@RequestBody Credentials credentials) {
        String twitterKey = credentials.getPassword();
        String emailToLogin = credentials.getEmail();
        try {
            if (!twitterKey.equals(Settings.getEncryptedTwitterKey(emailToLogin)))
                return new Message("Wrong Twitter key.");
        } catch(SignatureException e){
            return new Message("Server encryption error");
        }
        Integer foundCustomerId = service.findByEmail(emailToLogin);
        if (foundCustomerId != null) {
            return new Message("Login is successful");
        } else {
            Customer newCustomer = new Customer();
            newCustomer.setEmail(emailToLogin);
            service.save(newCustomer);
            return new Message("Registration and login is successful.");
        }
    }

    @RequestMapping(value = "loginWithFacebook", method = RequestMethod.POST)
    public @ResponseBody Message loginWithFacebook(@RequestBody TransferAccessToken accessToken) {
        Facebook facebook = new FacebookTemplate(accessToken.getAccessToken(), "IntratuinMobile", "1720162671574425");
        User profile = facebook.userOperations().getUserProfile();
        String emailToLoginWithFacebook = profile.getEmail();
        if(emailToLoginWithFacebook != null) {
            Integer existedCustomerId = service.findByEmail(emailToLoginWithFacebook);
            return existedCustomerId != null
                    ? new Message("Login is successful")
                    : addWithFacebook(profile);
        }
        else {
            return new Message("to successfully login we need your email");
        }
    }

    private Message addWithFacebook(User profile) {

        Customer customer = new Customer();

        customer.setFirstName(profile.getFirstName());
        customer.setLastName(profile.getLastName());
        customer.setEmail(profile.getEmail());
        customer.setCity(profile.getLocation().getName().toString());

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
        service.save(customer);
        return new Message("Registration with Facebook is successful");
    }
}
