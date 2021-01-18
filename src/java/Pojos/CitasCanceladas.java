package Pojos;

public class CitasCanceladas {
    
    private int idCancelacion;
    private String comentario;
    private int idCitas;

    public CitasCanceladas() {
    }

    public CitasCanceladas(int idCancelacion, String comentario, int idCitas) {
        this.idCancelacion = idCancelacion;
        this.comentario = comentario;
        this.idCitas = idCitas;
    }

    public CitasCanceladas(String comentario, int idCitas) {
        this.comentario = comentario;
        this.idCitas = idCitas;
    }
    
    public int getIdCancelacion() {
        return idCancelacion;
    }

    public void setIdCancelacion(int idCancelacion) {
        this.idCancelacion = idCancelacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdCitas() {
        return idCitas;
    }

    public void setIdCitas(int idCitas) {
        this.idCitas = idCitas;
    }
    
}
