package fitnutrition;

import NotificaCambios.NotificaCambios;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pojo.Citas;
import pojo.TipoPaciente;
import pojo.Mensaje;
import pojo.RespuestaWS;
import util.Constantes;
import util.ConsumoWS;




/**
 * FXML Controller class
 *
 * @author maria
 */
public class FXMLCitasController implements Initializable, NotificaCambios {

    @FXML
    private TextField tfBuscar;
    @FXML
    private TableColumn colIdPaciente;
    @FXML
    private TableColumn colNomPaciente;
    @FXML
    private TableView<Citas> tbCita;
    @FXML
    private TableColumn colFechaCita;
    @FXML
    private TableColumn colHoraCita;
    @FXML
    private TableColumn colObservaciones;
    
    private ObservableList<Citas> cita;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.colIdPaciente.setCellValueFactory(new PropertyValueFactory("idPaciente"));
        this.colNomPaciente.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colFechaCita.setCellValueFactory(new PropertyValueFactory("fecha_cita"));
        this.colHoraCita.setCellValueFactory(new PropertyValueFactory("hora_cita"));
        this.colObservaciones.setCellValueFactory(new PropertyValueFactory("observaciones"));
        
        cargaElementosTabla();
        FuncionBuscar();
    }   
    
    private void cargaElementosTabla(){
        String url = Constantes.URL + "fitNuutrition/getAllCita";
        RespuestaWS resp = ConsumoWS.consumoWSGET(url);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Type tipolistacita = new TypeToken<List<Citas>>(){}.getType();
            ArrayList<Citas> citasBD = gson.fromJson(resp.getMensaje(), tipolistacita);
            cita = FXCollections.observableArrayList(citasBD);
            tbCita.setItems(cita);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioAgregaCita.fxml"));
            Parent root = loader.load();
            
            FXMLFormularioAgregaCitaController controlador = loader.getController();
            controlador.InicializaCampos(this,true,null);
                    
            Scene scFormulario = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scFormulario);
            stage.showAndWait();
            
        } catch(IOException ex){
            System.out.println("Error al cargar ventana");
            Logger.getLogger(FXMLFormularioAgregaCitaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void clickEditar(ActionEvent event) {
        int celda = tbCita.getSelectionModel().getSelectedIndex();
        if(celda >= 0){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioAgregaCita.fxml"));
                Parent root = loader.load();
            
                FXMLFormularioAgregaCitaController controlador = loader.getController();
                controlador.InicializaCampos(this, false, cita.get(celda));
            
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
       int celda = tbCita.getSelectionModel().getSelectedIndex();
       if(celda >= 0){
           Citas aero = cita.get(celda);
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setTitle("Eliminar Registro");
           alert.setHeaderText(null);
           alert.setContentText("¿Seguro de eliminar el resgitro de la cita: " + aero.getIdCitas()+" ?");
           Optional<ButtonType> respboton = alert.showAndWait();
           if(respboton.get() == ButtonType.OK){
               EliminaWSRegistro(aero.getIdCitas());
           }
       }else{
           DialogError("Selecciona un registro", "Para eliminar un registro debes seleccionarlo de la tabla");
       }
    }
    
    private void EliminaWSRegistro(int idCita){
        String parametro = "idCita="+idCita;
        String url = Constantes.URL + "fitNuutrition/eliminarCita";
        RespuestaWS resp = ConsumoWS.consumoWSDELETE(url, parametro);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Mensaje msj = gson.fromJson(resp.getMensaje(), Mensaje.class);
            if(msj.isError()){
                DialogError("Error al eliminar", msj.getMensaje());
            }else{
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Registro eliminado");
                error.setHeaderText(null);
                error.setContentText(msj.getMensaje());
                error.showAndWait();
                //tbCita.getItems().clear();
                cargaElementosTabla();
            }
        }else{
            DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
        }
    }
    
    
    public void RefrescarTlaba(boolean dato) {
        System.out.println("Valor es: "+dato);
        //tbCita.getItems().clear();
        cargaElementosTabla();
    }
    
    private void FuncionBuscar(){
        if(cita.size() > 0){
            FilteredList<pojo.Citas> filtroCita = new FilteredList<>(cita, p->true);
            tfBuscar.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroCita.setPredicate(busqueda -> {
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        //Convertir minusculas
                        String CaseFilter = newValue.toLowerCase();
                        if(busqueda.getIdPaciente().toString().contains(CaseFilter)){
                            return true;
                        }else if(busqueda.getIdCitas().toString().contains(CaseFilter)){
                            return true;
                        }
                        return false;
                    });
                }      
            });
            SortedList<pojo.Citas> sortedDatos = new SortedList<>(filtroCita);
            sortedDatos.comparatorProperty().bind(tbCita.comparatorProperty());
            tbCita.setItems(sortedDatos);
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
