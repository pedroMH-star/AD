package org.example;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "LLAMADAS_EMITIDAS")
public class LlamadasEmitida {
    @Id
    @Column(name = "CODIGO_LLAMADA", nullable = false)
    private Integer id;

    @Column(name = "NUMERO_LLAMADO", nullable = false)
    private Integer numeroLlamado;

    @Column(name = "DURACION_LLAMADA", nullable = false)
    private Integer duracionLlamada;

    @Column(name = "IMPORTE_LLAMADA", nullable = false)
    private Float importeLlamada;

    @Column(name = "NUMERO_SIM", nullable = false)
    private Integer numeroSim;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumeroLlamado() {
        return numeroLlamado;
    }

    public void setNumeroLlamado(Integer numeroLlamado) {
        this.numeroLlamado = numeroLlamado;
    }

    public Integer getDuracionLlamada() {
        return duracionLlamada;
    }

    public void setDuracionLlamada(Integer duracionLlamada) {
        this.duracionLlamada = duracionLlamada;
    }

    public Float getImporteLlamada() {
        return importeLlamada;
    }

    public void setImporteLlamada(Float importeLlamada) {
        this.importeLlamada = importeLlamada;
    }

    public Integer getNumeroSim() {
        return numeroSim;
    }

    public void setNumeroSim(Integer numeroSim) {
        this.numeroSim = numeroSim;
    }

}