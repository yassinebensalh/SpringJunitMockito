package tn.esprit.tiramisu.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import tn.esprit.tiramisu.entities.Invoice;
import tn.esprit.tiramisu.entities.InvoiceDTO;
import tn.esprit.tiramisu.entities.Operator;
import tn.esprit.tiramisu.entities.Supplier;
import tn.esprit.tiramisu.repositories.InvoiceRepository;
import tn.esprit.tiramisu.repositories.OperatorRepository;
import tn.esprit.tiramisu.repositories.SupplierRepository;
import tn.esprit.tiramisu.services.Iservices.IInvoiceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class InvoiceServiceImpl implements IInvoiceService {
	private final OperatorRepository operatorRepository;
	private final SupplierRepository supplierRepository;

	@Autowired
	final InvoiceRepository invoiceRepository;

	@Override
	public List<Invoice> retrieveAllInvoices() {
		return invoiceRepository.findAll();
	}

	@Override
	public Invoice addInvoice(InvoiceDTO invoiceDTO) {
		return invoiceRepository.save(new Invoice(
				invoiceDTO.getAmountDiscount(),invoiceDTO.getAmountInvoice(),invoiceDTO.getDateCreationInvoice(),
				invoiceDTO.getDateLastModificationInvoice(),invoiceDTO.getArchived()
		));
	}
	@Override
	public void cancelInvoice(Long invoiceId) {
		Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new NullPointerException("Invoice not found"));
		invoice.setArchived(true);
		invoiceRepository.save(invoice);
	}

	@Override
	public Invoice retrieveInvoice(Long invoiceId) {

		return invoiceRepository.findById(invoiceId).orElseThrow(() -> new NullPointerException("Invoice not found"));
	}

	@Override
	public void deleteInvoice(Long idInvoice) {
		invoiceRepository.deleteById(idInvoice);
	}





	@Override
	public List<Invoice> getInvoicesBySupplier(Long idSupplier) {
		Supplier supplier = supplierRepository.findById(idSupplier).orElseThrow(() -> new NullPointerException("Supplier not found"));
		return new ArrayList<>(supplier.getInvoices());
	}

	@Override
	public void assignOperatorToInvoice(Long idOperator, Long idInvoice) {
		Invoice invoice = invoiceRepository.findById(idInvoice).orElseThrow(() -> new NullPointerException("Invoice not found"));
		Operator operator = operatorRepository.findById(idOperator).orElseThrow(() -> new NullPointerException("Operator not found"));
		operator.getInvoices().add(invoice);
		operatorRepository.save(operator);
	}

	@Override
	public float getTotalAmountInvoiceBetweenDates(@DateTimeFormat(pattern = "yyyy-mm-dd") Date startDate,@DateTimeFormat(pattern = "yyyy-mm-dd") Date endDate) {
		return invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate, endDate);

	}


}
