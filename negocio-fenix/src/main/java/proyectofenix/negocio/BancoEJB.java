package proyectofenix.negocio;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import proyecto.fenix.excepciones.ElementoRepetidoExcepcion;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Empleado;
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
	 * 
	 * @param email email de la persona
	 * @return true si encuentra la persona
	 */
	private boolean buscarClientePorEmail(String email) {
		TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.PERSONA_POR_EMAIL, Persona.class);
		query.setParameter("email", email);

		return query.getResultList().size() > 0;
	}

	/**
	 * Permite agregar un empleado a la base de datos de un banco Metodo Test para
	 * este metodo pruebas-fenix.proyectofenix.pruebas.agregarEmpleadoTest
	 * 
	 * @param Empleado empleado a ser agregado
	 * @return devuelve el empleado agregado o null si no lo agrega
	 */
	public Empleado agregarEmpleado(Empleado empleado) throws ElementoRepetidoExcepcion {
		if (entityManager.find(Persona.class, empleado.getCedula()) != null) {
			throw new ElementoRepetidoExcepcion("Error: ya se ha registrado una persona con este numero de documento");

		} else if (buscarClientePorEmail(empleado.getCorreo())) {
			throw new ElementoRepetidoExcepcion("Error: El email ya se encuentra registrado");
		}

		try {
			entityManager.persist(empleado);
			return empleado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Permite buscar un empleado a la base de datos de un banco por cedula o email
	 * Metodo Test para este metodo
	 * pruebas-fenix.proyectofenix.pruebas.agregarEmpleadoTest
	 * 
	 * @param Empleado empleado a ser agregado
	 * @return devuelve el empleado agregado o null si no lo agrega
	 */
	public Empleado buscarEmpleado(String cedula) throws ExcepcionesFenix {
		Empleado empleadoBuscar = entityManager.find(Empleado.class, cedula);

		if (empleadoBuscar != null) {
			return empleadoBuscar;
			// throw new ExcepcionesFenix("Error: ya se ha registrado una persona
			// con este numero de documento");

		} else if (buscarClientePorEmail(empleadoBuscar.getCorreo())) {
			return empleadoBuscar;
		}

		return empleadoBuscar;

	}

	/**
	 * Permite modificar un empleado a la base de datos de un banco por cedula o
	 * email Metodo Test para este metodo
	 * pruebas-fenix.proyectofenix.pruebas.modificarEmpleadoTest
	 * 
	 * @param Empleado empleado a ser agregado
	 * @return devuelve el empleado modificado o null si no lo modifica
	 */
	public Empleado modificarEmpleado(Empleado empleado) throws ExcepcionesFenix {

		if (empleado != null) {
			try {
				entityManager.merge(empleado);
				return empleado;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			throw new ExcepcionesFenix("El empleado a modificar es null");
		}

	}

	/**
	 * Permite eliminar un empleado a la base de datos de un banco por cedula. Para
	 * el caso de nuestro proyecto el eliminado sera logico, es decir se cammbia el
	 * estado del empleado. Para la entrega se realiza el remove. Metodo Test para
	 * este metodo pruebas-fenix.proyectofenix.pruebas.eliminarEmpleadoTest
	 * 
	 * @param cedula empleado a eliminar
	 * @return devuelve true si fue eliminado o false si no
	 */
	public boolean eliminarEmpleado(String cedula) throws ExcepcionesFenix {
		Empleado empleadoEliminar = buscarEmpleado(cedula);

		if (empleadoEliminar != null) {
			try {
				entityManager.remove(empleadoEliminar);
				// empleadoEliminar.setEstado("0");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			throw new ExcepcionesFenix("El empleado a eliminar es null");
		}

	}

	/**
	 * Metodo que permite obtener el listado de los empleados Metodo Test para este
	 * metodo pruebas-fenix.proyectofenix.pruebas.listarEmpleadosTest
	 * 
	 * @return List<Empleado> Lista de empleados
	 */
	public List<Empleado> listarEmpleados() {
		TypedQuery<Empleado> empleados = entityManager.createNamedQuery(Empleado.OBTENER_DATOS_EMPLEADO,
				Empleado.class);

		return empleados.getResultList();

	}
}
