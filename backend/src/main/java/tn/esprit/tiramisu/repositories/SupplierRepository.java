package tn.esprit.tiramisu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tiramisu.entities.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {


}