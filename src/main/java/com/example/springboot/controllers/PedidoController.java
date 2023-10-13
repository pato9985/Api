package com.example.springboot.controllers;

import com.example.springboot.dtos.PedidoRecordDto;
import com.example.springboot.models.PedidoModel;
import com.example.springboot.repositories.PedidoRepository;
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
public class PedidoController {
    
    @Autowired
    PedidoRepository pedidoRepository;


	@GetMapping("/pedido/teste")
	public String index() {
		return "hello world";
	}

    @GetMapping("/pedido")
    public ResponseEntity<List<PedidoModel>> getAllPedido(){
		List<PedidoModel> pedidoList = pedidoRepository.findAll();
		if(!pedidoList.isEmpty()) {
			for(PedidoModel pedido : pedidoList) {
				UUID id = pedido.getIdPedido();
				pedido.add(linkTo(methodOn(PedidoController.class).getOnePedido(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(pedidoList);
	}

    @GetMapping("/pedido/{id}")
	public ResponseEntity<Object> getOnePedido(@PathVariable(value="id") UUID id){
		Optional<PedidoModel> PedidoO = pedidoRepository.findById(id);
		if(PedidoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido not found.");
		}
		PedidoO.get().add(linkTo(methodOn(PedidoController.class).getAllPedido()).withRel("Pedido List"));
		return ResponseEntity.status(HttpStatus.OK).body(PedidoO.get());
	}

    @PostMapping("/pedido")
	public ResponseEntity<PedidoModel> savePedido(@RequestBody @Valid PedidoRecordDto PedidoRecordDto) {
		var PedidoModel = new PedidoModel();
		BeanUtils.copyProperties(PedidoRecordDto, PedidoModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.save(PedidoModel));
	}
	
	@DeleteMapping("/pedido/{id}")
	public ResponseEntity<Object> deletePedido(@PathVariable(value="id") UUID id) {
		Optional<PedidoModel> PedidoO = pedidoRepository.findById(id);
		if(PedidoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido not found.");
		}
		pedidoRepository.delete(PedidoO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Pedido deleted successfully.");
	}

    @PutMapping("/pedido/{id}")
	public ResponseEntity<Object> updatePedido(@PathVariable(value="id") UUID id,
													  @RequestBody @Valid PedidoRecordDto PedidoRecordDto) {
		Optional<PedidoModel> PedidoO = pedidoRepository.findById(id);
		if(PedidoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido not found.");
		}
		var PedidoModel = PedidoO.get(); 
		BeanUtils.copyProperties(PedidoRecordDto, PedidoModel);
		return ResponseEntity.status(HttpStatus.OK).body(pedidoRepository.save(PedidoModel));
	}

}
