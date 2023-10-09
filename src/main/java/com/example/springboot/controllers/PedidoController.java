package com.example.springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.repositories.PedidoRepository;

@RestController
public class PedidoController {
    
    @Autowired
    PedidoRepository PedidoRepository;

    @GetMapping("/Pedido")
    public ResponseEntity<List<PedidoModel>> getAllPedido(){
		List<PedidoModel> pedidoList = pedidoRepository.findAll();
		if(!pedidoList.isEmpty()) {
			for(pedidoModel pedido : pedidoList) {
				UUID id = pedido.getIdPedido();
				Pedido.add(linkTo(methodOn(PedidoController.class).getOnePedido(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(PedidoList);
	}

    @GetMapping("/Pedido/{id}")
	public ResponseEntity<Object> getOnePedido(@PathVariable(value="id") UUID id){
		Optional<PedidoModel> PedidoO = PedidoRepository.findById(id);
		if(PedidoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido not found.");
		}
		PedidoO.get().add(linkTo(methodOn(PedidoController.class).getAllPedido()).withRel("Pedido List"));
		return ResponseEntity.status(HttpStatus.OK).body(PedidoO.get());
	}

    @PostMapping("/Pedido")
	public ResponseEntity<PedidoModel> savePedido(@RequestBody @Valid PedidoRecordDto PedidoRecordDto) {
		var PedidoModel = new PedidoModel();
		BeanUtils.copyProperties(PedidoRecordDto, PedidoModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(PedidoRepository.save(PedidoModel));
	}
	
	@DeleteMapping("/Pedido/{id}")
	public ResponseEntity<Object> deletePedido(@PathVariable(value="id") UUID id) {
		Optional<PedidoModel> PedidoO = PedidoRepository.findById(id);
		if(PedidoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido not found.");
		}
		PedidoRepository.delete(PedidoO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Pedido deleted successfully.");
	}

    @PutMapping("/Pedido/{id}")
	public ResponseEntity<Object> updatePedido(@PathVariable(value="id") UUID id,
													  @RequestBody @Valid PedidoRecordDto PedidoRecordDto) {
		Optional<PedidoModel> PedidoO = PedidoRepository.findById(id);
		if(PedidoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido not found.");
		}
		var PedidoModel = PedidoO.get();
		BeanUtils.copyProperties(PedidoRecordDto, PedidoModel);
		return ResponseEntity.status(HttpStatus.OK).body(PedidoRepository.save(PedidoModel));
	}

}
