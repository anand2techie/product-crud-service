package com.challenge.productservice.service;

import com.challenge.productservice.entity.Product;
import com.challenge.productservice.exception.ProductNotFoundException;
import com.challenge.productservice.mapper.ProductDTO2EntityMapper;
import com.challenge.productservice.mapper.ProductEntity2DTOMapper;
import com.challenge.productservice.model.ProductDTO;
import com.challenge.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDTO2EntityMapper productDTO2EntityMapper;

    @Autowired
    private ProductEntity2DTOMapper productEntity2DTOMapper;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Long createProduct(ProductDTO productDTO) {
        Product product = productDTO2EntityMapper.from(productDTO);
        return productRepository.save(product).getId();
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productEntity2DTOMapper.from(getProductIfPresent(id));
    }

    @Override
    public void updateProductById(Long id, ProductDTO productDTO) {
        LOG.info("Update product for the given id: {}", id);
        Product updatedProduct = productDTO2EntityMapper.map(productDTO, getProductIfPresent(id));
        productRepository.save(updatedProduct);
    }

    @Override
    public void deleteProductById(Long id) {
        LOG.info("Delete product for the given id: {}", id);
        productRepository.delete(getProductIfPresent(id));
    }

    @Override
    public List<ProductDTO> listProducts(Integer page, Integer pageSize, Map<String, String> viewParams) {
        return productEntity2DTOMapper.from(productRepository.findAll());
    }

    private Product getProductIfPresent(Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            LOG.error("Product not found for the given id: {}", id);
            return new ProductNotFoundException("product not found");
        });
    }
}
