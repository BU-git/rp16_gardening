package nl.intratuin.testmarket.service.implementation;

import nl.intratuin.testmarket.Credentials;
import nl.intratuin.testmarket.Message;
import nl.intratuin.testmarket.Settings;
import nl.intratuin.testmarket.TransferAccessToken;
import nl.intratuin.testmarket.dao.contract.CustomerDao;
import nl.intratuin.testmarket.service.contract.CustomerService;
import nl.intratuin.testmarket.entity.Customer;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.inject.Named;
import java.security.SignatureException;
import java.time.LocalDate;
import java.util.List;

@Named
public class CustomerServiceImpl implements CustomerService {
    @Inject
    private CustomerDao customerDao;

    public Customer findById(int id) {
        return customerDao.findById(id);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Transactional
    public void save(Customer customer) {
        customer.setEmail(customer.getEmail().toLowerCase());
        customerDao.save(customer);
    }

    public Integer findByEmail(String email) {
        return customerDao.findByEmail(email.toLowerCase());
    }

    public Message addCustomer(Customer customer) {
        //Check whether is email already registered or not
        String emailToRegister = customer.getEmail().toLowerCase();
        Integer existedCustomerId = customerDao.findByEmail(emailToRegister);

        if (existedCustomerId != null) {
            return new Message("Sorry, this email address is already registered, choose another.");
        } else {
            customerDao.save(customer);
            return new Message("Registration is successful");
        }
    }

    @Override
    public Message login(Credentials credentials) {
        String emailToLogin = credentials.getEmail();
        Integer foundCustomerId = customerDao.findByEmail(emailToLogin);
        if (foundCustomerId != null) {
            Customer customerToLogin = customerDao.findById(foundCustomerId);
            return (customerToLogin.getPassword().equals(credentials.getPassword()))
                    ? new Message("Login is successful")
                    : new Message("Sorry, your username and password are incorrect - please try again.");
        } else {
            return new Message("Sorry, your username and password are incorrect - please try again.");
        }
    }

}
