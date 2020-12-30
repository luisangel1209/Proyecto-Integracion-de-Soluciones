/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnutrition;

import NotificaCambios.NotificaCambios;
import com.google.gson.Gson;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pojo.Medico;
import pojo.Mensaje;
import pojo.RespuestaWS;
import util.Constantes;
import util.ConsumoWS;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXMLFormularioAgregarMedicoController implements Initializable, NotificaCambios {

    @FXML
    private Label labelTitulo;
    @FXML
    private TextField lbNombre;
    @FXML
    private TextField lbApellido;
    @FXML
    private TextField lbDomicilio;
    @FXML
    private ComboBox<String> comboEstatus;
    @FXML
    private ImageView fotoMedico;
    @FXML
    private TextField lbCedula;
    @FXML
    private TextField lbNumeroPersonal;
    @FXML
    private DatePicker Date;
    @FXML
    private ComboBox<String> comboGenero;
    @FXML
    private TextField lbContra;

    private NotificaCambios notificacion;
    private boolean isNuevo;
    private Medico medico;
    
    ObservableList<String> genero = FXCollections.observableArrayList("Masculino", "Femenino");
    ObservableList<String> estatus = FXCollections.observableArrayList("Activo", "No Activo");
    @FXML
    private Button BotonGuardar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboGenero.setItems(genero);
        comboGenero.setPromptText("Elige el genero");
        comboEstatus.setItems(estatus);
        comboEstatus.setPromptText("Elige el estatus");
        
    }   
    
    public void InicializaCampos(NotificaCambios notificacion, boolean isNuevo, Medico medico){
        this.isNuevo = isNuevo;
        this.medico = medico;
        this.notificacion = notificacion;
        if(!isNuevo){
            labelTitulo.setText("Editar Médico");
            cargaDatosEdicion();
        }
    }
    
    private void cargaDatosEdicion(){
        if(medico != null){
            String fecha = medico.getfNac();
            LocalDate fecha2 = LocalDate.parse(fecha);
            lbNombre.setText(medico.getNombre());
            lbApellido.setText(medico.getApellidos());
            Date.setValue(fecha2);
            comboGenero.setValue(medico.getGenero());
            lbDomicilio.setText(medico.getDomicilio());
            lbNumeroPersonal.setText(""+medico.getNumPersonal());
            lbCedula.setText(medico.getCedulaProfesional());
            lbContra.setText(medico.getContraseña());
            comboEstatus.setValue(medico.getEstatus());
        }
    }

    @FXML
    private void clickSeleccionar(ActionEvent event) {
    }

    @FXML
    private void Guardar(ActionEvent event) {
        if(isNuevo){
            String url = Constantes.URL + "AlimentosyMedicos/registroMedico";
            int numPerso = Integer.parseInt(lbNumeroPersonal.getText());
            String foto = "Prueba";
            String date = ""+Date.getValue();
            String generoo = comboGenero.getValue();
            String estatuss = comboEstatus.getValue();
            String parametros = String.format("nombre=%s&apellidos=%s&fNac=%s&genero=%s&domicilio=%s&numPersonal=%d&cedulaProfesional=%s&contraseña=%s&foto_medico=%s&estatus=%s", 
                    lbNombre.getText(),
                    lbApellido.getText(),
                    date,
                    generoo,
                    lbDomicilio.getText(),
                    numPerso,
                    lbCedula.getText(),
                    lbContra.getText(),
                    foto,
                    estatuss
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
            String url = Constantes.URL + "AlimentosyMedicos/editarMedico";
            int numPerso = Integer.parseInt(lbNumeroPersonal.getText());
            String foto = "Prueba";
            String date = ""+Date.getValue();
            String generoo = comboGenero.getValue();
            String estatuss = comboEstatus.getValue();
            String parametros = String.format("idMedico=%d&nombre=%s&apellidos=%s&fNac=%s&genero=%s&domicilio=%s&numPersonal=%d&cedulaProfesional=%s&contraseña=%s&foto_medico=%s&estatus=%s", 
                    medico.getIdMedico(),
                    lbNombre.getText(),
                    lbApellido.getText(),
                    date,
                    generoo,
                    lbDomicilio.getText(),
                    numPerso,
                    lbCedula.getText(),
                    lbContra.getText(),
                    foto,
                    estatuss
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
    
    private void CerrarScena(){
        Stage stage = (Stage) this.BotonGuardar.getScene().getWindow();
        stage.close();
        notificacion.RefrescarTlaba(true);
    }
    
    private void DialogError(String titulo, String mensaje){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(titulo);
        error.setHeaderText(null);
        error.setContentText(mensaje);
        error.showAndWait();
    }

    @Override
    public void RefrescarTlaba(boolean dato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
