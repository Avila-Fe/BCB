package org.example.bcb.service;

import org.example.bcb.model.domain.Cliente;
import org.example.bcb.model.domain.SMS;
import org.example.bcb.model.dto.ClienteDTO;
import org.example.bcb.repository.ClienteRepository;
import org.example.bcb.repository.SMSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.example.bcb.util.Constantes.*;

@Service
public class ClienteService {

    @Autowired
    private ConsumoSMSService consumoSMSService;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private SMSRepository smsRepository;

    public Cliente cadastrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setCnpj(clienteDTO.getCnpj());
        cliente.setNomeEmpresa(clienteDTO.getNomeEmpresa());
        cliente.setSaldo(0.0);
        cliente.setTipoPlano(clienteDTO.getTipoPlano());
        cliente.setLimiteMaximo(0.0);

        return clienteRepository.save(cliente);
    }

    public Cliente inserirCreditos(Long id, double creditos) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(CLIENTE_NAO_ENCONTRADO));
        if (cliente.getTipoPlano().equals(PRE_PAGO)) {
            double novoSaldo = cliente.getSaldo() + creditos;
            cliente.setSaldo(novoSaldo);
            return clienteRepository.save(cliente);
        } else {
            throw new IllegalStateException(CLIENTE_NAO_TIPO_PRE_PAGO);
        }
    }

    public Double consultaSaldo(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(CLIENTE_NAO_ENCONTRADO));
        return cliente.getSaldo();
    }

    public Cliente alterarLimite(Long id, double novoLimite) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(CLIENTE_NAO_ENCONTRADO));
        if (cliente.getTipoPlano().equals(POS_PAGO)) {
            cliente.setLimiteMaximo(novoLimite);
            return clienteRepository.save(cliente);
        } else {
            throw new IllegalStateException(CLIENTE_NAO_TIPO_POS_PAGO);
        }
    }

    public Cliente alterarPlano(Long id, String novoPlano) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(CLIENTE_NAO_ENCONTRADO));
        cliente.setTipoPlano(novoPlano);
        return clienteRepository.save(cliente);
    }

    public Cliente consultaCliente(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(CLIENTE_NAO_ENCONTRADO));
    }

    public void enviarSMS(Long idCliente, String numeroTelefone, String texto, boolean isWhatsApp) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new IllegalArgumentException(CLIENTE_NAO_ENCONTRADO));
        if (cliente.getTipoPlano().equals(PRE_PAGO)) {
            if (cliente.getSaldo() >= 0.25) {
                double novoSaldo = cliente.getSaldo() - 0.25;
                cliente.setSaldo(novoSaldo);
                clienteRepository.save(cliente);
                SMS sms = new SMS();
                sms.setTelefone(numeroTelefone);
                sms.setTexto(texto);
                sms.setWhatsAppFlag(isWhatsApp ? 1 : 0);
                smsRepository.save(sms);
            } else {
                throw new IllegalStateException(SALDO_INSUFICIENTE);
            }
        } else if (cliente.getTipoPlano().equals(POS_PAGO)) {
            double limiteMaximoMensal = cliente.getLimiteMaximo();
            String primeiroDiaDoMes = LocalDate.now().withDayOfMonth(1).toString();
            String ultimoDiaDoMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).toString();
            double consumoNoMes = consumoSMSService.calcularConsumoNoPeriodo(idCliente, primeiroDiaDoMes, ultimoDiaDoMes);

            if (consumoNoMes + 0.25 <= limiteMaximoMensal) {
                String dataEnvio = LocalDateTime.now().toString();
                consumoSMSService.registrarConsumoSMS(idCliente, numeroTelefone, texto, dataEnvio);
            } else {
                throw new IllegalStateException(LIMITE_MENSAGEM_EXCEDIDO);
            }
        }
    }
}
