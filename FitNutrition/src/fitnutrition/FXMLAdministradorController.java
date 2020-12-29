/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnutrition;

import NotificaCambios.NotificaCambios;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
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
public class FXMLAdministradorController implements Initializable, NotificaCambios {

    @FXML
    private TextField TextBuscar;
    @FXML
    private TableView<Medico> tbMedicos;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidos;
    @FXML
    private TableColumn colfNac;
    @FXML
    private TableColumn colGenero;
    @FXML
    private TableColumn colDomicilio;
    @FXML
    private TableColumn colPersonal;
    @FXML
    private TableColumn colCedula;
    @FXML
    private TableColumn colContra;
    @FXML
    private TableColumn colFoto;
    @FXML
    private TableColumn colEstatus;
    
    private ObservableList<Medico> medicos;
    @FXML
    private Button BotonDarBaja;
    private NotificaCambios notificacion;
    @FXML
    private Button BotonActivar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BotonDarBaja.setDisable(true);
        BotonActivar.setDisable(true);
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
        this.colfNac.setCellValueFactory(new PropertyValueFactory("fNac"));
        this.colGenero.setCellValueFactory(new PropertyValueFactory("genero"));
        this.colDomicilio.setCellValueFactory(new PropertyValueFactory("domicilio"));
        this.colPersonal.setCellValueFactory(new PropertyValueFactory("numPersonal"));
        this.colCedula.setCellValueFactory(new PropertyValueFactory("cedulaProfesional"));
        this.colContra.setCellValueFactory(new PropertyValueFactory("contraseña"));
        this.colFoto.setCellValueFactory(new PropertyValueFactory("foto"));
        this.colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        cargaElementosTabla();
        FuncionBuscar();
    } 
    
    private void cargaElementosTabla(){
        String url = Constantes.URL + "AlimentosyMedicos/allbdMedicos";
        RespuestaWS resp = ConsumoWS.consumoWSGET(url);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Type tipolistamedi = new TypeToken<List<Medico>>(){}.getType();
            ArrayList<Medico> medicosBD = gson.fromJson(resp.getMensaje(), tipolistamedi);
            medicos = FXCollections.observableArrayList(medicosBD);
            tbMedicos.setItems(medicos);
        }else{
            DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
        }
    }

    private void DialogError(String titulo, String mensaje){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(titulo);
        error.setHeaderText(null);
        error.setContentText(mensaje);
        error.showAndWait();
    }
    
    @FXML
    private void clickAgregar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioAgregarMedico.fxml"));
            Parent root = loader.load();
            
            FXMLFormularioAgregarMedicoController controlador = loader.getController();
            controlador.InicializaCampos(this, true, null);
            
            Scene scForm = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scForm);
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error cargar ventana");
            Logger.getLogger(FXMLFormularioAgregarMedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clickEditar(ActionEvent event) {
        int celda = tbMedicos.getSelectionModel().getSelectedIndex();
        if(celda >= 0){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioAgregarMedico.fxml"));
                Parent root = loader.load();
            
                FXMLFormularioAgregarMedicoController controlador = loader.getController();
                controlador.InicializaCampos(this, false, medicos.get(celda));
            
                Scene scForm = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scForm);
                stage.showAndWait();
            } catch (IOException ex) {
                System.out.println("Error al cargar ventana");
                Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            DialogError("Selecciona registro", "Para editar un registro seleccionalo de la tabla");
        }
    }

    
    @Override
    public void RefrescarTlaba(boolean dato) {
        System.out.println("Valor es: "+dato);
        //tbMedicos.getItems().clear();
        cargaElementosTabla();
    }
    
    private void FuncionBuscar(){
        if(medicos.size() > 0){
            FilteredList<pojo.Medico> filtroMedi = new FilteredList<>(medicos, p->true);
            TextBuscar.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroMedi.setPredicate(busqueda -> {
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        //Convertir minusculas
                        String CaseFilter = newValue.toLowerCase();
                        if(busqueda.getNombre().toLowerCase().contains(CaseFilter)){
                            return true;
                        }else if(busqueda.getCedulaProfesional().toLowerCase().contains(CaseFilter)){
                            return true;
                        }
                        return false;
                    });
                }      
            });
            SortedList<pojo.Medico> sortedDatos = new SortedList<>(filtroMedi);
            sortedDatos.comparatorProperty().bind(tbMedicos.comparatorProperty());
            tbMedicos.setItems(sortedDatos);
        }
    }

    @FXML
    private void Baja(MouseEvent event) {
        String bandera = tbMedicos.getSelectionModel().getSelectedItem().getEstatus();
        if(!"Activo".equals(bandera)){
            BotonDarBaja.setDisable(true);
            BotonActivar.setDisable(false);
        }else{
            BotonDarBaja.setDisable(false);
            BotonActivar.setDisable(true);
        }
    }

    @FXML
    private void clickDarBaja(ActionEvent event) {
        int idMedico = tbMedicos.getSelectionModel().getSelectedItem().getIdMedico();
        String url = Constantes.URL + "AlimentosyMedicos/bajaMedico";
        String estatus = "No Activo";
        String parametros = String.format("idMedico=%d&estatus=%s", 
            idMedico,
            estatus);
        RespuestaWS res = ConsumoWS.consumoWSPUT(url, parametros);
        if(res.getCodigo() == 200){
            Gson gson = new Gson();
            Mensaje msj = gson.fromJson(res.getMensaje(), Mensaje.class);
            if(msj.isError()){
                DialogError("Error al desactivar", msj.getMensaje());
            }else{
                muestraDialogo("Médico dado de baja correctamente...", msj.getMensaje());
                RefrescarTlaba(true);
            }
        }else{
            DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
        }
    }
    
    private void muestraDialogo(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void Activar(ActionEvent event) {
        int idMedico = tbMedicos.getSelectionModel().getSelectedItem().getIdMedico();
        String url = Constantes.URL + "AlimentosyMedicos/bajaMedico";
        String estatus = "Activo";
        String parametros = String.format("idMedico=%d&estatus=%s", 
            idMedico,
            estatus);
        RespuestaWS res = ConsumoWS.consumoWSPUT(url, parametros);
        if(res.getCodigo() == 200){
            Gson gson = new Gson();
            Mensaje msj = gson.fromJson(res.getMensaje(), Mensaje.class);
            if(msj.isError()){
                DialogError("Error al activar", msj.getMensaje());
            }else{
                muestraDialogo("Activación", "Médico Activado correctamente...");
                RefrescarTlaba(true);
            }
        }else{
            DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
        }
    }
}
