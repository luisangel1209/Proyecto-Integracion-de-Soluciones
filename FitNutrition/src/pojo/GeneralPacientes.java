
package pojo;

/**
 *
 * @author maria
 */
public class GeneralPacientes {
     private Integer idPaciente;
    private String Nombre;
    private String Apellidos;
    //private Date fNac;
    private int peso;
    private int estatura;
    private int talla;
    private int idMedico;

    public GeneralPacientes() {
    }

    public GeneralPacientes(Integer idPaciente, String Nombre, String Apellidos, int peso, int estatura, int talla, int idMedico) {
        this.idPaciente = idPaciente;
        this.Nombre = Nombre;
        this.Apellidos = Apellidos;
        //this.fNac = fNac;
        this.peso = peso;
        this.estatura = estatura;
        this.talla = talla;
        this.idMedico = idMedico;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    /*public Date getfNac() {
        return fNac;
    }

    public void setfNac(Date fNac) {
        this.fNac = fNac;
    }*/

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getEstatura() {
        return estatura;
    }

    public void setEstatura(int estatura) {
        this.estatura = estatura;
    }

    public int getTalla() {
        return talla;
    }

    public void setTalla(int talla) {
        this.talla = talla;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }
}
