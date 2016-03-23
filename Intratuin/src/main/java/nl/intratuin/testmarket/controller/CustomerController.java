package nl.intratuin.testmarket.controller;

import nl.intratuin.testmarket.Credentials;
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
    public @ResponseBody Message addCustomer(@RequestBody Customer newCustomer) {
        return service.addCustomer(newCustomer);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody Message login(@RequestBody Credentials credentials) {
        return service.login(credentials);
    }

    @RequestMapping(value = "faceBookLogin", method = RequestMethod.POST)
    public @ResponseBody Message faceBookLogin(@RequestBody Credentials credentials){
        return new Message("Mock for FB login");
    }

    @RequestMapping(value = "twitterLogin", method = RequestMethod.POST)
    public @ResponseBody Message twitterLogin(@RequestBody Credentials credentials){
        return new Message("Mock for Twitter login");
    }

}
