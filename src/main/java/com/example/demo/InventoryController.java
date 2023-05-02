package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/views")
public class InventoryController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public ModelAndView getUsers() {
        List<Product> products = productRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("products");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

}
