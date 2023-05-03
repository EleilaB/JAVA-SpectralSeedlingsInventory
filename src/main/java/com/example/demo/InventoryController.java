package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/views")
public class InventoryController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/products")
    public ModelAndView getProducts(@RequestParam(required = false) Long categoryId) {

        List<Category> categories = categoryRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("Products");
        modelAndView.addObject("categories", categories);

        if(categoryId == null){
            return modelAndView;
        }

        Optional<Category> category = categoryRepository.findById(categoryId);
        if(!category.isPresent()){
            return modelAndView;
        }

        List<Product> products = productRepository.findByCategory(category.get());
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/create-category")
    public ModelAndView getCreateCategory(){
        ModelAndView createCategory = new ModelAndView("CreateCategory");
        return createCategory;
    }

    @PostMapping("/create-category")
    public String createCategory(Category category){
        Category newCategory = categoryRepository.save(category);
        return "redirect:/views/products?categoryId=" + newCategory.getCategory_id();
    }

}
