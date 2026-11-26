package com.induztek.backend.repository;

import com.induztek.backend.model.Prueba;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Long> {

    
    List<Prueba> findAllByOrderByIdDesc();

    
    List<Prueba> findByEquipoIdOrderByIdDesc(Long equipoId);

    
    boolean existsByEquipoId(Long equipoId);
}
