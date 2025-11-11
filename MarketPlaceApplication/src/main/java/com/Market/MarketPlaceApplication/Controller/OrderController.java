package com.Market.MarketPlaceApplication.Controller;

import com.Market.MarketPlaceApplication.Model.Order;
import com.Market.MarketPlaceApplication.Model.OrderItem;
import com.Market.MarketPlaceApplication.Model.User;
import com.Market.MarketPlaceApplication.Repository.UserRepository;
import com.Market.MarketPlaceApplication.Service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @GetMapping("/list")
    public List<Order> list() { return orderService.listAll(); }

    @PostMapping("/orderBook")
    public Order create(@RequestParam Long userId, @RequestBody List<OrderItem> items) {
        User buyer = userRepository.findById(userId).orElseThrow();
        return orderService.createOrder(buyer, items);
    }
}
