package com.clientes.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.clientes.model.entity.Cliente;
import com.clientes.model.entity.ServicoPrestado;
import com.clientes.model.repository.ClienteRepository;
import com.clientes.model.repository.ServicoPrestadoRepository;
import com.clientes.rest.dto.ServicoPrestadoDTO;
import com.clientes.util.BigDecimalConverter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servico/api/servico-prestado")
public class ServicoPrestadoController {
	
	private final ServicoPrestadoRepository repository; 
	private final ClienteRepository clienteRepository; 
	private final BigDecimalConverter bigDecimalConverter;
	
	@PostMapping(value="insert")
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoPrestado savar(@RequestBody @Valid ServicoPrestadoDTO dto, Cliente cliente  ){
		Integer idCliente = Integer.parseInt(dto.getIdCliente());
		Optional<Cliente> optCliente = clienteRepository.findById(idCliente);
		if(optCliente.isPresent()) {
			Cliente cliente1 = optCliente.get();
			LocalDate data =  LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			ServicoPrestado servicoPrestado = new ServicoPrestado();
			servicoPrestado.setDescricao(dto.getDescricao());
			servicoPrestado.setData(data);
			servicoPrestado.setCliente(cliente1);
			servicoPrestado.setValor(bigDecimalConverter.converter(dto.getValor()));
			return repository.save(servicoPrestado);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
		}

	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ServicoPrestado> pesquisar(
			@RequestParam(value= "nome", required = true) String nome,
			@RequestParam(value= "mes", required = true) Integer mes){
		List<ServicoPrestado> result= repository.findByNomeClienteAndMes("%"+nome+"%", mes);
			return Optional.ofNullable(result) //contem ou não a lista resultante
	                   .filter(list -> !list.isEmpty()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado!"));
			}
}
