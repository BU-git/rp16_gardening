package nl.intratuin.testmarket.controller;

import nl.intratuin.testmarket.entity.Category;
import nl.intratuin.testmarket.entity.Product;
import nl.intratuin.testmarket.service.contract.CategoryService;
import nl.intratuin.testmarket.service.contract.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
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
    public List<Product> getAllByCategory(@PathVariable String idCategory) {
        int id = Integer.parseInt(idCategory);
        return productService.findAllByCategory(id);
    }
}
