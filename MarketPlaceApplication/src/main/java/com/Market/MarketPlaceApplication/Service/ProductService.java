package com.Market.MarketPlaceApplication.Service;

import com.Market.MarketPlaceApplication.Model.Product;
import com.Market.MarketPlaceApplication.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product save(Product product){
        return productRepository.save(product);
    }
    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }
    public Page<Product> listAll(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }
    public void delete(Long id){
        productRepository.deleteById(id);
    }
}
