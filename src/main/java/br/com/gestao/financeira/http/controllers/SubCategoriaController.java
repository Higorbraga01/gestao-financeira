package br.com.gestao.financeira.http.controllers;


import br.com.gestao.financeira.http.request.SubCategoriaRequest;
import br.com.gestao.financeira.models.SubCategoria;
import br.com.gestao.financeira.repositories.SubCategoriaRepository;
import br.com.gestao.financeira.services.SubCategoriaService;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Sub-Categoria")
public class SubCategoriaController {

    private final SubCategoriaService service;

    @Autowired
    public SubCategoriaController(SubCategoriaService service) {
        this.service = service;
    }

    @PostMapping("/sub_categoria")
    public ResponseEntity<SubCategoria> createSubCategoria(@RequestBody SubCategoriaRequest dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/sub_categoria/{id}")
    public ResponseEntity<SubCategoria> findSubCategoriaById(@PathVariable("id") Long id){
        return ResponseEntity.of(service.findById(id));
    }

    @GetMapping("/sub_categoria")
    public ResponseEntity<Page<SubCategoria>> findAllSubCategorias(
            @QuerydslPredicate(root = SubCategoria.class, bindings = SubCategoriaRepository.class) @Parameter(hidden = true) Predicate predicate,
            @Parameter(hidden = true) Pageable pageable){
        return ResponseEntity.ok(service.findAll(predicate,pageable));
    }

    @PatchMapping("/sub_categoria/{id}")
    public ResponseEntity<SubCategoria> updateSubCategoria(@PathVariable("id") Long id, @RequestBody SubCategoriaRequest dto){
        return ResponseEntity.ok(service.update(id,dto));
    }

    @DeleteMapping("/sub_categoria/{id}")
    public ResponseEntity<?> deleteSubCategoria(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
