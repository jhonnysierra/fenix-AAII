package proyectofenix.negocio;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Asesoria;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Empleado;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.TipoAsesoria;
import proyectofenix.entidades.TipoPrestamo;

/**
 * Permite manejar las operaciones de negocio que son unicas para cliente
 * 
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

	/**
	 * Metodo que permite buscar una persona por email
	 * 
	 * @param email
	 * @return
	 */
	private boolean buscarPersonaPorEmail(String email) {
		TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.PERSONA_POR_EMAIL, Persona.class);
		query.setParameter("email", email);

		return query.getResultList().size() > 0;
	}

	/**
	 * Permite buscar un cliente a la base de datos de un banco por cedula o email
	 * 
	 * @param cedula del cliente que se desea buscar
	 * @return devuelve el cliente encontrado o null si no encuentra nada
	 * @throws ExcepcionesFenix si no se encuentra el cliente
	 */
	public Cliente buscarcliente(String cedula) throws ExcepcionesFenix {
		Cliente clienteBuscar = entityManager.find(Cliente.class, cedula);

		if (clienteBuscar != null) {
			return clienteBuscar;

		} else if (buscarPersonaPorEmail(clienteBuscar.getCorreo())) {
			return clienteBuscar;
		} else {
			throw new ExcepcionesFenix("No se encontró el cliente");
		}
	}

	/**
	 * Metodo que permite buscar un empleado por cedula
	 * 
	 * @param cedula del empleado a buscar
	 * @return Empleado encontrado
	 * @throws ExcepcionesFenix Si no se encuentra el empleado
	 */
	public Empleado buscarEmpleado(String cedula) throws ExcepcionesFenix {
		Empleado empleadoBuscar = entityManager.find(Empleado.class, cedula);

		if (empleadoBuscar != null) {
			return empleadoBuscar;

		} else {
			throw new ExcepcionesFenix("No se encontró el empleado");
		}

	}

	/**
	 * Metodo que genera el consecutivo de una asesoria
	 * 
	 * @return numero consecutivo para la asesoria
	 * @throws ExcepcionesFenix si no se genera el id de la asesoria
	 */
	public int consecutivoAsesoria() throws ExcepcionesFenix {
		int consecutivo;
		try {
			Query query = entityManager.createNamedQuery(Asesoria.OBTENER_CONSECUTIVO_ASESORIA);
			consecutivo = (int) query.getSingleResult() + 1;
			return consecutivo;
		} catch (Exception e) {
			// System.out.println("e.mesage:" + e.getMessage());
			throw new ExcepcionesFenix("No se puede generar el id de la asesoria");
		}
	}

	/**
	 * Metodo que permite buscar una asesoria por su codigo
	 * 
	 * @param idAsesoria codigo de la asesoria
	 * @return Tipo de asesoria encontrado
	 */
	public TipoAsesoria tipoAsesoriaPorCodigo(int idAsesoria) {
		TypedQuery<TipoAsesoria> queryTipoAsesoria = entityManager
				.createNamedQuery(TipoAsesoria.TIPO_ASESORIA_POR_CODIGO, TipoAsesoria.class);
		queryTipoAsesoria.setParameter("idAsesoria", idAsesoria);
		return queryTipoAsesoria.getSingleResult();
	}

	/**
	 * Metodo que permite crear una asesoria
	 * 
	 * @param asesoria asesoria que se va a crear
	 * @return Asesoria creada o null si no se registró
	 * @throws ExcepcionesFenix si no existe el cliente, si no existe el empleado
	 */
	public Asesoria realizarAsesoria(Asesoria asesoria) throws ExcepcionesFenix {

		if (asesoria.getCliente() == null) {
			throw new ExcepcionesFenix("No se puede realizar la asesoria porque NO se encontró el cliente");
		} else if (asesoria.getEmpleado() == null) {
			throw new ExcepcionesFenix("No se puede realizar la asesoria porque NO se encontró el empleado");
		}

		Cliente cliente = asesoria.getCliente();

		if (cliente == null) {
			/* Mostrar excepcion */
		} else {
			entityManager.find(Cliente.class, cliente.getCedula());
		}
		if (asesoria.getCliente() != null) {

		}

		return null;
	}
}
