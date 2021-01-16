package fitnutrition;

import pojo.Mensaje;
import pojo.Pacientes;
import pojo.RespuestaWS;
import pojo.TipoAlimento;
import pojo.TipoMedico;
import com.google.gson.Gson;
import NotificaCambios.NotificaCambios;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import util.Constantes;
import util.ConsumoWS;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class FXMLAgregarPacienteController implements Initializable, NotificaCambios{

    @FXML
    private Label labelTitulo;
    @FXML
    private TextField labelnombre;
    @FXML
    private TextField labelapellidos;
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
    private ComboBox<String> comboestatus;
    @FXML
    private TextField labelpaciente_foto;
    @FXML
    private ImageView fotopaciente;
    
    ObservableList<String> genero = FXCollections.observableArrayList("Masculino", "Femenino");
    ObservableList<String> estatus = FXCollections.observableArrayList("Activo", "No Activo");
    
    private NotificaCambios notificacion;
    private boolean isNuevo;
    private Pacientes paciente;
    
    @FXML
    private ComboBox<String> comboGenero;
    @FXML
    private Button BotonGuardar;
    //private ComboBox<TipoMedico> combomedico;
    private ObservableList<TipoMedico> tipomedico;
    @FXML
    private TextField labelidmedico;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargaElementos();
        tipomedico = FXCollections.observableArrayList();
        comboGenero.setItems(genero);
        comboGenero.setPromptText("Elige el genero");
        comboestatus.setItems(estatus);
        comboestatus.setPromptText("Elige el estatus");
    }    

    public void InicializaCampos(NotificaCambios notificacion, boolean isNuevo, Pacientes paciente){
        this.isNuevo = isNuevo;
        this.paciente = paciente;
        this.notificacion = notificacion;
        if(!isNuevo){
            labelTitulo.setText("Editar Paciente");
            cargaDatosEdicion();
        }
    }

    private void cargaDatosEdicion(){
        if(paciente != null){
            String fecha = paciente.getfNac();
            LocalDate fecha2 = LocalDate.parse(fecha);
            labelidmedico.setText(paciente.getTipo());
            labelidmedico.setEditable(false);
            labelnombre.setText(paciente.getNombre());
            labelnombre.setEditable(false);
            labelapellidos.setText(paciente.getApellidos());
            labelapellidos.setEditable(false);
            labelfNac.setValue(fecha2);
            labelfNac.setEditable(false);
            comboGenero.setValue(paciente.getGenero());
            labelpeso.setText(""+paciente.getPeso());
            labelestatura.setText(""+paciente.getEstatura());
            labeltalla.setText(""+paciente.getTalla());
            labelemail.setText(paciente.getEmail());
            labeltel.setText(""+paciente.getTel());
            labeldomicilio.setText(paciente.getDomicilio());
            labelusuario.setText(paciente.getUsuario());
            labelcontraseña.setText(paciente.getContraseña());
            labelpaciente_foto.setText(paciente.getPaciente_foto());
            comboestatus.setValue(paciente.getEstatus());
        }
    }
    
    @Override
    public void RefrescarTlaba(boolean dato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void DialogError(String titulo, String mensaje){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(titulo);
        error.setHeaderText(null);
        error.setContentText(mensaje);
        error.showAndWait();
    }
    
    private void CerrarScena(){
        Stage stage = (Stage) this.BotonGuardar.getScene().getWindow();
        stage.close();
        notificacion.RefrescarTlaba(true);
    }
    
    private int getIndexLista(int idTipo){
        for(int i=0; i < tipomedico.size(); i++){
            if(tipomedico.get(i).getIdMedico() == idTipo){
                return i;
            }
        }
        return 0;
    }

    @FXML
    private void Guardar(ActionEvent event) {
        //int idMedico = tipomedico.get(combomedico.getSelectionModel().getSelectedIndex()).getIdMedico();
        if(isNuevo){
            String url = Constantes.URL + "fitNutrition/registrarPaciente";
            float peso = Integer.parseInt(labelpeso.getText());
            float estatura = Integer.parseInt(labelestatura.getText());
            int talla = Integer.parseInt(labeltalla.getText());
            int tel = Integer.parseInt(labeltel.getText());
            //String foto = "Prueba";
            String date = ""+labelfNac.getValue();
            String genero = comboGenero.getValue();
            String estatus = comboestatus.getValue();
            String parametros = String.format("idMedico=%s&nombre=%s&apellidos=%s&fNac=%s&genero=%s&peso=%s&estatura=%s&talla=%s&email=%s&tel=%s&domicilio=%s&usuario=%s&contraseña=%s&paciente_foto=%s&estatus=%s", 
                    labelidmedico.getText(),
                    labelnombre.getText(),
                    labelapellidos.getText(),
                    date,
                    genero,
                    peso,
                    estatura,
                    talla,
                    labelemail.getText(),
                    tel,
                    labeldomicilio.getText(),
                    labelusuario.getText(),
                    labelcontraseña.getText(),
                    labelpaciente_foto.getText(),
                    estatus
                    );
            RespuestaWS resp = ConsumoWS.consumoWSPOST(url, parametros);
            if(resp.getCodigo() == 200){
                Gson gson = new Gson();
                Mensaje msj = gson.fromJson(resp.getMensaje(), Mensaje.class);
                if(msj.isError()){
                    DialogError("Error al agregar", msj.getMensaje());
                }else{
                    DialogError("Paciente Agregado", msj.getMensaje());
                    CerrarScena();
                }
            }else{
                DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
            }
        }else{
            String url = Constantes.URL + "fitNutrition/actualizarPaciente";
            float peso = Integer.parseInt(labelpeso.getText());
            float estatura = Integer.parseInt(labelestatura.getText());
            int talla = Integer.parseInt(labeltalla.getText());
            int tel = Integer.parseInt(labeltel.getText());
            //String foto = "Prueba";
            String date = ""+labelfNac.getValue();
            String genero = comboGenero.getValue();
            String estatus = comboestatus.getValue();
            String parametros = String.format("idPaciente=%d&idMedico=%s&nombre=%s&apellidos=%s&fNac=%s&genero=%s&peso=%s&estatura=%s&talla=%s&email=%s&tel=%s&domicilio=%s&usuario=%s&contraseña=%s&paciente_foto=%s&estatus=%s", 
                    paciente.getIdPaciente(),
                    labelidmedico.getText(),
                    labelnombre.getText(),
                    labelapellidos.getText(),
                    date,
                    genero,
                    peso,
                    estatura,
                    talla,
                    labelemail.getText(),
                    tel,
                    labeldomicilio.getText(),
                    labelusuario.getText(),
                    labelcontraseña.getText(),
                    labelpaciente_foto.getText(),
                    //foto,
                    estatus
                    );
            RespuestaWS res = ConsumoWS.consumoWSPUT(url, parametros);
            if(res.getCodigo() == 200){
                Gson gson = new Gson();
                Mensaje msj = gson.fromJson(res.getMensaje(), Mensaje.class);
                if(msj.isError()){
                    DialogError("Error al editar", msj.getMensaje());
                }else{
                    DialogError("Paciente editado", msj.getMensaje());
                    CerrarScena();
                }
            }else{
                DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
            }
        }
    }
    
    private void cargaElementos(){
        String url = Constantes.URL + "fitNutrition/getTiposMedico";
        RespuestaWS resp = ConsumoWS.consumoWSGET(url);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Type tipolistaaero = new TypeToken<List<TipoMedico>>(){}.getType();
            ArrayList<TipoMedico> tipoaerolineaBD = gson.fromJson(resp.getMensaje(), tipolistaaero);
            tipomedico = FXCollections.observableArrayList(tipoaerolineaBD);
            //String tipo = tipoaerolineaBD.get(1).toString();
            //combomedico.setItems(tipomedico);
        }else{
            DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
        }
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
}
