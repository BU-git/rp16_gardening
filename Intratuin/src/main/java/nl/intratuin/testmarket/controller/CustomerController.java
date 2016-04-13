package nl.intratuin.testmarket.controller;

import nl.intratuin.testmarket.dto.Credentials;
import nl.intratuin.testmarket.dto.TransferAccessToken;
import nl.intratuin.testmarket.dto.TransferMessage;
import nl.intratuin.testmarket.entity.Customer;
import nl.intratuin.testmarket.service.contract.CustomerService;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/customer/")
public class CustomerController{

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
    public @ResponseBody
    TransferMessage addCustomer(@RequestBody Customer newCustomer) {
        return service.addCustomer(newCustomer);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody
    TransferMessage login(@RequestBody Credentials credentials) {
        return service.login(credentials);
    }

    @RequestMapping(value = "loginTwitter", method = RequestMethod.POST)
    public @ResponseBody
    TransferMessage loginTwitter(@RequestBody Credentials credentials) {
        return service.loginTwitter(credentials);
    }

    @RequestMapping(value = "loginFacebook", method = RequestMethod.POST)
    public @ResponseBody
    TransferMessage loginWithFacebook(@RequestBody TransferAccessToken accessToken) {
        Facebook facebook = new FacebookTemplate(accessToken.getAccessToken(), "IntratuinMobile", "1720162671574425");
        User profile = facebook.userOperations().getUserProfile();
        return service.loginWithFacebook(profile);
    }
}

