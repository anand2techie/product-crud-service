package com.challenge.productservice.service;

import com.challenge.productservice.model.ProductDTO;

import java.util.List;
import java.util.Map;

public interface ProductService {

    Long createProduct(ProductDTO productDTO);

    ProductDTO getProductById(Long id);

    void updateProductById(Long id, ProductDTO productDTO);

    void deleteProductById(Long id);

    List<ProductDTO> listProducts(Integer page, Integer pageSize, Map<String, String> viewParams);
}
