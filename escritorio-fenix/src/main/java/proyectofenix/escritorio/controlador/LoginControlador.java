/**
 * 
 */
package proyectofenix.escritorio.controlador;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Permite controlar la vista login
 * @author JJ
 */
public class LoginControlador {

	/**
	 * campo para el usuario
	 */
	@FXML
	private TextField cmpUsuario;
	
	/**
	 * campo para la contrasenia
	 */
	@FXML
	private PasswordField cmpContrasenia;
	
	/**
	 * Boton Aceptar de la ventana edicion empleado y crear empleado
	 */
	@FXML
	private Button btnAceptar;
	
	/**
	 * Escenario inicial
	 */
	private ManejadorEscenarios escenarioInicial;
	
	/**
	 * Constructor
	 */
	public LoginControlador() {
		
	}
	
	@FXML
	private void initialize() {
		cmpUsuario.requestFocus();

	}


	
	/**
	 * Permite cerrar la aplicacación
	 */
	@FXML
	public void cerrarAplicacion() {
		escenarioInicial.getEscenario().close();
	}
	

	/**
	 * @return the escenarioInicial
	 */
	public ManejadorEscenarios getEscenarioInicial() {
		return escenarioInicial;
	}

	/**
	 * @param escenarioInicial the escenarioInicial to set
	 */
	public void setEscenarioInicial(ManejadorEscenarios escenarioInicial) {
		this.escenarioInicial = escenarioInicial;
	}

}
