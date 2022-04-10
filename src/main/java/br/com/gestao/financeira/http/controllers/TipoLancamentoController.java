package br.com.gestao.financeira.http.controllers;

import br.com.gestao.financeira.http.request.TipoLancamentoRequest;
import br.com.gestao.financeira.models.TipoLancamento;
import br.com.gestao.financeira.repositories.TipoLancamentoRepository;
import br.com.gestao.financeira.services.TipoLancamentoService;
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
@Tag(name = "Tipo-Lancamento")
public class TipoLancamentoController {

    private final TipoLancamentoService service;

    @Autowired
    public TipoLancamentoController(TipoLancamentoService service) {
        this.service = service;
    }

    @PostMapping("/tipo_lancamento")
    public ResponseEntity<TipoLancamento> createTipoLancamento(@RequestBody TipoLancamentoRequest dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/tipo_lancamento/{id}")
    public ResponseEntity<TipoLancamento> findTipoLancamentoById(@PathVariable("id") Long id){
        return ResponseEntity.of(service.findById(id));
    }

    @GetMapping("/tipo_lancamento")
    public ResponseEntity<Page<TipoLancamento>> findAllTipoLancamentos(
            @QuerydslPredicate(root = TipoLancamento.class, bindings = TipoLancamentoRepository.class) @Parameter(hidden = true) Predicate predicate,
            @Parameter(hidden = true) Pageable pageable){
        return ResponseEntity.ok(service.findAll(predicate,pageable));
    }

    @PatchMapping("/tipo_lancamento/{id}")
    public ResponseEntity<TipoLancamento> updateTipoLancamento(@PathVariable("id") Long id, @RequestBody TipoLancamentoRequest dto){
        return ResponseEntity.ok(service.update(id,dto));
    }

    @DeleteMapping("/tipo_lancamento/{id}")
    public ResponseEntity<?> deleteTipoLancamento(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
