package nl.intratuin.testmarket.dao.contract;

import nl.intratuin.testmarket.entity.Product;

import java.util.List;

public interface ProductDao {
    Product findById(int id);

    List<Product> findAll();

    List<Product> findByName(String name);

    List<Product> findAllByCategory(int category);
}
