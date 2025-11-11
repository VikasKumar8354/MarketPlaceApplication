package com.Market.MarketPlaceApplication.Repository;

import com.Market.MarketPlaceApplication.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
