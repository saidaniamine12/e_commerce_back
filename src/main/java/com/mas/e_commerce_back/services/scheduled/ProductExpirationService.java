package com.mas.e_commerce_back.services.scheduled;

import com.mas.e_commerce_back.entities.Product;
import com.mas.e_commerce_back.repositories.ProductRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductExpirationService {

    private final ProductRepository productRepository;

    public ProductExpirationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Scheduled(cron = "0 0 1 * * ?")
    public void updateProductExpiration() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            ZonedDateTime now = ZonedDateTime.now();
            if (product.getDiscountEndDate().isBefore(now)) {
                product.setDiscounted(false);
                products.add(product);
            }
        });
        productRepository.saveAll(products);

    }
}
