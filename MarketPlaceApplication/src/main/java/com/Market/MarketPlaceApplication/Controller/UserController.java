package com.Market.MarketPlaceApplication.Controller;

import com.Market.MarketPlaceApplication.DTOs.CreateUserDto;
import com.Market.MarketPlaceApplication.Model.Role;
import com.Market.MarketPlaceApplication.Model.User;
import com.Market.MarketPlaceApplication.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> list() {
        return userService.listAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUserDto dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole() != null ? dto.getRole() : Role.USER)
                .shopName(dto.getShopName())
                .vendorVerified(false)
                .build();
        User saved = userService.createUser(user);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/role/{role}")
    public List<User> byRole(@PathVariable Role role) {
        return userService.findByRole(role);
    }

    @PostMapping("/{id}/promote-admin")
    public ResponseEntity<?> promoteToAdmin(@RequestHeader("X-User-Id") Long actingUserId,
                                            @PathVariable Long id) {
        User promoted = userService.promoteToAdmin(actingUserId, id);
        return ResponseEntity.ok(promoted);
    }

    @PostMapping("/{id}/assign-vendor")
    public ResponseEntity<?> assignVendor(@RequestHeader("X-User-Id") Long actingUserId,
                                          @PathVariable Long id,
                                          @RequestParam String shopName) {
        User vendor = userService.assignVendor(actingUserId, id, shopName);
        return ResponseEntity.ok(vendor);
    }

    @PostMapping("/{id}/verify-vendor")
    public ResponseEntity<?> verifyVendor(@RequestHeader("X-User-Id") Long actingUserId,
                                          @PathVariable Long id) {
        User vendor = userService.verifyVendor(actingUserId, id);
        return ResponseEntity.ok(vendor);
    }
}
