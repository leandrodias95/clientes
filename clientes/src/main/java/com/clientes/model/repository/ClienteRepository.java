package com.clientes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clientes.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
