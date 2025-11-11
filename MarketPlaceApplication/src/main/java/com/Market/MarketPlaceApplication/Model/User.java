package com.Market.MarketPlaceApplication.Model;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password; // plain for now; replace with hashing in production

    @Enumerated(EnumType.STRING)
    private Role role;

    // Vendor-specific fields (optional)
    private String shopName;
    private boolean vendorVerified;

    public User() {}

    public User(Long id, String email, String name, String password, Role role, String shopName, boolean vendorVerified) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.shopName = shopName;
        this.vendorVerified = vendorVerified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public boolean isVendorVerified() {
        return vendorVerified;
    }

    public void setVendorVerified(boolean vendorVerified) {
        this.vendorVerified = vendorVerified;
    }
}
