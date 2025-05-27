package stationery.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Data // Getter, Setter
@NoArgsConstructor // Constructor không tham số
@AllArgsConstructor // Constructor đầy đủ tham số
@Entity
@Table(name = "contacts") // khai báo bảng
public class ContactModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String email;
    private String message;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;        
}
