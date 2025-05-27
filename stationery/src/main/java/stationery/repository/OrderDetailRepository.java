package stationery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stationery.model.OrderDetailModel;
@Repository



public interface OrderDetailRepository extends JpaRepository<OrderDetailModel, Long > {
   List<OrderDetailModel> findByOrderId(Long Id);
   List<OrderDetailModel> findByProductId(Long Id);
}
