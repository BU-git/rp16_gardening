package nl.intratuin.testmarket.service.contract;

import nl.intratuin.testmarket.entity.Customer;

import java.util.List;

public interface CustomerService {
   Customer findById(int id);

    List<Customer> findAll();

    void save(Customer customer);

    Integer findByEmail(String email);
}
