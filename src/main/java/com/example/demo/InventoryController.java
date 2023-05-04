package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/create-product")
    public ModelAndView getCreateProduct(){
        ModelAndView createProduct = new ModelAndView("CreateProduct");
        List<Category> categories = categoryRepository.findAll();
        createProduct.addObject("categories", categories);
        return createProduct;
    }

    @PostMapping("/create-product")
    public String createProduct(@ModelAttribute("product") Product product){
        Product newProduct = productRepository.save(product);
        Category category = newProduct.getCategory();
        return "redirect:/views/products?categoryId=" + category.getCategory_id();
    }

    @PostMapping("/delete-product")
    public String deleteProduct(Product productId){
        Optional<Product> product = productRepository.findById(productId.getProduct_id());
        if(!product.isPresent()){
            return "redirect:/views/products";
        }
        Category category = product.get().getCategory();
        productRepository.delete(productId);
        return "redirect:/views/products?categoryId=" + category.getCategory_id();
    }

    @PostMapping("/delete-category")
    public String deleteCategory(Category category){
        List<Product> products = productRepository.findByCategory(category);
        productRepository.deleteAll(products);
        categoryRepository.delete(category);
        return "redirect:/views/products";
    }

}
