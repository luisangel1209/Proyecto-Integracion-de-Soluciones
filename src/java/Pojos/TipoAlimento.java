package Pojos;

public class TipoAlimento {
    
    private int idAlimento;
    private String nombre;

    public TipoAlimento() {
    }

    public TipoAlimento(int idAlimento, String nombre) {
        this.idAlimento = idAlimento;
        this.nombre = nombre;
    }

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
