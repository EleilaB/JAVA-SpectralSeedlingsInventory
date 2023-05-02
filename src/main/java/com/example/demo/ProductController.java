package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    ProductController(CategoryRepository categoryRepository, ProductRepository productRepository){
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/hello")
    public List<Product> hello() {
        return productRepository.findAll();
    }

    @PostMapping("/createProduct")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {
        // Map the request body to a new Product entity
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException(("Category not found")));
        product.setCategory(category);

        // Save the new Product entity to the database
        Product savedProduct = productRepository.save(product);

        // Return a ResponseEntity with the saved Product entity and a 201 CREATED status
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/users")
    public ModelAndView getUsers() {
        List<Users> users = userRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", users);
        return modelAndView;
    }



}