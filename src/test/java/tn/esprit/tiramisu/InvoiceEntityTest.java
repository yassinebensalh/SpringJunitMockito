package tn.esprit.tiramisu;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tiramisu.entities.Invoice;
import tn.esprit.tiramisu.entities.InvoiceDTO;

@Slf4j
@ExtendWith(MockitoExtension.class)
@WebMvcTest(Invoice.class)
public class InvoiceEntityTest {


    @Mock
    private Invoice invoice;

    @Mock
    private InvoiceDTO invoiceDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(invoice).build();

    }

    @Test
    public void moniteurSetterTest() {
        Invoice invoice1 = new Invoice();
        invoice1.setArchived(true);
        Assertions.assertEquals(true , invoice1.getArchived());
    }
}