package com.clientes.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.clientes.model.entity.Cliente;
import com.clientes.model.repository.ClienteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/servico/api/clientes") //similar a webservlet
public class ClienteController {
	
	
	private final ClienteRepository repository;

	public ClienteController(ClienteRepository repository) {
		this.repository = repository;
	}
	
	@PostMapping(value="insert")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar(@RequestBody @Valid Cliente cliente) { //@RequestBody indica que o objeto vem da requisição
		return repository.save(cliente);
	}

	@GetMapping("{id}") //cria variavel na url
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Cliente>  acharPorId(@PathVariable Integer id){
		Optional<Cliente> optCliente = repository.findById(id);
		if(optCliente.isPresent()) {
			Cliente cliente = optCliente.get();
			return ResponseEntity.ok(cliente);
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		repository.findById(id)
		.map(cliente ->{ //serve para fazer mapeamento do objeto para alguma coisa, tansforma dados
			repository.delete(cliente);
			return cliente;
		})
		.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid Cliente clienteAtualizado) {
		repository.findById(id)
		.map(cliente ->{ //serve para fazer mapeamento do objeto para alguma coisa, tansforma dados
			clienteAtualizado.setId(cliente.getId());
			return repository.save(clienteAtualizado);
		})
		.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}
	
	@GetMapping
	public List<Cliente>obterTodos(){
		return repository.findAll();
	}
}
