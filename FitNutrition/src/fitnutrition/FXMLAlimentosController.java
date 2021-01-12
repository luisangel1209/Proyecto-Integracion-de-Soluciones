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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pojo.Alimentos;
import pojo.RespuestaWS;
import util.Constantes;
import util.ConsumoWS;

/**
 * FXML Controller class
 *
 * @author FAMSA
 */
public class FXMLAlimentosController implements Initializable, NotificaCambios {

    @FXML
    private TextField TextBuscar;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCalorias;
    @FXML
    private TableColumn colPorcion;
    @FXML
    private TableView<Alimentos> tbAlimentos;

    private ObservableList<Alimentos> alimentos;
    
    private NotificaCambios notificacion;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre_alimento"));
        this.colCalorias.setCellValueFactory(new PropertyValueFactory("calorias_porcion"));
        this.colPorcion.setCellValueFactory(new PropertyValueFactory("tipo"));
        cargaElementosTabla();
        FuncionBuscar();
    }

    private void cargaElementosTabla(){
        String url = Constantes.URL + "AlimentosyMedicos/allbdAlimentos";
        RespuestaWS resp = ConsumoWS.consumoWSGET(url);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Type tipolistaalimento = new TypeToken<List<Alimentos>>(){}.getType();
            ArrayList<Alimentos> alimentosBD = gson.fromJson(resp.getMensaje(), tipolistaalimento);
            alimentos = FXCollections.observableArrayList(alimentosBD);
            tbAlimentos.setItems(alimentos);
        }else{
            DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
        }
    }
    
    @FXML
    private void clickAgregar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioAgregarAlimento.fxml"));
            Parent root = loader.load();
            
            FXMLFormularioAgregarAlimentoController controlador = loader.getController();
            controlador.InicializaCampos(this, true, null);
            
            Scene scForm = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scForm);
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error cargar ventana");
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        System.out.println("Valor es: "+dato);
        //tbAlimentos.getItems().clear();
        cargaElementosTabla();
    }

    @FXML
    private void clickEditar(ActionEvent event) {
        int celda = tbAlimentos.getSelectionModel().getSelectedIndex();
        if(celda >= 0){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioAgregarAlimento.fxml"));
                Parent root = loader.load();
            
                FXMLFormularioAgregarAlimentoController controlador = loader.getController();
                controlador.InicializaCampos(this, false, alimentos.get(celda));
            
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

    
    private void FuncionBuscar(){
        if(alimentos.size() > 0){
            FilteredList<pojo.Alimentos> filtroAero = new FilteredList<>(alimentos, p->true);
            TextBuscar.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroAero.setPredicate(busqueda -> {
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        //Convertir minusculas
                        String CaseFilter = newValue.toLowerCase();
                        if(busqueda.getNombre_alimento().toLowerCase().contains(CaseFilter)){
                            return true;
                        }else if(busqueda.getTipo().toLowerCase().contains(CaseFilter)){
                            return true;
                        }
                        return false;
                    });
                }      
            });
            SortedList<pojo.Alimentos> sortedDatos = new SortedList<>(filtroAero);
            sortedDatos.comparatorProperty().bind(tbAlimentos.comparatorProperty());
            tbAlimentos.setItems(sortedDatos);
        }
    }    

    @FXML
    private void Regresar(ActionEvent event) {
        try {
            Stage stage = (Stage) TextBuscar.getScene().getWindow();
            Scene sceneprincipal = new Scene(FXMLLoader.load(getClass().getResource("FXMLPrincipal.fxml")));
            stage.setScene(sceneprincipal);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
