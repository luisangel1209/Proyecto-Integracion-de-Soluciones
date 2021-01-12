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
public class FXMLPrincipalController implements Initializable {

    @FXML
    private Label lbBienvenido;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
   
    @FXML
    private void clicPacientes(ActionEvent event) {
        try {
            Stage stage = (Stage) lbBienvenido.getScene().getWindow();
            Scene sceneprincipal = new Scene(FXMLLoader.load(getClass().getResource("FXMLPacientes.fxml")));
            stage.setScene(sceneprincipal);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clicDietas(ActionEvent event) {
        try {
            Stage stage = (Stage) lbBienvenido.getScene().getWindow();
            Scene sceneprincipal = new Scene(FXMLLoader.load(getClass().getResource("FXMLPacientes.fxml")));
            stage.setScene(sceneprincipal);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clicAlimentos(ActionEvent event) {
        try {
            Stage stage = (Stage) lbBienvenido.getScene().getWindow();
            Scene sceneprincipal = new Scene(FXMLLoader.load(getClass().getResource("FXMLAlimentos.fxml")));
            stage.setScene(sceneprincipal);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clicCitas(ActionEvent event) {
        try {
            Stage stage = (Stage) lbBienvenido.getScene().getWindow();
            Scene sceneprincipal = new Scene(FXMLLoader.load(getClass().getResource("FXMLCitas.fxml")));
            stage.setScene(sceneprincipal);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clicConsultas(ActionEvent event) {
        try {
            Stage stage = (Stage) lbBienvenido.getScene().getWindow();
            Scene sceneprincipal = new Scene(FXMLLoader.load(getClass().getResource("FXMLPacientes.fxml")));
            stage.setScene(sceneprincipal);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void CerrarSesion(ActionEvent event) {
        try {
            Stage stage = (Stage) lbBienvenido.getScene().getWindow();
            Scene sceneprincipal = new Scene(FXMLLoader.load(getClass().getResource("FXMLDocument.fxml")));
            stage.setScene(sceneprincipal);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
