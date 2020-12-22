package fitnutrition;

import NotificaCambios.NotificaCambios;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pojo.Citas;
import pojo.Mensaje;
import pojo.RespuestaWS;
import util.Constantes;
import util.ConsumoWS;

/**
 * FXML Controller class
 *
 * @author maria
 */
public class FXMLFormularioAgregaCitaController implements Initializable {

    @FXML
    private TextField idPaciente;
    @FXML
    private DatePicker dpFechaCita;
    @FXML
    private TextField tfHoraCita;
    @FXML
    private TextField tfObservaciones;
    @FXML
    private Label labelTitulo;
    
    private NotificaCambios notificacion;
    private boolean isNuevo;
    private Citas cita;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void InicializaCampos(NotificaCambios notificacion, boolean isNuevo, Citas cita){
        this.isNuevo = isNuevo;
        this.cita = cita;
        this.notificacion = notificacion;
        if(!isNuevo){
            labelTitulo.setText("Editar Aerolinea");
            cargaDatosEdicion();
        }
    }
    
    private void cargaDatosEdicion(){
        if(cita != null){
            //idPaciente.setText(cita.getIdPaciente());
            //dpFechaCita.(cita.getFechaCita());
            tfHoraCita.setText(cita.getHoraCita());
            tfObservaciones.setText(cita.getObservaciones());
        }
    }
    
    @FXML 
    private void clicEnviar (ActionEvent e){
        
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
    
    //error en override
    public void RefrescarTlaba(boolean dato){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
