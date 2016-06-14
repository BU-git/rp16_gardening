package nl.intratuin.testmarket.controller;

import nl.intratuin.testmarket.entity.Product;
import nl.intratuin.testmarket.service.contract.ProductService;
import org.json.simple.JSONObject;
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
    public JSONObject getByBarcode(@PathVariable String code){
        JSONObject jsonObject = new JSONObject();
        long barcode=Long.parseLong(code);
        Product findProductByBarcode = productService.findByBarcode(barcode);
        if(findProductByBarcode != null) {
            jsonObject.put("productId",""+findProductByBarcode.getProductId());
            jsonObject.put("categoryId",""+findProductByBarcode.getCategoryId());
            jsonObject.put("barcode",""+findProductByBarcode.getBarcode());
            jsonObject.put("productName", findProductByBarcode.getProductName());
            jsonObject.put("productPrice",""+findProductByBarcode.getProductPrice());
            jsonObject.put("productImage", findProductByBarcode.getProductImage());
            return jsonObject;
        } else {
            jsonObject.put("code", "303");
            jsonObject.put("error_description", "There isn't such product");
            return jsonObject;
        }

    }
}
