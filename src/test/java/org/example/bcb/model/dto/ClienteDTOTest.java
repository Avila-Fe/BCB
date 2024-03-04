package org.example.bcb.model.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteDTOTest {

    @Test
    public void testGetterAndSetters() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("João");
        clienteDTO.setEmail("joao@example.com");
        clienteDTO.setTelefone("123456789");
        clienteDTO.setCpf("123.456.789-00");
        clienteDTO.setCnpj("12.345.678/0001-90");
        clienteDTO.setNomeEmpresa("Empresa");
        clienteDTO.setTipoPlano("Plano A");

        assertEquals("João", clienteDTO.getNome());
        assertEquals("joao@example.com", clienteDTO.getEmail());
        assertEquals("123456789", clienteDTO.getTelefone());
        assertEquals("123.456.789-00", clienteDTO.getCpf());
        assertEquals("12.345.678/0001-90", clienteDTO.getCnpj());
        assertEquals("Empresa", clienteDTO.getNomeEmpresa());
        assertEquals("Plano A", clienteDTO.getTipoPlano());
    }

    @Test
    public void testNoArgsConstructor() {
        ClienteDTO clienteDTO = new ClienteDTO();
        assertNotNull(clienteDTO);
    }

    @Test
    public void testAllArgsConstructor() {
        ClienteDTO clienteDTO = new ClienteDTO("João", "joao@example.com", "123456789",
                "123.456.789-00", "12.345.678/0001-90", "Empresa", "Plano A");

        assertEquals("João", clienteDTO.getNome());
        assertEquals("joao@example.com", clienteDTO.getEmail());
        assertEquals("123456789", clienteDTO.getTelefone());
        assertEquals("123.456.789-00", clienteDTO.getCpf());
        assertEquals("12.345.678/0001-90", clienteDTO.getCnpj());
        assertEquals("Empresa", clienteDTO.getNomeEmpresa());
        assertEquals("Plano A", clienteDTO.getTipoPlano());
    }

}
