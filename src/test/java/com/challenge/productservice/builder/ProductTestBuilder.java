package com.challenge.productservice.builder;

import com.challenge.productservice.model.ProductDTO;
import org.springframework.stereotype.Service;

@Service
public class ProductTestBuilder {

    private ProductDTO productDTO;

    ProductTestBuilder() {
        productDTO = new ProductDTO();
    }

    public ProductTestBuilder withProductDetails(String name, String description) {
        productDTO.setName(name);
        productDTO.setDescription(description);
        return this;
    }

    public ProductTestBuilder pricedAt(Double price, String currency) {
        productDTO.setPrice(price);
        productDTO.setCurrency(currency);
        return this;
    }

    public ProductTestBuilder ofBrand(String brand) {
        productDTO.setBrand(brand);
        return this;
    }

    public ProductDTO build() {
        return productDTO;
    }
}
