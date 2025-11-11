package com.Market.MarketPlaceApplication.DataLoader;

import com.Market.MarketPlaceApplication.Model.Category;
import com.Market.MarketPlaceApplication.Model.Product;
import com.Market.MarketPlaceApplication.Model.Role;
import com.Market.MarketPlaceApplication.Model.User;
import com.Market.MarketPlaceApplication.Repository.CategoryRepository;
import com.Market.MarketPlaceApplication.Repository.ProductRepository;
import com.Market.MarketPlaceApplication.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;

    public DataLoader(UserRepository userRepo, CategoryRepository categoryRepo, ProductRepository productRepo) {
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // create users with roles
        User superAdmin = User.builder()
                .name("Super Admin")
                .email("super@market.com")
                .password("superpass")
                .role(Role.SUPER_ADMIN)
                .build();
        userRepo.save(superAdmin);

        User admin = User.builder()
                .name("Admin User")
                .email("admin@market.com")
                .password("adminpass")
                .role(Role.ADMIN)
                .build();
        userRepo.save(admin);

        User vendor = User.builder()
                .name("Vendor One")
                .email("vendor1@market.com")
                .password("vendorpass")
                .role(Role.VENDOR)
                .shopName("VendorShop")
                .vendorVerified(true)
                .build();
        userRepo.save(vendor);

        User user = User.builder()
                .name("Buyer")
                .email("buyer@market.com")
                .password("buyerpass")
                .role(Role.USER)
                .build();
        userRepo.save(user);

        // categories
        Category electronics = categoryRepo.save(Category.builder().name("Electronics").description("Gadgets").build());
        Category books = categoryRepo.save(Category.builder().name("Books").description("Books").build());

        // products
        productRepo.save(Product.builder()
                .title("Wireless Headphones")
                .description("Noise-cancelling")
                .price(89.99)
                .stock(50)
                .category(electronics)
                .vendor(vendor)
                .build());

        productRepo.save(Product.builder()
                .title("Learning Java")
                .description("Java book for beginners")
                .price(29.99)
                .stock(100)
                .category(books)
                .vendor(vendor)
                .build());
    }
}
