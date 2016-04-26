package nl.intratuin.testmarket.controller;

import nl.intratuin.testmarket.dto.*;
import nl.intratuin.testmarket.entity.Customer;
import nl.intratuin.testmarket.service.contract.AccessKeyService;
import nl.intratuin.testmarket.service.contract.CustomerService;
import org.json.simple.JSONObject;
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
    JSONObject addCustomer(@RequestBody MultiValueMap<String, String> header) {
        return customerService.addCustomer(header);
    }

    @RequestMapping(value="info", params = {"access_token"})
    public Customer info(@RequestParam(value = "access_token") String token){
        return customerService.getCustomerByAccessKey(token);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONObject login(@RequestBody MultiValueMap<String, String> header) {
        return customerService.login(header);
    }

    @RequestMapping(value = "loginTwitter", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONObject loginTwitter(@RequestBody MultiValueMap<String, String> header) {
        if(!header.containsKey("twitter_token")){
            JSONObject json=new JSONObject();
            json.put("code","400");
            json.put("error","invalid_request");
            json.put("error_description","No twitter access token found");
            return json;
        }
        if(!header.containsKey("twitter_secret")){
            JSONObject json=new JSONObject();
            json.put("code","400");
            json.put("error","invalid_request");
            json.put("error_description","No twitter token secret found");
            return json;
        }
        TwitterTemplate twitterTemplate=new TwitterTemplate(prop.getProperty("twitter.consumerKey"),prop.getProperty("twitter.consumerSecret"),
                header.getFirst("twitter_token"),header.getFirst("twitter_secret"));
        RestTemplate restTemplate = twitterTemplate.getRestTemplate();

        String email;
        try {
            String response = restTemplate.getForObject("https://api.twitter.com/1.1/account/verify_credentials.json?include_email=true", String.class);
            email = response.substring(response.indexOf("\"email\":") + 9, response.lastIndexOf("\""));
        } catch(Exception e){
            JSONObject json=new JSONObject();
            json.put("code","400");
            json.put("error","invalid_request");
            json.put("error_description","Can't get email from twitter");
            return json;
        }
        return customerService.loginTwitter(email);
    }

    @RequestMapping(value = "loginFacebook", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONObject loginWithFacebook(@RequestBody MultiValueMap<String, String> header) {
        if(!header.containsKey("facebook_token")){
            JSONObject json=new JSONObject();
            json.put("code","400");
            json.put("error","invalid_request");
            json.put("error_description","No facebook access token found");
            return json;
        }
        Facebook facebook = new FacebookTemplate(header.getFirst("facebook_token"), "IntratuinMobile", prop.getProperty("facebook.appId"));
        User profile = facebook.userOperations().getUserProfile();
        return customerService.loginWithFacebook(profile);
    }
}

