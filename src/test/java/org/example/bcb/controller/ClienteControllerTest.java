package org.example.bcb.controller;

import org.example.bcb.model.domain.Cliente;
import org.example.bcb.model.dto.ClienteDTO;
import org.example.bcb.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    public void cadastrarCliente_ReturnsCreatedResponse() {

        ClienteDTO clienteDTO = new ClienteDTO();
        Cliente cliente = new Cliente();
        when(clienteService.cadastrarCliente(clienteDTO)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.cadastrarCliente(clienteDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    public void inserirCreditos_ReturnsOkResponse() {

        Long id = 1L;
        double creditos = 100.0;
        Cliente cliente = new Cliente();
        when(clienteService.inserirCreditos(id, creditos)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.inserirCreditos(id, creditos);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    public void consultaSaldo_ReturnsOkResponse() {

        Long id = 1L;
        double saldo = 500.0;
        when(clienteService.consultaSaldo(id)).thenReturn(saldo);

        ResponseEntity<Double> response = clienteController.consultaSaldo(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saldo, response.getBody());
    }

    @Test
    public void alterarLimite_ReturnsOkResponse() {
        Long id = 1L;
        double novoLimite = 1000.0;
        Cliente cliente = new Cliente();

        when(clienteService.alterarLimite(id, novoLimite)).thenReturn(cliente);
        ResponseEntity<Cliente> response = clienteController.alterarLimite(id, novoLimite);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    public void alterarPlano_ReturnsOkResponse() {
        Long id = 1L;
        String novoPlano = "Plano B";
        Cliente cliente = new Cliente();
        when(clienteService.alterarPlano(id, novoPlano)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.alterarPlano(id, novoPlano);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    public void consultaCliente_ReturnsOkResponse() {
        Long id = 1L;
        Cliente cliente = new Cliente();
        when(clienteService.consultaCliente(id)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.consultaCliente(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }
}
