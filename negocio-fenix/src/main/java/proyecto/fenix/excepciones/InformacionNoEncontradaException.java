package proyecto.fenix.excepciones;

/**
 * Es lanzada cada que alguna informacion obligatoria no es encontrada
 * @author JHONNY
 *
 */
public class InformacionNoEncontradaException extends Exception{

	private static final long serialVersionUID=1L;
	
	public InformacionNoEncontradaException(String mensaje) {
		super(mensaje);
	}
}
