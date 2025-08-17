package com.example.SpringSecurityRole;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @GetMapping("/my/{id}")
    public Product getMyProduct(@PathVariable int id) {
        return productService.getMyProduct(id);
    }

}
