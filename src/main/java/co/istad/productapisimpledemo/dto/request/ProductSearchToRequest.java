package co.istad.productapisimpledemo.dto.request;

public record ProductSearchToRequest(
        String name,
        String description,
        String categoryName  // ✅ String មិនមែន Category entity
) {
}
