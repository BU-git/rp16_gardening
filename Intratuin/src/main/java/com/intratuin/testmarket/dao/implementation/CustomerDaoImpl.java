package com.intratuin.testmarket.dao.implementation;

import com.intratuin.testmarket.dao.contract.CustomerDao;
import com.intratuin.testmarket.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        TypedQuery<Integer> queryFindByEmail = em.createQuery("SELECT c.id FROM Customer c WHERE c.email = :email", Integer.class);
        queryFindByEmail.setParameter("email", email);
        List<Integer> foundCustomerIds = queryFindByEmail.getResultList();
        return (foundCustomerIds.size() == 0) ? null : foundCustomerIds.get(0);
    }

}

