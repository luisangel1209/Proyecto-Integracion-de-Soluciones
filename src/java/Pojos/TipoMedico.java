package Pojos;

public class TipoMedico {
    
    private int idMedico;
    private String nombre;

    public TipoMedico() {
    }

    public TipoMedico(int idMedico, String nombre) {
        this.idMedico = idMedico;
        this.nombre = nombre;
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
    
}
