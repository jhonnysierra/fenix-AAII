package proyectofenix.negocio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import proyecto.fenix.excepciones.ElementoRepetidoExcepcion;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyecto.fenix.excepciones.InformacionNoEncontradaException;
import proyectofenix.entidades.Asesoria;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Empleado;
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
	 * Metodo que permite buscar una persona por numero de cedula
	 * 
	 * @param cedula a buscar
	 * @return Persona encontrada o null si no encuentra nada
	 */
	public Persona buscarPersona(String cedula) {
		Persona personaBuscar = entityManager.find(Persona.class, cedula);

		return personaBuscar;
	}
	
	/**
	 * Metodo que permite listar las personas del Banco
	 * 
	 * @return Lista de personas
	 */
	public List<Persona> listarPersonas() {
		TypedQuery<Persona> personas = entityManager.createNamedQuery(Persona.OBTENER_DATOS_PERSONAS, Persona.class);

		return personas.getResultList();
	}
	
	/**
	 * Metodo que permite listar los empleados del Banco
	 * 
	 * @return lista con los empleados del banco
	 */
	public List<Empleado> listarEmpleados() {
		TypedQuery<Empleado> empleados = entityManager.createNamedQuery(Empleado.OBTENER_DATOS_EMPLEADO,
				Empleado.class);

		return empleados.getResultList();

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

		} else {
			throw new ExcepcionesFenix("No se encontró el cliente");
		}
	}
	
	/**
	 * Permite agregar un cliente a la base de datos de un banco
	 * 
	 * @param cliente cliente a ser agregado
	 * @return devuelve el cliente agregado o null si no lo agrega
	 */
	public Cliente agregarCliente(Cliente cliente) throws ElementoRepetidoExcepcion {
		if (entityManager.find(Persona.class, cliente.getCedula()) != null) {
			throw new ElementoRepetidoExcepcion("Error: ya se ha registrado una persona con este número de documento");

		} else if (buscarPersonaPorEmail(cliente.getCorreo())) {
			throw new ElementoRepetidoExcepcion("Error: El email ya se encuentra registrado");
		}

		try {
			entityManager.persist(cliente);
			return cliente;
		} catch (Exception e) {
			
			return null;
		}

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
				//entityManager.remove(clienteEliminar);
				clienteEliminar.setEstado("0");
				entityManager.merge(clienteEliminar);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			throw new ExcepcionesFenix("El Cliente a eliminar es null");
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
	 * Permite listar todos los tipos de prestamos existentes
	 * 
	 * @return Lista con los tipos de prestamos
	 */
	public List<TipoPrestamo> listarTodosTipoPrestamo() {
		TypedQuery<TipoPrestamo> queryTipoPrestamo = entityManager.createNamedQuery(TipoPrestamo.ALL_TIPO_PRESTAMO,
				TipoPrestamo.class);
		return queryTipoPrestamo.getResultList();
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
			throw new ExcepcionesFenix("No se puede generar el id de la asesoria");
		}
	}

	/**
	 * Metodo que permite buscar una asesoria por su codigo
	 * 
	 * @param idAsesoria codigo de la asesoria
	 * @return Tipo de asesoria encontrado
	 */
	public TipoAsesoria tipoAsesoriaPorCodigo(int idAsesoria) throws ExcepcionesFenix{
		TipoAsesoria tipoAsesoria;
		
		
		try {
			TypedQuery<TipoAsesoria> queryTipoAsesoria = entityManager
					.createNamedQuery(TipoAsesoria.TIPO_ASESORIA_POR_CODIGO, TipoAsesoria.class);
			queryTipoAsesoria.setParameter("idAsesoria", idAsesoria);
			tipoAsesoria=queryTipoAsesoria.getSingleResult();
		} catch (NoResultException re) {
			re.printStackTrace();
			throw new ExcepcionesFenix("No se encontró el tipo de asesoria");
		}
		
		return tipoAsesoria;
	}

	/**
	 * Permite listar todos los tipos de prestamos existentes
	 * 
	 * @return Lista con los tipos de prestamos
	 */
	public List<TipoAsesoria> listarTodosTipoAsesoria() {
		TypedQuery<TipoAsesoria> queryTipoAsesoria = entityManager.createNamedQuery(TipoAsesoria.OBTENER_TIPOASESORIAS_ALL,
				TipoAsesoria.class);
		return queryTipoAsesoria.getResultList();
	}
	
	/**
	 * Metodo que permite crear una asesoria
	 * 
	 * @param asesoria asesoria que se va a crear
	 * @return Asesoria creada o null si no se registró
	 * @throws ExcepcionesFenix                                      si no existe el
	 *                                                               cliente, si no
	 *                                                               existe el
	 *                                                               empleado
	 * @throws InformacionNoEncontradaException,NullPointerException si no encuentra
	 *                                                               el cliente
	 */
	public Asesoria realizarAsesoria(Asesoria asesoria)
			throws ExcepcionesFenix, InformacionNoEncontradaException, NullPointerException {

		/*
		 * if (asesoria.getCliente() == null) { throw new
		 * ExcepcionesFenix("No se puede realizar la asesoria porque NO se encontró el cliente"
		 * ); } else if (asesoria.getEmpleado() == null) { throw new
		 * ExcepcionesFenix("No se puede realizar la asesoria porque NO se encontró el empleado"
		 * ); }
		 */

		Cliente cliente = asesoria.getCliente();
		// System.out.println("El cliente es" + cliente);

		/**
		 * Hacer una excepcion para cada foranea si es null
		 */

		if (cliente == null) {
			throw new NullPointerException("El cliente especificado no existe");
		} else if (asesoria.getEmpleado() == null) {
			throw new NullPointerException("El empleado especificado no existe");
		} else {
			cliente = entityManager.find(Cliente.class, cliente.getCedula());
			if (cliente == null) {
				throw new InformacionNoEncontradaException("El cliente no se encuentra registrado");
			} else {
				try {
					entityManager.persist(asesoria);
					return asesoria;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		/*
		 * if (cliente == null) { // TODO throw new
		 * NullPointerException("El cliente especificado no existe"); } else { cliente =
		 * entityManager.find(Cliente.class, cliente.getCedula());
		 * System.out.println(""); if (cliente == null) { throw new
		 * InformacionNoEncontradaException("El cliente no se encuentra registrado"); }
		 * else { try { asesoria.setCliente(cliente); entityManager.persist(asesoria);
		 * return asesoria; } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } }
		 */
		/*
		 * if (asesoria.getCliente() != null) {
		 * 
		 * }
		 */

		return null;
	}

	/**
	 * Metodo que permite crear una asesoria
	 * 
	 * @param tipo_asesoria tipo de asesoria de la asesoria
	 * @param cedula_empleado cedula del empleado que atendera la asesoria
	 * @param cedula_cliente cedula del cliente que solicita la asesoria
	 * @param fecha fecha de la asesoria
	 * @return Asesoria creada
	 * @throws ExcepcionesFenix Si no se encuentra el cliente, el empleado o el tipo de asesoria
	 */
	public Asesoria crearAsesoria(int tipo_asesoria, String cedula_empleado, String cedula_cliente, Date fecha)
			throws ExcepcionesFenix {

		Cliente cliente = buscarcliente(cedula_cliente);
		Empleado empleado = buscarEmpleado(cedula_empleado);
		TipoAsesoria tipoasesoria = tipoAsesoriaPorCodigo(tipo_asesoria);
		
		if (cliente == null) {
			throw new ExcepcionesFenix("No se puede realizar la asesoria porque NO se encontró el cliente");
		} else if (empleado == null) {
			throw new ExcepcionesFenix("No se puede realizar la asesoria porque NO se encontró el empleado");
		}
		else if (tipoasesoria==null) {
			throw new ExcepcionesFenix("No se puede realizar la asesoria porque el tipo de asesoria no existe");
		}else {
			
			Asesoria asesoria = new Asesoria();
			
			asesoria.setId(consecutivoAsesoria());
			asesoria.setCliente(cliente);
			asesoria.setEmpleado(empleado);
			asesoria.setTipoasesoria(tipoasesoria);
			asesoria.setFecha(fecha);
			
			try {
				entityManager.persist(asesoria); 
				return asesoria;
			} catch (Exception e) {
				throw new ExcepcionesFenix("No se pudo crear la asesoria. " + e.getMessage());
			}
		}
	}
}
