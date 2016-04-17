package nl.intratuin.testmarket.service.contract;

import nl.intratuin.testmarket.dto.Credentials;
import nl.intratuin.testmarket.dto.LoginAndCacheResult;
import nl.intratuin.testmarket.dto.ResultLoginWithSocial;
import nl.intratuin.testmarket.entity.Customer;
import org.springframework.social.facebook.api.User;

import java.util.List;

public interface CustomerService {
    Customer findById(int id);

    List<Customer> findAll();

    boolean addCustomer(Customer customer);

    LoginAndCacheResult login(Credentials credentials);

    ResultLoginWithSocial loginTwitter(Credentials credentials);

    ResultLoginWithSocial loginWithFacebook(User profile);

    Integer getCustomerIdByFacebookProfile(User profile);
}
