package com.Market.MarketPlaceApplication.Controller;

import com.Market.MarketPlaceApplication.Model.Order;
import com.Market.MarketPlaceApplication.Model.OrderItem;
import com.Market.MarketPlaceApplication.Repository.ProductRepository;
import com.Market.MarketPlaceApplication.Repository.UserRepository;
import com.Market.MarketPlaceApplication.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderController(OrderService orderService, UserRepository userRepository, ProductRepository productRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam Long buyerId, @RequestBody List<OrderItem> items) {
        // buyerId is the user placing order (no auth)
        Order order = orderService.createOrder(buyerId, items);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/list")
    public List<Order> listAll() {
        return orderService.listAll();
    }

    @GetMapping("/buyer/{buyerId}")
    public List<Order> listByBuyer(@PathVariable Long buyerId) {
        return orderService.listByBuyer(buyerId);
    }
}
