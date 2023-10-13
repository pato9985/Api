package com.example.springboot.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.models.AvaliacaoModel;
import com.example.springboot.repositories.AvaliacaoRepository;
import com.example.springboot.dtos.AvaliacaoRecordDto;
import jakarta.validation.Valid;

@RestController
public class AvaliacaoController {
    
    @Autowired
    AvaliacaoRepository avaliacaoRepository;

     @GetMapping("/avaliacao")
    public ResponseEntity<List<AvaliacaoModel>> getAllAvaliacao(){
		List<AvaliacaoModel> avaliacaoList = avaliacaoRepository.findAll();
		if(!avaliacaoList.isEmpty()) {
			for(AvaliacaoModel avaliacao : avaliacaoList) {
				UUID id = avaliacao.getIdAvaliacao();
				avaliacao.add(linkTo(methodOn(AvaliacaoController.class).getOneAvaliacao(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(avaliacaoList);
	}

    @GetMapping("/avaliacao/{id}")
	public ResponseEntity<Object> getOneAvaliacao(@PathVariable(value="id") UUID id){
		Optional<AvaliacaoModel> AvaliacaoO = avaliacaoRepository.findById(id);
		if(AvaliacaoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliacao not found.");
		}
		AvaliacaoO.get().add(linkTo(methodOn(AvaliacaoController.class).getAllAvaliacao()).withRel("Avaliacao List"));
		return ResponseEntity.status(HttpStatus.OK).body(AvaliacaoO.get());
	}

    @PostMapping("/avaliacao")
	public ResponseEntity<AvaliacaoModel> saveAvaliacao(@RequestBody @Valid AvaliacaoRecordDto AvaliacaoRecordDto) {
		var AvaliacaoModel = new AvaliacaoModel();
		BeanUtils.copyProperties(AvaliacaoRecordDto, AvaliacaoModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoRepository.save(AvaliacaoModel));
	}
	
	@DeleteMapping("/avaliacao/{id}")
	public ResponseEntity<Object> deleteAvaliacao(@PathVariable(value="id") UUID id) {
		Optional<AvaliacaoModel> AvaliacaoO = avaliacaoRepository.findById(id);
		if(AvaliacaoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliacao not found.");
		}
		avaliacaoRepository.delete(AvaliacaoO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Avaliacao deleted successfully.");
	}

    @PutMapping("/avaliacao/{id}")
	public ResponseEntity<Object> updateAvaliacao(@PathVariable(value="id") UUID id,
													  @RequestBody @Valid AvaliacaoRecordDto AvaliacaoRecordDto) {
		Optional<AvaliacaoModel> AvaliacaoO = avaliacaoRepository.findById(id);
		if(AvaliacaoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliacao not found.");
		}
		var AvaliacaoModel = AvaliacaoO.get();
		BeanUtils.copyProperties(AvaliacaoRecordDto, AvaliacaoModel);
		return ResponseEntity.status(HttpStatus.OK).body(avaliacaoRepository.save(AvaliacaoModel));
	}

}





