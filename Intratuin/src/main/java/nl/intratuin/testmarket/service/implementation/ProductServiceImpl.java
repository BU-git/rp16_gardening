package nl.intratuin.testmarket.service.implementation;

import nl.intratuin.testmarket.dao.contract.ProductDao;
import nl.intratuin.testmarket.entity.Product;
import nl.intratuin.testmarket.service.contract.ProductService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by Иван on 27.03.2016.
 */
@Named
public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductDao productDao;

    @Override
    public Product findById(int id) {
        return productDao.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public List<Product> findByName(String name) {
        return productDao.findByName(name);
    }

    @Override
    public List<Product> findAllByCategory(int category) {
        return productDao.findAllByCategory(category);
    }

    @Override
    public Product findByBarcode(long barcode) {
        return productDao.findByBarcode(barcode);
    }
}
