package co.istad.productapisimpledemo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateProductRequest(
        @NotBlank(message = "name is required")
        String name,
        @NotBlank(message = "description is required")
        String description,
        @NotNull(message = "price is required")
        @Positive(message = "price is must positive")
        Float price,
        @NotNull(message = "categoryId is required")
        Integer categoryId
) {
}
