package com.auth.user.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    private String id;

    private int quantity = 1;

    @NotBlank(message = "Product name cannot be blank.")
    private String name;

    private String description;

    @NotBlank(message = "Product URL cannot be blank.")
    private String url;

    private String userId;

    private double amount;
}
