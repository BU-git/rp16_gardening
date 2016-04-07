package nl.intratuin.testmarket.controller;

import nl.intratuin.testmarket.dto.Credentials;
import nl.intratuin.testmarket.dto.RecoveryRecord;
import nl.intratuin.testmarket.dto.TransferAccessToken;
import nl.intratuin.testmarket.dto.TransferMessage;
import nl.intratuin.testmarket.service.contract.CustomerService;
import nl.intratuin.testmarket.entity.Customer;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/customer/")
public class CustomerController{
    public static List<RecoveryRecord> recoveryRecords=null;

    @Inject
    CustomerService service;

    Properties fMailServerConfig = new Properties();

    public CustomerController(){
        Path path = Paths.get("mail.txt");
        try (InputStream input = Files.newInputStream(path)) {
            fMailServerConfig.load(input);
        }
        catch (IOException ex){
            System.err.println("Cannot open and load mail server properties file.");
        }
    }

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

    @RequestMapping(value = "forgot", method = RequestMethod.POST)
    public @ResponseBody
    TransferMessage forgot(@RequestBody String email) {
        return service.forgot(email, fMailServerConfig);
    }

    @RequestMapping(value = "recovery/{link}", method = RequestMethod.GET)
    public @ResponseBody
    void recovery(@PathVariable String link) {
        service.recovery(link, fMailServerConfig);
    }
}

