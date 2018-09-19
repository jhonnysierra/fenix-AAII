package proyectofenix.negocio;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
	 * 
	 * @param cliente cliente a ser agregado
	 * @return devuelve el cliente agregado o null si no lo agrega
	 */
	public Cliente agregarCliente(Cliente cliente) throws ElementoRepetidoExcepcion {
		if (entityManager.find(Persona.class, cliente.getCedula()) != null) {
			throw new ElementoRepetidoExcepcion("Error: ya se ha registrado una persona con este numero de documento");

		} else if (buscarClientePorEmail(cliente.getCorreo())) {
			throw new ElementoRepetidoExcepcion("Error: El email ya se encuentra registrado");
		}

		try {
			entityManager.persist(cliente);
			return cliente;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Permite buscar una persona por email
	 * @param email email de la persona
	 * @return true si encuentra la persona
	 */
	private boolean buscarClientePorEmail(String email) {
		TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.PERSONA_POR_EMAIL, Persona.class);
		query.setParameter("email", email);

		return query.getResultList().size() > 0;
	}

}
