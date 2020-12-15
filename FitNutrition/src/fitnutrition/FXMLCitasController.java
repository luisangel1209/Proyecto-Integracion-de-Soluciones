package fitnutrition;

import pojo.Citas;
import pojo.RespuestaWS;
import util.Constantes;
import util.ConsumoWS;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
        this.colFechaCita.setCellValueFactory(new PropertyValueFactory("Fecha cita"));
        this.colHoraCita.setCellValueFactory(new PropertyValueFactory("Hora cita"));
        this.colObservaciones.setCellValueFactory(new PropertyValueFactory("Observaciones"));
        
        cargaElementosTabla();
    }   
    
    private void cargaElementosTabla(){
        
    }
    
    @FXML
    private void clicAgregar(ActionEvent e){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormulario.fxml"));
            Parent root = loader.load();
            
            //FXMLFormularioController controlador = loader.getController();
            Scene scFormulario = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scFormulario);
            stage.showAndWait();
            
            
        } catch(IOException ex){
            System.out.println("Error al cargar ventana");
        }
    }
    private void DialogError(String titulo, String mensaje){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(titulo);
        error.setHeaderText(null);
        error.setContentText(mensaje);
        error.showAndWait();
    }
    
}
