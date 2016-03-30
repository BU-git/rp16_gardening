package nl.intratuin.testmarket.service.contract;

import nl.intratuin.testmarket.Credentials;
import nl.intratuin.testmarket.Message;
import nl.intratuin.testmarket.entity.Customer;
import org.springframework.social.facebook.api.User;

import java.util.List;

public interface CustomerService {
   Customer findById(int id);

    List<Customer> findAll();

    Message addCustomer(Customer customer);

    Message login(Credentials credentials);

    Message loginTwitter(Credentials credentials);

    Message loginWithFacebook(User profile);
}
