package tn.esprit.tiramisu;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tiramisu.entities.Invoice;
import tn.esprit.tiramisu.entities.InvoiceDTO;
import tn.esprit.tiramisu.entities.Operator;
import tn.esprit.tiramisu.entities.Supplier;
import tn.esprit.tiramisu.repositories.InvoiceRepository;
import tn.esprit.tiramisu.repositories.OperatorRepository;
import tn.esprit.tiramisu.repositories.SupplierRepository;
import tn.esprit.tiramisu.services.InvoiceServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@WebMvcTest(InvoiceServiceImpl.class)
public class InvoiceServiceTest {

    @Mock
    private Invoice inv;

    @Mock
    private Supplier sup;

    @Mock
    private Operator op;
    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceServiceImpl;

    Invoice invoice = Invoice.builder().idInvoice(1L).amountDiscount(15.5f).amountInvoice(20.5f)
            .dateCreationInvoice(Date.from(Instant.now()))
            .dateLastModificationInvoice(Date.from(Instant.now()))
            .archived(false).build();

    InvoiceDTO invoiceDTO = InvoiceDTO.builder().amountDiscount(15.5f).amountInvoice(20.5f)
            .dateCreationInvoice(Date.from(Instant.now()))
            .dateLastModificationInvoice(Date.from(Instant.now()))
            .archived(false).build();

    List<Invoice> records = new ArrayList<Invoice>() {
        {
            add(
                    Invoice.builder().idInvoice(1L).amountDiscount(15.5f).amountInvoice(17.5f)
                            .dateCreationInvoice(Date.from(Instant.now()))
                            .dateLastModificationInvoice(Date.from(Instant.now()))
                            .archived(false).build()
            );
            add(
                    Invoice.builder().idInvoice(2L).amountDiscount(20.5f).amountInvoice(22.5f)
                            .dateCreationInvoice(Date.from(Instant.now()))
                            .dateLastModificationInvoice(Date.from(Instant.now()))
                            .archived(true).build()
            );
            add(
                    Invoice.builder().idInvoice(3L).amountDiscount(25f).amountInvoice(27f)
                            .dateCreationInvoice(Date.from(Instant.now()))
                            .dateLastModificationInvoice(Date.from(Instant.now()))
                            .archived(true).build()
            );
        }
    };
    Set<Invoice> invoiceSet = new HashSet<>(records);

    Supplier supplier = Supplier.builder().idSupplier(1L).code("az12").label("yassine").invoices(invoiceSet).build();

    Operator operator = Operator.builder().idOperateur(1L).fname("yassine").lname("ben salha").password("Slawa10")
            .invoices(invoiceSet).build();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(invoiceServiceImpl).build();
    }


    @Test
    public void getAllInvoicesServiceTest() {
        Mockito.when(invoiceRepository.findAll()).thenReturn(records);
        List<Invoice> invoiceList = invoiceServiceImpl.retrieveAllInvoices();
        Assertions.assertEquals(17.5, invoiceList.get(0).getAmountInvoice());
    }

    @Test
    public void getOneInvoiceServiceTest() {
        Mockito.when(invoiceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(invoice));
        Invoice invoice1 = invoiceServiceImpl.retrieveInvoice(1L);
        Assert.assertNotNull(invoice1);
        log.info("get ===> " + invoice1.toString());
        Mockito.verify(invoiceRepository).findById(Mockito.anyLong());
    }

    @Test
    public void addInvoiceServiceTest() {
        log.info("first : " + invoice.getIdInvoice());
        Mockito.when(invoiceRepository.save(Mockito.any())).thenReturn(invoice);
        Invoice invoice1 = invoiceServiceImpl.addInvoice(invoiceDTO);
        log.info("second : " + invoice1.toString());
        Assert.assertNotNull(invoice1);
    }

    @Test
    public void cancelInvoiceServiceTest() {
        Mockito.when(invoiceRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(invoice));
        invoiceServiceImpl.cancelInvoice(1L);
        Assertions.assertEquals(true , invoice.getArchived());
    }

    @Test
    public void deleteInvoiceServiceTest() {
        invoiceServiceImpl.deleteInvoice(invoice.getIdInvoice());
        Mockito.verify(invoiceRepository).deleteById(invoice.getIdInvoice());
    }


    @Test
    public void getInvoicesBySupplierServiceTest() {
        Mockito.when(supplierRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(supplier));
        List<Invoice> invoiceList = invoiceServiceImpl.getInvoicesBySupplier(supplier.getIdSupplier());
        Assert.assertEquals(3 , invoiceList.size());
    }

    @Test
    public void getTotalAmountInvoiceBetweenDatesServiceTest() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = dateFormat.parse("30/11/2024");
        Date endDate = dateFormat.parse("25/12/2024");
        Mockito.when(invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate,endDate)).thenReturn(5f);
        float result  = invoiceServiceImpl.getTotalAmountInvoiceBetweenDates(startDate,endDate);
        Assertions.assertEquals(5f,result);
    }

    @Test
    public void assignOperatorToInvoiceServiceTest() {
        Mockito.when(invoiceRepository.findById(invoice.getIdInvoice())).thenReturn(Optional.of(invoice));
        Mockito.when(operatorRepository.findById(operator.getIdOperateur())).thenReturn(Optional.of(operator));
        Mockito.when(operatorRepository.save(operator)).thenReturn(operator);
        invoiceServiceImpl.assignOperatorToInvoice(operator.getIdOperateur(), invoice.getIdInvoice());
        log.info("first : " + operator.getInvoices());
        Mockito.verify(invoiceRepository).findById(invoice.getIdInvoice());
        Mockito.verify(operatorRepository).findById(operator.getIdOperateur());
        Mockito.verify(operatorRepository).save(operator);
    }


}