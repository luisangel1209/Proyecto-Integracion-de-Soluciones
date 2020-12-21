package Pojos;

public class Pacientes {
    
    private int idPaciente;
    private int idMedico;
    private String nombre;
    private String apellidos;
    private String fNac;
    private String genero;
    private float peso;
    private float estatura;
    private int talla;
    private String email;
    private int tel;
    private String domicilio;
    private String usuario;
    private String contraseña;
    private String paciente_foto;
    private String estatus;

    public Pacientes() {
    }

    public Pacientes(int idPaciente, int idMedico, String nombre, String apellidos, String fNac, String genero, float peso, float estatura, int talla, String email, int tel, String domicilio, String usuario, String contraseña, String paciente_foto, String estatus) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fNac = fNac;
        this.genero = genero;
        this.peso = peso;
        this.estatura = estatura;
        this.talla = talla;
        this.email = email;
        this.tel = tel;
        this.domicilio = domicilio;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.paciente_foto = paciente_foto;
        this.estatus = estatus;
    }
    
    public Pacientes(int idPaciente, float peso, float estatura, int tel){
        this.idPaciente = idPaciente;
        this.peso = peso;
        this.estatura = estatura;
        this.tel = tel;
    }

    public Pacientes(int idPaciente, String estatus){
        this.idPaciente = idPaciente;
        this.estatus = estatus;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
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

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getEstatura() {
        return estatura;
    }

    public void setEstatura(float estatura) {
        this.estatura = estatura;
    }

    public int getTalla() {
        return talla;
    }

    public void setTalla(int talla) {
        this.talla = talla;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getPaciente_foto() {
        return paciente_foto;
    }

    public void setPaciente_foto(String paciente_foto) {
        this.paciente_foto = paciente_foto;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
}