package com.Market.MarketPlaceApplication.Controller;

import com.Market.MarketPlaceApplication.Model.Role;
import com.Market.MarketPlaceApplication.Model.User;
import com.Market.MarketPlaceApplication.Service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VendorAdminController {

    private final UserService userService;

    public VendorAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/vendors")
    public List<User> listVendors() {
        return userService.findByRole(Role.VENDOR);
    }
}
