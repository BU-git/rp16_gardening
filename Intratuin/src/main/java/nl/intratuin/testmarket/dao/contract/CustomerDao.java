package nl.intratuin.testmarket.dao.contract;

import nl.intratuin.testmarket.dto.DTOEmail;
import nl.intratuin.testmarket.dto.DTOFingerprint;
import nl.intratuin.testmarket.dto.DTOPassword;
import nl.intratuin.testmarket.entity.Customer;

import java.util.List;

public interface CustomerDao {
    Customer findById(int id);

    List<Customer> findAll();

    void save(Customer customer);

    Integer findByEmail(String email);

    boolean updateCustomer(Customer customer);

    boolean updateEmail(DTOEmail dtoNewEmail);

    boolean updatePassword(DTOPassword dtoNewPassword);

    boolean saveFingerprint(DTOFingerprint dtoFingerprint);
}
