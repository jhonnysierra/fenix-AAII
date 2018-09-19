package proyectofenix.negocio;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import proyecto.fenix.excepciones.ElementoRepetidoExcepcion;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Persona;

/**
 * Session Bean implementation class BancoEJB
 */
@Stateless
@LocalBean
public class BancoEJB implements BancoEJBRemote {

	@PersistenceContext
	private EntityManager entityManager;
    /**
     * Default constructor. 
     */
    public BancoEJB() {
        
    }
    
    /**
     * Permite agregar un cliente a la base de datos de un banco
     * @param cliente Cliente cliente a ser agregado
     * @return
     */
    public Cliente agregarCliente(Cliente cliente) throws ElementoRepetidoExcepcion{
    	if (entityManager.find(Persona.class, cliente.getCedula())!=null) {
    		throw new ElementoRepetidoExcepcion("Error ya se ha registrado una persona");
			
		}
    	else if(buscarCliente) {
    		
    	}
    	
    	entityManager.persist(cliente);
    	try {
    		
		} catch (Exception e) {
			
		}
    	return cliente;
    }
    
    private boolean buscarCliente;

}
