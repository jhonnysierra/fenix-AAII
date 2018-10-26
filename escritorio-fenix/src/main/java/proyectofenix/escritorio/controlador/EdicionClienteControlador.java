package proyectofenix.escritorio.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
	 * Campo para la cuenta
	 */
	@FXML
	private TextField cmpNoCuenta;

	/**
	 * Campo para la cuenta de telefono
	 */
	@FXML
	private Label cmpInfoCrearCliente;

	/**
	 * Boton Aceptar de la ventana edicion cliente y crear cliente
	 */
	@FXML
	private Button btnAceptar;

	/**
	 * Boton Aceptar de la ventana edicion cliente
	 */
	@FXML
	private Button btnEditar;

	/**
	 * Genero de tipo enumeracion
	 */
	// private Genero genero;

	/**
	 * representa el escenario en donde se agrega la vista
	 */
	private Stage escenarioEditar;

	/**
	 * instancia del manejador de los escenario
	 */
	private ManejadorEscenarios manejador;

	/**
	 * Lista observable con las opciones del genero
	 */
	private ObservableList<String> itemsGenero;

	/**
	 * para almacenar clientes observables que se recibe desde detalle cliente
	 */
	private ObservableList<ClienteObservable> clientesObservablesDetalleCliente;

	/**
	 * Cliente que se envia como parametro a esta instancia
	 */
	// private ClienteObservable clienteEditado;

	/**
	 * Indice de la posicion en la lsita de clientes observables del cliente a
	 * editar
	 */
	private int indiceListaClientesObservables;

	/**
	 * Caracter ingresado
	 */
	private char caracter;

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
		cmpCedula.requestFocus();
		cmpGenero.getItems().addAll(itemsGenero);
		btnEditar.setVisible(false);
		/*
		 * cmpEmail.focusedProperty().addListener((obs, oldVal, newVal) ->
		 * System.out.println(newVal ? "Focused" : "Unfocused"));
		 */

	}

	/**
	 * permite cargar la informacion de un persona para realizar una edicion
	 * 
	 * @param cliente cliente a editar
	 */
	public void cargarPersona(ClienteObservable cliente) {

		btnAceptar.setVisible(false);
		btnEditar.setVisible(true);
		cmpInfoCrearCliente.setText("Por favor edite la información del cliente");
		cmpCedula.setDisable(true);
		cmpEmail.setEditable(false);
		cmpNombre.requestFocus();

		cmpCedula.setText(cliente.getCedula().getValue());
		cmpNombre.setText(cliente.getNombre().getValue());
		cmpApellido.setText(cliente.getApellido().getValue());
		cmpEmail.setText(cliente.getEmail().getValue());
		cmpClave.setText(cliente.getClave().getValue());
		cmpFechaNacimiento.setValue(Utilidades.pasarALocalDate(cliente.getFechaNacimiento().getValue()));
		// cmpGenero.getSelectionModel().select(cliente.getGenero().getValue());
		if (cliente.getGenero().getValue() == "masculino") {
			cmpGenero.getSelectionModel().select(0);
		} else {
			cmpGenero.getSelectionModel().select(1);
		}
		cmpTelefono.setText(cliente.getTelefono().getValue().get(0));
		cmpDireccion.setText(cliente.getDireccion().getValue());
		cmpNoCuenta.setText(cliente.getNumeroCuenta().getValue());

		clientesObservablesDetalleCliente = manejador.getClientesObservables();

		for (ClienteObservable c : manejador.getClientesObservables()) {
			if (c.getCedula().getValue() == cliente.getCedula().getValue()) {
				indiceListaClientesObservables = clientesObservablesDetalleCliente.indexOf(c);
			}
		}

		// System.out.println("Indice: " + indiceListaClientesObservables);

	}

	/**
	 * permite registrar una persona en la base de datos
	 */
	@FXML
	public void registrarCliente() {
		int seleccionGenero;

		if (validarFormulario()) {
			if (validarEmail()) {
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
				seleccionGenero = cmpGenero.getSelectionModel().getSelectedIndex();
				if (seleccionGenero == 0) {
					cliente.setGenero(Genero.masculino);
				} else {
					cliente.setGenero(Genero.femenino);
				}

				if (manejador.registrarCliente(cliente)) {
					manejador.agregarALista(cliente);
					Utilidades.mostrarMensaje("Registro", "Registro exitoso!!");
					escenarioEditar.close();
				} else {
					Utilidades.mostrarMensajeError("Registro", "Error en registro!!");
				}
			} else {
				Utilidades.mostrarMensajeError("Email invalido", "El email no es valido");
			}
		} else {
			Utilidades.mostrarMensajeError("Datos incompletos",
					"Debes ingresar todos los datos. Algunos estan vacíos!");
		}

	}

	/**
	 * permite editar la informacion de una persona
	 */
	@FXML
	private void editarCliente() {
		int seleccionGenero;

		if (validarFormulario()) {
			if (validarEmail()) {

			} else {
				Utilidades.mostrarMensajeError("Email invalido", "El email no es valido");
			}
		} else {
			Utilidades.mostrarMensajeError("Datos incompletos",
					"Debes ingresar todos los datos. Algunos estan vacíos!");
		}

		Cliente cliente = new Cliente();
		cliente.setCedula(cmpCedula.getText());
		cliente.setNombres(cmpNombre.getText());
		cliente.setApellidos(cmpApellido.getText());
		cliente.setContrasenia(cmpClave.getText());
		cliente.setCorreo(cmpEmail.getText());
		cliente.setFecha_nacimiento(Utilidades.pasarADate(cmpFechaNacimiento.getValue()));
		cliente.setCiudad(null);
		cliente.setNoCuenta(cmpNoCuenta.getText());
		telefonos.add(0, cmpTelefono.getText());
		cliente.setTelefonos(telefonos);
		cliente.setDireccion(cmpDireccion.getText());
		cliente.setEstado("1");
		seleccionGenero = cmpGenero.getSelectionModel().getSelectedIndex();
		if (seleccionGenero == 0) {
			cliente.setGenero(Genero.masculino);
		} else {
			cliente.setGenero(Genero.femenino);
		}

		if (manejador.editarCliente(cliente)) {
			Utilidades.mostrarMensaje("Edición", "Se editó el cliente con éxito!");
			clientesObservablesDetalleCliente.set(indiceListaClientesObservables, new ClienteObservable(cliente));

			escenarioEditar.close();
		} else {
			Utilidades.mostrarMensajeError("Edición", "Error en edición de cliente!");
		}
	}

	/**
	 * permite cerrar la ventana de editar y crear
	 */
	@FXML
	private void cancelar() {
		escenarioEditar.close();
	}

	/**
	 * Permite validar que el texto ingresado solo sean numeros
	 */
	@FXML
	public void validarSoloNumeros(KeyEvent ke) {
		caracter = ke.getCharacter().charAt(0);
		if (!Character.isDigit(caracter)) {
			ke.consume();
		}
		// ke.consume();
	}

	/**
	 * Permite validar que el texto ingresado solo sean letras
	 */
	@FXML
	public void validarSoloLetrasConEspacio(KeyEvent ke) {
		caracter = ke.getCharacter().charAt(0);
		if (!Character.isAlphabetic(caracter) && !Character.isWhitespace(caracter)) {
			ke.consume();
		}
	}

	/**
	 * Permite validar el email
	 * 
	 * @return true si el email es valido false si no
	 */
	@FXML
	public boolean validarEmail() {
		Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
		Matcher m = p.matcher(cmpEmail.getText());

		if (m.find() && m.group().equals(cmpEmail.getText())) {
			return true;
		} else {
			return false;
		}

	}

	public boolean validarFormulario() {
		if (!cmpCedula.getText().equals("") && (!cmpNombre.getText().isEmpty() || cmpNombre.getText().startsWith(" "))
				&& (!cmpApellido.getText().isEmpty() || cmpApellido.getText().startsWith(" "))
				&& !cmpEmail.getText().isEmpty() && !cmpClave.getText().isEmpty()
				&& !(cmpFechaNacimiento.getValue() == null) && !cmpGenero.getSelectionModel().isEmpty()
				&& !cmpTelefono.getText().isEmpty() && !cmpDireccion.getText().isEmpty()
				&& !cmpNoCuenta.getText().isEmpty()) {

			return true;

		} else {
			return false;
		}
	}

	/**
	 * permite cargar el manejador de escenarios
	 * 
	 * @param manejador
	 */
	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
		this.cmpCedula.requestFocus();
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
