package nl.intratuin.testmarket.service.contract;

import nl.intratuin.testmarket.entity.Customer;
import org.json.simple.JSONObject;
import org.springframework.social.facebook.api.User;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface CustomerService {
    Customer findById(int id);

    List<Customer> findAll();

    JSONObject addCustomer(MultiValueMap<String, String> header);

    void save(Customer customer);

    JSONObject login(MultiValueMap<String, String> header);

    JSONObject loginTwitter(String email);

    JSONObject loginWithFacebook(User profile);

    Customer getCustomerByAccessKey(String accessKey);
}
