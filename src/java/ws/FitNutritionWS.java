package ws;

import Pojos.Dieta;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import Pojos.Medico;
import Pojos.Mensaje;
import Pojos.Pacientes;
import Pojos.TipoMedico;
import Pojos.TipoAlimento;

@Path("fitNutrition")
public class FitNutritionWS {
    
    @Context
    private UriInfo context;

    public FitNutritionWS() {
    }
    
    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje login (@FormParam("cedulaProfesional") String cedulaProfesional, @FormParam("contraseña") String contraseña){
        Mensaje respuesta = new Mensaje();
        SqlSession conn = MyBatisUtil.getSession();
        Medico user = new Medico();
        HashMap<String,Object> param = new HashMap<>();
        param.put("cedulaProfesional", cedulaProfesional);
        param.put("contraseña", contraseña);
        
        if(conn != null){
            try {
                user = conn.selectOne("Medico.login", param);
                conn.commit();
                if( user !=  null && user.getIdMedico() > 0){
                    respuesta.setError(false);
                    respuesta.setMensaje(""+user.getIdMedico());
                }else{
                    respuesta.setError(true);
                    respuesta.setMensaje("Credenciales Incorectas");
                }
            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexión con la BD...");
        }
        return respuesta;
    }

    @Path("loginPaciente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Pacientes loginPaciente (@FormParam("usuario") String usuario, @FormParam("contraseña") String contraseña){
        Pacientes respuesta = new Pacientes();
        SqlSession conn = MyBatisUtil.getSession();
        Pacientes user = new Pacientes();
        HashMap<String,Object> param = new HashMap<>();
        param.put("usuario", usuario);
        param.put("contraseña", contraseña);
        
        if(conn != null){
            try {
                user = conn.selectOne("Pacientes.login", param);
                conn.commit();
                if( user !=  null && user.getIdPaciente()> 0){
                    respuesta.setIdPaciente(user.getIdPaciente());
                    respuesta.setNombre(user.getNombre());
                    respuesta.setPeso(user.getPeso());
                    respuesta.setEstatura(user.getEstatura());
                    respuesta.setTalla(user.getTalla());
                    respuesta.setEmail(user.getEmail());
                    respuesta.setTel(user.getTel());
                    respuesta.setDomicilio(user.getDomicilio());
                    respuesta.setUsuario(user.getUsuario());
                    respuesta.setContraseña(user.getContraseña());
                    respuesta.setPaciente_foto(user.getPaciente_foto());
                }else{
                    respuesta.setUsuario("Credenciales Incorrectas");
                }
            } catch (Exception e) {
                respuesta.setUsuario(e.getMessage());
            }
        }else{
            respuesta.setUsuario("No hay conexión con la BD...");
        }
        return respuesta;
    }

    @Path("getAllPacientes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pacientes> getPacientes(){
        List<Pacientes> paciente = null;
        SqlSession conn = MyBatisUtil.getSession();
        if (conn != null) {
            try {
                paciente = conn.selectList("Pacientes.getAllPacientes");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return paciente;
    }
    
    @Path("getTiposMedico")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TipoMedico> getTipoMedicos(){
        List<TipoMedico> tipoAero = null;
        SqlSession conn = MyBatisUtil.getSession();
        if (conn != null) {
            try {
                tipoAero = conn.selectList("Pacientes.getTiposMedico");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tipoAero;
    }
    
    @Path("registrarPaciente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje crearPaciente(@FormParam("idMedico") Integer idMedico, @FormParam("nombre") String nombre, @FormParam("apellidos") String apellidos, @FormParam("fNac") String fNac,
            @FormParam("genero") String genero, @FormParam("peso") float peso, @FormParam("estatura") float estatura,
            @FormParam("talla") Integer talla, @FormParam("email") String email, @FormParam("tel") Integer tel,
            @FormParam("domicilio") String domicilio, @FormParam("usuario") String usuario, @FormParam("contraseña") String contraseña, @FormParam("paciente_foto") String paciente_foto, @FormParam("estatus") String estatus){
        Mensaje respuesta = new Mensaje();       
        Pacientes paciente =  new Pacientes(0, idMedico ,nombre, apellidos, fNac, genero, peso, estatura, talla, email, tel, domicilio, usuario, contraseña, paciente_foto, estatus);
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                int resultado = conn.insert("Pacientes.registrarPaciente", paciente);
                conn.commit();
                if(resultado > 0){
                    respuesta.setError(false);
                    respuesta.setMensaje("Paciente agregado con éxito...");
                }else{
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo agregar el paciente...");
                }
            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("No se puede conectar con la base de datos...");
        }
        return respuesta;
    }
    
    @Path("actualizarPaciente")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje editarPaciente(@FormParam("idPaciente") Integer idPaciente, @FormParam("peso") float peso, @FormParam("estatura") float estatura,
            @FormParam("talla") Integer talla, @FormParam("email") String email, @FormParam("tel") Integer tel, @FormParam("domicilio") String domicilio,
            @FormParam("usuario") String usuario, @FormParam("contraseña") String contraseña, @FormParam("paciente_foto") String paciente_foto){
        Mensaje respuesta = new Mensaje();
        Pacientes paciente = new Pacientes(idPaciente, peso, estatura, talla, email, tel, domicilio, usuario, contraseña, paciente_foto);
        SqlSession conn =  MyBatisUtil.getSession();
        if(conn != null){
           try{
               int resultado = conn.update("Pacientes.actualizarPaciente", paciente);
               conn.commit();
               if(resultado > 0){
                   respuesta.setError(false);
                   respuesta.setMensaje("Paciente actualizado con éxito...");
               }else{
                   respuesta.setError(true);
                   respuesta.setMensaje("El paciente No pudo ser actualizado...");
               }
           }catch(Exception e){
               respuesta.setError(true);
               respuesta.setMensaje(e.getMessage());
           }
        }else{
           respuesta.setError(true);
           respuesta.setMensaje("No hay conexión con la BD...");
        }
        return respuesta;
    }
    
    @Path("bajaPaciente")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje bajaPaciente(@FormParam("idPaciente") Integer idPaciente, @FormParam("estatus") String estatus){
        Mensaje respuesta = new Mensaje();
        Pacientes paciente = new Pacientes(idPaciente, estatus);
        SqlSession conn =  MyBatisUtil.getSession();
        if(conn != null){
           try{
               int resultado = conn.update("Pacientes.bajaPaciente", paciente);
               conn.commit();
               if(resultado > 0){
                   respuesta.setError(false);
                   respuesta.setMensaje("Paciente dado de baja con éxito...");
               }else{
                   respuesta.setError(true);
                   respuesta.setMensaje("El paciente no pudo darse de baja...");
               }
           }catch(Exception e){
               respuesta.setError(true);
               respuesta.setMensaje(e.getMessage());
           }
        }else{
           respuesta.setError(true);
           respuesta.setMensaje("No hay conexión con la BD...");
        }
        return respuesta;
    }
    
    @Path("getAllDietas")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Dieta> getDietas(){
        List<Dieta> dieta = null;
        SqlSession conn = MyBatisUtil.getSession();
        if (conn != null) {
            try {
                dieta = conn.selectList("Dieta.getAllDietas");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dieta;
    }
    
    @Path("getTiposAlimentos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TipoAlimento> getTipoAlimentos(){
        List<TipoAlimento> tipoAero = null;
        SqlSession conn = MyBatisUtil.getSession();
        if (conn != null) {
            try {
                tipoAero = conn.selectList("Dieta.getTiposAlimentos");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tipoAero;
    }
    
    @Path("registrarDieta")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje crearDieta(@FormParam("idAlimento") Integer idAlimento, @FormParam("numero_dieta") Integer numero_dieta, @FormParam("cantidad") String cantidad,
            @FormParam("hora_dia") String hora_dia, @FormParam("calorias_dieta") float calorias_dieta, @FormParam("observaciones") String observaciones, @FormParam("idPaciente") Integer idPaciente){
        Mensaje respuesta = new Mensaje();       
        Dieta dieta =  new Dieta(0, idAlimento, numero_dieta, cantidad, hora_dia, calorias_dieta, observaciones, idPaciente);
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                int resultado = conn.insert("Dieta.registrarDieta", dieta);
                conn.commit();
                if(resultado > 0){
                    respuesta.setError(false);
                    respuesta.setMensaje("Dieta creada con éxito...");
                }else{
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo crear la dieta...");
                }
            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("No se puede conectar con la base de datos...");
        }
        return respuesta;
    }
    
    @Path("actualizarDieta")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje editarDieta(@FormParam("idDieta") Integer idDieta, @FormParam("cantidad") String cantidad, @FormParam("hora_dia") String hora_dia, @FormParam("calorias_dieta") float calorias_dieta, @FormParam("observaciones") String observaciones){
        Mensaje respuesta = new Mensaje();
        Dieta dieta = new Dieta(idDieta, cantidad, hora_dia, calorias_dieta, observaciones);
        SqlSession conn =  MyBatisUtil.getSession();
        if(conn != null){
           try{
               int resultado = conn.update("Dieta.actualizarDieta", dieta);
               conn.commit();
               if(resultado > 0){
                   respuesta.setError(false);
                   respuesta.setMensaje("Dieta actualizada con éxito...");
               }else{
                   respuesta.setError(true);
                   respuesta.setMensaje("La dieta No pudo ser actualizada...");
               }
           }catch(Exception e){
               respuesta.setError(true);
               respuesta.setMensaje(e.getMessage());
           }
        }else{
           respuesta.setError(true);
           respuesta.setMensaje("No hay conexión con la BD...");
        }
        return respuesta;
    }
    
    @Path("eliminarDieta")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarDieta(@FormParam("idDieta") Integer idDieta){
        Mensaje respuesta = new Mensaje();
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                int resultado = conn.delete("Dieta.eliminarDieta", idDieta);
                conn.commit();
                if( resultado > 0 ){
                    respuesta.setError(false);
                    respuesta.setMensaje("Dieta eliminada correctamente...");
                }else{
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo eliminar la dieta...");
                }
            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexión con la BD...");
        }
        return respuesta;
    }
    
}
