package com.induztek.backend.model;


public record ProtocoloResponse(
        String numero,
        String resultado,
        Prueba prueba,
        Equipo equipo,
        Prueba anterior,
        Double variacionPorcentaje,
        boolean requiereRevision
) {}