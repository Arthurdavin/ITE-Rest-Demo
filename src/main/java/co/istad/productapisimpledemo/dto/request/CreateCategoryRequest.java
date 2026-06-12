package co.istad.productapisimpledemo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
        @NotBlank(message = "name is require")
        @Size(min = 1,max = 100)
        String name,
        @NotBlank(message = "description is require")
        @Size(min = 1,max = 255)
        String description
) {
}
