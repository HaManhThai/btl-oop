package stationery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stationery.model.ContactModel;

@Repository
public interface ContactRepository extends JpaRepository<ContactModel, Long> {
}
