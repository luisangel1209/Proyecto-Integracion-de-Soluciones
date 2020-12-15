package fitnutrition;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author maria
 */
public class FXMLFormularioAgregaPacienteController implements Initializable {

    @FXML
    private TextField lbNombre;
    @FXML
    private TextField lbApellidos;
    @FXML
    private TextField lbGenero;
    @FXML
    private TextField lbPeso;
    @FXML
    private TextField lbEstatura;
    @FXML
    private TextField lbTalla;
    @FXML
    private TextField lbEmail;
    @FXML
    private TextField lbTelefono;
    @FXML
    private TextField lbDomicilio;
    @FXML
    private TextField lbMedico;
    @FXML
    private ImageView ivImgPaciente;
    
    //private ObservableList<Paciente> datosPaciente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    @FXML
    private void clicSeleccionar(ActionEvent e){
        FileChooser seleccionaImg = new FileChooser();
        seleccionaImg.setTitle("Seleccionar logo");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        seleccionaImg.getExtensionFilters().add(extFilterJPG);
        
        Stage stage = (Stage) lbNombre.getScene().getWindow();
        File archivoSel = seleccionaImg.showOpenDialog(stage);
        
        if(archivoSel != null){
            try{
                BufferedImage bufferedImage = ImageIO.read(archivoSel);
                Image logo = SwingFXUtils.toFXImage(bufferedImage, null);
                ivImgPaciente.setImage(logo);
            }catch(IOException ex){
                //Logger.getLogger(FXMLFormularioController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al cargar imagen"+ex.getMessage());
            }    
        }else{
            System.out.println("No se selecciono imagen");
        }
    }
    
     @FXML 
    private void clicEnviar (ActionEvent e){
        
    }
    @FXML
    private void clicModifica (ActionEvent e){
        
    }
    @FXML
    private void clicElimina (ActionEvent e){
        
    }
    
    
}
