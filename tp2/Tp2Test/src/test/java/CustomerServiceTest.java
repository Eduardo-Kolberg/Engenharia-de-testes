import org.example.Customer;
import org.example.CustomerService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerServiceTest {

    private CustomerService customerService = new CustomerService();

    // Teste idade menor que 18 anos
    @Test
    void cadastroDeveFalharComIdadeMenorDeDezoitoAnos() {
        Customer customer = new Customer(1, "Joao Silva", "Joao.Silva@aleatorio.com", 17, true);
        assertFalse(customerService.registerCustomer(customer), "Cliente menor de 18 não deve ser cadastrado");
    }

    // Teste idade maior que 99 anos
    @Test
    void cadastroDeveFalharComIdadeMaiorQueNoventaENoveAnos() {
        Customer customer = new Customer(2, "Joao Silva", "Joao.Silva@aleatorio.com", 100, true);
        assertFalse(customerService.registerCustomer(customer), "Cliente maior de 99 anos não deve ser cadastrado");
    }

    // Idade limite 18 anos
    @Test
    void deveCadastrarComIdadeIgualADezoitoAnos() {
        Customer customer = new Customer(3, "Joao Silva", "Joao.Silva@aleatorio.com", 18, true);
        assertTrue(customerService.registerCustomer(customer), "Cliente com idade de 18 anos deve ser cadastrado.");
    }

    // Teste limite 99 anos
    @Test
    void deveCadastrarComIdadeIgualANoventaENoveAnos() {
        Customer customer = new Customer(4, "Joao Silva", "Joao.Silva@aleatorio.com", 99, true);
        assertTrue(customerService.registerCustomer(customer), "Cliente com 99 anos deve ser cadastrado.");
    }

    // Update cliente ativo
    @Test
    void deveDarUpdateEmClienteAtivo() {
        Customer customer = new Customer(1, "Joao Silva", "Joao.Silva@aleatorio.com", 18, true);
        assertTrue(customerService.updateCustomer(customer, "Joao Da Silva", "Joao.DaSilva@aleatorio.com", 19), "O cliente ativo deve ser atualizado.");
    }

    // Update cliente inativo
    @Test
    void updateDeveFalharParaClienteInativo() {
        Customer customer = new Customer(2, "Joao Silva", "Joao.Silva@aleatorio.com", 18, false);
        assertFalse(customerService.updateCustomer(customer, "Joao Da Silva", "Joao.DaSilva@aleatorio.com", 19), "Cliente inativo não deve ser atualizado");
    }

    // Delete cliente ativo
    @Test
    void deveDarDeleteEmClienteAtivo() {
        Customer customer = new Customer(1, "Joao Silva", "Joao.Silva@aleatorio.com", 18, true);
        assertTrue(customerService.deleteCustomer(customer), "Cliente ativo deve ser excluido");
    }

    // Delete cliente inativo
    @Test
    void deleteDeveFalharParaClienteInativo() {
        Customer customer = new Customer(2, "Joao Silva", "Joao.Silva@aleatorio.com", 18, false);
        assertFalse(customerService.deleteCustomer(customer), "Cliente inativo não deve ser excluido.");
    }

    // E-mail válido
    @Test
    void eMailValidadoDeveCadastrar() {
        Customer customer = new Customer(1, "Joao Silva", "Joao.Silva@aleatorio.com", 18, true);
        assertTrue(customerService.registerCustomer(customer), "E-mail válido deve realizar cadastro.");
    }

    // E-mail sem @ - invalido
    @Test
    void eMailInvalidoSemArrobaNaoDeveCadastrar() {
        Customer customer = new Customer(2, "Joao Silva", "Joao.Silvaaleatorio.com", 18, true);
        assertFalse(customerService.registerCustomer(customer), "E-mail inválido sem @, não deve realizar cadastro.");
    }

    // E-mail sem domínio - invalido
    @Test
    void eMailInvalidoSemDominioNaoDeveCadastrar() {
        Customer customer = new Customer(3, "Joao Silva", "Joao.Silva.com", 18, true);
        assertFalse(customerService.registerCustomer(customer), "E-mail inválido sem dominio, não deve realizar cadastro.");
    }

    @Test
    void testeDeCadastroCompleto() {
        Customer customer = new Customer(1, "Eduado Silva", "Eduardo@aleatorio.com", 37, true);
        assertTrue(customerService.registerCustomer(customer), "Cadastro deve ser realizado com sucesso.");
    }
}
