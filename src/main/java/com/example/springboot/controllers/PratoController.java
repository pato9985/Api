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
	PratoRepository PratoRepository;
	
	@GetMapping("/Prato")
	public ResponseEntity<List<PratoModel>> getAllPrato(){
		List<PratoModel> PratoList = PratoRepository.findAll();
		if(!PratoList.isEmpty()) {
			for(PratoModel Prato : PratoList) {
				UUID id = Prato.getIdPrato();
				Prato.add(linkTo(methodOn(PratoController.class).getOnePrato(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(PratoList);
	}


	@GetMapping("/Prato/{id}")
	public ResponseEntity<Object> getOnePrato(@PathVariable(value="id") UUID id){
		Optional<PratoModel> PratoO = PratoRepository.findById(id);
		if(PratoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prato not found.");
		}
		PratoO.get().add(linkTo(methodOn(PratoController.class).getAllPrato()).withRel("Prato List"));
		return ResponseEntity.status(HttpStatus.OK).body(PratoO.get());
	}
	
	@PostMapping("/Prato")
	public ResponseEntity<PratoModel> savePrato(@RequestBody @Valid PratoRecordDto PratoRecordDto) {
		var PratoModel = new PratoModel();
		BeanUtils.copyProperties(PratoRecordDto, PratoModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(PratoRepository.save(PratoModel));
	}
	
	@DeleteMapping("/Prato/{id}")
	public ResponseEntity<Object> deletePrato(@PathVariable(value="id") UUID id) {
		Optional<PratoModel> PratoO = PratoRepository.findById(id);
		if(PratoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prato not found.");
		}
		PratoRepository.delete(PratoO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Prato deleted successfully.");
	}
	
	@PutMapping("/Prato/{id}")
	public ResponseEntity<Object> updatePrato(@PathVariable(value="id") UUID id,
													  @RequestBody @Valid PratoRecordDto PratoRecordDto) {
		Optional<PratoModel> PratoO = PratoRepository.findById(id);
		if(PratoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prato not found.");
		}
		var PratoModel = PratoO.get();
		BeanUtils.copyProperties(PratoRecordDto, PratoModel);
		return ResponseEntity.status(HttpStatus.OK).body(PratoRepository.save(PratoModel));
	}


	

}
