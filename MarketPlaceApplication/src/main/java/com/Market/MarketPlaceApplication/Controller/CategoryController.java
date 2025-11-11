package com.Market.MarketPlaceApplication.Controller;

import com.Market.MarketPlaceApplication.Model.Category;
import com.Market.MarketPlaceApplication.Repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/create")
    public Category create(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @GetMapping("/list")
    public List<Category> list() {
        return categoryRepository.findAll();
    }

}
