package com.Market.MarketPlaceApplication.Repository;

import com.Market.MarketPlaceApplication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
