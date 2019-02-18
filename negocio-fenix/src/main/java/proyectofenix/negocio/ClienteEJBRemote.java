package proyectofenix.negocio;

import javax.ejb.Remote;

import proyecto.fenix.excepciones.ElementoRepetidoExcepcion;
import proyectofenix.entidades.Cliente;

@Remote
public interface ClienteEJBRemote {
	
	/**
	 * 
	 * @param cliente
	 * @return
	 * @throws ElementoRepetidoExcepcion
	 */
	public Cliente agregarCliente(Cliente cliente) throws ElementoRepetidoExcepcion;

}
