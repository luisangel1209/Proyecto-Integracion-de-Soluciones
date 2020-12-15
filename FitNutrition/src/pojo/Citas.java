/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.util.Date;

/**
 *
 * @author maria
 */
public class Citas {
    private Date fechaCita;
    private Date horaCita;
    private String Observaciones;

    public Citas() {
    }

    public Citas(Date fechaCita, Date horaCita, String Observaciones) {
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.Observaciones = Observaciones;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Date getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(Date horaCita) {
        this.horaCita = horaCita;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }
    
    
}
