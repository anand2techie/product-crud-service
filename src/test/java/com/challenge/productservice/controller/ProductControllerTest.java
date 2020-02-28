package com.challenge.productservice.controller;

import com.challenge.productservice.builder.ProductTestBuilder;
import com.challenge.productservice.entity.Product;
import com.challenge.productservice.mapper.ProductDTO2EntityMapper;
import com.challenge.productservice.model.ProductDTO;
import com.challenge.productservice.repository.ProductRepository;
import com.challenge.productservice.rest.apimodel.IdResponse;
import com.challenge.productservice.rest.apimodel.ListResponse;
import com.challenge.productservice.rest.apimodel.SimpleResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerTest {
    private static ProductDTO testProductDTO;
    private final String PRODUCTS_ENDPOINT = "/products";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTestBuilder productTestBuilder;
    @Autowired
    private ProductDTO2EntityMapper productDTO2EntityMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
        testProductDTO = buildTestProductDTO("Samsung Galaxy S10e", "launched in 2019", "Samsung", 600d, "EUR");
    }

    @AfterEach
    public void tearDown() throws Exception {
        productRepository.deleteAll();
    }

    @Test
    public void givenProducts_whenGetProductById_thenStatus200()
        throws Exception {

        //save a test data directly in repository and later send a GET API request
        Product product = productRepository.save(productDTO2EntityMapper.from(testProductDTO));

        MvcResult result = mvc.perform(get(PRODUCTS_ENDPOINT + "/" + product.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        //get product dto from response object
        ProductDTO productDTOFromResponse = getProductDTOFromResponse(result);

        //assert values got from API with test data
        Assert.assertEquals(testProductDTO.getBrand(), productDTOFromResponse.getBrand());
        Assert.assertEquals(testProductDTO.getCurrency(), productDTOFromResponse.getCurrency());
        Assert.assertEquals(testProductDTO.getDescription(), productDTOFromResponse.getDescription());
        Assert.assertEquals(testProductDTO.getName(), productDTOFromResponse.getName());
        Assert.assertEquals(testProductDTO.getPrice(), productDTOFromResponse.getPrice());
    }

    @Test
    public void givenProducts_whenGetProductWhichNotExist_thenStatus200()
        throws Exception {
        mvc.perform(get(PRODUCTS_ENDPOINT + "/" + 2)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();
    }

    @Test
    @Transactional
    public void givenProduct_whenCreateAProduct_thenStatus201()
        throws Exception {

        //perform post request for an product
        MvcResult result = mvc.perform(
            post(PRODUCTS_ENDPOINT)
                .content(objectMapper.writeValueAsString(testProductDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        //get id from API response and find in repository
        Long id = getIdFromResponse(result);
        Product product = productRepository.findById(id).get();

        //assert values got from repository with test data
        Assert.assertEquals(testProductDTO.getBrand(), product.getBrand());
        Assert.assertEquals(testProductDTO.getCurrency(), product.getCurrency());
        Assert.assertEquals(testProductDTO.getDescription(), product.getDescription());
        Assert.assertEquals(testProductDTO.getName(), product.getName());
        Assert.assertEquals(testProductDTO.getPrice(), product.getPrice());
    }

    @Test
    @Transactional
    public void givenProduct_whenUpdateAProduct_thenStatus200()
        throws Exception {

        //create a test product to be updated through API put request
        Product product = productRepository.save(productDTO2EntityMapper.from(testProductDTO));

        testProductDTO.setBrand("Apple");
        testProductDTO.setDescription("Sponsored by Apple Inc.");

        //perform put request for an product
        mvc.perform(
            put(PRODUCTS_ENDPOINT + "/" + product.getId())
                .content(objectMapper.writeValueAsString(testProductDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        //assert values got from repository with test data
        Product updatedProduct = productRepository.findById(product.getId()).get();
        Assert.assertEquals(testProductDTO.getBrand(), updatedProduct.getBrand());
        Assert.assertEquals(testProductDTO.getCurrency(), updatedProduct.getCurrency());
        Assert.assertEquals(testProductDTO.getDescription(), updatedProduct.getDescription());
        Assert.assertEquals(testProductDTO.getName(), updatedProduct.getName());
        Assert.assertEquals(testProductDTO.getPrice(), updatedProduct.getPrice());
    }

    @Test
    @Transactional
    public void givenProduct_whenDeleteAProduct_thenStatus200()
        throws Exception {

        //create a test product to be deleted through API delete request
        Product product = productRepository.save(productDTO2EntityMapper.from(testProductDTO));

        //perform delete request for an product
        mvc.perform(
            delete(PRODUCTS_ENDPOINT + "/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        //assert for product should have been deleted
        Optional<Product> updatedProduct = productRepository.findById(product.getId());
        Assert.assertFalse(updatedProduct.isPresent());
    }

    @Test
    @Transactional
    public void givenProduct_whenlistProducts_thenStatus200()
        throws Exception {

        //create a test product to be deleted through API delete request
        productRepository.save(productDTO2EntityMapper.from(testProductDTO));

        //create bulk products to test for pagination and searching
        productRepository.save(productDTO2EntityMapper.from(testProductDTO));
        productRepository.save(productDTO2EntityMapper.from(testProductDTO));

        //request params with multi value map (without any criteria)
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

        //perform get request for list of products based on criteria
        MvcResult result = mvc.perform(get(PRODUCTS_ENDPOINT).params(requestParams)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        //get list of product dto from response object
        List<ProductDTO> responseProductDTOs = getProductDTOsFromListResponse(result);

        //assert for list of products should match the criteria result count
        Assert.assertEquals(3, responseProductDTOs.size());
    }

    private ProductDTO getProductDTOFromResponse(MvcResult result)
        throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
        SimpleResponse<ProductDTO> simpleResponse = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<SimpleResponse<ProductDTO>>() {
            });

        return simpleResponse.getData().get(0);
    }

    private List<ProductDTO> getProductDTOsFromListResponse(MvcResult result)
        throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
        ListResponse<ProductDTO> listResponse = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<ListResponse<ProductDTO>>() {
            });

        return listResponse.getData();
    }

    private Long getIdFromResponse(MvcResult result)
        throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
        IdResponse idResponse = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<IdResponse>() {
            });

        return idResponse.getData().get(0).getId().getId();
    }

    private ProductDTO buildTestProductDTO(String name, String description, String brand, Double price,
        String currency) {
        ProductDTO productDTO = productTestBuilder
            .withProductDetails(name, description)
            .ofBrand(brand)
            .pricedAt(price, currency)
            .build();
        return productDTO;
    }
}
