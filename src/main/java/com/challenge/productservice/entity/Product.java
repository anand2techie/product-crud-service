package com.challenge.productservice.entity;

import com.challenge.productservice.model.entities.ProductEntityBase;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends ProductEntityBase {

    String name;

    String brand;

    Double price;

    String currency;

    String description;
}
