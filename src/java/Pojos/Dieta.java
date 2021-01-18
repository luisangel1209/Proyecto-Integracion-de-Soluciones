package Pojos;

public class Dieta {
    
    private int idDieta;
    private int idAlimento;
    private int numero_dieta;
    private String cantidad;
    private String hora_dia;
    private float calorias_dieta;
    private String observaciones;
    private int idPaciente;
    
    private String tipo;

    public Dieta() {
    }

    public Dieta(int idDieta, int idAlimento, int numero_dieta, String cantidad, String hora_dia, float calorias_dieta, String observaciones, String tipo) {
        this.idDieta = idDieta;
        this.idAlimento = idAlimento;
        this.numero_dieta = numero_dieta;
        this.cantidad = cantidad;
        this.hora_dia = hora_dia;
        this.calorias_dieta = calorias_dieta;
        this.observaciones = observaciones;
        this.tipo = tipo;
    }

    public Dieta(int idDieta, int idAlimento, int numero_dieta, String cantidad, String hora_dia, float calorias_dieta, String observaciones, int idPaciente) {
        this.idDieta = idDieta;
        this.idAlimento = idAlimento;
        this.numero_dieta = numero_dieta;
        this.cantidad = cantidad;
        this.hora_dia = hora_dia;
        this.calorias_dieta = calorias_dieta;
        this.observaciones = observaciones;
        this.idPaciente = idPaciente;
    }
    
    public Dieta(int idDieta, String cantidad, String hora_dia, float calorias_dieta, String observaciones) {
        this.idDieta = idDieta;
        this.cantidad = cantidad;
        this.hora_dia = hora_dia;
        this.calorias_dieta = calorias_dieta;
        this.observaciones = observaciones;
    }
    
    public int getIdDieta() {
        return idDieta;
    }

    public void setIdDieta(int idDieta) {
        this.idDieta = idDieta;
    }

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public int getNumero_dieta() {
        return numero_dieta;
    }

    public void setNumero_dieta(int numero_dieta) {
        this.numero_dieta = numero_dieta;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getHora_dia() {
        return hora_dia;
    }

    public void setHora_dia(String hora_dia) {
        this.hora_dia = hora_dia;
    }

    public float getCalorias_dieta() {
        return calorias_dieta;
    }

    public void setCalorias_dieta(float calorias_dieta) {
        this.calorias_dieta = calorias_dieta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
    
}
