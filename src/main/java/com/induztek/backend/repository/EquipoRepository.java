package com.induztek.backend.repository;

import com.induztek.backend.model.Equipo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    
    List<Equipo> findAllByOrderByCodigoAsc();

    
    boolean existsByCodigoIgnoreCase(String codigo);

    
    boolean existsByCodigoIgnoreCaseAndIdNot(String codigo, Long id);
}