package com.challenge.productservice.controller;

import com.challenge.productservice.model.ProductDTO;
import com.challenge.productservice.rest.apimodel.IdResponse;
import com.challenge.productservice.rest.apimodel.ListResponse;
import com.challenge.productservice.rest.apimodel.PageDTO;
import com.challenge.productservice.rest.apimodel.SimpleResponse;
import com.challenge.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SimpleResponse<ProductDTO> getProduct(@PathVariable Long id) {
        return new SimpleResponse(productService.getProductById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return new IdResponse(productService.createProduct(productDTO));
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        productService.updateProductById(id, productDTO);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListResponse<ProductDTO> listProducts(@RequestParam(defaultValue = "0", required = false) Integer page,
        @RequestParam(defaultValue = "10", required = false) Integer pageSize,
        @RequestParam Map<String, String> viewParams) {

        List<ProductDTO> productDTOList = productService.listProducts(page, pageSize, viewParams);
        return new ListResponse<>(new PageDTO<>(page, pageSize, productDTOList.size(), productDTOList));
    }
}
