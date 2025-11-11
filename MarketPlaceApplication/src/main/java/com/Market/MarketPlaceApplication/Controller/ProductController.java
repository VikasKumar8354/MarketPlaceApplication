package com.Market.MarketPlaceApplication.Controller;

import com.Market.MarketPlaceApplication.DTOs.CreateProductDto;
import com.Market.MarketPlaceApplication.Model.Category;
import com.Market.MarketPlaceApplication.Model.Product;
import com.Market.MarketPlaceApplication.Repository.CategoryRepository;
import com.Market.MarketPlaceApplication.Repository.UserRepository;
import com.Market.MarketPlaceApplication.Service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ProductController(ProductService productService, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Page<Product> list(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        return productService.listAll(PageRequest.of(page, size));
    }

    @GetMapping("/vendor/{vendorId}")
    public Page<Product> listByVendor(@PathVariable Long vendorId,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return productService.findByVendor(vendorId, PageRequest.of(page, size));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader("X-User-Id") Long actingUserId,
                                    @RequestBody CreateProductDto dto) {
        Category cat = null;
        if (dto.getCategoryId() != null) {
            cat = categoryRepository.findById(dto.getCategoryId()).orElse(null);
        }
        Product p = Product.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .imageUrl(dto.getImageUrl())
                .category(cat)
                .build();

        Product created = productService.createProduct(actingUserId, p);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestHeader("X-User-Id") Long actingUserId,
                                    @PathVariable Long id,
                                    @RequestBody CreateProductDto dto) {
        Product updated = Product.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .imageUrl(dto.getImageUrl())
                .build();
        if (dto.getCategoryId() != null) {
            Category cat = categoryRepository.findById(dto.getCategoryId()).orElse(null);
            updated.setCategory(cat);
        }
        Product res = productService.updateProduct(actingUserId, id, updated);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader("X-User-Id") Long actingUserId,
                                    @PathVariable Long id) {
        productService.deleteProduct(actingUserId, id);
        return ResponseEntity.ok().build();
    }
}
