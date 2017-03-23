package com.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "TIPO_DE_PAGO")
public class TipoPago {
    @Id
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "Texto")
    private String descipcion;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }
}
