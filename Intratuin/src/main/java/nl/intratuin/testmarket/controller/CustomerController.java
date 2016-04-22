package nl.intratuin.testmarket.controller;

import nl.intratuin.testmarket.dto.*;
import nl.intratuin.testmarket.entity.Customer;
import nl.intratuin.testmarket.service.contract.AccessKeyService;
import nl.intratuin.testmarket.service.contract.CustomerService;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/customer/")
public class CustomerController {
    Properties prop;

    @Inject
    CustomerService customerService;

    @Inject
    AccessKeyService service;

    public CustomerController(){
        prop = new Properties();
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("all")
    public List<Customer> getAll() {
        return customerService.findAll();
    }

    @RequestMapping("time")
    public String test() {
        return LocalDateTime.now().toString();
    }

    @RequestMapping(value = "{id}")
    public Customer getById(@PathVariable int id) {
        return customerService.findById(id);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public
    @ResponseBody
    TransferMessage addCustomer(@RequestBody Customer newCustomer) {
        return customerService.addCustomer(newCustomer)
                ? new TransferMessage("Registration is successful")
                : new TransferMessage("Sorry, this email address is already registered, choose another.");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseAccessToken login(@RequestBody MultiValueMap<String, String> header) {
        return customerService.login(header);
    }

    @RequestMapping(value = "loginTwitter", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseAccessToken loginTwitter(@RequestBody Credentials credentials) {
        TwitterTemplate twitterTemplate=new TwitterTemplate(prop.getProperty("twitter.consumerKey"),prop.getProperty("twitter.consumerSecret"),
                credentials.getEmail(),credentials.getPassword());
        RestTemplate restTemplate = twitterTemplate.getRestTemplate();

        String email;
        try {
            String response = restTemplate.getForObject("https://api.twitter.com/1.1/account/verify_credentials.json?include_email=true", String.class);
            email = response.substring(response.indexOf("\"email\":") + 9, response.lastIndexOf("\""));
        } catch(Exception e){
            ResponseAccessToken token=new ResponseAccessToken();
            token.setToken_type("error");
            token.setAccess_token("Twitter error.");
            return token;
        }
        return customerService.loginTwitter(email);
    }

    @RequestMapping(value = "loginFacebook", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseAccessToken loginWithFacebook(@RequestBody TransferAccessToken accessToken) {
        Facebook facebook = new FacebookTemplate(accessToken.getAccessToken(), "IntratuinMobile", prop.getProperty("facebook.appId"));
        User profile = facebook.userOperations().getUserProfile();
        return customerService.loginWithFacebook(profile);
    }

    @RequestMapping(value = "confirmCredentialsAccessKey/{accessKey}")
    public LoginAndCacheResult checkCredentialsAccessKey(@PathVariable String accessKey) {
        Customer customer;
        Integer foundCustomerIdByAccessKey = service.getCustomerIdByAccessKey(accessKey);
        if(foundCustomerIdByAccessKey != null) {
            if(foundCustomerIdByAccessKey != -1) {
                customer = customerService.findById(foundCustomerIdByAccessKey);
                return new LoginAndCacheResult("Hello, " + customer.getFirstName() + " " + customer.getLastName(),
                        accessKey);
            } else {
                return new LoginAndCacheResult("access key is expired", null);
            }} else {
            return new LoginAndCacheResult("Invalid access key", null);
            }
    }

    @RequestMapping(value = "confirmFacebookAccessKey/{accessToken}")
    public LoginAndCacheResult checkFacebookAccessToken(@PathVariable String accessToken) {
        Facebook facebook = new FacebookTemplate(accessToken, "IntratuinMobile", "1720162671574425");
        User profile = facebook.userOperations().getUserProfile();
        Integer existedCustomerId = customerService.getCustomerIdByFacebookProfile(profile);
        if(existedCustomerId != null) {
            if (existedCustomerId > 0){
                Customer customer = customerService.findById(existedCustomerId);
                return new LoginAndCacheResult("Hello, " + customer.getFirstName() + " " + customer.getLastName(),
                        accessToken);
            } else {
                return new LoginAndCacheResult("Invalid access token", null);
            }
        } else {
            return new LoginAndCacheResult("to successfully login we need your email", null);
        }
    }
//
//    @RequestMapping(value = "checkTwitterAccessToken/{accesstoken}")
//    public List<TransferAccessKey> checkTwitterAccessToken(@PathVariable String accessToken) {
//        return service.findAllByCategory(idCategory);
//    }
}

