package com.Market.MarketPlaceApplication.Service;

import com.Market.MarketPlaceApplication.Model.Product;
import com.Market.MarketPlaceApplication.Model.Role;
import com.Market.MarketPlaceApplication.Model.User;
import com.Market.MarketPlaceApplication.Repository.CategoryRepository;
import com.Market.MarketPlaceApplication.Repository.ProductRepository;
import com.Market.MarketPlaceApplication.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Product createProduct(Long actingUserId, Product p) {
        User actor = userRepository.findById(actingUserId).orElseThrow();
        if (actor.getRole() != Role.VENDOR && actor.getRole() != Role.ADMIN && actor.getRole() != Role.SUPER_ADMIN) {
            throw new RuntimeException("Only vendors or admins can create products");
        }
        // vendor must be verified before creating products
        if (actor.getRole() == Role.VENDOR && !actor.isVendorVerified()) {
            throw new RuntimeException("Vendor not verified");
        }
        p.setVendor(actor.getRole() == Role.VENDOR ? actor : p.getVendor());
        return productRepository.save(p);
    }

    public Page<Product> listAll(Pageable page) {
        return productRepository.findAll(page);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Page<Product> findByVendor(Long vendorId, Pageable page) {
        User vendor = userRepository.findById(vendorId).orElseThrow();
        return productRepository.findByVendor(vendor, page);
    }

    public Product updateProduct(Long actingUserId, Long productId, Product updated) {
        User actor = userRepository.findById(actingUserId).orElseThrow();
        Product existing = productRepository.findById(productId).orElseThrow();
        // only vendor(owner) or admin/super_admin can update
        if (actor.getRole() == Role.VENDOR && !existing.getVendor().getId().equals(actor.getId())) {
            throw new RuntimeException("Vendor can only update their own product");
        }
        if (actor.getRole() == Role.USER) throw new RuntimeException("Not allowed");
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        existing.setImageUrl(updated.getImageUrl());
        if (updated.getCategory() != null) existing.setCategory(updated.getCategory());
        return productRepository.save(existing);
    }

    public void deleteProduct(Long actingUserId, Long productId) {
        User actor = userRepository.findById(actingUserId).orElseThrow();
        Product existing = productRepository.findById(productId).orElseThrow();
        if (actor.getRole() == Role.VENDOR && !existing.getVendor().getId().equals(actor.getId())) {
            throw new RuntimeException("Vendor can only delete their own product");
        }
        if (actor.getRole() == Role.USER) throw new RuntimeException("Not allowed");
        productRepository.deleteById(productId);
    }
}
