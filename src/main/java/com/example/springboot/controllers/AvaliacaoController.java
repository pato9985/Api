package main.java.com.example.springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.springboot.controllers.AvaliacaoController;

@RestController
public class AvaliacaoController {
    
    @Autowired
    AvaliacaoRepository AvaliacaoRepository;

     @GetMapping("/Avaliacao")
    public ResponseEntity<List<AvaliacaoModel>> getAllAvaliacao(){
		List<AvaliacaoModel> avaliacaoList = AvaliacaoRepository.findAll();
		if(!avaliacaoList.isEmpty()) {
			for(avaliacaoModel avaliacao : avaliacaoList) {
				UUID id = avaliacao.getIdAvaliacao();
				Avaliacao.add(linkTo(methodOn(AvaliacaoController.class).getOneAvaliacao(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(AvaliacaoList);
	}

    @GetMapping("/Avaliacao/{id}")
	public ResponseEntity<Object> getOneAvaliacao(@PathVariable(value="id") UUID id){
		Optional<AvaliacaoModel> AvaliacaoO = AvaliacaoRepository.findById(id);
		if(AvaliacaoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliacao not found.");
		}
		AvaliacaoO.get().add(linkTo(methodOn(AvaliacaoController.class).getAllAvaliacao()).withRel("Avaliacao List"));
		return ResponseEntity.status(HttpStatus.OK).body(AvaliacaoO.get());
	}

    @PostMapping("/Avaliacao")
	public ResponseEntity<AvaliacaoModel> saveAvaliacao(@RequestBody @Valid AvaliacaoRecordDto AvaliacaoRecordDto) {
		var AvaliacaoModel = new AvaliacaoModel();
		BeanUtils.copyProperties(AvaliacaoRecordDto, AvaliacaoModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(AvaliacaoRepository.save(AvaliacaoModel));
	}
	
	@DeleteMapping("/Avaliacao/{id}")
	public ResponseEntity<Object> deleteAvaliacao(@PathVariable(value="id") UUID id) {
		Optional<AvaliacaoModel> AvaliacaoO = AvaliacaoRepository.findById(id);
		if(AvaliacaoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliacao not found.");
		}
		AvaliacaoRepository.delete(AvaliacaoO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Avaliacao deleted successfully.");
	}

    @PutMapping("/Avaliacao/{id}")
	public ResponseEntity<Object> updateAvaliacao(@PathVariable(value="id") UUID id,
													  @RequestBody @Valid AvaliacaoRecordDto AvaliacaoRecordDto) {
		Optional<AvaliacaoModel> AvaliacaoO = AvaliacaoRepository.findById(id);
		if(AvaliacaoO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliacao not found.");
		}
		var AvaliacaoModel = AvaliacaoO.get();
		BeanUtils.copyProperties(AvaliacaoRecordDto, AvaliacaoModel);
		return ResponseEntity.status(HttpStatus.OK).body(AvaliacaoRepository.save(AvaliacaoModel));
	}

}





