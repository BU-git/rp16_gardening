package nl.intratuin.testmarket.controller;

import nl.intratuin.testmarket.entity.Category;
import nl.intratuin.testmarket.entity.Product;
import nl.intratuin.testmarket.service.contract.CategoryService;
import nl.intratuin.testmarket.service.contract.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category/")
public class CategoryController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("all")
    public List<Category> getAllRootCategories() {
        return categoryService.findAllRootCategories();
    }
    @RequestMapping(value = "{id}/product")
    public List<Product> getByCategoryId(@PathVariable int id) {
        return productService.findAllByCategory(id);
    }

    @RequestMapping(value = "{id}")
    public List<Category> getCategoryById(@PathVariable int id) {
        return categoryService.findById(id);
    }

    @RequestMapping(value = "{id}/child")
    public List<Category> getAllChildCategories(@PathVariable int id) {
        return categoryService.findChildCategories(id);
    }

}
