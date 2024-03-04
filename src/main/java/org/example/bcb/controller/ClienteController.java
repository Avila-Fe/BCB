package org.example.bcb.controller;

import org.example.bcb.model.domain.Cliente;
import org.example.bcb.model.dto.ClienteDTO;
import org.example.bcb.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.cadastrarCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PutMapping(value = "/{id}/inserirCreditos")
    public ResponseEntity<Cliente> inserirCreditos(@PathVariable Long id, @RequestParam double creditos) {
        Cliente cliente = clienteService.inserirCreditos(id, creditos);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping(value = "/{id}/consultaSaldo")
    public ResponseEntity<Double> consultaSaldo(@PathVariable Long id) {
        Double saldo = clienteService.consultaSaldo(id);
        return ResponseEntity.ok(saldo);
    }

    @PutMapping(value = "/{id}/alterarLimite")
    public ResponseEntity<Cliente> alterarLimite(@PathVariable Long id, @RequestParam double novoLimite) {
        Cliente cliente = clienteService.alterarLimite(id, novoLimite);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping(value = "/{id}/alterarPlano")
    public ResponseEntity<Cliente> alterarPlano(@PathVariable Long id, @RequestParam String novoPlano) {
        Cliente cliente = clienteService.alterarPlano(id, novoPlano);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> consultaCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.consultaCliente(id);
        return ResponseEntity.ok(cliente);
    }
}
