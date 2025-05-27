package stationery.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

@Data // Getter, Setter
@NoArgsConstructor // Constructor không tham số
@AllArgsConstructor // Constructor đầy đủ tham số
@Entity
@Table(name = "order_details") // khai báo bảng
public class OrderDetailModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private int quantity;
    private BigDecimal unit_price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;
  
}
