package tn.esprit.tiramisu;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tiramisu.entities.Invoice;
import tn.esprit.tiramisu.entities.Operator;
import tn.esprit.tiramisu.repositories.InvoiceRepository;
import tn.esprit.tiramisu.repositories.OperatorRepository;
import tn.esprit.tiramisu.repositories.SupplierRepository;
import tn.esprit.tiramisu.services.Iservices.IInvoiceService;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
//@RunWith(JUnitPlatform.class)
@TestMethodOrder(OrderAnnotation.class)
public class InvoiceServiceImplJUnitTest {

    @Autowired
    IInvoiceService invoiceService;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    OperatorRepository operatorRepository;
    @Autowired
    SupplierRepository supplierRepository;

    @Test
    @Order(2)
    public void testRetrieveAllInvoices() {
        List<Invoice> listProduits = invoiceService.retrieveAllInvoices();
        Assertions.assertEquals(listProduits.size(), listProduits.size());
    }

    @Test
    @Order(1)
    public void testGetTotalAmountInvoiceBetweenDates() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = dateFormat.parse("30/11/2024");
        Date endDate = dateFormat.parse("25/12/2024");


        Invoice invoice1 = new Invoice();
        invoice1.setDateCreationInvoice(startDate);
        invoice1.setDateLastModificationInvoice(endDate);
        invoice1.setAmountInvoice(100.0f);
        invoice1.setArchived(false);
        invoiceRepository.save(invoice1);

        Invoice invoice2 = new Invoice();
        invoice2.setDateCreationInvoice(startDate);
        invoice2.setDateLastModificationInvoice(endDate);
        invoice2.setAmountInvoice(200.0f);
        invoice2.setArchived(false);
        invoiceRepository.save(invoice2);


        float totalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(startDate,endDate);
        assertEquals(300.0f, totalAmount);
    }

    @Test
    @Order(3)
    public void testAssignOperatorToInvoice(){
        Invoice invoice1 = new Invoice();
        invoice1.setArchived(false);
        invoice1.setAmountInvoice(100.0f);
        invoice1 = invoiceRepository.save(invoice1);

        Operator operator1 = new Operator();
        operator1.setFname("mouna");
        operator1.setLname("ziadi");
        operator1 = operatorRepository.save(operator1);

        Long idOperator = operator1.getIdOperateur();
        Long idInvoice = invoice1.getIdInvoice();


        invoiceService.assignOperatorToInvoice(idOperator, idInvoice);

        Operator operator = operatorRepository.findById(idOperator).orElse(null);

        assertNotNull(operator);
        assertEquals(1, operator.getInvoices().size());

    }

    @Test
    @Order(4)
    public void testCancelInvoice() throws ParseException  {
        Invoice invoice = new Invoice();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = dateFormat.parse("30/09/2020");
        Date date2 = dateFormat.parse("05/12/2022");
        invoice.setDateCreationInvoice(date1);
        invoice.setDateLastModificationInvoice(date2);
        invoice.setAmountInvoice(1.5f);
        invoice.setArchived(false);
        invoiceRepository.save(invoice);

        invoiceService.cancelInvoice(invoice.getIdInvoice());
        assertNotNull(invoice.getIdInvoice());
    }

    @Test
    @Order(5)
    public void delete() {
        operatorRepository.deleteAll();
        invoiceRepository.deleteAll();
    }

}