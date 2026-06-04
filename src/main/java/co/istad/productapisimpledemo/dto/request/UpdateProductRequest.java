package co.istad.productapisimpledemo.dto.request;

public record UpdateProductRequest(
        String name,
        String description,
        Float price
) {
}
