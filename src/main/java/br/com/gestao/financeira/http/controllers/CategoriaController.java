package br.com.gestao.financeira.http.controllers;

import br.com.gestao.financeira.http.request.CategoriaRequest;
import br.com.gestao.financeira.models.Categoria;
import br.com.gestao.financeira.repositories.CategoriaRepository;
import br.com.gestao.financeira.services.CategoriaService;
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
@Tag(name = "Categoria")
public class CategoriaController {

    private final CategoriaService service;

    @Autowired
    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @PostMapping("/categoria")
    public ResponseEntity<Categoria> createCategoria(@RequestBody CategoriaRequest dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<Categoria> findCategoriaById(@PathVariable("id") Long id){
        return ResponseEntity.of(service.findById(id));
    }

    @GetMapping("/categoria")
    public ResponseEntity<Page<Categoria>> findAllCategorias(
            @QuerydslPredicate(root = Categoria.class, bindings = CategoriaRepository.class) @Parameter(hidden = true) Predicate predicate,
            @Parameter(hidden = true) Pageable pageable){
        return ResponseEntity.ok(service.findAll(predicate,pageable));
    }

    @PatchMapping("/categoria/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable("id") Long id, @RequestBody CategoriaRequest dto){
        return ResponseEntity.ok(service.update(id,dto));
    }

    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
