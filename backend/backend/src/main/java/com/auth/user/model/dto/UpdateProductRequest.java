package com.auth.user.model.dto;

import com.auth.user.enums.UpdateType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UpdateProductRequest {
    @NotBlank(message = "Product ID cannot be blank")
    private String productId;

    private int quantity;

    @NotNull(message = "Update type cannot be blank.")
    private UpdateType updateType;
}
