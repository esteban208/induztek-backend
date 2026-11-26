package com.induztek.backend.controller;

import com.induztek.backend.model.ProtocoloResponse;
import com.induztek.backend.model.Prueba;
import com.induztek.backend.service.PruebaService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pruebas")
public class PruebaController {

    private final PruebaService service;

    public PruebaController(PruebaService service) {
        this.service = service;
    }

    
    @GetMapping
    public List<Prueba> getAll() {
        return service.findAll();
    }

    
    @GetMapping("/{id}")
    public Prueba getById(@PathVariable Long id) {
        return service.findById(id);
    }

    
    @GetMapping("/{id}/protocolo")
    public ProtocoloResponse getProtocolo(@PathVariable Long id) {
        return service.generarProtocolo(id);
    }

    
    @PostMapping
    public ResponseEntity<Prueba> create(@Valid @RequestBody Prueba prueba) {
        Prueba created = service.create(prueba);
        return ResponseEntity.created(URI.create("/api/pruebas/" + created.getId())).body(created);
    }

    
    @PutMapping("/{id}")
    public Prueba update(@PathVariable Long id, @Valid @RequestBody Prueba prueba) {
        return service.update(id, prueba);
    }

    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}