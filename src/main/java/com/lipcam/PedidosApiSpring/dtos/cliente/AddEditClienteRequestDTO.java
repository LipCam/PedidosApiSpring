package com.lipcam.PedidosApiSpring.dtos.cliente;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class AddEditClienteRequestDTO {
    @NotBlank(message = "Campo Nome deve ser preenchido")
    String Nome;

    @NotBlank(message = "Campo CPF deve ser preenchido")
    @CPF(message = "CPF com formato inválido")
    String Cpf;
}
