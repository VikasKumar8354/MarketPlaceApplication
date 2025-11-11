package com.Market.MarketPlaceApplication.Service;

import com.Market.MarketPlaceApplication.Model.Order;
import com.Market.MarketPlaceApplication.Model.OrderItem;
import com.Market.MarketPlaceApplication.Model.Product;
import com.Market.MarketPlaceApplication.Model.User;
import com.Market.MarketPlaceApplication.Repository.OrderRepository;
import com.Market.MarketPlaceApplication.Repository.ProductRepository;
import com.Market.MarketPlaceApplication.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Order createOrder(Long buyerId, List<OrderItem> items) {
        User buyer = userRepository.findById(buyerId).orElseThrow();
        double total = 0;
        List<OrderItem> persisted = new ArrayList<>();
        for (OrderItem it : items) {
            Product p = productRepository.findById(it.getProduct().getId()).orElseThrow();
            if (p.getStock() < it.getQuantity()) throw new RuntimeException("Insufficient stock for: " + p.getTitle());
            p.setStock(p.getStock() - it.getQuantity());
            productRepository.save(p);

            OrderItem copy = OrderItem.builder()
                    .product(p)
                    .quantity(it.getQuantity())
                    .price(p.getPrice())
                    .build();
            persisted.add(copy);
            total += p.getPrice() * it.getQuantity();
        }

        Order order = Order.builder()
                .buyer(buyer)
                .items(persisted)
                .total(total)
                .status("CREATED")
                .createdAt(Instant.now())
                .build();
        return orderRepository.save(order);
    }

    public List<Order> listAll() {
        return orderRepository.findAll();
    }

    public List<Order> listByBuyer(Long buyerId) {
        User buyer = userRepository.findById(buyerId).orElseThrow();
        return orderRepository.findByBuyer(buyer);
    }
}