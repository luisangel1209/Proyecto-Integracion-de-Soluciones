/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

/**
 *
 * @author maria
 */
public class Cita {
    private Integer idCitas;
    private Integer idPaciente;
    private String fecha_cita;
    private String hora_cita;
    private String observaciones;

    public Cita() {
    }

    public Cita(Integer idCitas, Integer idPaciente, String fecha_cita, String hora_cita, String observaciones) {
        this.idCitas = idCitas;
        this.idPaciente = idPaciente;
        this.fecha_cita = fecha_cita;
        this.hora_cita = hora_cita;
        this.observaciones = observaciones;
    }

    public Cita(Integer idPaciente, String fecha_cita, String hora_cita, String observaciones) {
        this.idPaciente = idPaciente;
        this.fecha_cita = fecha_cita;
        this.hora_cita = hora_cita;
        this.observaciones = observaciones;
    }

    public Integer getIdCitas() {
        return idCitas;
    }

    public void setIdCitas(Integer idCitas) {
        this.idCitas = idCitas;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getFecha_cita() {
        return fecha_cita;
    }

    public void setFecha_cita(String fecha_cita) {
        this.fecha_cita = fecha_cita;
    }

    public String getHora_cita() {
        return hora_cita;
    }

    public void setHora_cita(String hora_cita) {
        this.hora_cita = hora_cita;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
