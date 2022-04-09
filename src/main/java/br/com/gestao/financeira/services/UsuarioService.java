package br.com.gestao.financeira.services;

import br.com.gestao.financeira.models.Usuario;
import br.com.gestao.financeira.repositories.UsuarioRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements BaseService<Usuario> {

    private final UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario create(Usuario dto) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(dto, usuario);
        return repository.save(usuario);
    }

    @Override
    public Usuario update(Long id, Usuario dto) {
        Optional<Usuario> found = repository.findById(id);
        found.ifPresent(usuario -> {
            BeanUtils.copyProperties(dto, usuario);
            repository.save(usuario);
        });
        return found.orElse(null);
    }

    @Override
    public void delete(Long id) {
        Optional<Usuario> found = repository.findById(id);
        found.ifPresent(usuario -> repository.deleteById(id));
    }

    @Override
    public Page<Usuario> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }
}
