package tn.esprit.tiramisu.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tiramisu.entities.Invoice;
import tn.esprit.tiramisu.entities.InvoiceDTO;
import tn.esprit.tiramisu.services.Iservices.IInvoiceService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class InvoiceController {

    @Autowired
    IInvoiceService invoiceService;

    @GetMapping("/invoice")
    public List<Invoice> getInvoices() {
       return invoiceService.retrieveAllInvoices();
    }

    @PostMapping("/invoice")
    public ResponseEntity<Invoice> addInvoice(@RequestBody InvoiceDTO invoiceDto) throws ParseException {
        log.info(invoiceDto.toString());
        return new ResponseEntity<>(invoiceService.addInvoice(invoiceDto), HttpStatus.CREATED);

    }

    @GetMapping("/invoice/{invoiceId}")
    public Invoice retrieveInvoice(@PathVariable Long invoiceId) {
        return invoiceService.retrieveInvoice(invoiceId);
    }

    @PutMapping("/invoice/{invoiceId}")
    public void cancelInvoice(@PathVariable Long invoiceId) {
        invoiceService.cancelInvoice(invoiceId);
    }

    @DeleteMapping("/invoice/{invoiceId}")
    public void removeInvoice(@PathVariable("invoiceId") Long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
    }
    @GetMapping("/invoice/supplier/{supplierId}")
    public List<Invoice> getInvoicesBySupplier(@PathVariable Long supplierId) {
        return invoiceService.getInvoicesBySupplier(supplierId);
    }

    @PutMapping(value = "/invoice/operator/{idOperator}/{idInvoice}")
    public void assignOperatorToInvoice(@PathVariable Long idOperator,@PathVariable Long idInvoice) {
        invoiceService.assignOperatorToInvoice(idOperator, idInvoice);
    }

    @GetMapping("/invoice/price/{startDate}/{endDate}")
    public float getTotalAmountInvoiceBetweenDates(@PathVariable  @DateTimeFormat(pattern = "yyyy-mm-dd")  Date startDate,@PathVariable @DateTimeFormat(pattern = "yyyy-mm-dd") Date endDate){
        return invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);
    }


}
