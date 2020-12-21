package fitnutrition;

import java.awt.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        if(isValido){
            irPrincipal();
        }
    }
    
    private void irPrincipal(){
        try {
            Stage stage = (Stage) tfUsuario.getScene().getWindow();
            Scene sceneprincipal = new Scene(FXMLLoader.load(getClass().getResource("FXMLAdministrador.fxml")));
            stage.setScene(sceneprincipal);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
