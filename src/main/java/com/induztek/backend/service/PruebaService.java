package com.induztek.backend.service;

import com.induztek.backend.exception.BadRequestException;
import com.induztek.backend.exception.ResourceNotFoundException;
import com.induztek.backend.model.Equipo;
import com.induztek.backend.model.ProtocoloResponse;
import com.induztek.backend.model.Prueba;
import com.induztek.backend.repository.EquipoRepository;
import com.induztek.backend.repository.PruebaRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PruebaService {

    
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final PruebaRepository repository;
    private final EquipoRepository equipoRepository;

    public PruebaService(PruebaRepository repository, EquipoRepository equipoRepository) {
        this.repository = repository;
        this.equipoRepository = equipoRepository;
    }

    @Transactional(readOnly = true)
    public List<Prueba> findAll() {
        return repository.findAllByOrderByIdDesc();
    }

    @Transactional(readOnly = true)
    public Prueba findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prueba no encontrada: " + id));
    }

    
    @Transactional(readOnly = true)
    public List<Prueba> findByEquipo(Long equipoId) {
        buscarEquipo(equipoId);
        return repository.findByEquipoIdOrderByIdDesc(equipoId);
    }

    
    @Transactional
    public Prueba create(Prueba data) {
        Equipo equipo = buscarEquipo(data.getEquipoId());
        validarMedicion(data.getValorMedicion());

        Prueba prueba = new Prueba();
        prueba.setEquipoId(equipo.getId());
        prueba.setCodigoEquipo(equipo.getCodigo());
        prueba.setTipoEquipo(equipo.getTipo());
        prueba.setValorMedicion(data.getValorMedicion().trim());
        prueba.setFecha(LocalDateTime.now().format(FORMATO_FECHA));
        prueba.setFotoUri(data.getFotoUri());
        prueba.setLatitud(data.getLatitud());
        prueba.setLongitud(data.getLongitud());
        return repository.save(prueba);
    }

    
    @Transactional
    public Prueba update(Long id, Prueba data) {
        Prueba prueba = findById(id);
        validarMedicion(data.getValorMedicion());
        prueba.setValorMedicion(data.getValorMedicion().trim());
        prueba.setFotoUri(data.getFotoUri());
        prueba.setLatitud(data.getLatitud());
        prueba.setLongitud(data.getLongitud());
        return repository.save(prueba);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));
    }

    
    @Transactional(readOnly = true)
    public ProtocoloResponse generarProtocolo(Long id) {
        Prueba prueba = findById(id);
        Equipo equipo = equipoRepository.findById(prueba.getEquipoId()).orElse(null);

        
        List<Prueba> historial = repository.findByEquipoIdOrderByIdDesc(prueba.getEquipoId());
        Prueba anterior = null;
        for (int i = 0; i < historial.size() - 1; i++) {
            if (historial.get(i).getId().equals(prueba.getId())) {
                anterior = historial.get(i + 1);
                break;
            }
        }

        Double variacion = (anterior == null) ? null
                : Calculos.variacionPorcentual(prueba.getValorMedicion(), anterior.getValorMedicion());
        boolean requiereRevision = Calculos.requiereRevision(variacion);

        return new ProtocoloResponse(
                String.format("PR-%04d", prueba.getId()),
                requiereRevision ? "REVISAR" : "CONFORME",
                prueba,
                equipo,
                anterior,
                variacion,
                requiereRevision
        );
    }

    private Equipo buscarEquipo(Long equipoId) {
        return equipoRepository.findById(equipoId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado: " + equipoId));
    }

    
    private void validarMedicion(String valorMedicion) {
        Double numero = Calculos.aNumero(valorMedicion);
        if (numero == null) {
            throw new BadRequestException("La medición debe ser un número (ej. 220 o 12,5)");
        }
        if (numero <= 0) {
            throw new BadRequestException("La medición debe ser mayor a 0");
        }
    }
}