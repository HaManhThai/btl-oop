package stationery.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stationery.model.OrderModel;
@Repository



public interface OrderRepository extends JpaRepository<OrderModel, Long> {
}
