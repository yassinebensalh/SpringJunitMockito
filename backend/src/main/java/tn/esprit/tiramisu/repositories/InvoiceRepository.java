package tn.esprit.tiramisu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import tn.esprit.tiramisu.entities.Invoice;
import tn.esprit.tiramisu.entities.Supplier;

import java.util.Date;
import java.util.List;
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	
	@Query("SELECT i FROM Invoice i where i.supplier=:supplier and i.archived=false")
	public List<Invoice> retrieveInvoicesBySupplier(@Param("supplier") Supplier supplier);

	
	@Query("SELECT COALESCE(sum(i.amountInvoice),'0')  FROM Invoice i where  i.dateCreationInvoice between :startDate"
			+ " and :endDate and i.archived=false")
	float getTotalAmountInvoiceBetweenDates(@Param("startDate") @DateTimeFormat(pattern = "yyyy-mm-dd") Date startDate, @Param("endDate") @DateTimeFormat(pattern = "yyyy-mm-dd") Date endDate);

	@Modifying
	@Query("update Invoice i set i.archived=true where i.idInvoice=?1")
	void updateInvoice(Long id);
	
}
