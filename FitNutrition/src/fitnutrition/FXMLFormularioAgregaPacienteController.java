package fitnutrition;

import NotificaCambios.NotificaCambios;
import pojo.Mensaje;
import pojo.RespuestaWS;
import util.Constantes;
import util.ConsumoWS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import pojo.Pacientes;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;


public class FXMLFormularioAgregaPacienteController implements Initializable {

    @FXML
    private TextField labelidMedico;
    @FXML
    private TextField labelnombre;
    @FXML
    private TextField labelapellidos;
    @FXML
    private TextField labelgenero;
    @FXML
    private TextField labelpeso;
    @FXML
    private TextField labelestatura;
    @FXML
    private TextField labeltalla;
    @FXML
    private TextField labelemail;
    @FXML
    private TextField labeltel;
    @FXML
    private TextField labeldomicilio;
    @FXML
    private TextField labelusuario;
    @FXML
    private TextField labelcontraseña;
    @FXML
    private DatePicker labelfNac;
    @FXML
    private ComboBox<?> comboestatus;
    @FXML
    private TextField labelpaciente_foto;
    @FXML
    private ImageView fotopaciente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void InicializaCampos(FXMLPacientesController aThis, boolean b, Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void Guardar(ActionEvent event) {
    }
    
}
