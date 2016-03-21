package com.intratuin.testmarket.controller;

import com.intratuin.testmarket.Credentials;
import com.intratuin.testmarket.service.contract.CustomerService;
import com.intratuin.testmarket.Message;
import com.intratuin.testmarket.entity.Customer;
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
        String emailToRegister = newCustomer.getEmail();
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
}
