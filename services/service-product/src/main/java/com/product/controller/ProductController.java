package com.product.controller;

import com.product.service.ProductService;
import common.Result;
import org.springframework.web.bind.annotation.*;
import product.dto.ProductDTO;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @GetMapping("/getPort")
    public Result<Integer> getPort() {
        return Result.ok(productService.getPort());
    }

    @GetMapping("/getProductById/{id}")
    public Result<ProductDTO> getProductById(@PathVariable Integer id) {
        return Result.ok(productService.getProductById(id));
    }

    @GetMapping("/getProductByName")
    public Result<ProductDTO> getProductByName(@RequestParam String name) {
        return Result.ok(productService.getProductByName(name));
    }

    @PutMapping("/updateStock")
    public Result<Boolean> updateStock(@RequestParam Integer id,
                                       @RequestParam Integer stock) {
        return Result.ok(productService.updateStock(id, stock));
    }

    @PostMapping("/createProduct")
    public Result<Boolean> createProduct(@RequestBody ProductDTO product) {
        return Result.ok(productService.createProduct(product));
    }
}
