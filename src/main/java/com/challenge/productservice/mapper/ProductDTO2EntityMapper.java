package com.challenge.productservice.mapper;

import com.challenge.productservice.entity.Product;
import com.challenge.productservice.model.ProductDTO;
import org.springframework.stereotype.Service;

@Service
public class ProductDTO2EntityMapper {

    public Product from(ProductDTO productDTO) {
        Product product = new Product();
        map(productDTO, product);
        return product;
    }

    public Product map(ProductDTO productDTO, Product product) {
        product.setBrand(productDTO.getBrand());
        product.setCurrency(productDTO.getCurrency());
        product.setDescription(productDTO.getDescription());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        return product;
    }
}
