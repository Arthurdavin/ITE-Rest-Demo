package co.istad.productapisimpledemo.dto.response;

public record ProductResponse(
        Integer id,
        String name,
        String description,
        Float price,
        CategoryResponse category
) {
}
