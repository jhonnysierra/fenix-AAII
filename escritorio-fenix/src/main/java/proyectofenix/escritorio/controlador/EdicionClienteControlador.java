package proyectofenix.escritorio.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Persona.Genero;
import proyectofenix.escritorio.modelo.ClienteObservable;
import proyectofenix.escritorio.utilidades.Utilidades;

/**
 * Permite controlar la vista editar_cliente
 * 
 * @author EinerZG version 1.0
 */
public class EdicionClienteControlador {

	/**
	 * campo para la cedula
	 */
	@FXML
	private TextField cmpCedula;
	/**
	 * campo para el nombre
	 */
	@FXML
	private TextField cmpNombre;
	/**
	 * campo para el apellido
	 */
	@FXML
	private TextField cmpApellido;
	/**
	 * campo para el email
	 */
	@FXML
	private TextField cmpEmail;
	/**
	 * campo para la calve
	 */
	@FXML
	private TextField cmpClave;

	/**
	 * campo para la fecha de nacimiento
	 */
	@FXML
	private DatePicker cmpFechaNacimiento;

	/**
	 * Combobox para el genero
	 */
	@FXML
	private ComboBox<String> cmpGenero;

	/**
	 * Campo para el telefono
	 */
	@FXML
	private TextField cmpTelefono;

	/**
	 * Lista para los telefonos
	 */
	List<String> telefonos;

	/**
	 * Campo para la direccion
	 */
	@FXML
	private TextField cmpDireccion;

	/**
	 * Campo para la cuenta de telefono
	 */
	@FXML
	private TextField cmpNoCuenta;

	private Genero genero;

	/**
	 * representa el escenario en donde se agrega la vista
	 */
	private Stage escenarioEditar;

	/**
	 * instancia del manejador de los escenario
	 */
	private ManejadorEscenarios manejador;

	private ObservableList<String> itemsGenero;

	/**
	 * Metodo constructor
	 */
	public EdicionClienteControlador() {
		itemsGenero = FXCollections.observableArrayList();
		itemsGenero.addAll("Masculino", "Femenino");
		telefonos = new ArrayList<>();
	}

	/**
	 * 
	 */
	@FXML
	private void initialize() {
		cmpGenero.getItems().addAll(itemsGenero);
	}

	/**
	 * permite cargar la informacion de un persona para realizar una edicion
	 * 
	 * @param cliente cliente a editar
	 */
	public void cargarPersona(ClienteObservable cliente) {

		cmpCedula.setText(cliente.getCedula().getValue());
		cmpNombre.setText(cliente.getNombre().getValue());
		cmpApellido.setText(cliente.getApellido().getValue());
		cmpEmail.setText(cliente.getEmail().getValue());
		cmpClave.setText(cliente.getClave().getValue());
		cmpFechaNacimiento.setValue(Utilidades.pasarALocalDate(cliente.getFechaNacimiento().getValue()));

	}

	/**
	 * permite registrar una persona en la base de datos
	 */
	@FXML
	public void registrarCliente() {
		int seleccionGenero;
		seleccionGenero = cmpGenero.getSelectionModel().getSelectedIndex();

		Cliente cliente = new Cliente();
		cliente.setCedula(cmpCedula.getText());
		cliente.setNombres(cmpNombre.getText());
		cliente.setApellidos(cmpApellido.getText());
		cliente.setContrasenia(cmpClave.getText());
		cliente.setCorreo(cmpEmail.getText());
		cliente.setFecha_nacimiento(Utilidades.pasarADate(cmpFechaNacimiento.getValue()));
		cliente.setCiudad(null);
		cliente.setNoCuenta(cmpNoCuenta.getText());
		telefonos.add(cmpTelefono.getText());
		cliente.setTelefonos(telefonos);
		cliente.setDireccion(cmpDireccion.getText());
		cliente.setEstado("1");
		if (seleccionGenero == 0) {
			cliente.setGenero(genero.masculino);
		} else {
			cliente.setGenero(genero.femenino);
		}

		if (manejador.registrarCliente(cliente)) {
			manejador.agregarALista(cliente);
			Utilidades.mostrarMensaje("Registro", "Registro exitoso!!");
			escenarioEditar.close();
		} else {
			Utilidades.mostrarMensaje("Registro", "Error en registro!!");
		}
	}

	/**
	 * permite editar la informacion de una persona
	 */
	@FXML
	private void modificar() {
		// TODO terminar modificar cliente
		escenarioEditar.close();
	}

	/**
	 * permite cerrar la ventana de editar y crear
	 */
	@FXML
	private void cancelar() {
		escenarioEditar.close();
	}

	/**
	 * permite cargar el manejador de escenarios
	 * 
	 * @param manejador
	 */
	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
	}

	/**
	 * permite modificar el escenario
	 * 
	 * @param escenarioEditar
	 */
	public void setEscenarioEditar(Stage escenarioEditar) {
		this.escenarioEditar = escenarioEditar;
	}

}
