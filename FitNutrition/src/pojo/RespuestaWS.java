package pojo;

/**
 *
 * @author maria
 */
public class RespuestaWS {
    private int codigo;
    private String Mensaje;

    public RespuestaWS() {
    }

    public RespuestaWS(int codigo, String Mensaje) {
        this.codigo = codigo;
        this.Mensaje = Mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }
    
    
    
}
