package com.Market.MarketPlaceApplication.Service;

import com.Market.MarketPlaceApplication.Model.Role;
import com.Market.MarketPlaceApplication.Model.User;
import com.Market.MarketPlaceApplication.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // simple uniqueness check
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already used");
        }
        return userRepository.save(user);
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public User promoteToAdmin(Long actingUserId, Long targetUserId) {
        // only SUPER_ADMIN may promote
        User actor = userRepository.findById(actingUserId).orElseThrow(() -> new RuntimeException("Acting user not found"));
        if (actor.getRole() != Role.SUPER_ADMIN) throw new RuntimeException("Only SUPER_ADMIN can promote to ADMIN");

        User target = userRepository.findById(targetUserId).orElseThrow();
        target.setRole(Role.ADMIN);
        return userRepository.save(target);
    }

    public User assignVendor(Long actingUserId, Long targetUserId, String shopName) {
        // SUPER_ADMIN or ADMIN can assign vendor role
        User actor = userRepository.findById(actingUserId).orElseThrow();
        if (actor.getRole() != Role.SUPER_ADMIN && actor.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only SUPER_ADMIN or ADMIN can assign VENDOR role");
        }
        User target = userRepository.findById(targetUserId).orElseThrow();
        target.setRole(Role.VENDOR);
        target.setShopName(shopName);
        target.setVendorVerified(false);
        return userRepository.save(target);
    }

    public User verifyVendor(Long actingUserId, Long vendorId) {
        // ADMIN or SUPER_ADMIN can verify vendors
        User actor = userRepository.findById(actingUserId).orElseThrow();
        if (actor.getRole() != Role.SUPER_ADMIN && actor.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only SUPER_ADMIN or ADMIN can verify vendors");
        }
        User vendor = userRepository.findById(vendorId).orElseThrow();
        if (vendor.getRole() != Role.VENDOR) throw new RuntimeException("User is not a vendor");
        vendor.setVendorVerified(true);
        return userRepository.save(vendor);
    }

    public User updateUser(Long id, User updated) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(updated.getName());
        user.setEmail(updated.getEmail());
        if (updated.getPassword() != null) user.setPassword(updated.getPassword());
        return userRepository.save(user);
    }
}
