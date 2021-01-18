package fitnutrition;

import NotificaCambios.NotificaCambios;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pojo.Dieta;
import pojo.Mensaje;
import pojo.RespuestaWS;
import util.Constantes;
import util.ConsumoWS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLDietasController implements Initializable, NotificaCambios {

    @FXML
    private TableView<Dieta> tbDieta;
    @FXML
    private TableColumn colidAlimento;
    @FXML
    private TableColumn colnumero_dieta;
    @FXML
    private TableColumn colcantidad;
    @FXML
    private TableColumn colhora_dia;
    @FXML
    private TableColumn colcalorias;
    @FXML
    private TableColumn colobservasiones;
    
    private ObservableList<Dieta> dieta;
    @FXML
    private TableColumn colPaciente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.colidAlimento.setCellValueFactory(new PropertyValueFactory("tipo"));
        this.colnumero_dieta.setCellValueFactory(new PropertyValueFactory("numero_dieta"));
        this.colcantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        this.colhora_dia.setCellValueFactory(new PropertyValueFactory("hora_dia"));
        this.colcalorias.setCellValueFactory(new PropertyValueFactory("calorias_dieta"));
        this.colobservasiones.setCellValueFactory(new PropertyValueFactory("observaciones"));
        this.colPaciente.setCellValueFactory(new PropertyValueFactory("idPaciente"));
        cargaElementosTabla();
    }    

    private void cargaElementosTabla(){
        String url = Constantes.URL + "fitNutrition/getAllDietas";
        RespuestaWS resp = ConsumoWS.consumoWSGET(url);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Type tipolistaaero = new TypeToken<List<Dieta>>(){}.getType();
            ArrayList<Dieta> aerolineaBD = gson.fromJson(resp.getMensaje(), tipolistaaero);
            dieta = FXCollections.observableArrayList(aerolineaBD);
            tbDieta.setItems(dieta);
        }else{
            DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
        }
    }
    
    @FXML
    private void clickAgregar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAgregarDieta.fxml"));
            Parent root = loader.load();
            FXMLAgregarDietaController controlador = loader.getController();
            controlador.InicializaCampos(this, true, null);
            Scene scForm = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scForm);
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error cargar ventana");
            Logger.getLogger(FXMLDietasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clickEditar(ActionEvent event) {
        int celda = tbDieta.getSelectionModel().getSelectedIndex();
        if(celda >= 0){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAgregarDieta.fxml"));
                Parent root = loader.load();
                FXMLAgregarDietaController controlador = loader.getController();
                controlador.InicializaCampos(this, false, dieta.get(celda));
                Scene scForm = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scForm);
                stage.showAndWait();
            } catch (IOException ex) {
                System.out.println("Error al cargar ventana");
                Logger.getLogger(FXMLDietasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            DialogError("Selecciona registro", "Para editar un registro debes seleccionalo de la tabla");
        }
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
       int celda = tbDieta.getSelectionModel().getSelectedIndex();
       if(celda >= 0){
           Dieta aero = dieta.get(celda);
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setTitle("Eliminar Registro");
           alert.setHeaderText(null);
           alert.setContentText("¿Seguro de eliminar el resgitro de la dieta: " + aero.getNumero_dieta()+" ?");
           Optional<ButtonType> respboton = alert.showAndWait();
           if(respboton.get() == ButtonType.OK){
               EliminaWSRegistro(aero.getIdDieta());
           }
       }else{
           DialogError("Selecciona un registro", "Para eliminar un registro debes seleccionarlo de la tabla");
       }
    }
    
    private void EliminaWSRegistro(int idDieta){
        String parametro = "idDieta="+idDieta;
        String url = Constantes.URL + "fitNutrition/eliminarDieta";
        RespuestaWS resp = ConsumoWS.consumoWSDELETE(url, parametro);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Mensaje msj = gson.fromJson(resp.getMensaje(), Mensaje.class);
            if(msj.isError()){
                DialogError("Error al eliminar", msj.getMensaje());
            }else{
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Registro eliminado");
                error.setHeaderText(null);
                error.setContentText(msj.getMensaje());
                error.showAndWait();
                tbDieta.getItems().clear();
                cargaElementosTabla();
            }
        }else{
            DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
        }
    }

    @Override
    public void RefrescarTlaba(boolean dato) {
        System.out.println("El valor es: "+dato);
        tbDieta.getItems().clear();
        cargaElementosTabla();
    }
    
    private void DialogError(String titulo, String mensaje){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(titulo);
        error.setHeaderText(null);
        error.setContentText(mensaje);
        error.showAndWait();
    }
}
