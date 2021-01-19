package fitnutrition;

import pojo.Dieta;
import pojo.Mensaje;
import pojo.RespuestaWS;
import pojo.TipoAlimento;
import util.Constantes;
import util.ConsumoWS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import NotificaCambios.NotificaCambios;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class FXMLAgregarDietaController implements Initializable, NotificaCambios {

    @FXML
    private TextField txtnumero_dieta;
    @FXML
    private TextField txtcantidad;
    @FXML
    private TextField txthora_dia;
    @FXML
    private TextField txtcalorias;
    @FXML
    private TextField txtobservaciones;
    
    private ObservableList<TipoAlimento> tipoalimento;
    private NotificaCambios notificacion;
    private boolean isNuevo;
    private Dieta dieta;
    
    @FXML
    private Label labelTitulo;
    //private ComboBox<TipoAlimento> comboalimento;
    //private TextField txtidalimento;
    @FXML
    private TextField txtidPaciente;
    @FXML
    private ComboBox<TipoAlimento> comboalimento;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tipoalimento = FXCollections.observableArrayList();
        integerTextField(txtcalorias);
        integerTextField(txtnumero_dieta);
        integerTextField(txtidPaciente);
        cargaElementos();
    }    

    void InicializaCampos(NotificaCambios notificacion, boolean isNuevo, Dieta dieta) {
        this.isNuevo = isNuevo;
        this.dieta = dieta;
        this.notificacion = notificacion;
        if(!isNuevo){
            labelTitulo.setText("Editar Dieta");
            cargaDatosEdicion();
        }
    }
    
    private void cargaDatosEdicion(){
        if(dieta != null){
            //txtidalimento.setEditable(false);
            //txtidalimento.setText(dieta.getTipo());
            txtnumero_dieta.setEditable(false);
            txtnumero_dieta.setText(""+dieta.getNumero_dieta());
            txtcantidad.setText(dieta.getCantidad());
            txthora_dia.setText(dieta.getHora_dia());
            txtcalorias.setText(""+dieta.getCalorias_dieta());
            txtobservaciones.setText(dieta.getObservaciones());
            txtidPaciente.setText(""+dieta.getIdPaciente());
            txtidPaciente.setEditable(false);
            int posCombo = getIndexLista(dieta.getIdAlimento());
            comboalimento.getSelectionModel().select(posCombo);
        }
    }

    @Override
    public void RefrescarTlaba(boolean dato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void Guardar(ActionEvent event) {
        int idAlimento = tipoalimento.get(comboalimento.getSelectionModel().getSelectedIndex()).getIdAlimento();
        if(isNuevo){
            String url = Constantes.URL + "fitNutrition/registrarDieta";
            float caloriasDieta = Integer.parseInt(txtcalorias.getText());
            String parametros = String.format("idAlimento=%d&numero_dieta=%s&cantidad=%s&hora_dia=%s&calorias_dieta=%s&observaciones=%s&idPaciente=%s&", 
                    idAlimento,
                    txtnumero_dieta.getText(),
                    txtcantidad.getText(),
                    txthora_dia.getText(),
                    caloriasDieta,
                    txtobservaciones.getText(),
                    txtidPaciente.getText()
                    );
            RespuestaWS resp = ConsumoWS.consumoWSPOST(url, parametros);
            if(resp.getCodigo() == 200){
                Gson gson = new Gson();
                Mensaje msj = gson.fromJson(resp.getMensaje(), Mensaje.class);
                if(msj.isError()){
                    DialogError("Error al agregar", msj.getMensaje());
                }else{
                    DialogError("Registro Agregado", msj.getMensaje());
                    CerrarScena();
                }
            }else{
                DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
            }
        }else{
            String url = Constantes.URL + "fitNutrition/actualizarDieta";
            float caloriasDieta = Integer.parseInt(txtcalorias.getText());
            String parametros = String.format("idDieta=%d&idAlimento=%d&numero_dieta=%s&cantidad=%s&hora_dia=%s&calorias_dieta=%s&observaciones=%s&idPaciente=%s&", 
                    dieta.getIdDieta(),
                    idAlimento,
                    txtnumero_dieta.getText(),
                    txtcantidad.getText(),
                    txthora_dia.getText(),
                    caloriasDieta,
                    txtobservaciones.getText(),
                    txtidPaciente.getText()
                    );
            RespuestaWS res = ConsumoWS.consumoWSPUT(url, parametros);
            if(res.getCodigo() == 200){
                Gson gson = new Gson();
                Mensaje msj = gson.fromJson(res.getMensaje(), Mensaje.class);
                if(msj.isError()){
                    DialogError("Error al editar", msj.getMensaje());
                }else{
                    DialogError("Registro editado", msj.getMensaje());
                    CerrarScena();
                }
            }else{
                DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
            }
        }
    }

    private void DialogError(String titulo, String mensaje){
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.setTitle(titulo);
        error.setHeaderText(null);
        error.setContentText(mensaje);
        error.showAndWait();
    }
    
    public void integerTextField(TextField textField) {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([0-9]*)?")) {
                return change;
            }
            return null;
        };
        textField.setTextFormatter(
                new TextFormatter<Integer>(
                        new IntegerStringConverter(), 0, integerFilter));
    }
    
    private void CerrarScena(){
        Stage stage = (Stage) this.txtcantidad.getScene().getWindow();
        stage.close();
        notificacion.RefrescarTlaba(true);
    }

    private int getIndexLista(int idAlimento){
        for(int i=0; i < tipoalimento.size(); i++){
            if(tipoalimento.get(i).getIdAlimento() == idAlimento){
                return i;
            }
        }
        return 0;
    }
    
    private void cargaElementos(){
        String url = Constantes.URL + "fitNutrition/getTiposAlimentos";
        RespuestaWS resp = ConsumoWS.consumoWSGET(url);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Type tipolistaaero = new TypeToken<List<TipoAlimento>>(){}.getType();
            ArrayList<TipoAlimento> tipoaerolineaBD = gson.fromJson(resp.getMensaje(), tipolistaaero);
            tipoalimento = FXCollections.observableArrayList(tipoaerolineaBD);
            String tipo = tipoaerolineaBD.get(1).toString();
            //System.out.println(tipo);
            comboalimento.setItems(tipoalimento);
        }else{
            DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
        }
    }
}
