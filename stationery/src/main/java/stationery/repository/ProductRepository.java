package stationery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stationery.model.CategoryModel;
import stationery.model.ProductModel;
@Repository


public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    List<ProductModel> findByCategory(CategoryModel category);
    List<ProductModel> findByNameContainingIgnoreCase(String keyword);
}
