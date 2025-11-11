package com.Market.MarketPlaceApplication.Controller;

import com.Market.MarketPlaceApplication.Model.Category;
import com.Market.MarketPlaceApplication.Repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository repo;
    public CategoryController(CategoryRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Category> list() { return repo.findAll(); }

    @PostMapping
    public Category create(@RequestBody Category c) { return repo.save(c); }
}
