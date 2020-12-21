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
public class Consulta {
    private Integer idConsultas;
   private Integer idPaciente;
   private String observaciones;
   private float peso;
   private int talla;
   private float IMC;
   private Integer idDieta;

    public Consulta() {
    }

    public Consulta(Integer idConsultas, Integer idPaciente, String observaciones, float peso, int talla, float IMC, Integer idDieta) {
        this.idConsultas = idConsultas;
        this.idPaciente = idPaciente;
        this.observaciones = observaciones;
        this.peso = peso;
        this.talla = talla;
        this.IMC = IMC;
        this.idDieta = idDieta;
    }

    public Integer getIdConsultas() {
        return idConsultas;
    }

    public void setIdConsultas(Integer idConsultas) {
        this.idConsultas = idConsultas;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getTalla() {
        return talla;
    }

    public void setTalla(int talla) {
        this.talla = talla;
    }

    public float getIMC() {
        return IMC;
    }

    public void setIMC(float IMC) {
        this.IMC = IMC;
    }

    public Integer getIdDieta() {
        return idDieta;
    }

    public void setIdDieta(Integer idDieta) {
        this.idDieta = idDieta;
    }
}
