package com.example.springboot.controllers;

import com.example.springboot.dtos.ClienteRecordDto;
import com.example.springboot.models.ClienteModel;
import com.example.springboot.repositories.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping("/cliente")
	public ResponseEntity<List<ClienteModel>> getAllCliente(){
		List<ClienteModel> ClienteList = clienteRepository.findAll();
		if(!ClienteList.isEmpty()) {
			for(ClienteModel Cliente : ClienteList) {
				UUID id = Cliente.getIdCliente();
				Cliente.add(linkTo(methodOn(ClienteController.class).getOneCliente(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(ClienteList);
	}


	@GetMapping("/cliente/{id}")
	public ResponseEntity<Object> getOneCliente(@PathVariable(value="id") UUID id){
		Optional<ClienteModel> ClienteO = clienteRepository.findById(id);
		if(ClienteO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente not found.");
		}
		ClienteO.get().add(linkTo(methodOn(ClienteController.class).getAllCliente()).withRel("Cliente List"));
		return ResponseEntity.status(HttpStatus.OK).body(ClienteO.get());
	}

    @PostMapping("/cliente")
	public ResponseEntity<Object> saveCliente(@RequestBody @Valid ClienteRecordDto ClienteRecordDto) {
		var ClienteModel = new ClienteModel();
		BeanUtils.copyProperties(ClienteRecordDto, ClienteModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(ClienteModel));
	}


	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<Object> deleteCliente(@PathVariable(value="id") UUID id) {
		Optional<ClienteModel> ClienteO = clienteRepository.findById(id);
		if(ClienteO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente not found.");
		}
		clienteRepository.delete(ClienteO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Cliente deleted successfully.");
	}
	
	@PutMapping("/cliente/{id}")
	public ResponseEntity<Object> updateCliente(@PathVariable(value="id") UUID id,
													  @RequestBody @Valid ClienteRecordDto ClienteRecordDto) {
		Optional<ClienteModel> ClienteO = clienteRepository.findById(id);
		if(ClienteO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente not found.");
		}
		var ClienteModel = ClienteO.get();
		BeanUtils.copyProperties(ClienteRecordDto, ClienteModel);
		return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.save(ClienteModel));
	}


	

} 
