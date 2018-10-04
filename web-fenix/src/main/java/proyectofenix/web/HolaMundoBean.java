package proyectofenix.web;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Bean de ejemplo para mostrar un saludo en una pagina web
 * @author JJ
 * @version 1.0
 */
@Named
@ApplicationScoped
public class HolaMundoBean {

	public String getMensaje() {
		return "Hola JSF";
	}
}
