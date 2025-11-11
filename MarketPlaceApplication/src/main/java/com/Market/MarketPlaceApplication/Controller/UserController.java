package com.Market.MarketPlaceApplication.Controller;

import com.Market.MarketPlaceApplication.Model.User;
import com.Market.MarketPlaceApplication.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        return userService.save(user);
    }
}
