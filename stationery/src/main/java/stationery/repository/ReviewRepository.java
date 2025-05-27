package stationery.repository;

// import stationery.model.CategoryModel;
import stationery.model.ReviewModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel, Long> {
    List<ReviewModel> findByProductId(Long productId);
}
