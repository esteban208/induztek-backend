package com.induztek.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @NotBlank(message = "El código es obligatorio")
    @Pattern(regexp = "^[A-Za-z]{2}-\\d{3}$",
            message = "Formato: 2 letras, guion y 3 números (ej. TR-001)")
    @Column(nullable = false, unique = true, length = 6)
    private String codigo;

    
    @NotBlank(message = "El tipo de equipo es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "Solo se permiten letras")
    @Column(nullable = false)
    private String tipo;

    
    @NotBlank(message = "La marca es obligatoria")
    @Size(min = 2, max = 30, message = "La marca debe tener entre 2 y 30 caracteres")
    @Column(nullable = false, length = 30)
    private String marca;

    
    @NotBlank(message = "La instalación es obligatoria")
    @Size(min = 3, max = 100, message = "La instalación debe tener al menos 3 caracteres")
    @Column(nullable = false, length = 100)
    private String instalacion;

    
    private String fotoPlacaUri;

    public Equipo() {}

    public Equipo(Long id, String codigo, String tipo, String marca, String instalacion, String fotoPlacaUri) {
        this.id = id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.marca = marca;
        this.instalacion = instalacion;
        this.fotoPlacaUri = fotoPlacaUri;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getInstalacion() { return instalacion; }
    public void setInstalacion(String instalacion) { this.instalacion = instalacion; }

    public String getFotoPlacaUri() { return fotoPlacaUri; }
    public void setFotoPlacaUri(String fotoPlacaUri) { this.fotoPlacaUri = fotoPlacaUri; }
}