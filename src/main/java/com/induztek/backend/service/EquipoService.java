package com.induztek.backend.service;

import com.induztek.backend.exception.ConflictException;
import com.induztek.backend.exception.ResourceNotFoundException;
import com.induztek.backend.model.Equipo;
import com.induztek.backend.repository.EquipoRepository;
import com.induztek.backend.repository.PruebaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipoService {

    private final EquipoRepository repository;
    private final PruebaRepository pruebaRepository;

    public EquipoService(EquipoRepository repository, PruebaRepository pruebaRepository) {
        this.repository = repository;
        this.pruebaRepository = pruebaRepository;
    }

    @Transactional(readOnly = true)
    public List<Equipo> findAll() {
        return repository.findAllByOrderByCodigoAsc();
    }

    @Transactional(readOnly = true)
    public Equipo findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado: " + id));
    }

    
    @Transactional
    public Equipo create(Equipo equipo) {
        String codigo = equipo.getCodigo().trim().toUpperCase();
        if (repository.existsByCodigoIgnoreCase(codigo)) {
            throw new ConflictException("Ya existe un equipo con el código " + codigo);
        }
        Equipo nuevo = new Equipo(
                null,
                codigo,
                equipo.getTipo().trim(),
                equipo.getMarca().trim(),
                equipo.getInstalacion().trim(),
                equipo.getFotoPlacaUri()
        );
        return repository.save(nuevo);
    }

    @Transactional
    public Equipo update(Long id, Equipo data) {
        Equipo equipo = findById(id);
        String codigo = data.getCodigo().trim().toUpperCase();
        if (repository.existsByCodigoIgnoreCaseAndIdNot(codigo, id)) {
            throw new ConflictException("Ya existe un equipo con el código " + codigo);
        }
        equipo.setCodigo(codigo);
        equipo.setTipo(data.getTipo().trim());
        equipo.setMarca(data.getMarca().trim());
        equipo.setInstalacion(data.getInstalacion().trim());
        equipo.setFotoPlacaUri(data.getFotoPlacaUri());
        return repository.save(equipo);
    }

    
    @Transactional
    public void delete(Long id) {
        Equipo equipo = findById(id);
        if (pruebaRepository.existsByEquipoId(id)) {
            throw new ConflictException("No se puede eliminar: el equipo " + equipo.getCodigo()
                    + " tiene pruebas registradas");
        }
        repository.delete(equipo);
    }
}