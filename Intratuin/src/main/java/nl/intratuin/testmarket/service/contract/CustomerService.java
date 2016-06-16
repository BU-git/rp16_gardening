package nl.intratuin.testmarket.service.contract;

import nl.intratuin.testmarket.dto.DTOEmail;
import nl.intratuin.testmarket.dto.DTOFingerprint;
import nl.intratuin.testmarket.dto.DTOPassword;
import nl.intratuin.testmarket.entity.Customer;
import org.json.simple.JSONObject;
import org.springframework.social.facebook.api.User;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface CustomerService {
    Customer findById(int id);

    List<Customer> findAll();

    JSONObject addCustomer(String header);

    void save(Customer customer);

    JSONObject login(MultiValueMap<String, String> header);

    JSONObject loginTwitter(String email);

    JSONObject loginWithFacebook(User profile);

    Customer getCustomerByAccessKey(String accessKey);

    boolean updateCustomer(Customer customer);

    JSONObject updateEmail(DTOEmail dtoNewEmail);

    boolean updatePassword(DTOPassword dtoNewPassword);

    boolean saveFingerprint(DTOFingerprint dtoFingerprint);
}
