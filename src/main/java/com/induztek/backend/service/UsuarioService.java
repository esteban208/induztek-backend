package com.induztek.backend.service;

import com.induztek.backend.exception.ConflictException;
import com.induztek.backend.exception.ResourceNotFoundException;
import com.induztek.backend.exception.UnauthorizedException;
import com.induztek.backend.model.Usuario;
import com.induztek.backend.repository.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    
    @Transactional(readOnly = true)
    public Usuario login(String username, String password) {
        return repository.findByUsernameAndPassword(username.trim(), password)
                .orElseThrow(() -> new UnauthorizedException("Usuario o contraseña incorrectos"));
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return repository.findAllByOrderByUsernameAsc();
    }

    @Transactional(readOnly = true)
    public Usuario findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
    }

    
    @Transactional
    public Usuario create(Usuario usuario) {
        String username = usuario.getUsername().trim();
        if (repository.existsByUsernameIgnoreCase(username)) {
            throw new ConflictException("El usuario ya existe: " + username);
        }
        return repository.save(new Usuario(null, username, usuario.getPassword(),
                usuario.getRole().trim().toUpperCase()));
    }

    @Transactional
    public Usuario update(Integer id, Usuario data) {
        Usuario usuario = findById(id);
        String username = data.getUsername().trim();
        if (repository.existsByUsernameIgnoreCaseAndIdNot(username, id)) {
            throw new ConflictException("El usuario ya existe: " + username);
        }
        usuario.setUsername(username);
        usuario.setPassword(data.getPassword());
        usuario.setRole(data.getRole().trim().toUpperCase());
        return repository.save(usuario);
    }

    @Transactional
    public void delete(Integer id) {
        repository.delete(findById(id));
    }
}
