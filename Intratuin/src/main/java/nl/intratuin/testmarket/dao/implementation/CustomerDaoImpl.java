package nl.intratuin.testmarket.dao.implementation;

import nl.intratuin.testmarket.dao.contract.CustomerDao;
import nl.intratuin.testmarket.dto.DTOEmail;
import nl.intratuin.testmarket.dto.DTOPassword;
import nl.intratuin.testmarket.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao {
    @PersistenceContext
    private EntityManager em;

    public Customer findById(int id) {
        return em.find(Customer.class, id);
    }

    public List<Customer> findAll() {
        TypedQuery<Customer> queryFindAllCustomers = em.createQuery("SELECT C FROM Customer C", Customer.class);
        return queryFindAllCustomers.getResultList();
    }

    public void save(Customer customer) {
        em.persist(customer);
    }

    public Integer findByEmail(String email) {
        email = email.toLowerCase();
        TypedQuery<Integer> queryFindByEmail = em.createQuery("SELECT c.id FROM Customer c WHERE c.email = :email", Integer.class);
        queryFindByEmail.setParameter("email", email);
        List<Integer> foundCustomerIds = queryFindByEmail.getResultList();
        return (foundCustomerIds.size() == 0) ? null : foundCustomerIds.get(0);
    }

    public boolean updateCustomer(Customer customer) {
        String query = "UPDATE Customer c " +
                "SET c.firstName = :firstName, c.lastName = :lastName, c.phoneNumber = :phoneNumber, " +
                "c.birthday = :birthday, c.city = :city, c.streetName = :streetName, " +
                "c.houseNumber = :houseNumber, c.postalCode = :postalCode, c.gender = :gender ";
        query += "WHERE c.id = :id";

        TypedQuery<Customer> queryUpdatePersonalInfo = em.createQuery(query, Customer.class);
        queryUpdatePersonalInfo.setParameter("firstName", customer.getFirstName());
        queryUpdatePersonalInfo.setParameter("lastName", customer.getLastName());
        queryUpdatePersonalInfo.setParameter("phoneNumber", customer.getPhoneNumber());
        queryUpdatePersonalInfo.setParameter("birthday", customer.getBirthday());
        queryUpdatePersonalInfo.setParameter("city", customer.getCity());
        queryUpdatePersonalInfo.setParameter("streetName", customer.getStreetName());
        queryUpdatePersonalInfo.setParameter("houseNumber", customer.getHouseNumber());
        queryUpdatePersonalInfo.setParameter("postalCode", customer.getPostalCode());
        queryUpdatePersonalInfo.setParameter("gender", customer.getGender());
        queryUpdatePersonalInfo.setParameter("id", customer.getId());

        return queryUpdatePersonalInfo.executeUpdate() > 0
                ? true
                : false;
    }

    public boolean updateEmail(DTOEmail dtoNewEmail) {
        TypedQuery<Customer> queryUpdateEmail = em.createQuery("UPDATE Customer c SET c.email = :email WHERE c.id = :id", Customer.class);
        queryUpdateEmail.setParameter("email", dtoNewEmail.getEmail());
        queryUpdateEmail.setParameter("id", dtoNewEmail.getId());

        return queryUpdateEmail.executeUpdate() > 0
                ? true
                : false;
    }


    public boolean updatePassword(DTOPassword dtoNewPassword) {
        TypedQuery<Customer> queryUpdatePassword = em.createQuery("UPDATE Customer c SET c.password = :password WHERE c.id = :id", Customer.class);
        queryUpdatePassword.setParameter("password", dtoNewPassword.getPassword());
        queryUpdatePassword.setParameter("id", dtoNewPassword.getId());

        return queryUpdatePassword.executeUpdate() > 0
                ? true
                : false;
    }

}

