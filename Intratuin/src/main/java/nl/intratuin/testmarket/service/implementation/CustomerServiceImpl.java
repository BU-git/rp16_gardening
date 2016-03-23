package nl.intratuin.testmarket.service.implementation;

import nl.intratuin.testmarket.dao.contract.CustomerDao;
import nl.intratuin.testmarket.service.contract.CustomerService;
import nl.intratuin.testmarket.entity.Customer;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
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
}
