/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnutrition;

import NotificaCambios.NotificaCambios;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import pojo.Medico;

/**
 * FXML Controller class
 *
 * @author FAMSA
 */
public class FXMLFormularioAgregarMedicoController implements Initializable {

    @FXML
    private Label labelTitulo;
    @FXML
    private TextField lbNombre;
    @FXML
    private TextField lbApellido;
    @FXML
    private TextField lbDomicilio;
    @FXML
    private ComboBox<String> comboEstatus;
    @FXML
    private ImageView fotoMedico;
    @FXML
    private TextField lbCedula;
    @FXML
    private TextField lbNumeroPersonal;
    @FXML
    private DatePicker Date;
    @FXML
    private ComboBox<String> comboGenero;
    @FXML
    private TextField lbContra;

    private NotificaCambios notificacion;
    private boolean isNuevo;
    private Medico medico;
    
    ObservableList<String> genero = FXCollections.observableArrayList("Masculino", "Femenino");
    ObservableList<String> estatus = FXCollections.observableArrayList("Activo", "No Activo");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboGenero.setItems(genero);
        comboGenero.setPromptText("Elige el genero");
        comboEstatus.setItems(estatus);
        comboEstatus.setPromptText("Elige el estatus");
    }   
    
    public void InicializaCampos(NotificaCambios notificacion, boolean isNuevo, Medico medico){
        this.isNuevo = isNuevo;
        this.medico = medico;
        this.notificacion = notificacion;
        if(!isNuevo){
            labelTitulo.setText("Editar Aerolinea");
            //cargaDatosEdicion();
        }
    }
    
    /*private void cargaDatosEdicion(){
        if(medico != null){
            lbNombre.setText(aerolinea.getCodigo());
            lbApellido.setEditable(false);
            lbDomicilio.setText(aerolinea.getNombre());
            lbCedula.setText(aerolinea.getNacionalidad());
            lbNumeroPersonal.setText(""+aerolinea.getCantidad());
            labelrespon.setText(aerolinea.getResponsable());
            int posCombo = getIndexLista(aerolinea.getTipo());
            combotipo.getSelectionModel().select(posCombo);
            descargaImagen(aerolinea.getIdAerolinea());
        }
    }*/

    @FXML
    private void clickSeleccionar(ActionEvent event) {
    }

    @FXML
    private void Guardar(ActionEvent event) {
        System.out.println(comboGenero.getValue());
        System.out.println(Date.getValue());
    }
    
}
