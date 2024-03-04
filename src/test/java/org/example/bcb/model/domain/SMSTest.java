package org.example.bcb.model.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SMSTest {

    @Test
    public void testGetterAndSetters() {
        SMS sms = new SMS();
        sms.setId(1L);
        sms.setTelefone("123456789");
        sms.setTexto("Teste de mensagem");
        sms.setWhatsAppFlag(0);

        assertEquals(1L, sms.getId());
        assertEquals("123456789", sms.getTelefone());
        assertEquals("Teste de mensagem", sms.getTexto());
        assertEquals(0, sms.getWhatsAppFlag());
    }

    @Test
    public void testNoArgsConstructor() {
        SMS sms = new SMS();
        assertNotNull(sms);
    }

    @Test
    public void testAllArgsConstructor() {
        SMS sms = new SMS(1L, "123456789", "Teste de mensagem", 0);

        assertEquals(1L, sms.getId());
        assertEquals("123456789", sms.getTelefone());
        assertEquals("Teste de mensagem", sms.getTexto());
        assertEquals(0, sms.getWhatsAppFlag());
    }

}
