package com.challenge.productservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    String name;

    String brand;

    Double price;

    String currency;

    String description;
}
