package co.istad.productapisimpledemo.dto.request;

import co.istad.productapisimpledemo.entity.Category;

public record UpdateProductRequest(
        String name,
        String description,
        Float price,
        Category categoryId
) {
}
