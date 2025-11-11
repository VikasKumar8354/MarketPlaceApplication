package com.Market.MarketPlaceApplication.Service;

import com.Market.MarketPlaceApplication.Model.Order;
import com.Market.MarketPlaceApplication.Model.OrderItem;
import com.Market.MarketPlaceApplication.Model.Product;
import com.Market.MarketPlaceApplication.Model.User;
import com.Market.MarketPlaceApplication.Repository.OrderRepository;
import com.Market.MarketPlaceApplication.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order createOrder(User buyer, List<OrderItem> items) {
        double total = 0;
        List<OrderItem> persisted = new ArrayList<>();

        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProduct().getId()).orElseThrow();
            if (product.getStock() < item.getQuantity()) throw new RuntimeException("Not enough stock");
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            OrderItem newItem = OrderItem.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .price(product.getPrice())
                    .build();
            persisted.add(newItem);
            total += product.getPrice() * item.getQuantity();
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
}