package com.induztek.backend.controller;

import com.induztek.backend.model.Equipo;
import com.induztek.backend.model.Prueba;
import com.induztek.backend.service.EquipoService;
import com.induztek.backend.service.PruebaService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService service;
    private final PruebaService pruebaService;

    public EquipoController(EquipoService service, PruebaService pruebaService) {
        this.service = service;
        this.pruebaService = pruebaService;
    }

    
    @GetMapping
    public List<Equipo> getAll() {
        return service.findAll();
    }

    
    @GetMapping("/{id}")
    public Equipo getById(@PathVariable Long id) {
        return service.findById(id);
    }

    
    @GetMapping("/{id}/pruebas")
    public List<Prueba> getPruebas(@PathVariable Long id) {
        return pruebaService.findByEquipo(id);
    }

    
    @PostMapping
    public ResponseEntity<Equipo> create(@Valid @RequestBody Equipo equipo) {
        Equipo created = service.create(equipo);
        return ResponseEntity.created(URI.create("/api/equipos/" + created.getId())).body(created);
    }

   
    @PutMapping("/{id}")
    public Equipo update(@PathVariable Long id, @Valid @RequestBody Equipo equipo) {
        return service.update(id, equipo);
    }

    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}