package nl.intratuin.testmarket.controller;

import nl.intratuin.testmarket.entity.Product;
import nl.intratuin.testmarket.service.contract.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Иван on 27.03.2016.
 */
@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping("all")
    public List<Product> getAll() {
        return productService.findAll();
    }

    @RequestMapping(value = "{id}")
    public Product getById(@PathVariable int id) {
        return productService.findById(id);
    }

    @RequestMapping(value = "search/{name}")
    public List<Product> getByName(@PathVariable String name) {
        return productService.findByName(name);
    }

    @RequestMapping(value = "list/byCategory/{idCategory}")
    public List<Product> getAllByCategory(@PathVariable int idCategory) {
        return productService.findAllByCategory(idCategory);
    }

    @RequestMapping(value = "barcode/{code}")
    public int getByBarcode(@PathVariable String code){
        long barcode=Long.parseLong(code);
        return productService.findByBarcode(barcode);
    }
}
