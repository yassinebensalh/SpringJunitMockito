package tn.esprit.tiramisu.services.Iservices;

import tn.esprit.tiramisu.entities.Invoice;
import tn.esprit.tiramisu.entities.InvoiceDTO;

import java.util.Date;
import java.util.List;

public interface IInvoiceService {

	List<Invoice> retrieveAllInvoices();

	Invoice addInvoice(InvoiceDTO invoiceDTO);

	List<Invoice> getInvoicesBySupplier(Long idSupplier);

	void cancelInvoice(Long id);

	Invoice retrieveInvoice(Long id);

	public void deleteInvoice(Long idInvoice);
	
	void assignOperatorToInvoice(Long idOperator, Long idInvoice);

	float getTotalAmountInvoiceBetweenDates(Date startDate, Date endDate);
}
