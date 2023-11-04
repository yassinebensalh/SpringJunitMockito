package tn.esprit.tiramisu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tiramisu.controllers.InvoiceController;
import tn.esprit.tiramisu.entities.Invoice;
import tn.esprit.tiramisu.entities.InvoiceDTO;
import tn.esprit.tiramisu.entities.Operator;
import tn.esprit.tiramisu.entities.Supplier;
import tn.esprit.tiramisu.repositories.InvoiceRepository;
import tn.esprit.tiramisu.repositories.OperatorRepository;
import tn.esprit.tiramisu.repositories.SupplierRepository;
import tn.esprit.tiramisu.services.InvoiceServiceImpl;
import tn.esprit.tiramisu.services.Iservices.IInvoiceService;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceContollerTest {

    private MockMvc mockMvc;

    static ObjectMapper objectMapper = new ObjectMapper();
    static ObjectWriter objectWriter = objectMapper.writer();
    public static String asJsonString(final Object obj) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Mock
    private Supplier sup;

    @Mock
    private Operator op;

    @Mock
    private IInvoiceService invoiceService;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceServiceImpl;

    @InjectMocks
    private InvoiceController invoiceController;


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
        this.mockMvc = MockMvcBuilders.standaloneSetup(invoiceController).build();
    }

    @Test
    public void retriveInvoicesTest() throws Exception {

        Mockito.when(invoiceService.retrieveAllInvoices()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/invoice")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$" , hasSize(3)))
                .andExpect(jsonPath("$[2].amountDiscount" , is(25.0)));
    }

    @Test
    public void createInvoiceTest() throws Exception
    {

        Mockito.when(invoiceServiceImpl.addInvoice(invoiceDTO)).thenReturn(invoice);
        mockMvc.perform(MockMvcRequestBuilders.post("/invoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(asJsonString(invoiceDTO)))
                .andExpect(status().isCreated())
                .andReturn();
    }


    @Test
    public void deleteInvoiceTest() throws Exception
    {
        Mockito.when(invoiceRepository.findById(invoice.getIdInvoice())).thenReturn(Optional.ofNullable(invoice));
        mockMvc.perform(MockMvcRequestBuilders.delete("/invoice/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void retrieveInvoiceTest() throws Exception
    {
        Mockito.when(invoiceService.retrieveInvoice(Mockito.anyLong())).thenReturn(invoice);
        mockMvc.perform(MockMvcRequestBuilders.get("/invoice/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.amountDiscount" , is(15.5)))
                .andReturn();
    }

    @Test
    public void cancelInvoiceTest() throws Exception
    {
        Invoice updatedinvoice = Invoice.builder().idInvoice(1L).amountDiscount(15.5f).amountInvoice(20.5f)
                .dateCreationInvoice(Date.from(Instant.now()))
                .dateLastModificationInvoice(Date.from(Instant.now()))
                .archived(true).build();
        Mockito.when(invoiceRepository.findById(invoice.getIdInvoice())).thenReturn(Optional.of(invoice));
        Mockito.when(invoiceRepository.save(invoice)).thenReturn(updatedinvoice);
        mockMvc.perform(MockMvcRequestBuilders.put("/invoice/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    // ########################## Mockito Test Functions for the relationships with the other ############################################

    @Test
    public void getInvoicesBySupplierTest() throws Exception
    {
        Mockito.when(supplierRepository.findById(invoice.getIdInvoice())).thenReturn(Optional.of(supplier));
        log.info(supplier.getInvoices().toString());
        List<Invoice> LocalRecords = new ArrayList<Invoice>(supplier.getInvoices());
        log.info(LocalRecords.toString());

        Mockito.when(invoiceService.getInvoicesBySupplier(supplier.getIdSupplier())).thenReturn(LocalRecords);
        mockMvc.perform(MockMvcRequestBuilders.get("/invoice/supplier/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$" , hasSize(3)))
                .andExpect(jsonPath("$[2].amountDiscount" , is(25.0)))
                .andReturn();
    }

    @Test
    public void assignOperatorToInvoiceTest() throws Exception
    {
        Mockito.when(invoiceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(invoice));
        Mockito.when(operatorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(operator));
        Mockito.when(operatorRepository.save(operator)).thenReturn(operator);
        mockMvc.perform(MockMvcRequestBuilders.put("/invoice/operator/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void getTotalAmountInvoiceBetweenDatesTest() throws Exception
    {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-31");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-31");
        Date testDate = Date.from(LocalDate.parse("2023||12||31", DateTimeFormatter.ofPattern("yyyy||MM||dd")).atStartOfDay(ZoneId.systemDefault()).toInstant());
        log.info(String.valueOf(testDate));
        log.info(String.valueOf(startDate));

        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate= DateFor.format(date);
        log.info(String.valueOf(stringDate));
        String end = "2015-12-31T23:59:59.999Z";

        Mockito.when(invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate,endDate))
                .thenReturn(5F);
        mockMvc.perform(MockMvcRequestBuilders.get("/invoice/price/2023-12-31/2023-12-31")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$" , is(0.0)))
                .andReturn();
    }

}