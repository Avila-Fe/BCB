package org.example.bcb.service;

import org.example.bcb.model.domain.ConsumoSMS;
import org.example.bcb.repository.ConsumoSMSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumoSMSService {

    @Autowired
    private ConsumoSMSRepository consumoSMSRepository;

    public void registrarConsumoSMS(Long clienteId, String numeroTelefone, String texto, String dataEnvio) {
        ConsumoSMS consumoSMS = new ConsumoSMS();
        consumoSMS.setClienteId(clienteId);
        consumoSMS.setNumeroTelefone(numeroTelefone);
        consumoSMS.setTexto(texto);
        consumoSMS.setDataEnvio(dataEnvio);
        consumoSMSRepository.save(consumoSMS);
    }

    public double calcularConsumoNoPeriodo(Long clienteId, String inicioPeriodo, String fimPeriodo) {
        List<ConsumoSMS> consumosNoPeriodo = consumoSMSRepository.findByClienteIdAndDataEnvioBetween(
                clienteId, inicioPeriodo, fimPeriodo);

        double consumoTotal = 0.0;

        for (ConsumoSMS consumo : consumosNoPeriodo) {
            consumoTotal += 0.25;
        }
        return consumoTotal;
    }

}
