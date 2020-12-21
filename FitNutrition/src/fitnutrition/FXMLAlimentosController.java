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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
        String url = Constantes.URL + "allbdAlimentos";
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
        tbAlimentos.getItems().clear();
        cargaElementosTabla();
    }

    @FXML
    private void clickAgregar(ActionEvent event) {
    }

    @FXML
    private void clickEditar(ActionEvent event) {
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
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
    
}
