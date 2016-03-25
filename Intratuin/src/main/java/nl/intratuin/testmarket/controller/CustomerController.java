package nl.intratuin.testmarket.controller;

import nl.intratuin.testmarket.Credentials;
import nl.intratuin.testmarket.Settings;
import nl.intratuin.testmarket.TwitterLogin;
import nl.intratuin.testmarket.service.contract.CustomerService;
import nl.intratuin.testmarket.Message;
import nl.intratuin.testmarket.entity.Customer;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
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
    public @ResponseBody Message loginTwitter(@RequestBody TwitterLogin twitterLogin) {
        String twitterKey = twitterLogin.getKey();
        if(!twitterKey.equals(Settings.getTwitterKey()))
            return new Message("Wrong Twitter key.");
        String emailToLogin = twitterLogin.getEmail();
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
}
