import org.example.OrderService;
import org.example.PaymentProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private PaymentProcessor paymentProcessor;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(paymentProcessor);
    }


    @Test
    void ordemDePagamentoDeveRetornarVerdadeiro() {

        when(paymentProcessor.processPayment(300.0)).thenReturn(true);

        System.setOut(new java.io.PrintStream(System.out));
        boolean resultado = orderService.processOrder(300.0);

        verify(paymentProcessor, times(1)).processPayment(300.0);

        assertTrue(resultado, "O resultado deve ser true.");
    }


    @Test
    void ordemDepagamentoDeveRetornarFalso() {

        when(paymentProcessor.processPayment(-50.0)).thenReturn(false);

        System.setOut(new java.io.PrintStream(System.out));
        boolean resultado = orderService.processOrder(50.0);

        verify(paymentProcessor, times(1)).processPayment(50.0);

        assertFalse(resultado, "O resultado deve ser false.");

    }
}
