/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

/**
 *
 * @author FAMSA
 */
public class TipoPorcionAlimentos {
    private int idTipoPorcion;
    private String nombre;

    public TipoPorcionAlimentos() {
    }

    public TipoPorcionAlimentos(int idTipoPorcion, String nombre) {
        this.idTipoPorcion = idTipoPorcion;
        this.nombre = nombre;
    }

    public int getIdTipoPorcion() {
        return idTipoPorcion;
    }

    public void setIdTipoPorcion(int idTipoPorcion) {
        this.idTipoPorcion = idTipoPorcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
