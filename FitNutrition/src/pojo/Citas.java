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
    private Integer idCitas;
    private Integer idPaciente;
    private String fecha_cita;
    private String hora_cita;
    private String observaciones;

    public Citas() {
    }

    public Citas(Integer idCitas, Integer idPaciente, String fecha_cita, String hora_cita, String observaciones) {
        this.idCitas = idCitas;
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

    public String getFecha_Cita() {
        return fecha_cita;
    }

    public void setFecha_Cita(String fecha_Cita) {
        this.fecha_cita = fecha_Cita;
    }

    public String getHora_Cita() {
        return hora_cita;
    }

    public void setHora_Cita(String hora_Cita) {
        this.hora_cita = hora_Cita;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
}
