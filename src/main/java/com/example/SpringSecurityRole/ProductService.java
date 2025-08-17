package com.example.SpringSecurityRole;


import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = List.of(
            new Product(1, "Laptop", "user"),
            new Product(2, "Mobile", "manager"),
            new Product(3, "Tablet", "admin")
    );

    // @Secured -> sirf ADMIN role access kar sakta hai
    @Secured("ROLE_ADMIN")
    public List<Product> getAllProducts() {
        return products;
    }

    // @PreAuthorize -> method call se pehle check hota hai
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public Product getProductById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    // @PostAuthorize -> method chalne ke baad result filter hota hai
    @PostAuthorize("returnObject.owner == authentication.name or hasRole('ADMIN')")
    public Product getMyProduct(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }
}
