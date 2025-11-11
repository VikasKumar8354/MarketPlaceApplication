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

    @GetMapping("/list")
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

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestHeader("X-User-Id") Long actingUserId,
                                    @RequestBody CreateProductDto dto) {
        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
        }
        Product product = Product.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .imageUrl(dto.getImageUrl())
                .category(category)
                .build();

        Product created = productService.createProduct(actingUserId, product);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
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
            Category category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
            updated.setCategory(category);
        }
        Product product = productService.updateProduct(actingUserId, id, updated);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader("X-User-Id") Long actingUserId,
                                    @PathVariable Long id) {
        productService.deleteProduct(actingUserId, id);
        return ResponseEntity.ok().build();
    }
}
