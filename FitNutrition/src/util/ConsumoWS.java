package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import pojo.RespuestaWS;

/**
 *
 * @author maria
 */
public class ConsumoWS {
    public static RespuestaWS consumoWSGET(String url){
        RespuestaWS respuesta = new RespuestaWS();
        try {
            URL urlws = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)urlws.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(Constantes.time_out);
            conn.connect();
            
            System.out.println("Respuesta Codigo "+ conn.getResponseCode());
            respuesta.setCodigo(conn.getResponseCode());
                
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String salida;
            while((salida = br.readLine()) != null){
                respuesta.setMensaje(salida);
                System.out.println(salida);
            }    
            conn.disconnect();
        } catch (Exception ex) {
            respuesta.setCodigo(505);
            respuesta.setMensaje(ex.getMessage());
        }
        return respuesta;
    }
    
    public static RespuestaWS consumoWSPOST(String url, String parametros){
        RespuestaWS resp = new RespuestaWS();
        try {
            URL urlws = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)urlws.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(Constantes.time_out);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStream os = conn.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            conn.connect();
            
            //leer
            System.out.println("Respuesta Codigo "+ conn.getResponseCode());
            resp.setCodigo(conn.getResponseCode());
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String salida;
            while((salida = br.readLine()) != null){
                resp.setMensaje(salida);
                System.out.println(salida);
            }
            conn.disconnect();
        } catch (Exception ex) {
            resp.setCodigo(505);
            resp.setMensaje(ex.getMessage());
        }
        return resp;
    }
    
    public static RespuestaWS consumoWSPUT(String url, String parametros){
        RespuestaWS resp = new RespuestaWS();
        try {
            URL urlws = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)urlws.openConnection();
            conn.setRequestMethod("PUT");
            conn.setConnectTimeout(Constantes.time_out);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStream os = conn.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            conn.connect();
            
            //leer
            System.out.println("Respuesta Codigo "+ conn.getResponseCode());
            resp.setCodigo(conn.getResponseCode());
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String salida;
            while((salida = br.readLine()) != null){
                resp.setMensaje(salida);
                System.out.println(salida);
            }
            conn.disconnect();
        } catch (Exception ex) {
            resp.setCodigo(505);
            resp.setMensaje(ex.getMessage());
        }
        return resp;
    }
    
    public static RespuestaWS consumoWSDELETE(String url, String parametros){
        RespuestaWS resp = new RespuestaWS();
        try {
            URL urlws = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)urlws.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setConnectTimeout(Constantes.time_out);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStream os = conn.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            conn.connect();
            
            //leer
            System.out.println("Respuesta Codigo "+ conn.getResponseCode());
            resp.setCodigo(conn.getResponseCode());
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String salida;
            while((salida = br.readLine()) != null){
                resp.setMensaje(salida);
                System.out.println(salida);
            }
            conn.disconnect();
        } catch (Exception ex) {
            resp.setCodigo(505);
            resp.setMensaje(ex.getMessage());
        }
        return resp;
    }
}
