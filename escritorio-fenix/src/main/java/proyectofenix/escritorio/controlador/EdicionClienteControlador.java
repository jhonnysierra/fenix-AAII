package proyectofenix.escritorio.controlador;


import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyectofenix.entidades.Cliente;
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
	 * representa el escenario en donde se agrega la vista
	 */
	private Stage escenarioEditar;
	/**
	 * instancia del manejador de los escenario
	 */
	private ManejadorEscenarios manejador;

	/**
	 * 
	 */
	@FXML
	private void initialize() {
		
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
	public void registrarPersona() {

		Cliente persona = new Cliente();
		persona.setCedula(cmpCedula.getText());
		persona.setNombres(cmpNombre.getText());
		persona.setApellidos(cmpApellido.getText());
		persona.setContrasenia(cmpClave.getText());
		persona.setCorreo(cmpEmail.getText());
		persona.setFecha_nacimiento(Utilidades.pasarADate(cmpFechaNacimiento.getValue()));
		persona.setCiudad(null);
		persona.setNoCuenta("345678");
		persona.setEstado("1");
		
		if (manejador.registrarCliente(persona)) {
			manejador.agregarALista(persona);
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
