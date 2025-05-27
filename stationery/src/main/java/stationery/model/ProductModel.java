package stationery.model;
import java.math.BigDecimal;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;


@Data // Getter, Setter
@NoArgsConstructor // Constructor không tham số
@AllArgsConstructor // Constructor đầy đủ tham số
@Entity
@Table(name = "products")

public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private String image_url;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel category;

    @OneToMany(mappedBy = "product")
    private List<ReviewModel> review;
}
