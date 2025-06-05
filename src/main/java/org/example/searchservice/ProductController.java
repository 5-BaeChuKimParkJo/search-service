package org.example.searchservice;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductSearchRepository productSearchRepository;

    @PostMapping("/products")
    public Product save(@RequestBody Product product) {
        return productSearchRepository.save(product);
    }

    @GetMapping("/products")
    public List<Product> search(@RequestParam String keyword) {
        return productSearchRepository.findByNameContaining(keyword);
    }
}
