/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnutrition;

import NotificaCambios.NotificaCambios;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pojo.Consulta;
import pojo.TipoPaciente;
import pojo.Mensaje;
import pojo.RespuestaWS;
import pojo.TipoPaciente;
import util.Constantes;
import util.ConsumoWS;

/**
 * FXML Controller class
 *
 * @author maria
 */
public class FXMLFormularioAgregaConsultaController implements Initializable, NotificaCambios {

    @FXML
    private Label labelTitulo;
    private TextField idPaciente;
    @FXML
    private TextField tfPeso;
    @FXML
    private TextField tfTalla;
    @FXML
    private TextField tfImc;
    @FXML
    private Button botonGuardar;
    @FXML
    private TextField tfObservaciones;
    @FXML
    private ComboBox<TipoPaciente> comboPaciente;
    private ObservableList<TipoPaciente> Pacientes;

    private NotificaCambios notificacion;
    private boolean isNuevo;
    private Consulta consulta;
        

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Pacientes = FXCollections.observableArrayList();
        cargaElementos();
    }    
    
    public void InicializaCampos(NotificaCambios notificacion, boolean isNuevo, Consulta consulta){
        this.isNuevo = isNuevo;
        this.consulta = consulta;
        this.notificacion = notificacion;
        if(!isNuevo){
            labelTitulo.setText("Editar consulta");
            cargaDatosEdicion();
        }
    }
    
    private void cargaDatosEdicion(){
        if(consulta != null){
            int posCombo = getIndexLista(consulta.getIdPaciente());
            comboPaciente.getSelectionModel().select(posCombo);
            idPaciente.setText( (consulta.getIdPaciente()).toString());
            tfObservaciones.setText(consulta.getObservaciones());
            tfPeso.setText(Float.toString(consulta.getPeso()));
            tfTalla.setText(Integer.toString(consulta.getTalla()));
            tfImc.setText(Float.toString(consulta.getIMC()));
        }
    }
    
    @FXML
    private void clicEnviar (ActionEvent e){
        int idPacientee = Pacientes.get(comboPaciente.getSelectionModel().getSelectedIndex()).getIdPaciente();
        if(isNuevo){
            String url = Constantes.URL + "fitNuutrition/registraConsulta";
            String parametros = String.format("idPaciente=%d&observaciones=%s&peso=%s&talla=%s&IMC=%s&idDieta=%d", 
                    idPacientee,
                    tfObservaciones.getText(),
                    tfPeso.getText(),
                    tfTalla.getText(),
                    tfImc.getText()
                    );
            
            RespuestaWS resp = ConsumoWS.consumoWSPOST(url, parametros);
            if(resp.getCodigo() == 200){
                Gson gson = new Gson();
                Mensaje msj = gson.fromJson(resp.getMensaje(), Mensaje.class);
                if(msj.isError()){
                    DialogError("Error al agregar", msj.getMensaje());
                }else{
                    muestraDialogo("Registro Agregado", msj.getMensaje());
                    CerrarScena();
                }
            }else{
                DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
            }
        }else{
            String url = Constantes.URL + "fitNuutrition/editarConsulta";
            //int idPacientee = Pacientes.get(comboPaciente.getSelectionModel().getSelectedIndex()).getIdPaciente();
            String parametros = String.format("idConsultas=%d&idPaciente=%d&observaciones=%s&peso=%s&talla=%s&IMC=%s&idDieta=%d", 
                    consulta.getIdConsultas(),
                    idPacientee,
                    tfObservaciones.getText(),
                    tfPeso.getText(),
                    tfTalla.getText(),
                    tfImc.getText()
                    );
            RespuestaWS res = ConsumoWS.consumoWSPUT(url, parametros);
            if(res.getCodigo() == 200){
                Gson gson = new Gson();
                Mensaje msj = gson.fromJson(res.getMensaje(), Mensaje.class);
                if(msj.isError()){
                    DialogError("Error al editar", msj.getMensaje());
                }else{
                    muestraDialogo("Registro editado", msj.getMensaje());
                    CerrarScena();
                }
            }else{
                DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
            }
        }
        
    }
    
    private void muestraDialogo(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void DialogError(String titulo, String mensaje){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(titulo);
        error.setHeaderText(null);
        error.setContentText(mensaje);
        error.showAndWait();
    }
    
     private void CerrarScena(){
        Stage stage = (Stage) this.botonGuardar.getScene().getWindow();
        stage.close();
        notificacion.RefrescarTlaba(true);
    }
     
    private void cargaElementos(){
        String url = Constantes.URL + "fitNuutrition/allPacientee";
        RespuestaWS resp = ConsumoWS.consumoWSGET(url);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Type tipolistaaero = new TypeToken<List<TipoPaciente>>(){}.getType();
            ArrayList<TipoPaciente> tipoaerolineaBD = gson.fromJson(resp.getMensaje(), tipolistaaero);
            Pacientes = FXCollections.observableArrayList(tipoaerolineaBD);
            String tipo = tipoaerolineaBD.get(1).toString();
            System.out.println(tipo);
            comboPaciente.setItems(Pacientes);  
        }else{
            DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
        }
    }
    
    private int getIndexLista(int idTipo){
        for(int i=0; i < Pacientes.size(); i++){
            if(Pacientes.get(i).getIdPaciente() == idTipo){
                return i;
            }
        }
    return 0;
    }
    
    @Override
    public void RefrescarTlaba(boolean dato){
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
