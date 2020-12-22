/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

/**
 *
 * @author maria
 */
public class Citas {
    private Integer idCita;
    private Integer idPaciente;
    private String fechaCita;
    private String horaCita;
    private String Observaciones;

    public Citas() {
    }

    public Citas(Integer idCita, Integer idPaciente, String fechaCita, String horaCita, String Observaciones) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.Observaciones = Observaciones;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(String horaCita) {
        this.horaCita = horaCita;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    
}
