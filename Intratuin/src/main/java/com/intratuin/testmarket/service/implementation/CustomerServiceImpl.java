package com.intratuin.testmarket.service.implementation;

import com.intratuin.testmarket.dao.contract.CustomerDao;
import com.intratuin.testmarket.service.contract.CustomerService;
import com.intratuin.testmarket.entity.Customer;
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
        customerDao.save(customer);
    }

    public Integer findByEmail(String email) {
        return customerDao.findByEmail(email);
    }
}
