package com.challenge.productservice.mapper;

import com.challenge.productservice.entity.Product;
import com.challenge.productservice.model.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductEntity2DTOMapper {

    public ProductDTO from(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setBrand(product.getBrand());
        productDTO.setCurrency(product.getCurrency());
        productDTO.setDescription(product.getDescription());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());

        return productDTO;
    }

    public List<ProductDTO> from(List<Product> products) {
        return products.stream().map(this::from).collect(Collectors.toList());
    }
}
