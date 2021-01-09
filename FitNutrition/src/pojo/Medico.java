/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

/**
 *
 * @author Luis Angel
 */
public class Medico {
    private int idMedico;
    private String nombre;
    private String apellidos;
    private String fNac;
    private String genero;
    private String domicilio;
    private int numPersonal;
    private String cedulaProfesional;
    private String contraseña;
    private byte[] foto_medico;
    private String estatus;

    public Medico() {
    }
    
    public Medico(int idMedico, String estatus) {
        this.idMedico = idMedico;
        this.estatus = estatus;
    }

    public Medico(int idMedico, String nombre, String apellidos, String fNac, String genero, String domicilio, int numPersonal, String cedulaProfesional, String contraseña, String estatus) {
        this.idMedico = idMedico;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fNac = fNac;
        this.genero = genero;
        this.domicilio = domicilio;
        this.numPersonal = numPersonal;
        this.cedulaProfesional = cedulaProfesional;
        this.contraseña = contraseña;
        this.estatus = estatus;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getfNac() {
        return fNac;
    }

    public void setfNac(String fNac) {
        this.fNac = fNac;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public int getNumPersonal() {
        return numPersonal;
    }

    public void setNumPersonal(int numPersonal) {
        this.numPersonal = numPersonal;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public byte[] getFoto() {
        return foto_medico;
    }

    public void setFoto(byte[] foto) {
        this.foto_medico = foto;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}