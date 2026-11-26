package com.induztek.backend.service;


public final class Calculos {

   
    public static final double UMBRAL_REVISION = 10.0;

    private Calculos() {}

    
    public static Double aNumero(String valor) {
        if (valor == null || valor.isBlank()) return null;
        try {
            return Double.parseDouble(valor.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return null;
        }
    }

   
    public static Double variacionPorcentual(String actual, String anterior) {
        Double valorActual = aNumero(actual);
        Double valorAnterior = aNumero(anterior);
        if (valorActual == null || valorAnterior == null || valorAnterior == 0.0) return null;
        return (valorActual - valorAnterior) / valorAnterior * 100;
    }

    
    public static boolean requiereRevision(Double variacion) {
        return variacion != null && Math.abs(variacion) > UMBRAL_REVISION;
    }
}