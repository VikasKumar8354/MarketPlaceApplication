package com.Market.MarketPlaceApplication.Repository;

import com.Market.MarketPlaceApplication.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
