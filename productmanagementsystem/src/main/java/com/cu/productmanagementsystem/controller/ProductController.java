package com.cu.productmanagementsystem.controller;

import com.cu.productmanagementsystem.model.Product;
import com.cu.productmanagementsystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public String addProduct(@RequestBody Product product) throws ExecutionException, InterruptedException {
        return productService.addProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() throws ExecutionException, InterruptedException {
        return productService.getAllProducts();
    }

    @GetMapping("/{pid}")
    public Product getProductById(@PathVariable String pid) throws ExecutionException, InterruptedException {
        return productService.getProductById(pid);
    }

    @PutMapping("/{pid}")
    public String updateProduct(@PathVariable String pid, @RequestBody Product product) throws ExecutionException, InterruptedException {
        return productService.updateProduct(pid, product);
    }

    @DeleteMapping("/{pid}")
    public String deleteProduct(@PathVariable String pid) throws ExecutionException, InterruptedException {
        return productService.deleteProduct(pid);
    }
}
