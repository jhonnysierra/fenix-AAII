package proyectofenix.negocio;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import proyectofenix.entidades.Asesoria;
import proyectofenix.entidades.Cliente;

/**
 * Permite manejar las operaciones de negocio que son unicas para cliente
 * @author JJ
 * @version 1.0
 */
@Stateless
@LocalBean
public class ClienteEJB implements ClienteEJBRemote {

	@PersistenceContext
	private EntityManager entityManager;
    /**
     * Default constructor. 
     */
    public ClienteEJB() {
        // TODO Auto-generated constructor stub
    }

    public Asesoria realizarPregunta(Asesoria asesoria) {
    	
    	Cliente cliente = asesoria.getCliente();
    	
    	
    	if (cliente == null) {
			/*Mostrar excepcion*/
		} else {
			entityManager.find(Cliente.class, cliente.getCedula());
		}
    	if (asesoria.getCliente()!=null) {
			
		}
    	
    	return null;
    }
}
