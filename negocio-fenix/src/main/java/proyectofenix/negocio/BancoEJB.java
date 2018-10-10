package proyectofenix.negocio;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import proyecto.fenix.excepciones.ElementoRepetidoExcepcion;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Empleado;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.Prestamo;
import proyectofenix.entidades.TipoPrestamo;

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

		} else if (buscarPersonaPorEmail(cliente.getCorreo())) {
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
	private boolean buscarPersonaPorEmail(String email) {
		TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.PERSONA_POR_EMAIL, Persona.class);
		query.setParameter("email", email);

		return query.getResultList().size() > 0;
	}

	/**
	 * Permite buscar un cliente a la base de datos de un banco por cedula o email
	 * 
	 * @param String cedula del cliente que se desea buscar
	 * @return devuelve el cliente encontrado o null si no encuentra nada
	 */
	public Cliente buscarcliente(String cedula) throws ExcepcionesFenix {
		Cliente clienteBuscar = entityManager.find(Cliente.class, cedula);

		if (clienteBuscar != null) {
			return clienteBuscar;

		} else if (buscarPersonaPorEmail(clienteBuscar.getCorreo())) {
			return clienteBuscar;
		}

		return clienteBuscar;
	}

	/**
	 * Permite modificar un cliente a la base de datos de un banco
	 * 
	 * @param Empleado empleado a ser agregado
	 * @return devuelve el cliente modificado o null si no lo modifica
	 */
	public Cliente modificarCliente(Cliente cliente) throws ExcepcionesFenix {

		if (cliente != null) {
			try {
				entityManager.merge(cliente);
				return cliente;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			throw new ExcepcionesFenix("El cliente a modificar es null");
		}

	}

	/**
	 * Permite eliminar un cliente a la base de datos de un banco por cedula. Para
	 * el caso de nuestro proyecto el eliminado sera logico, es decir se cammbia el
	 * estado del empleado.
	 * 
	 * @param cedula cliente a eliminar
	 * @return boolean devuelve true si fue eliminado o false si no
	 */
	public boolean eliminarCliente(String cedula) throws ExcepcionesFenix {
		Cliente clienteEliminar = buscarcliente(cedula);

		if (clienteEliminar != null) {
			try {
				entityManager.remove(clienteEliminar);
				// clienteEliminar.setEstado("0");
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
	public List<Cliente> listarclientes() {
		TypedQuery<Cliente> clientes = entityManager.createNamedQuery(Cliente.OBTENER_DATOS_CLIENTE, Cliente.class);

		return clientes.getResultList();

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

		} else if (buscarPersonaPorEmail(empleado.getCorreo())) {
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
	 * @return devuelve el empleado encontrado o null si no lo encuentra
	 */
	public Empleado buscarEmpleado(String cedula) throws ExcepcionesFenix {
		Empleado empleadoBuscar = entityManager.find(Empleado.class, cedula);

		if (empleadoBuscar != null) {
			return empleadoBuscar;

		} else if (buscarPersonaPorEmail(empleadoBuscar.getCorreo())) {
			return empleadoBuscar;
		}

		return empleadoBuscar;

	}

	/**
	 * Permite modificar un empleado a la base de datos de un banco
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

	/**
	 * Metodo que permite obtener una lista con todos los prestamos realizados por
	 * los clientes del banco
	 * 
	 * @return List<Prestamo> lista de todos los prestamos realizados en el banco
	 */
	public List<Prestamo> listarAllPrestamos() {
		TypedQuery<Prestamo> prestamos = entityManager.createNamedQuery(Prestamo.OBTENER_PRESTAMOS_ALL, Prestamo.class);

		return prestamos.getResultList();
	}

	/**
	 * Metodo que permite obtener una lista con todos los prestamos de pendiendo del
	 * tipo realizados por los clientes del banco
	 * @param idTipoPrestamo id del tipo de prestamo por el que se quiere filtrar los prestamos
	 * @return List<Prestamo> lista de todos los prestamos realizados de tipo
	 */
	public List<Prestamo> listarPrestamosPorTipo(int idTipoPrestamo) throws ExcepcionesFenix {
		TipoPrestamo tipoPrestamo = entityManager.find(TipoPrestamo.class, idTipoPrestamo);

		if (tipoPrestamo == null) {
			throw new ExcepcionesFenix("No se encontr� el id del prestamo");
		}

		TypedQuery<Prestamo> prestamos = entityManager.createNamedQuery(Prestamo.OBTENER_PRESTAMOS_POR_TIPO,
				Prestamo.class);
		prestamos.setParameter("tipoPrestamo", tipoPrestamo);

		return prestamos.getResultList();

	}

	/**
	 * Metodo que permite buscar un prestamo por id
	 * 
	 * @param id del prestamo a buscar
	 * @return Prestamo, prestamo encontrado o null si no encuentra nada
	 */
	public Prestamo listarPrestamoPorId(int id) throws ExcepcionesFenix{

		try {
			TypedQuery<Prestamo> query = entityManager.createNamedQuery(Prestamo.OBTENER_PRESTAMO_POR_ID,
					Prestamo.class);
			query.setParameter("id", id);
			Prestamo prestamo = query.getSingleResult();
			return prestamo;
		} catch (NoResultException e) {
			throw new ExcepcionesFenix("No se encontr� el prestamo " + e.getMessage());
		}
		
	}
	
	/**
	 * Metodo que permite registrar un pago
	 * @param pago a realizar
	 * @return Pago, pago realizado
	 */
	public Pago registrarPagoCuota(Pago pago) throws ExcepcionesFenix{
		if(pago.getPrestamo()==null) {
			throw new ExcepcionesFenix("No se puede realizar el pago porque NO se encontr� el prestamo");
		}
		else if(entityManager.find(Pago.class, pago.getId())!=null) {
			throw new ExcepcionesFenix("No se puede realizar el pago porque el id del pago ya existe");
		}
		
		try {
			entityManager.persist(pago);
			return pago;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Prestamo registrarPrestamo(Prestamo prestamo) throws ExcepcionesFenix{
		
		if(entityManager.find(Prestamo.class, prestamo.getId())!=null) {
			throw new ExcepcionesFenix("No se puede realizar el prestamo porque el id del prestamo ya existe");
		}
		// Si el prestamo es hipotecario(4) y la persona no tiene bien raiz hay una excepcion
		if(prestamo.getTipoPrestamo().getId()==4 && prestamo.getPersona().getBienRaiz()==null) {
			throw new ExcepcionesFenix("No se puede realizar el prestamo hipotecario porque el cliente no tiene un bien asociado");
		}
		
		try {
			entityManager.persist(prestamo);
			return prestamo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
