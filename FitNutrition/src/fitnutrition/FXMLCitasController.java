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
import pojo.Citas;
import pojo.RespuestaWS;
import util.Constantes;
import util.ConsumoWS;




/**
 * FXML Controller class
 *
 * @author maria
 */
public class FXMLCitasController implements Initializable {

    @FXML
    private TextField tfBuscar;
    @FXML
    private TableView<Citas> tbCita;
    @FXML
    private TableColumn colIdPaciente;
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
        this.colIdPaciente.setCellValueFactory(new PropertyValueFactory("id Paciente"));
        this.colFechaCita.setCellValueFactory(new PropertyValueFactory("Fecha cita"));
        this.colHoraCita.setCellValueFactory(new PropertyValueFactory("Hora cita"));
        this.colObservaciones.setCellValueFactory(new PropertyValueFactory("Observaciones"));
        
        cargaElementosTabla();
    }   
    
    private void cargaElementosTabla(){
        String url = Constantes.URL + "allbdCita";
        RespuestaWS resp = ConsumoWS.consumoWSGET(url);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Type tipolistaaero = new TypeToken<List<Citas>>(){}.getType();
            ArrayList<Citas> medicosBD = gson.fromJson(resp.getMensaje(), tipolistaaero);
            cita = FXCollections.observableArrayList(medicosBD);
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
            controlador.InicializaCampos((NotificaCambios) this,true,null); //falta corregir
                    
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
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
    }
    
    
    public void RefrescarTlaba(boolean dato) {
        System.out.println("Valor es: "+dato);
        tbCita.getItems().clear();
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
                        }else if(busqueda.getIdCita().toString().contains(CaseFilter)){
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
    
   
     
}
