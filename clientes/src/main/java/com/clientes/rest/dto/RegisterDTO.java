package com.clientes.rest.dto;

import com.clientes.model.entity.RegraUsuario;

public record RegisterDTO (String login, String password, RegraUsuario role) {

}
