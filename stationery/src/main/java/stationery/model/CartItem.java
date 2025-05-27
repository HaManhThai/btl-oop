    package stationery.model;

    import lombok.*;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class CartItem {
        private ProductModel product;
        private int quantity;
    }
