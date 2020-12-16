/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

/**
 *
 * @author FAMSA
 */
public class Alimentos {
    private int idAlimento;
    private String nombre_alimento;
    private int calorias_porcion;
    private int idTipoPorcion;
    private String tipo;

    public Alimentos() {
    }

    public Alimentos(int idAlimento, String nombre_alimento, int calorias_porcion, int idTipoPorcion, String nombre) {
        this.idAlimento = idAlimento;
        this.nombre_alimento = nombre_alimento;
        this.calorias_porcion = calorias_porcion;
        this.idTipoPorcion = idTipoPorcion;
        this.tipo = nombre;
    }

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public String getNombre_alimento() {
        return nombre_alimento;
    }

    public void setNombre_alimento(String nombre_alimento) {
        this.nombre_alimento = nombre_alimento;
    }

    public int getCalorias_porcion() {
        return calorias_porcion;
    }

    public void setCalorias_porcion(int calorias_porcion) {
        this.calorias_porcion = calorias_porcion;
    }

    public int getIdTipoPorcion() {
        return idTipoPorcion;
    }

    public void setIdTipoPorcion(int idTipoPorcion) {
        this.idTipoPorcion = idTipoPorcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String nombre) {
        this.tipo = nombre;
    }
}
