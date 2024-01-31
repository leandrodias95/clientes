package com.clientes.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServicoPrestadoDTO {
	@NotEmpty(message="{campo.descricao.obrigatorio}")
	private String descricao;
	@NotEmpty(message="{campo.preco.obrigatorio}")
	private String valor;
	@NotEmpty(message="{campo.data.obrigatorio}")
	private String data;
	@NotEmpty(message="{campo.cliente.obrigatorio}")
	private String idCliente;
}
