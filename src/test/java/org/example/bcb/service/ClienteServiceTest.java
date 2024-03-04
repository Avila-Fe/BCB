package org.example.bcb.service;

import org.example.bcb.model.domain.Cliente;
import org.example.bcb.model.dto.ClienteDTO;
import org.example.bcb.repository.ClienteRepository;
import org.example.bcb.repository.SMSRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private SMSRepository smsRepository;

    @Mock
    private ConsumoSMSService consumoSMSService;

    @InjectMocks
    private ClienteService clienteService;

    public ClienteServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarCliente() {
        ClienteDTO clienteDTO = new ClienteDTO("João", "joao@example.com", "123456789",
                "123.456.789-00", "12.345.678/0001-90", "Empresa", "Plano A");
        Cliente cliente = new Cliente();
        when(clienteRepository.save(any())).thenReturn(cliente);

        when(clienteRepository.save(any())).thenAnswer(invocation -> {
            Cliente clienteArgument = invocation.getArgument(0);
            clienteArgument.setId(1L);
            return clienteArgument;
        });

        Cliente result = clienteService.cadastrarCliente(clienteDTO);

        assertNotNull(result);
        assertEquals("João", result.getNome());
        assertEquals("joao@example.com", result.getEmail());
        assertEquals("123456789", result.getTelefone());
        assertEquals("123.456.789-00", result.getCpf());
        assertEquals("12.345.678/0001-90", result.getCnpj());
        assertEquals("Empresa", result.getNomeEmpresa());
        assertEquals(0.0, result.getSaldo());
        assertEquals("Plano A", result.getTipoPlano());
        assertEquals(0.0, result.getLimiteMaximo());
    }

    @Test
    public void testInserirCreditos_PrePago() {
        Long id = 1L;
        double creditos = 100.0;
        Cliente cliente = new Cliente();
        cliente.setTipoPlano("pre-pago");
        cliente.setSaldo(50.0);
        when(clienteRepository.findById(id)).thenReturn(java.util.Optional.of(cliente));
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente result = clienteService.inserirCreditos(id, creditos);

        assertNotNull(result);
        assertEquals(150.0, result.getSaldo());
    }

    @Test
    public void testConsultaSaldo() {
        Long id = 1L;
        double saldo = 100.0;
        Cliente cliente = new Cliente();
        cliente.setSaldo(saldo);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Double result = clienteService.consultaSaldo(id);

        assertNotNull(result);
        assertEquals(saldo, result);
    }

    @Test
    public void testAlterarLimite_PosPago() {
        Long id = 1L;
        double novoLimite = 200.0;
        Cliente cliente = new Cliente();
        cliente.setTipoPlano("pos-pago");
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente result = clienteService.alterarLimite(id, novoLimite);

        assertNotNull(result);
        assertEquals(novoLimite, result.getLimiteMaximo());
    }

    @Test
    public void testAlterarLimite_PrePago() {
        Long id = 1L;
        double novoLimite = 200.0;
        Cliente cliente = new Cliente();
        cliente.setTipoPlano("pre-pago");
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        assertThrows(IllegalStateException.class, () -> clienteService.alterarLimite(id, novoLimite));
    }

    @Test
    public void testAlterarPlano() {
        Long id = 1L;
        String novoPlano = "Plano B";
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente result = clienteService.alterarPlano(id, novoPlano);

        assertNotNull(result);
        assertEquals(novoPlano, result.getTipoPlano());
    }

    @Test
    public void testConsultaCliente() {
        Long id = 1L;
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.consultaCliente(id);

        assertNotNull(result);
        assertEquals(cliente, result);
    }

    @Test
    public void testEnviarSMS_PrePago_SaldoSuficiente() {
        Long idCliente = 1L;
        double saldo = 1.0;
        String numeroTelefone = "123456789";
        String texto = "Teste";
        boolean isWhatsApp = false;

        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        cliente.setTipoPlano("pre-pago");
        cliente.setSaldo(saldo);
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));

        clienteService.enviarSMS(idCliente, numeroTelefone, texto, isWhatsApp);

        assertEquals(saldo - 0.25, cliente.getSaldo());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testEnviarSMS_PrePago_SaldoInsuficiente() {
        Long idCliente = 1L;
        double saldo = 0.0;
        String numeroTelefone = "123456789";
        String texto = "Teste";
        boolean isWhatsApp = false;

        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        cliente.setTipoPlano("pre-pago");
        cliente.setSaldo(saldo);
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));

        assertThrows(IllegalStateException.class, () -> clienteService.enviarSMS(idCliente, numeroTelefone, texto, isWhatsApp));

        assertEquals(saldo, cliente.getSaldo());
        verify(clienteRepository, never()).save(cliente);
    }

    @Test
    public void testEnviarSMS_PosPago_ConsumoDentroDoLimite() {
        Long idCliente = 1L;
        double limiteMaximo = 1.0;
        double consumoNoMes = 0.0;
        String numeroTelefone = "123456789";
        String texto = "Teste";
        boolean isWhatsApp = false;

        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        cliente.setTipoPlano("pos-pago");
        cliente.setLimiteMaximo(limiteMaximo);
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        when(consumoSMSService.calcularConsumoNoPeriodo(eq(idCliente), anyString(), anyString())).thenReturn(consumoNoMes);

        clienteService.enviarSMS(idCliente, numeroTelefone, texto, isWhatsApp);

        verify(consumoSMSService, times(1)).registrarConsumoSMS(eq(idCliente), eq(numeroTelefone), eq(texto), anyString());
    }

    @Test
    public void testEnviarSMS_PosPago_ConsumoExcedeLimite() {
        Long idCliente = 1L;
        double limiteMaximo = 0.5;
        double consumoNoMes = 0.6;
        String numeroTelefone = "123456789";
        String texto = "Teste";
        boolean isWhatsApp = false;

        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        cliente.setTipoPlano("pos-pago");
        cliente.setLimiteMaximo(limiteMaximo);
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        when(consumoSMSService.calcularConsumoNoPeriodo(eq(idCliente), anyString(), anyString())).thenReturn(consumoNoMes);

        assertThrows(IllegalStateException.class, () -> clienteService.enviarSMS(idCliente, numeroTelefone, texto, isWhatsApp));
        verify(consumoSMSService, never()).registrarConsumoSMS(eq(idCliente), eq(numeroTelefone), eq(texto), anyString());
    }

}
