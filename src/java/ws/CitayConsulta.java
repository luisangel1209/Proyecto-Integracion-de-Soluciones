/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import Pojos.Cita;
import Pojos.Consulta;
import Pojos.Mensaje;
import Pojos.Pacientes;
import Pojos.TipoPaciente;
import java.util.HashMap;
/**
 *
 * @author maria
 */
@Path("fitNuutrition")
public class CitayConsulta {
    
    @Context
    private UriInfo context;

   
    public CitayConsulta() {
    }

    @Path("getAllCita")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cita> getCita(){
        List<Cita> resultado = null;
        SqlSession conn = MyBatisUtil.getSession();
        
        if(conn != null){ 
            try {
                resultado = conn.selectList("Cita.getAllCitas");
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conn.close();
            }
        }else{
            System.out.println("Error de conexion");
        }
        return resultado;
    }

    @Path("allPaciente")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TipoPaciente> getAllPacientes(){
        List<TipoPaciente> aero = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                aero = conn.selectList("Cita.getPaciente");
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conn.close();
            }
        }else{
            System.out.println("Error de conexion");
        }
        return aero;
    }    
    
    /*@Path("citaPaciente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cita> citaPaciente (@FormParam("idPaciente") Integer idPaciente){
        List<Cita> respuesta = null;
        SqlSession conn = MyBatisUtil.getSession();
        Cita user = new Cita();
        HashMap<String,Object> param = new HashMap<>();
        param.put("idPaciente", idPaciente);
        
        if(conn != null){
            try {
                user = conn.selectList("Cita.getCitaPaciente", param);
                conn.commit();
                if( user !=  null && user.getIdPaciente()> 0){
                    respuesta.setIdCitas(user.getIdCitas());
                    respuesta.setIdPaciente(user.getIdPaciente());
                    respuesta.setFecha_cita(user.getFecha_cita());
                    respuesta.setHora_cita(user.getHora_cita());
                    respuesta.setObservaciones(user.getObservaciones());
                }else{
                    respuesta.setObservaciones("No tienes citas agendadas");
                }
            } catch (Exception e) {
                respuesta.setObservaciones(e.getMessage());
            }
        }else{
            respuesta.setObservaciones("No hay conexión con la BD...");
        }
        return respuesta;
    }*/
    
    @Path("citaPaciente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cita> getCitaPaciente(@FormParam("idPaciente") Integer idPaciente){
        List<Cita> resultado = null;
        SqlSession conn = MyBatisUtil.getSession();
        HashMap<String,Object> param = new HashMap<>();
        param.put("idPaciente", idPaciente);
        if(conn != null){ 
            try {
                resultado = conn.selectList("Cita.getCitaPaciente", idPaciente);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conn.close();
            }
        }else{
            System.out.println("Error de conexion");
        }
        return resultado;
    }
    
    @Path("registraCita")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje registraCita(@FormParam("idPaciente") int idPaciente,@FormParam("fecha_cita")String fecha_cita,@FormParam("hora_cita") String hora_cita,
            @FormParam("observaciones") String observaciones){
        Mensaje respuesta = new Mensaje();
        
        Cita cita = new Cita (0,idPaciente,fecha_cita,hora_cita,observaciones);
        SqlSession conn = MyBatisUtil.getSession();
        
        if(conn != null){
            try{
               int resultado = conn.insert("Cita.registraCita", cita);
               conn.commit();
               if(resultado > 0){
                   respuesta.setError(false);
                   respuesta.setMensaje("Registro agregado con exito...");
               }else{
                   respuesta.setError(true);
                   respuesta.setMensaje("No se pudo agregar el registro");
               }
            }catch(Exception e){
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexion con la BD..");
        }
        return respuesta;
    }
    
    @Path("eliminarCita")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarCita(@FormParam("idCita") Integer idCitas){
        Mensaje respuesta = new  Mensaje();
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try{
              int resultado = conn.delete("Cita.eliminarCita", idCitas);
              conn.commit();
              if(resultado > 0){
                  respuesta.setError(false);
                  respuesta.setMensaje("Registro eliminado correctamente...");
              }else{
                  respuesta.setError(true);
                  respuesta.setMensaje("El registro no se pudo eliminar...");
              }
            }catch(Exception e){
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexion con la BD..");
        }
        return respuesta;
    }
    
    @Path("editarCita")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje editarCita(@FormParam("idCitas") Integer idCitas, @FormParam("idPaciente") Integer idPaciente, @FormParam("fecha_cita") String fecha_cita,
            @FormParam("hora_cita") String hora_cita, @FormParam("observaciones") String observaciones){
        Mensaje respuesta = new Mensaje();
        Cita cita = new Cita(idCitas,idPaciente,fecha_cita,hora_cita,observaciones);
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try{
                int resultado = conn.update("Cita.editarCita", cita);
                conn.commit();
                if(resultado > 0 ){
                    respuesta.setError(false);
                    respuesta.setMensaje("Registro actualizado con exito...");
                }else{
                    respuesta.setError(true);
                    respuesta.setMensaje("El registro no pudo ser actualizado...");
                }
            }catch(Exception e){
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexion con la BD...");
        }
        return respuesta;
    }
    
    @Path("getAllConsulta")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Consulta> getConsultas(){
        List<Consulta> resultado = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                resultado = conn.selectList("Consulta.getAllConsultas");
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conn.close();
            }
        }else{
            System.out.println("Error de conexion");
        }
        return resultado;
    }
    
    @Path("allPacientee")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TipoPaciente> AllPacientes(){
        List<TipoPaciente> aero = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                aero = conn.selectList("Cita.getPaciente");
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conn.close();
            }
        }else{
            System.out.println("Error de conexion");
        }
        return aero;
    }
    
    @Path("consultaPaciente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public List<Consulta> getConsultaPaciente(@FormParam("idPaciente") Integer idPaciente){
        List<Consulta> resultado = null;
        SqlSession conn = MyBatisUtil.getSession();
        HashMap<String,Object> param = new HashMap<>();
        param.put("idPaciente", idPaciente);
        if(conn != null){ 
            try {
                resultado = conn.selectList("Consulta.getConsultaPaciente", idPaciente);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conn.close();
            }
        }else{
            System.out.println("Error de conexion");
        }
        return resultado;
    }
    
    
    @Path("registraConsulta")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje registraConsulta(@FormParam("idPaciente") int idPaciente,@FormParam("observaciones") String observaciones,@FormParam("peso") float peso,@FormParam("talla") int talla,
            @FormParam("IMC") float IMC, @FormParam("idDieta") int idDieta){
        Mensaje respuesta = new Mensaje();
        
        Consulta cons = new Consulta (0,idPaciente,observaciones,peso,talla,IMC,idDieta);
        SqlSession conn = MyBatisUtil.getSession();
        
        if(conn != null){
            try{
               int resultado = conn.insert("Consulta.registraConsulta", cons);
               conn.commit();
               if(resultado > 0){
                   respuesta.setError(false);
                   respuesta.setMensaje("Registro agregado con exito...");
               }else{
                   respuesta.setError(true);
                   respuesta.setMensaje("No se pudo agregar el registro");
               }
            }catch(Exception e){
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexion con la BD..");
        }
        return respuesta;
    }
    
    @Path("editarConsulta")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje editarConsulta(@FormParam("idConsultas") int idConsultas,@FormParam("observaciones") String observaciones,@FormParam("peso") float peso,@FormParam("talla") int talla,
            @FormParam("IMC") float IMC, @FormParam("idDieta") int idDieta, @FormParam("idPaciente") int idPaciente){
        Mensaje respuesta = new Mensaje();
        Consulta cons = new Consulta(idConsultas,idPaciente,observaciones,peso,talla,IMC,idDieta);
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try{
                int resultado = conn.update("Consulta.editarConsulta", cons);
                conn.commit();
                if(resultado > 0 ){
                    respuesta.setError(false);
                    respuesta.setMensaje("Registro actualizado con exito...");
                }else{
                    respuesta.setError(true);
                    respuesta.setMensaje("El registro no pudo ser actualizado...");
                }
            }catch(Exception e){
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexion con la BD...");
        }
        return respuesta;
    }
    
    /*@Path("eliminarConsulta")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarConsulta(@FormParam("idConsulta") Integer idConsultas){
        Mensaje respuesta = new  Mensaje();
        Consulta cons = new Consulta(idConsultas,1,"ninguna",55,36,21,1); 
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try{
              int resultado = conn.delete("Consulta.eliminarConsulta", idConsultas);
              conn.commit();
              if(resultado > 0){
                  respuesta.setError(false);
                  respuesta.setMensaje("Registro eliminado correctamente...");
              }else{
                  respuesta.setError(true);
                  respuesta.setMensaje("El registro no se pudo eliminar...");
              }
            }catch(Exception e){
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("No hay conexion con la BD..");
        }
        return respuesta;
    }*/
    
    @Path("eliminarConsulta")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarConsulta(@FormParam("idConsulta") Integer idConsultas){
        Mensaje respuesta = new Mensaje();
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                System.out.println("idcatalogo: "+ idConsultas);
                int resultado = conn.delete("Consulta.eliminarConsulta", idConsultas);
                conn.commit();
                if( resultado > 0 ){
                    respuesta.setError(false);
                    respuesta.setMensaje("Registro eliminado correctamente...");
                }else{
                    respuesta.setError(true);
                    respuesta.setMensaje("El registro no se pudo eliminar...");
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