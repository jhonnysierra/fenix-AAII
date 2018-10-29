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

import proyectofenix.entidades.Empleado;
import proyectofenix.entidades.Persona.Genero;

import proyectofenix.escritorio.modelo.EmpleadoObservable;
import proyectofenix.escritorio.utilidades.Utilidades;

/**
 * Permite controlar la vista crear_editar empleado
 * 
 * @author JJ
 * @version 1.0
 */
public class CrearEditarEmpleadoControlador {

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
	 * Campo para la fecha de inicio de contrato
	 */
	@FXML
	private DatePicker cmpFechaInicio;

	/**
	 * Campo para la fecha de fin de contrato
	 */
	@FXML
	private DatePicker cmpFechaFin;

	/**
	 * Campo para el salario
	 */
	@FXML
	private TextField cmpSalario;

	/**
	 * Campo para informacion
	 */
	@FXML
	private Label cmpInfoEncabezado;

	/**
	 * Boton Aceptar de la ventana edicion empleado y crear empleado
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
	private ObservableList<EmpleadoObservable> empleadosObservablesDetalleCliente;

	/**
	 * Empleado que se envia como parametro a esta instancia
	 */
	// private EmpleadoObservable empleadoEditado;

	/**
	 * Indice de la posicion en la lsita de clientes observables del cliente a
	 * editar
	 */
	private int indiceListaEmpleadosObservables;

	/**
	 * Caractere para la validacion
	 */
	private static char caracter;

	/**
	 * Metodo constructor
	 */
	public CrearEditarEmpleadoControlador() {
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
	}

	/**
	 * permite cargar la informacion de un persona para realizar una edicion
	 * 
	 * @param cliente cliente a editar
	 */
	public void cargarPersona(EmpleadoObservable empleado) {

		btnAceptar.setVisible(false);
		btnEditar.setVisible(true);
		cmpInfoEncabezado.setText("Por favor edite la información del empleado");
		cmpCedula.setDisable(true);
		cmpEmail.setEditable(false);
		cmpNombre.requestFocus();

		cmpCedula.setText(empleado.getCedula().getValue());
		cmpNombre.setText(empleado.getNombre().getValue());
		cmpApellido.setText(empleado.getApellido().getValue());
		cmpEmail.setText(empleado.getEmail().getValue());
		cmpClave.setText(empleado.getClave().getValue());
		cmpFechaNacimiento.setValue(Utilidades.pasarALocalDate(empleado.getFechaNacimiento().getValue()));
		// cmpGenero.getSelectionModel().select(empleado.getGenero().getValue());
		if (empleado.getGenero().getValue() == "masculino") {
			cmpGenero.getSelectionModel().select(0);
		} else {
			cmpGenero.getSelectionModel().select(1);
		}

		cmpTelefono.setText(empleado.getTelefono().getValue().get(0));
		cmpDireccion.setText(empleado.getDireccion().getValue());
		cmpFechaInicio.setValue(Utilidades.pasarALocalDate(empleado.getFechaInicio().getValue()));
		cmpFechaFin.setValue(Utilidades.pasarALocalDate(empleado.getFechaFin().getValue()));
		cmpSalario.setText(empleado.getSalario().getValue().toString());

		empleadosObservablesDetalleCliente = manejador.getEmpleadosObservables();

		for (EmpleadoObservable e : manejador.getEmpleadosObservables()) {
			if (e.getCedula().getValue() == empleado.getCedula().getValue()) {
				indiceListaEmpleadosObservables = empleadosObservablesDetalleCliente.indexOf(e);
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
				Empleado empleado = new Empleado();
				empleado.setCedula(cmpCedula.getText());
				empleado.setNombres(cmpNombre.getText());
				empleado.setApellidos(cmpApellido.getText());
				empleado.setContrasenia(cmpClave.getText());
				empleado.setCorreo(cmpEmail.getText());
				empleado.setFecha_nacimiento(Utilidades.pasarADate(cmpFechaNacimiento.getValue()));
				empleado.setCiudad(null);
				telefonos.add(cmpTelefono.getText());
				empleado.setTelefonos(telefonos);
				empleado.setDireccion(cmpDireccion.getText());
				empleado.setEstado("1");
				seleccionGenero = cmpGenero.getSelectionModel().getSelectedIndex();
				if (seleccionGenero == 0) {
					empleado.setGenero(Genero.masculino);
				} else {
					empleado.setGenero(Genero.femenino);
				}
				empleado.setFechaInicio(Utilidades.pasarADate(cmpFechaInicio.getValue()));
				empleado.setFechaFin(Utilidades.pasarADate(cmpFechaFin.getValue()));
				empleado.setSalario(Double.parseDouble(cmpSalario.getText()));

				if (manejador.registrarEmpleado(empleado)) {
					manejador.agregarEmpleadoALista(empleado);
					;
					Utilidades.mostrarMensaje("Registro", "Registro exitoso!!");
					escenarioEditar.close();
				} else {
					Utilidades.mostrarMensaje("Registro", "Error en registro!!");
				}
			} else {
				Utilidades.mostrarMensajeError("Email inválido", "El email no es válido");
			}

		} else {
			Utilidades.mostrarMensajeError("Datos incompletos",
					"Debes ingresar todos los datos. Algunos estan vacíos!");
		}

	}

	/**
	 * Permite editar la informacion de un empleado
	 */
	@FXML
	private void editarEmpleado() {
		int seleccionGenero;

		if (validarFormulario()) {
			if (validarEmail()) {
				Empleado empleado = new Empleado();
				empleado.setCedula(cmpCedula.getText());
				empleado.setNombres(cmpNombre.getText());
				empleado.setApellidos(cmpApellido.getText());
				empleado.setContrasenia(cmpClave.getText());
				empleado.setCorreo(cmpEmail.getText());
				empleado.setFecha_nacimiento(Utilidades.pasarADate(cmpFechaNacimiento.getValue()));
				empleado.setCiudad(null);
				telefonos.add(0, cmpTelefono.getText());
				empleado.setTelefonos(telefonos);
				empleado.setDireccion(cmpDireccion.getText());
				empleado.setEstado("1");
				seleccionGenero = cmpGenero.getSelectionModel().getSelectedIndex();
				// System.out.println("Seleccion genero empleado:" + seleccionGenero);
				if (seleccionGenero == 0) {
					empleado.setGenero(Genero.masculino);
				} else {
					empleado.setGenero(Genero.femenino);
				}
				empleado.setFechaInicio(Utilidades.pasarADate(cmpFechaInicio.getValue()));
				empleado.setFechaFin(Utilidades.pasarADate(cmpFechaFin.getValue()));
				empleado.setSalario(Double.parseDouble(cmpSalario.getText()));

				if (manejador.editarEmpleado(empleado)) {
					Utilidades.mostrarMensaje("Edición", "Se editó el empleado con éxito!");
					empleadosObservablesDetalleCliente.set(indiceListaEmpleadosObservables,
							new EmpleadoObservable(empleado));

					escenarioEditar.close();
				} else {
					Utilidades.mostrarMensajeError("Edición", "Error en edición de empleado!");
				}
			} else {
				Utilidades.mostrarMensajeError("Email inválido", "El email no es válido");
			}
		} else {
			Utilidades.mostrarMensajeError("Datos incompletos",
					"Debes ingresar todos los datos. Algunos estan vacíos!");
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

	/**
	 * Permite validar que el texto ingresado solo sean letras, numeros, # o -
	 */
	@FXML
	public void validarDireccion(KeyEvent ke) {
		caracter = ke.getCharacter().charAt(0);
		if (!Character.isAlphabetic(caracter) && !Character.isDigit(caracter) && !Character.isWhitespace(caracter)
				&& !(caracter == '#') && !(caracter == '-')) {
			ke.consume();
		}
	}

	/**
	 * Valida si los campos son diferente de vacio
	 * 
	 * @return true si todos contienen informacion false si algunos estan vacios
	 */
	public boolean validarFormulario() {
		if (!cmpCedula.getText().equals("") && (!cmpNombre.getText().isEmpty() || cmpNombre.getText().startsWith(" "))
				&& (!cmpApellido.getText().isEmpty() || cmpApellido.getText().startsWith(" "))
				&& !cmpEmail.getText().isEmpty() && !cmpClave.getText().isEmpty()
				&& !(cmpFechaNacimiento.getValue() == null) && !cmpGenero.getSelectionModel().isEmpty()
				&& !cmpTelefono.getText().isEmpty() && !cmpDireccion.getText().isEmpty()
				&& !(cmpFechaInicio.getValue() == null) && !(cmpFechaFin.getValue() == null)
				&& !cmpSalario.getText().isEmpty()) {

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
