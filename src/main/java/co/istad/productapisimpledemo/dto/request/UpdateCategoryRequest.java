package co.istad.productapisimpledemo.dto.request;

public record UpdateCategoryRequest(
        String name,
        String description
) {
}
