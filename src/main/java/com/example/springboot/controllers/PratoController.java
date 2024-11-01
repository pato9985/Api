package com.example.springboot.controllers;

import com.example.springboot.dtos.PratoRecordDto;
import com.example.springboot.models.PratoModel;
import com.example.springboot.repositories.PratoRepository;
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
public class PratoController {
	
	@Autowired
	PratoRepository pratoRepository;
	
	@GetMapping("/prato")
	public ResponseEntity<List<PratoModel>> getAllPrato(){
		List<PratoModel> PratoList = pratoRepository.findAll();
		if(!PratoList.isEmpty()) {
			for(PratoModel Prato : PratoList) {
				UUID id = Prato.getIdPrato();
				Prato.add(linkTo(methodOn(PratoController.class).getOnePrato(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(PratoList);
	}


	@GetMapping("/prato/{id}")
	public ResponseEntity<Object> getOnePrato(@PathVariable(value="id") UUID id){
		Optional<PratoModel> PratoO = pratoRepository.findById(id);
		if(PratoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prato not found.");
		}
		PratoO.get().add(linkTo(methodOn(PratoController.class).getAllPrato()).withRel("Prato List"));
		return ResponseEntity.status(HttpStatus.OK).body(PratoO.get());
	}
	
	@PostMapping("/prato")
	public ResponseEntity<PratoModel> savePrato(@RequestBody @Valid PratoRecordDto pratoRecordDto) {
		var PratoModel = new PratoModel();
		BeanUtils.copyProperties(pratoRecordDto, PratoModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(pratoRepository.save(PratoModel));
	}
	
	@DeleteMapping("/prato/{id}")
	public ResponseEntity<Object> deletePrato(@PathVariable(value="id") UUID id) {
		Optional<PratoModel> PratoO = pratoRepository.findById(id);
		if(PratoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prato not found.");
		}
		pratoRepository.delete(PratoO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Prato deleted successfully.");
	}
	
	@PutMapping("/prato/{id}")
	public ResponseEntity<Object> updatePrato(@PathVariable(value="id") UUID id,
													  @RequestBody @Valid PratoRecordDto PratoRecordDto) {
		Optional<PratoModel> PratoO = pratoRepository.findById(id);
		if(PratoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prato not found.");
		}
		var PratoModel = PratoO.get();
		BeanUtils.copyProperties(PratoRecordDto, PratoModel);
		return ResponseEntity.status(HttpStatus.OK).body(pratoRepository.save(PratoModel));
	}


	

}
