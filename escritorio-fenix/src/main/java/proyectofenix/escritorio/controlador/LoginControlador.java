/**
 * 
 */
package proyectofenix.escritorio.controlador;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Administrador;
import proyectofenix.escritorio.utilidades.Utilidades;

/**
 * Permite controlar la vista login
 * 
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
	 * Link olvide contrasenia
	 */
	@FXML
	private Hyperlink cmpOlvideContrasenia;

	/**
	 * Escenario inicial
	 */
	private ManejadorEscenarios escenarioInicial;

	/**
	 * cedula administrador
	 */
	private String cedula;

	/**
	 * Contrasenia administrador
	 */
	private String contrasenia;

	/**
	 * Usuario del correo electronico del cual se envian los corres
	 */
	private static String usuario = "teloprestamosfenix@gmail.com";

	/**
	 * Contrasenia del correo electronico del cual se envian los corres
	 */
	private static String password = "uniquindio123";

	/**
	 * Direccion a la cual se envia el correo de recordatorio
	 */
	private String para;

	/**
	 * Asunto del mensaje de correo electronico
	 */
	private String asunto;

	/**
	 * Mensaje que contiene la contrasenia del usuario
	 */
	private String mensaje;

	/**
	 * Caracter a validar
	 */
	private char caracter;

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
	 * Permite validar el ingreso de un administrador
	 */
	public void ingresar() {
		cedula = cmpUsuario.getText();
		contrasenia = cmpContrasenia.getText();

		try {

			if (escenarioInicial.login(cedula, contrasenia)) {
				escenarioInicial.cargarEscenaInicio(escenarioInicial);
			}
		} catch (ExcepcionesFenix e) {
			Utilidades.mostrarMensajeError("Login", "Error: " + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Permite enviar un correo electronico al usuario para recordar su contrasenia
	 */
	public void olvideContrasenia() {
		try {
			Administrador admin = escenarioInicial.listarAdministradorPorId(cmpUsuario.getText());
			para = admin.getCorreo();
			asunto = "OLVIDE CONTRASEÑA TE LO PRESTAMOS FENIX";
			mensaje = "Su contraseña de acceso al sistemas es: " + admin.getContrasenia();
		} catch (ExcepcionesFenix e1) {
			Utilidades.mostrarMensajeError("Login", "Error: " + e1.getMessage());
			e1.printStackTrace();
		}

		Properties props = new Properties();

		props = System.getProperties();
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(usuario, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(usuario));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para));
			message.setSubject(asunto);
			message.setText(mensaje);

			Transport.send(message);
			Utilidades.mostrarMensaje("Recuperar contraseña", "Su contraseña ha sido enviado al correo electrónico registrado.");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Permite cerrar la aplicacación
	 */
	@FXML
	public void cerrarAplicacion() {
		escenarioInicial.getEscenario().close();
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
		cmpUsuario.requestFocus();
	}

}
