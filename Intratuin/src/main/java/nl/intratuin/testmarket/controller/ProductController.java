package nl.intratuin.testmarket.controller;

import nl.intratuin.testmarket.entity.Product;
import nl.intratuin.testmarket.service.contract.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Иван on 27.03.2016.
 */
@RestController
@RequestMapping("/product/")
public class ProductController {

    @Inject
    ProductService service;

    @RequestMapping("all")
    public List<Product> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "{id}")
    public Product getById(@PathVariable int id) {
        return service.findById(id);
    }

    @RequestMapping(value = "search/{name}")
    public List<Product> getByName(@PathVariable String name) {
        return service.findByName(name);
    }
}
