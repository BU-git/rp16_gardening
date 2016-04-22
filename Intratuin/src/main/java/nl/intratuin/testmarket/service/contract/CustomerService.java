package nl.intratuin.testmarket.service.contract;

import nl.intratuin.testmarket.dto.ResponseAccessToken;
import nl.intratuin.testmarket.entity.Customer;
import org.springframework.social.facebook.api.User;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface CustomerService {
    Customer findById(int id);

    List<Customer> findAll();

    boolean addCustomer(Customer customer);

    ResponseAccessToken login(MultiValueMap<String, String> header);

    ResponseAccessToken loginTwitter(String email);

    ResponseAccessToken loginWithFacebook(User profile);

    Integer getCustomerIdByFacebookProfile(User profile);
}
