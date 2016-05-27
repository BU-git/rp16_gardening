package nl.intratuin.testmarket.controller;

import nl.intratuin.testmarket.dto.DTOEmail;
import nl.intratuin.testmarket.dto.DTOPassword;
import nl.intratuin.testmarket.entity.Customer;
import nl.intratuin.testmarket.entity.Product;
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
import java.sql.Date;
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
    public String time() {
        return LocalDateTime.now().toString();
    }

    @RequestMapping(value = "{id}")
    public Customer getById(@PathVariable int id) {
        return customerService.findById(id);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONObject add(@RequestBody String header) {
        return customerService.addCustomer(header);
    }

    @RequestMapping(value="info", params = {"access_token"})
    public String info(@RequestParam(value = "access_token") String token){
        Customer c=customerService.getCustomerByAccessKey(token);
        if(c!=null) {
            JSONObject r = new JSONObject();
            r.put("id",""+c.getId());
            String name=getCustomerName(c);
            r.put("name",name);
            r.put("email",c.getEmail());
            Date birthday=c.getBirthday();
            if(birthday!=null)
                r.put("birthday",c.getBirthday().toString());
            r.put("gender",c.getGender());
            r.put("phoneNumber",c.getPhoneNumber());
            r.put("city",c.getCity());
            r.put("postalCode",c.getPostalCode());
            r.put("streetName",c.getStreetName());
            r.put("houseNumber",c.getHouseNumber());
            return "["+r.toJSONString()+"]";
        }else{
            JSONObject e=new JSONObject();
            e.put("code","403");
            e.put("error","forbidden");
            e.put("error_description","Token you provided is invalid or deprecated");
            return "["+e.toJSONString()+"]";
        }
    }

    private String getCustomerName(Customer c){
        String res="";
        if(c.getFirstName()!=null && c.getFirstName().length()>0) {
            res += c.getFirstName();
            if((c.getTussen()!=null && c.getTussen().length()>0) || (c.getLastName()!=null && c.getLastName().length()>0))
                res+=" ";
        }
        if(c.getTussen()!=null && c.getTussen().length()>0){
            res += c.getTussen();
            if(c.getLastName()!=null && c.getLastName().length()>0)
                res += " ";
        }
        if(c.getLastName()!=null && c.getLastName().length()>0)
            res += c.getLastName();
        return res;
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

    @RequestMapping(value = "access_token/{token}")
    public Customer getByName(@PathVariable String token) {
        return customerService.getCustomerByAccessKey(token);
    }

        @RequestMapping(value = "update/personal", method = RequestMethod.POST)
    public
    @ResponseBody
    Boolean updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @RequestMapping(value = "update/email", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONObject updateCustomer(@RequestBody DTOEmail dtoNewEmail) {
        return customerService.updateEmail(dtoNewEmail);
    }


    @RequestMapping(value = "update/password", method = RequestMethod.POST)
    public
    @ResponseBody
    Boolean updateCustomer(@RequestBody DTOPassword dtoNewPassword) {
        return customerService.updatePassword(dtoNewPassword);
    }
}

