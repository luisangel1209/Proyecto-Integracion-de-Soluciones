package fitnutrition;

import pojo.Mensaje;
import pojo.RespuestaWS;
import util.Constantes;
import util.ConsumoWS;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button button;
    @FXML
    private TextField textusuario;
    @FXML
    private PasswordField textcontra;
    @FXML
    private Label labelerrorusuario;
    @FXML
    private Label labelerrorcontra;
    
    @FXML
    private void ClicLogin(ActionEvent event) {
        labelerrorusuario.setText("");
        labelerrorcontra.setText("");
        String username = textusuario.getText();
        String password = textcontra.getText();
        boolean isValido = true;
        
        if(username.isEmpty()){
            labelerrorusuario.setText("Campo Usuario Requerido");
            isValido = false;
        } 
        if(password.isEmpty()){
            labelerrorcontra.setText("Campo Contraseña Requerido");
            isValido = false;
        }
        if(isValido){
            String parametros = String.format("cedulaProfesional=%s&contraseña=%s", username, password);
            String url = Constantes.URL + "fitNutrition/login";
            RespuestaWS resp = ConsumoWS.consumoWSPOST(url, parametros);
            if(resp.getCodigo() == 200){
                Gson gson = new Gson();
                Mensaje msj = gson.fromJson(resp.getMensaje(), Mensaje.class);
                if(msj.isError()){
                    DialogError("Usuario no encontrado", msj.getMensaje());
                }else{
                    irPrincipal();
                }
            }else{
                DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
            }
        }
    }
    
    private void irPrincipal(){
        try {
            Stage stage = (Stage) textusuario.getScene().getWindow();
            Scene sceneprincipal = new Scene(FXMLLoader.load(getClass().getResource("FXMLPacientes.fxml")));
            stage.setScene(sceneprincipal);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void DialogError(String titulo, String mensaje){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(titulo);
        error.setHeaderText(null);
        error.setContentText(mensaje);
        error.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}