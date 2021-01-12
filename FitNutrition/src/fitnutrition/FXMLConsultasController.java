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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pojo.Consulta;
import pojo.Mensaje;
import pojo.RespuestaWS;
import util.Constantes;
import util.ConsumoWS;

/**
 * FXML Controller class
 *
 * @author maria
 */
public class FXMLConsultasController implements Initializable, NotificaCambios {

    @FXML
    private TextField tfBuscar;
    @FXML
    private TableView<Consulta> tbConsulta;
    @FXML
    private TableColumn colIdPaciente;
    @FXML
    private TableColumn colObservaciones;
    @FXML
    private TableColumn colPeso;
    @FXML
    private TableColumn colTalla;
    @FXML
    private TableColumn colImc;
    
    private ObservableList<Consulta> consulta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.colIdPaciente.setCellValueFactory(new PropertyValueFactory("idPaciente"));
        this.colObservaciones.setCellValueFactory(new PropertyValueFactory("observaciones"));
        this.colPeso.setCellValueFactory(new PropertyValueFactory("peso"));
        this.colTalla.setCellValueFactory(new PropertyValueFactory("talla"));
        this.colImc.setCellValueFactory(new PropertyValueFactory("imc"));
        
        cargaElementosTabla();
        FuncionBuscar();
    }   
    
    private void cargaElementosTabla(){
        String url = Constantes.URL + "fitNuutrition/getAllConsulta";
        RespuestaWS resp = ConsumoWS.consumoWSGET(url);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Type tipolistaconsul = new TypeToken<List<Consulta>>(){}.getType();
            ArrayList<Consulta> consultasBD = gson.fromJson(resp.getMensaje(), tipolistaconsul);
            consulta = FXCollections.observableArrayList(consultasBD);
            tbConsulta.setItems(consulta);
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
    private void clicAgregar(ActionEvent e){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioAgregaConsulta.fxml"));
            Parent root = loader.load();
            
            FXMLFormularioAgregaCitaController controlador = loader.getController();
            controlador.InicializaCampos(this,true,null); //falta corregir
                    
            Scene scFormulario = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scFormulario);
            stage.showAndWait();    
        } catch(IOException ex){
            System.out.println("Error al cargar ventana");
            Logger.getLogger(FXMLFormularioAgregaConsultaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void clickEditar(ActionEvent event) {
        int celda = tbConsulta.getSelectionModel().getSelectedIndex();
        if(celda >= 0){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioAgregaConsulta.fxml"));
                Parent root = loader.load();
            
                FXMLFormularioAgregaConsultaController controlador = loader.getController();
                controlador.InicializaCampos(this, false, consulta.get(celda));
            
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
    
    @FXML
    private void clickEliminar(ActionEvent event) {
    }
    
    
    public void RefrescarTlaba(boolean dato) {
        System.out.println("Valor es: "+dato);
        tbConsulta.getItems().clear();
        cargaElementosTabla();
    }
    
    private void FuncionBuscar(){
        if(consulta.size() > 0){
            FilteredList<pojo.Consulta> filtroConsulta = new FilteredList<>(consulta, p->true);
            tfBuscar.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroConsulta.setPredicate(busqueda -> {
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        //Convertir minusculas
                        String CaseFilter = newValue.toLowerCase();
                        if(busqueda.getIdPaciente().toString().contains(CaseFilter)){
                            return true;
                        }else if(busqueda.getIdPaciente().toString().contains(CaseFilter)){
                            return true;
                        }
                        return false;
                    });
                }      
            });
            SortedList<pojo.Consulta> sortedDatos = new SortedList<>(filtroConsulta);
            sortedDatos.comparatorProperty().bind(tbConsulta.comparatorProperty());
            tbConsulta.setItems(sortedDatos);
        }
    }
    
    @FXML
    private void Regresar(ActionEvent event) {
        try {
            Stage stage = (Stage) tfBuscar.getScene().getWindow();
            Scene sceneprincipal = new Scene(FXMLLoader.load(getClass().getResource("FXMLPrincipal.fxml")));
            stage.setScene(sceneprincipal);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
