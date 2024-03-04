package org.example.bcb.model.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClienteTest {

    @Test
    public void testGetterAndSetters() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João");
        cliente.setEmail("joao@example.com");
        cliente.setTelefone("123456789");
        cliente.setCpf("123.456.789-00");
        cliente.setCnpj("12.345.678/0001-90");
        cliente.setNomeEmpresa("Empresa");
        cliente.setSaldo(1000.0);
        cliente.setTipoPlano("Plano A");
        cliente.setLimiteMaximo(2000.0);

        assertEquals(1L, cliente.getId());
        assertEquals("João", cliente.getNome());
        assertEquals("joao@example.com", cliente.getEmail());
        assertEquals("123456789", cliente.getTelefone());
        assertEquals("123.456.789-00", cliente.getCpf());
        assertEquals("12.345.678/0001-90", cliente.getCnpj());
        assertEquals("Empresa", cliente.getNomeEmpresa());
        assertEquals(1000.0, cliente.getSaldo());
        assertEquals("Plano A", cliente.getTipoPlano());
        assertEquals(2000.0, cliente.getLimiteMaximo());
    }

    @Test
    public void testNoArgsConstructor() {
        Cliente cliente = new Cliente();
        assertNotNull(cliente);
    }

    @Test
    public void testAllArgsConstructor() {
        Cliente cliente = new Cliente(1L, "João", "joao@example.com", "123456789",
                "123.456.789-00", "12.345.678/0001-90", "Empresa", 1000.0, "Plano A", 2000.0);

        assertEquals(1L, cliente.getId());
        assertEquals("João", cliente.getNome());
        assertEquals("joao@example.com", cliente.getEmail());
        assertEquals("123456789", cliente.getTelefone());
        assertEquals("123.456.789-00", cliente.getCpf());
        assertEquals("12.345.678/0001-90", cliente.getCnpj());
        assertEquals("Empresa", cliente.getNomeEmpresa());
        assertEquals(1000.0, cliente.getSaldo());
        assertEquals("Plano A", cliente.getTipoPlano());
        assertEquals(2000.0, cliente.getLimiteMaximo());
    }

}
