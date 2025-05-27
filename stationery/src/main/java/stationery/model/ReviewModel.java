package stationery.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Data // Getter, Setter
@NoArgsConstructor // Constructor không tham số
@AllArgsConstructor // Constructor đầy đủ tham số
@Entity
@Table(name = "reviews") // khai báo bảng
public class ReviewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String review;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;
}
