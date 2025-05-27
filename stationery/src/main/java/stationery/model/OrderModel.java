package stationery.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Data // Getter, Setter
@NoArgsConstructor // Constructor không tham số
@AllArgsConstructor // Constructor đầy đủ tham số
@Entity
@Table(name = "orders") // khai báo bảng
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String recipient_name; 
    private String phone;
    private String address;
    private BigDecimal total_price;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
