/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnutrition;

import NotificaCambios.NotificaCambios;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.IntegerStringConverter;
import pojo.Alimentos;
import pojo.Mensaje;
import pojo.RespuestaWS;
import pojo.TipoPorcionAlimentos;
import util.Constantes;
import util.ConsumoWS;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXMLFormularioAgregarAlimentoController implements Initializable, NotificaCambios {

    @FXML
    private Label labelTitulo;
    @FXML
    private TextField lbNombre;
    @FXML
    private TextField lbCalorias;
    @FXML
    private ComboBox<TipoPorcionAlimentos> comboTipoPorcion;
    
    private ObservableList<TipoPorcionAlimentos> tipoporcion;
    private NotificaCambios notificacion;
    private boolean isNuevo;
    private Alimentos alimentos;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tipoporcion = FXCollections.observableArrayList();
        cargaElementos();
        integerTextField(lbCalorias);
    }    
    
    public void InicializaCampos(NotificaCambios notificacion, boolean isNuevo, Alimentos alimentos){
        this.isNuevo = isNuevo;
        this.alimentos = alimentos;
        this.notificacion = notificacion;
        if(!isNuevo){
            labelTitulo.setText("Editar Alimento");
            cargaDatosEdicion();
        }
    }
    
    private void cargaDatosEdicion(){
        if(alimentos != null){
            lbNombre.setText(alimentos.getNombre_alimento());
            lbCalorias.setText(""+alimentos.getCalorias_porcion());
            int posCombo = getIndexLista(alimentos.getIdTipoPorcion());
            comboTipoPorcion.getSelectionModel().select(posCombo);
        }
    }
    
    @FXML
    private void Guardar(ActionEvent event) {
        String nombre = lbNombre.getText();
        String calo = lbCalorias.getText();
        if(!nombre.isEmpty() || !calo.isEmpty()){
            int tipo = tipoporcion.get(comboTipoPorcion.getSelectionModel().getSelectedIndex()).getIdTipoPorcion();
            if(isNuevo){
                int calorias = Integer.parseInt(lbCalorias.getText());
                //String urll = Constantes.URL + "aerolineas/SubirImagen/"+aerolinea.getIdAerolinea();
                String url = Constantes.URL + "AlimentosyMedicos/registroAlimentos";
                String parametros = String.format("nombre_alimento=%s&calorias_porcion=%d&idTipoPorcion=%d", 
                        lbNombre.getText(),
                        calorias,
                        tipo);
                RespuestaWS resp = ConsumoWS.consumoWSPOST(url, parametros);
                //RespuestaWS respp = ConsumoWS.ConsumoWSPOST(urll, imagen);&& respp.getCodigo() == 200
                if(resp.getCodigo() == 200){
                    Gson gson = new Gson();
                    Mensaje msj = gson.fromJson(resp.getMensaje(), Mensaje.class);
                    if(msj.isError()){
                        DialogError("Error al agregar", msj.getMensaje());
                    }else{
                        muestraDialogo("Alimento agregado correctamente...", msj.getMensaje());
                        CerrarScena();
                    }
                }else{
                    DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
                }
            }else{
                int calorias = Integer.parseInt(lbCalorias.getText());
                String url = Constantes.URL + "AlimentosyMedicos/editarAlimento";
                String parametros = String.format("idAlimento=%d&nombre_alimento=%s&calorias_porcion=%d&idTipoPorcion=%d", 
                        alimentos.getIdAlimento(),
                        lbNombre.getText(),
                        calorias,
                        tipo);
                RespuestaWS res = ConsumoWS.consumoWSPUT(url, parametros);
                if(res.getCodigo() == 200){
                    Gson gson = new Gson();
                    Mensaje msj = gson.fromJson(res.getMensaje(), Mensaje.class);
                    if(msj.isError()){
                        DialogError("Error al editar", msj.getMensaje());
                    }else{
                        muestraDialogo("Alimento editado correctamente...", msj.getMensaje());
                        CerrarScena();
                    }
                }else{
                    DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");

                }
            }
        }else{
            DialogError("Error", "Por favor rellena los campos requeridos");
        }
    }
    
    private void muestraDialogo(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
        
    private void CerrarScena(){
        Stage stage = (Stage) this.lbNombre.getScene().getWindow();
        stage.close();
        notificacion.RefrescarTlaba(true);
    }
    
    private void cargaElementos(){
        String url = Constantes.URL + "AlimentosyMedicos/allTipoPorcion";
        RespuestaWS resp = ConsumoWS.consumoWSGET(url);
        if(resp.getCodigo() == 200){
            Gson gson = new Gson();
            Type tipolistaaero = new TypeToken<List<TipoPorcionAlimentos>>(){}.getType();
            ArrayList<TipoPorcionAlimentos> tipoaerolineaBD = gson.fromJson(resp.getMensaje(), tipolistaaero);
            tipoporcion = FXCollections.observableArrayList(tipoaerolineaBD);
            String tipo = tipoaerolineaBD.get(1).toString();
            System.out.println(tipo);
            comboTipoPorcion.setItems(tipoporcion);
        }else{
            DialogError("Error de conexión", "Lo sentimos, tenemos problemas para conectar con el servidor");
        }
    }
    
    private void DialogError(String titulo, String mensaje){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(titulo);
        error.setHeaderText(null);
        error.setContentText(mensaje);
        error.showAndWait();
    }
    
    //Metodo Auxiliar
    private int getIndexLista(int idTipo){
        for(int i=0; i < tipoporcion.size(); i++){
            if(tipoporcion.get(i).getIdTipoPorcion() == idTipo){
                return i;
            }
        }
        return 0;
    }
    
    public void integerTextField(TextField textField) {
        UnaryOperator<Change> integerFilter = change -> {
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
    
    @Override
    public void RefrescarTlaba(boolean dato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
