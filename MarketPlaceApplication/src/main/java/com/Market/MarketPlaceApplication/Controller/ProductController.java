package com.Market.MarketPlaceApplication.Controller;

import com.Market.MarketPlaceApplication.Model.Product;
import com.Market.MarketPlaceApplication.Repository.CategoryRepository;
import com.Market.MarketPlaceApplication.Service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/list")
    public Page<Product> list(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        return productService.listAll(page, size);
    }

    @PostMapping("/create")
    public Product create(@RequestBody Product product) {
        return productService.save(product); }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id); }
}
