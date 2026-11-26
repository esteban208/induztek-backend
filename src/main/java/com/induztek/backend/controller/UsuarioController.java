package com.induztek.backend.controller;

import com.induztek.backend.model.LoginRequest;
import com.induztek.backend.model.Usuario;
import com.induztek.backend.service.UsuarioService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    
    @PostMapping("/login")
    public Usuario login(@Valid @RequestBody LoginRequest request) {
        return service.login(request.username(), request.password());
    }

    
    @GetMapping
    public List<Usuario> getAll() {
        return service.findAll();
    }

    
    @GetMapping("/{id}")
    public Usuario getById(@PathVariable Integer id) {
        return service.findById(id);
    }

   
    @PostMapping
    public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario usuario) {
        Usuario created = service.create(usuario);
        return ResponseEntity.created(URI.create("/api/usuarios/" + created.getId())).body(created);
    }

    
    @PutMapping("/{id}")
    public Usuario update(@PathVariable Integer id, @Valid @RequestBody Usuario usuario) {
        return service.update(id, usuario);
    }

   
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}