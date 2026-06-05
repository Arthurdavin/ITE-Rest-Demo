package co.istad.productapisimpledemo.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank(message = "name is require")
        String name,
        @NotBlank(message = "description is require")
        String description
) {
}
