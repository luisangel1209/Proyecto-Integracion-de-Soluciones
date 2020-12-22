package fitnutrition;

import NotificaCambios.NotificaCambios;
import pojo.Pacientes;
import pojo.Mensaje;
import pojo.RespuestaWS;
import util.Constantes;
import util.ConsumoWS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLPacientesController implements Initializable, NotificaCambios {

    @FXML
    private TableView<Pacientes> tbPacientes;
    @FXML
    private TextField TextBuscar;
    @FXML
    private TableColumn colnombre;
    @FXML
    private TableColumn colapellidos;
    @FXML
    private TableColumn colgenero;
    @FXML
    private TableColumn colpeso;
    @FXML
    private TableColumn colestatura;
    @FXML
    private TableColumn coltalla;
    @FXML
    private TableColumn colemail;
    @FXML
    private TableColumn coltel;
    @FXML
    private TableColumn coldomicilio;
    @FXML
    private TableColumn colusuario;
    @FXML
    private TableColumn colcontraseña;
    @FXML
    private TableColumn colpaciente_foto;
    @FXML
    private TableColumn colestatus;
   
    private ObservableList<Pacientes> paciente;
    @FXML
    private TableColumn colfNac;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.colnombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colapellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
        this.colfNac.setCellValueFactory(new PropertyValueFactory("fNac"));
        this.colgenero.setCellValueFactory(new PropertyValueFactory("genero"));
        this.colpeso.setCellValueFactory(new PropertyValueFactory("peso"));
        this.colestatura.setCellValueFactory(new PropertyValueFactory("estatura"));
        this.coltalla.setCellValueFactory(new PropertyValueFactory("talla"));
        this.colemail.setCellValueFactory(new PropertyValueFactory("email"));
        this.coltel.setCellValueFactory(new PropertyValueFactory("tel"));
        this.coldomicilio.setCellValueFactory(new PropertyValueFactory("domicilio"));
        this.colusuario.setCellValueFactory(new PropertyValueFactory("usuario"));
        this.colcontraseña.setCellValueFactory(new PropertyValueFactory("contraseña"));
        this.colpaciente_foto.setCellValueFactory(new PropertyValueFactory("paciente_foto"));
        this.colestatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        cargaElementosTabla();
        FuncionBuscar();
    }    

    private void cargaElementosTabla(){
        String url = Constantes.URL + "fitNutrition/getAllPacientes";
        RespuestaWS resp = ConsumoWS.consumoWSGET(url);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Type tipolistaaero = new TypeToken<List<Pacientes>>(){}.getType();
            ArrayList<Pacientes> aerolineaBD = gson.fromJson(resp.getMensaje(), tipolistaaero);
            paciente = FXCollections.observableArrayList(aerolineaBD);
            tbPacientes.setItems(paciente);
        }else{
            DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
        }
    }
    
    @FXML
    private void clickAgregar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioAgregaPaciente.fxml"));
            Parent root = loader.load();
            FXMLFormularioAgregaPacienteController controlador = loader.getController();
            controlador.InicializaCampos(this, true, null);
            Scene scForm = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scForm);
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error cargar ventana");
            Logger.getLogger(FXMLPacientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clickEditar(ActionEvent event) {
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
    }

    @Override
    public void RefrescarTlaba(boolean dato) {
        System.out.println("El valor es: "+dato);
        tbPacientes.getItems().clear();
        cargaElementosTabla();
    }

    private void DialogError(String titulo, String mensaje){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(titulo);
        error.setHeaderText(null);
        error.setContentText(mensaje);
        error.showAndWait();
    }
    
     private void FuncionBuscar(){
        if(paciente.size() > 0){
            FilteredList<pojo.Pacientes> filtroAero = new FilteredList<>(paciente, p->true);
            TextBuscar.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroAero.setPredicate(busqueda -> {
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        //CONVERTIR A MINUSCULAS
                        String CaseFilter = newValue.toLowerCase();
                        if(busqueda.getNombre().toLowerCase().contains(CaseFilter)){
                            return true;
                        }
                        return false;
                    });
                }      
            });
            SortedList<pojo.Pacientes> sortedDatos = new SortedList<>(filtroAero);
            sortedDatos.comparatorProperty().bind(tbPacientes.comparatorProperty());
            tbPacientes.setItems(sortedDatos);
        }
    }   
}
