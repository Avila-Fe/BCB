package org.example.bcb.service;

import org.example.bcb.model.domain.ConsumoSMS;
import org.example.bcb.repository.ConsumoSMSRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsumoSMSServiceTest {

    @Mock
    private ConsumoSMSRepository consumoSMSRepository;

    @InjectMocks
    private ConsumoSMSService consumoSMSService;

    public ConsumoSMSServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistrarConsumoSMS() {
        Long clienteId = 1L;
        String numeroTelefone = "123456789";
        String texto = "Teste";
        String dataEnvio = "2024-03-04T12:00:00";

        ConsumoSMS consumoSMS = new ConsumoSMS();
        consumoSMS.setClienteId(clienteId);
        consumoSMS.setNumeroTelefone(numeroTelefone);
        consumoSMS.setTexto(texto);
        consumoSMS.setDataEnvio(dataEnvio);

        when(consumoSMSRepository.save(any(ConsumoSMS.class))).thenReturn(consumoSMS);

        consumoSMSService.registrarConsumoSMS(clienteId, numeroTelefone, texto, dataEnvio);

        verify(consumoSMSRepository, times(1)).save(any(ConsumoSMS.class));
    }

    @Test
    public void testCalcularConsumoNoPeriodo() {
        Long clienteId = 1L;
        String inicioPeriodo = "2024-03-01";
        String fimPeriodo = "2024-03-04";

        List<ConsumoSMS> consumosNoPeriodo = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            ConsumoSMS consumoSMS = new ConsumoSMS();
            consumoSMS.setClienteId(clienteId);
            consumoSMS.setDataEnvio("2024-03-0" + i);
            consumosNoPeriodo.add(consumoSMS);
        }

        when(consumoSMSRepository.findByClienteIdAndDataEnvioBetween(eq(clienteId), eq(inicioPeriodo), eq(fimPeriodo)))
                .thenReturn(consumosNoPeriodo);

        double consumoTotal = consumoSMSService.calcularConsumoNoPeriodo(clienteId, inicioPeriodo, fimPeriodo);

        assertEquals(1.0, consumoTotal);
    }
}
