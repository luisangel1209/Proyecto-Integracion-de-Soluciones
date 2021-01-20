package fitnutrition;

import NotificaCambios.NotificaCambios;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pojo.Citas;
import pojo.Mensaje;
import pojo.RespuestaWS;
import pojo.Pacientes;
import pojo.TipoPaciente;
import util.Constantes;
import util.ConsumoWS;

/**
 * FXML Controller class
 *
 * @author maria
 */
public class FXMLFormularioAgregaCitaController implements Initializable, NotificaCambios {

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
    @FXML
    private Button botonGuardar;
    @FXML
    private ComboBox<TipoPaciente> comboPaciente;
    private ObservableList<TipoPaciente> Pacientes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Pacientes = FXCollections.observableArrayList();
        cargaElementos();
        
    }

    public void InicializaCampos(NotificaCambios notificacion, boolean isNuevo, Citas cita){
        this.isNuevo = isNuevo;
        this.cita = cita;
        this.notificacion = notificacion;
        if(!isNuevo){
            labelTitulo.setText("Editar Cita");
            cargaDatosEdicion();
        }
    }
    
    private void cargaDatosEdicion(){
        if(cita != null){
            int posCombo = getIndexLista(cita.getIdPaciente());
            comboPaciente.getSelectionModel().select(posCombo);
            String fecha = cita.getFecha_Cita();
            LocalDate fecha2 = LocalDate.parse(fecha);
            dpFechaCita.setValue(fecha2);
            tfHoraCita.setText(cita.getHora_Cita());
            tfObservaciones.setText(cita.getObservaciones());
        }
    }
    
    @FXML 
    private void clicEnviar (ActionEvent e){
        int idPacientee = Pacientes.get(comboPaciente.getSelectionModel().getSelectedIndex()).getIdPaciente();
        if(isNuevo){
            String url = Constantes.URL + "fitNuutrition/registraCita";
         
            String date = ""+dpFechaCita.getValue();
            //System.out.println("############");
            //System.out.println(date);
            String parametros = String.format("idPaciente=%d&fecha_cita=%s&hora_cita=%s&observaciones=%s", 
                    idPacientee,
                    date,
                    tfHoraCita.getText(),
                    tfObservaciones.getText()
                    );
            
            RespuestaWS resp = ConsumoWS.consumoWSPOST(url, parametros);
            if(resp.getCodigo() == 200){
                Gson gson = new Gson();
                Mensaje msj = gson.fromJson(resp.getMensaje(), Mensaje.class);
                if(msj.isError()){
                    DialogError("Error al agregar", msj.getMensaje());
                }else{
                    muestraDialogo("Registro Agregado", msj.getMensaje());
                    CerrarScena();
                }
            }else{
                DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
            }
        }else{
            String url = Constantes.URL + "fitNuutrition/editarCita";
            
            String date = dpFechaCita.getValue().toString();
            //System.out.println("############");
            //System.out.println(date);
            String parametros = String.format("idCitas=%d&idPaciente=%d&fecha_cita=%s&hora_cita=%s&observaciones=%s", 
                    cita.getIdCitas(),
                    idPacientee,
                    date,
                    tfHoraCita.getText(),
                    tfObservaciones.getText()
                    );
            RespuestaWS res = ConsumoWS.consumoWSPUT(url, parametros);
            if(res.getCodigo() == 200){
                Gson gson = new Gson();
                Mensaje msj = gson.fromJson(res.getMensaje(), Mensaje.class);
                if(msj.isError()){
                    DialogError("Error al editar", msj.getMensaje());
                }else{
                    muestraDialogo("Registro editado", msj.getMensaje());
                    CerrarScena();
                }
            }else{
                DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
            }
        }
    }
    
    private void CerrarScena(){
        Stage stage = (Stage) this.botonGuardar.getScene().getWindow();
        stage.close();
        notificacion.RefrescarTlaba(true);
    }
    
    private void cargaElementos(){
        String url = Constantes.URL + "fitNuutrition/allPaciente";
        RespuestaWS resp = ConsumoWS.consumoWSGET(url);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Type tipolistaaero = new TypeToken<List<TipoPaciente>>(){}.getType();
            ArrayList<TipoPaciente> tipoaerolineaBD = gson.fromJson(resp.getMensaje(), tipolistaaero);
            Pacientes = FXCollections.observableArrayList(tipoaerolineaBD);
            String tipo = tipoaerolineaBD.get(1).toString();
            System.out.println(tipo);
            comboPaciente.setItems(Pacientes);  
        }else{
            DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
        }
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
    
     private int getIndexLista(int idTipo){
        for(int i=0; i < Pacientes.size(); i++){
            if(Pacientes.get(i).getIdPaciente() == idTipo){
                return i;
            }
        }
        return 0;
    }
    
    @Override
    public void RefrescarTlaba(boolean dato){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
