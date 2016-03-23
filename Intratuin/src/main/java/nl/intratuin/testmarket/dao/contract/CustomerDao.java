package nl.intratuin.testmarket.dao.contract;

import nl.intratuin.testmarket.entity.Customer;

import java.util.List;

public interface CustomerDao {
    Customer findById(int id);

    List<Customer> findAll();

    void save(Customer customer);

    Integer findByEmail(String email);
}
