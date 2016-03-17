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
    public String add(@RequestBody Customer newCustomer) {
        //Check whether is email already registered or not
        String emailToRegister = newCustomer.getEmail();
        Integer existedCustomerId = service.findByEmail(emailToRegister);

        if (existedCustomerId != null) {
            return Message.ERROR_REGISTRATION.getMessage();
        } else {
            service.save(newCustomer);
            return Message.SUCCESS_REGISTRATION.getMessage();
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody Credentials credentials) {
        String emailToLogin = credentials.getEmail();
        Integer foundCustomerId = service.findByEmail(emailToLogin);

        if (foundCustomerId != null) {
            Customer customerToLogin = service.findById(foundCustomerId);
            return (customerToLogin.getPassword().equals(credentials.getPassword()))
                    ? Message.SUCCESS_LOGIN.getMessage()
                    : Message.ERROR_LOGIN.getMessage();
        } else {
            return Message.ERROR_LOGIN.getMessage();
        }
    }
}
