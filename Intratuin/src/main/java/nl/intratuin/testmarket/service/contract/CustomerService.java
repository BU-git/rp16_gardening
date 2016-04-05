package nl.intratuin.testmarket.service.contract;

import nl.intratuin.testmarket.dto.Credentials;
import nl.intratuin.testmarket.dto.TransferMessage;
import nl.intratuin.testmarket.entity.Customer;
import org.springframework.social.facebook.api.User;

import java.util.List;

public interface CustomerService {
   Customer findById(int id);

    List<Customer> findAll();

    TransferMessage addCustomer(Customer customer);

    TransferMessage login(Credentials credentials);

    TransferMessage loginTwitter(Credentials credentials);

    TransferMessage loginWithFacebook(User profile);
}
