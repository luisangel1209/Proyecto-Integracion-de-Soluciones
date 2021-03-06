/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import Pojos.Alimentos;
import Pojos.Medico;
import Pojos.Mensaje;
import Pojos.TipoPorcionAlimentos;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Luis Angel 
 */
@Path("AlimentosyMedicos")
public class AlimentoyMedico {

    public AlimentoyMedico() {
    }
    
    @Path("allbdAlimentos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alimentos> getAllBdAlimentos(){
        List<Alimentos> resultado = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                resultado = conn.selectList("AlimentosyMedicos.getAllAlimentos");
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
    
    @Path("allTipoPorcion")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TipoPorcionAlimentos> getAllBdTipoPorcion(){
        List<TipoPorcionAlimentos> aero = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                aero = conn.selectList("AlimentosyMedicos.getTiposPorcion");
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
    
    @Path("registroAlimentos")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje crearAlimento(@FormParam("nombre_alimento") String nombre_alimento, @FormParam("calorias_porcion") int calorias_porcion, 
            @FormParam("idTipoPorcion") Integer idTipoPorcion){
        Mensaje respuesta = new Mensaje();
        
        Alimentos ali =  new Alimentos(0,nombre_alimento, calorias_porcion, idTipoPorcion, "");
        SqlSession conn = MyBatisUtil.getSession();
        
        if(conn != null){
            try {
                int resultado = conn.insert("AlimentosyMedicos.registrarAlimento", ali);
                conn.commit();
                System.out.println("nombre :"+ nombre_alimento + "calorias: "+calorias_porcion+" tipo:"+idTipoPorcion);
                if(resultado > 0){
                    respuesta.setError(false);
                    respuesta.setMensaje("Alimento agregado con éxito...");
                }else{
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo agregar el alimento");
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
    
    @Path("editarAlimento")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje editarAlimento(@FormParam("idAlimento") Integer idAlimento, @FormParam("nombre_alimento") String nombre_alimento, @FormParam("calorias_porcion") int calorias_porcion, 
            @FormParam("idTipoPorcion") Integer idTipoPorcion){
        Mensaje respuesta = new Mensaje();
        Alimentos ali = new Alimentos(idAlimento, nombre_alimento, calorias_porcion, idTipoPorcion, "");
        SqlSession conn =  MyBatisUtil.getSession();
        if(conn != null){
           try{
               int resultado = conn.update("AlimentosyMedicos.actualizarAlimento", ali);
               conn.commit();
               if(resultado > 0){
                   respuesta.setError(false);
                   respuesta.setMensaje("Alimento actualizado con éxito...");
               }else{
                   respuesta.setError(true);
                   respuesta.setMensaje("El alimento no pudo ser actualizado...");
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
    
    //Medicos
    
    @Path("allbdMedicos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Medico> getAllBdMedicos(){
        List<Medico> resultado = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                resultado = conn.selectList("AlimentosyMedicos.getAllMedicos");
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
    
    @Path("allbdMedicosActivos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Medico> getAllBdMedicosActivos(){
        List<Medico> resultado = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                resultado = conn.selectList("AlimentosyMedicos.getAllMedicosActivos");
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
    
    @Path("registroMedico")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje crearMedico(@FormParam("nombre") String nombre, @FormParam("apellidos") String apellidos, @FormParam("fNac") String fNac,
            @FormParam("genero") String genero, @FormParam("domicilio") String domicilio, @FormParam("numPersonal") int numPersonal,
            @FormParam("cedulaProfesional") String cedulaProfesional, @FormParam("contraseña") String contraseña, @FormParam("foto_medico") String foto_medico,
            @FormParam("estatus") String estatus){
        Mensaje respuesta = new Mensaje();
        
        Medico medi =  new Medico(0,nombre, apellidos, fNac, genero, domicilio, numPersonal, cedulaProfesional, contraseña, estatus);
        SqlSession conn = MyBatisUtil.getSession();
        
        if(conn != null){
            try {
                int resultado = conn.insert("AlimentosyMedicos.registrarMedico", medi);
                conn.commit();
                if(resultado > 0){
                    respuesta.setError(false);
                    respuesta.setMensaje("Médico agregado con éxito...");
                }else{
                    respuesta.setError(true);
                    respuesta.setMensaje("No se pudo agregar el médico");
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
    
    @Path("bajaMedico")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje bajaMedico(@FormParam("idMedico") Integer idMedico, @FormParam("estatus") String estatus){
        Mensaje respuesta = new Mensaje();
        Medico medi = new Medico(idMedico, estatus);
        SqlSession conn =  MyBatisUtil.getSession();
        if(conn != null){
           try{
               int resultado = conn.update("AlimentosyMedicos.bajaMedico", medi);
               conn.commit();
               if(resultado > 0){
                   respuesta.setError(false);
                   respuesta.setMensaje("Médico dado de baja con éxito...");
               }else{
                   respuesta.setError(true);
                   respuesta.setMensaje("El médico no se pudo dar de baja...");
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
    
    @Path("editarMedico")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje editarMedico(@FormParam("idMedico") int idMedico, @FormParam("nombre") String nombre, @FormParam("apellidos") String apellidos, @FormParam("fNac") String fNac,
            @FormParam("genero") String genero, @FormParam("domicilio") String domicilio, @FormParam("numPersonal") int numPersonal,
            @FormParam("cedulaProfesional") String cedulaProfesional, @FormParam("contraseña") String contraseña, @FormParam("estatus") String estatus){
        Mensaje respuesta = new Mensaje();
        Medico medico = new Medico(idMedico, nombre, apellidos, fNac, genero, domicilio, numPersonal, cedulaProfesional, contraseña, estatus);
        SqlSession conn =  MyBatisUtil.getSession();
        if(conn != null){
           try{
               int resultado = conn.update("AlimentosyMedicos.editarMedico", medico);
               conn.commit();
               if(resultado > 0){
                   respuesta.setError(false);
                   respuesta.setMensaje("Médico actualizado con éxito...");
               }else{
                   respuesta.setError(true);
                   respuesta.setMensaje("Los datos no pudieron ser actualizados...");
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
    
    @Path("SubirImagen/{idMedico}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje subirImagen(byte[] foto_medico, @PathParam("idMedico") int idMedico){
        SqlSession con = MyBatisUtil.getSession();
        Medico medico = new Medico();
        medico.setIdMedico(idMedico);;
        medico.setFoto(foto_medico);
        Mensaje msj = new Mensaje();
        try{
            int res = con.update("AlimentosyMedicos.enviarImgAerolinea", medico);
            con.commit();
            if(res > 0){
                msj.setError(false);
                msj.setMensaje("Imagen subida con éxito");
            }else{
                msj.setError(true);
                msj.setMensaje("No se pudo guardar la imagen");
            }
        }catch(Exception ex){
            msj.setError(true);
            msj.setMensaje(ex.getMessage());
        }finally{
            con.close();
        }
        return msj;
    }
    
    @Path("getImgAerolinea/{idMedico}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje getImgAerolinea(@PathParam("idMedico") int idMedico){
        Mensaje msj = new Mensaje();
        SqlSession con = MyBatisUtil.getSession();
        String PATH = "/Users/FAMSA/Desktop/imgMedicos/"+idMedico+".png";
        if(con != null){
            try{
                Medico medico = con.selectOne("AlimentosyMedicos.getImgAerolinea", idMedico);
                System.out.println("Medico: "+ medico.getNombre());
                boolean isSave = EscribeImgAvion(PATH, medico.getFoto());
                if(isSave){
                    msj.setError(false);
                    msj.setMensaje(PATH);
                }else{
                    msj.setError(true);
                    msj.setMensaje("No se pudo obtener la imagen");
                }
            }catch(Exception ex){
                
            }
        }else{
            msj.setError(true);
            msj.setMensaje("Sin conexion a la BD");
        }
        return msj;
    }
    
    private boolean EscribeImgAvion(String path, byte[] bytes){
       InputStream in = new ByteArrayInputStream(bytes);
        try {
            BufferedImage buffImage = ImageIO.read(in);
            ImageIO.write(buffImage, "png", new File(path));
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }
}
