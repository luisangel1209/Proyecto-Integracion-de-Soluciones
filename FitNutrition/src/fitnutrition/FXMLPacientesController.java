package fitnutrition;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pojo.GeneralPacientes;


/**
 * FXML Controller class
 *
 * @author maria
 */
public class FXMLPacientesController implements Initializable {

    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidos;
    @FXML
    private TableColumn colFNac;
    @FXML
    private TableColumn colGenero;
    @FXML
    private TableColumn colPeso;
    @FXML
    private TableColumn colEstatura;
    @FXML
    private TableColumn colTalla;
    @FXML
    private TableColumn colMedico;
    @FXML
    private TableView<GeneralPacientes> tbPacientes;
    
    private ObservableList<GeneralPacientes> gpaciente;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
       this.colApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
       this.colFNac.setCellValueFactory(new PropertyValueFactory("fecha nacimiento"));
       this.colGenero.setCellValueFactory(new PropertyValueFactory("genero"));
       this.colPeso.setCellValueFactory(new PropertyValueFactory("peso"));
       this.colEstatura.setCellValueFactory(new PropertyValueFactory("estatura"));
       this.colTalla.setCellValueFactory(new PropertyValueFactory ("talla"));
       this.colMedico.setCellValueFactory(new PropertyValueFactory ("medico"));
       
       cargaElementosTabla();
    }
    
    private void cargaElementosTabla(){
        gpaciente = FXCollections.observableArrayList();
        //provisional
        GeneralPacientes p1 = new GeneralPacientes(1, "Luis","Garcia Arellano",66,165,47,1);
        GeneralPacientes p2 = new GeneralPacientes(2, "Alejandro", "Pedraza Viveros",66,166,48,2 );
        GeneralPacientes p3 = new GeneralPacientes(3, "Mariana", "Luna González", 55,159,30,3 );
        
        gpaciente.add(p1);
        gpaciente.add(p2);
        gpaciente.add(p3);
        
        tbPacientes.setItems(gpaciente);
    }
    
    @FXML
    private void clicAgregar(ActionEvent e){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPacientes.fxml"));
            Parent root = loader.load();
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
