package utilidades.web;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import proyectofenix.entidades.Persona;

public class EmailUtils {
	private static final String HOSTNAME = "smtp.gmail.com";
	private static final String USERNAME = "teloprestamosfenix@gmail.com";
	private static final String PASSWORD = "uniquindio123";
	private static final String EMAILORIGEM = "teloprestamosfenix@gmail.com";

	public static Email conectaEmail() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(HOSTNAME);
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
		email.setTLS(true);
		email.setFrom(EMAILORIGEM);
		return email;
	}

	public void enviaEmail(Persona usuario) throws EmailException {
		Email email = new SimpleEmail();
		email = conectaEmail();
		email.setSubject("ProyectoFenix - Recuperar contraseña");
		email.setMsg("Su contraseña de acceso al sistema es: " + usuario.getContrasenia());
		email.addTo(usuario.getCorreo());
		String resposta = email.send();
		System.out.println("Envio email portugues:" + resposta);
	}

}
