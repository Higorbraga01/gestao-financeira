package br.com.gestao.financeira.http.controllers;

import br.com.gestao.financeira.http.request.LancamentoRequest;
import br.com.gestao.financeira.models.Lancamento;
import br.com.gestao.financeira.repositories.LancamentoRepository;
import br.com.gestao.financeira.services.LancamentoService;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Lancamento")
public class LancamentoController {

    private final LancamentoService service;

    @Autowired
    public LancamentoController(LancamentoService service) {
        this.service = service;
    }

    @PostMapping("/lancamento")
    public ResponseEntity<Lancamento> createLancamento(@RequestBody  @Valid LancamentoRequest dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/lancamento/{id}")
    public ResponseEntity<Lancamento> findLancamentoById(@PathVariable("id") Long id){
        return ResponseEntity.of(service.findById(id));
    }

    @GetMapping("/lancamento")
    public ResponseEntity<Page<Lancamento>> findAllLancamentos(
            @QuerydslPredicate(root = Lancamento.class, bindings = LancamentoRepository.class) @Parameter(hidden = true) Predicate predicate,
            @Parameter(hidden = true) Pageable pageable){
        return ResponseEntity.ok(service.findAll(predicate,pageable));
    }

    @PatchMapping("/lancamento/{id}")
    public ResponseEntity<Lancamento> updateLancamento(@PathVariable("id") Long id, @RequestBody LancamentoRequest dto){
        return ResponseEntity.ok(service.update(id,dto));
    }

    @DeleteMapping("/lancamento/{id}")
    public ResponseEntity<?> deleteLancamento(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
