package fitnutrition;

import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author maria
 */
public class FXMLInicioController implements Initializable {
    
    @FXML
    private Button button;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Label lbErrorContraseña;
   
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void clicLogin (ActionEvent event){
        lbErrorContraseña.setText("");
        lbErrorUsuario.setText("");
        String usuario = tfUsuario.getText();
        String password = tfPassword.getText();
        boolean isValido = true;
        
        if(usuario.isEmpty()){
            lbErrorUsuario.setText("Campo usuario requerido");
            isValido = false;
        }
        
        if(password.isEmpty()){
            lbErrorContraseña.setText("Campo contraseña requerido");
            isValido = false;
        }
        
    }
    
}
