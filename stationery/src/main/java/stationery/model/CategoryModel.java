package stationery.model;

import java.util.List;

// import com.example.stationery.controller.customer.ProductController;

import jakarta.persistence.*;
import lombok.*;

@Data // Getter, Setter, toString
@NoArgsConstructor // Constructor không tham số
@AllArgsConstructor // Constructor đầy đủ tham số
@Entity
@Table(name = "categories") // khai báo bảng
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    @OneToMany(mappedBy = "category")
    private List<ProductModel> products;
}
