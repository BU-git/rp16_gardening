package nl.intratuin.testmarket.service.contract;

import nl.intratuin.testmarket.entity.Product;

import java.util.List;

/**
 * Created by Иван on 27.03.2016.
 */
public interface ProductService {
    Product findById(int id);

    List<Product> findAll();

    List<Product> findByName(String name);

    List<Product> findAllByCategory(int category);
}
