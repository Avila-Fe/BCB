package org.example.bcb.model.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConsumoSMSTest {

    @Test
    public void testGetterAndSetters() {
        ConsumoSMS consumoSMS = new ConsumoSMS();
        consumoSMS.setId(1L);
        consumoSMS.setClienteId(2L);
        consumoSMS.setNumeroTelefone("123456789");
        consumoSMS.setTexto("Teste de mensagem");
        consumoSMS.setDataEnvio("2024-03-04");

        assertEquals(1L, consumoSMS.getId());
        assertEquals(2L, consumoSMS.getClienteId());
        assertEquals("123456789", consumoSMS.getNumeroTelefone());
        assertEquals("Teste de mensagem", consumoSMS.getTexto());
        assertEquals("2024-03-04", consumoSMS.getDataEnvio());
    }

    @Test
    public void testNoArgsConstructor() {
        ConsumoSMS consumoSMS = new ConsumoSMS();
        assertNotNull(consumoSMS);
    }

    @Test
    public void testAllArgsConstructor() {
        ConsumoSMS consumoSMS = new ConsumoSMS(1L, 2L, "123456789", "Teste de mensagem", "2024-03-04");

        assertEquals(1L, consumoSMS.getId());
        assertEquals(2L, consumoSMS.getClienteId());
        assertEquals("123456789", consumoSMS.getNumeroTelefone());
        assertEquals("Teste de mensagem", consumoSMS.getTexto());
        assertEquals("2024-03-04", consumoSMS.getDataEnvio());
    }

}
