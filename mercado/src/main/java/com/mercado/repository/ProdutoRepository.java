package com.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercado.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
