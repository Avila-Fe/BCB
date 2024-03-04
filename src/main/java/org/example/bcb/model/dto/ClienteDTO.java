package org.example.bcb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String cnpj;
    private String nomeEmpresa;
    private String tipoPlano;

}
