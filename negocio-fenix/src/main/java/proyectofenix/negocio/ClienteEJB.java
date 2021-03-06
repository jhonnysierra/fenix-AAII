package proyectofenix.negocio;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.Prestamo;
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
			throw new ExcepcionesFenix("No se encontr� el cliente");
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
			throw new ElementoRepetidoExcepcion("Error: ya se ha registrado una persona con este n�mero de documento");

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
				throw new ExcepcionesFenix("No se pudo modificar el cliente:" + e.getMessage());
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
				// entityManager.remove(clienteEliminar);
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
			throw new ExcepcionesFenix("No se encontr� el empleado");
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
	public TipoAsesoria tipoAsesoriaPorCodigo(int idAsesoria) throws ExcepcionesFenix {
		TipoAsesoria tipoAsesoria;

		try {
			TypedQuery<TipoAsesoria> queryTipoAsesoria = entityManager
					.createNamedQuery(TipoAsesoria.TIPO_ASESORIA_POR_CODIGO, TipoAsesoria.class);
			queryTipoAsesoria.setParameter("idAsesoria", idAsesoria);
			tipoAsesoria = queryTipoAsesoria.getSingleResult();
		} catch (NoResultException re) {
			re.printStackTrace();
			throw new ExcepcionesFenix("No se encontr� el tipo de asesoria");
		}

		return tipoAsesoria;
	}

	/**
	 * Permite listar todos los tipos de prestamos existentes
	 * 
	 * @return Lista con los tipos de prestamos
	 */
	public List<TipoAsesoria> listarTodosTipoAsesoria() {
		TypedQuery<TipoAsesoria> queryTipoAsesoria = entityManager
				.createNamedQuery(TipoAsesoria.OBTENER_TIPOASESORIAS_ALL, TipoAsesoria.class);
		return queryTipoAsesoria.getResultList();
	}

	/**
	 * Metodo que permite crear una asesoria
	 * 
	 * @param asesoria asesoria que se va a crear
	 * @return Asesoria creada o null si no se registr�
	 * @throws ExcepcionesFenix                                      si no existe el
	 *                                                               cliente, si no
	 *                                                               existe el
	 *                                                               empleado
	 * @throws InformacionNoEncontradaException,NullPointerException si no encuentra
	 *                                                               el cliente
	 */
	public Asesoria realizarAsesoria(Asesoria asesoria)
			throws ExcepcionesFenix, InformacionNoEncontradaException, NullPointerException {

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

		return null;
	}

	/**
	 * Metodo que permite crear una asesoria
	 * 
	 * @param tipo_asesoria   tipo de asesoria de la asesoria
	 * @param cedula_empleado cedula del empleado que atendera la asesoria
	 * @param cedula_cliente  cedula del cliente que solicita la asesoria
	 * @param fecha           fecha de la asesoria
	 * @return Asesoria creada
	 * @throws ExcepcionesFenix Si no se encuentra el cliente, el empleado o el tipo
	 *                          de asesoria
	 */
	public Asesoria crearAsesoria(int tipo_asesoria, String cedula_empleado, String cedula_cliente, Date fecha,
			Date horaInicio) throws ExcepcionesFenix {

		Cliente cliente = buscarcliente(cedula_cliente);
		Empleado empleado = buscarEmpleado(cedula_empleado);
		TipoAsesoria tipoasesoria = tipoAsesoriaPorCodigo(tipo_asesoria);
		List<Asesoria> listaAsesoriasEmpleado = listaAsesoriaEmpleado(cedula_empleado);

		// SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
		// new Locale("es", "COL"));

		Calendar sumaHora = Calendar.getInstance();
		sumaHora.setTime(horaInicio);
		sumaHora.add(Calendar.HOUR, 1);

		for (Asesoria a : listaAsesoriasEmpleado) {
			if (a.getFecha().equals(fecha) && a.getHoraInicio().equals(horaInicio)) {
				throw new ExcepcionesFenix(
						"El empleado no puede atender tu solicitud en este horario. Intenta cambiar la hora si no funciona cambia el d�a");

			}
		}

		if (cliente == null) {
			throw new ExcepcionesFenix("No se puede realizar la asesoria porque NO se encontr� el cliente");
		} else if (empleado == null) {
			throw new ExcepcionesFenix("No se puede realizar la asesoria porque NO se encontr� el empleado");
		} else if (tipoasesoria == null) {
			throw new ExcepcionesFenix("No se puede realizar la asesoria porque el tipo de asesoria no existe");
		} else {

			Asesoria asesoria = new Asesoria();

			asesoria.setId(consecutivoAsesoria());
			asesoria.setCliente(cliente);
			asesoria.setEmpleado(empleado);
			asesoria.setTipoasesoria(tipoasesoria);
			asesoria.setFecha(fecha);
			asesoria.setHoraInicio(horaInicio);

			try {
				entityManager.persist(asesoria);
				return asesoria;
			} catch (Exception e) {
				throw new ExcepcionesFenix("No se pudo crear la asesoria. " + e.getMessage());
			}
		}
	}

	/**
	 * Metodo que permite obtener el listado de asesorias de un empleado
	 * 
	 * @param cedulaEmpleado cedula del empleado del que se quiere obtener las
	 *                       asesorias
	 * @return Lista de asesorias del empleado
	 * @throws ExcepcionesFenix si no se genera la lista
	 */
	public List<Asesoria> listaAsesoriaEmpleado(String cedulaEmpleado) throws ExcepcionesFenix {

		try {
			TypedQuery<Asesoria> query = entityManager.createNamedQuery(Asesoria.OBTENER_LISTA_ASESORIAS_EMPLEADO,
					Asesoria.class);
			query.setParameter("cedula", cedulaEmpleado);

			List<Asesoria> listaAsesorias = query.getResultList();
			// System.out.println("lista asesorias empleado ejb:" + listaAsesorias.size());
			return listaAsesorias;
		} catch (Exception e) {
			throw new ExcepcionesFenix("No se genero la lista de asesoria del empleado. " + e.getMessage());
		}
	}

	/**
	 * Permite lista los prestamo que ha realizado una persona
	 * 
	 * @param cedula cedula de la persona
	 * @return lista de los prestamos de una persona
	 */
	public List<Prestamo> listarPrestamosPersona(String cedula) {
		TypedQuery<Prestamo> query = entityManager.createNamedQuery(Persona.OBTENER_PRESTAMOS_PERSONA, Prestamo.class);
		query.setParameter("cedula", cedula);

		return query.getResultList();

	}

	/**
	 * Metodo que permite registrar un pago
	 * 
	 * @param pago a realizar
	 * @return Pago, pago realizado
	 */
	public Pago registrarPagoCuota(Pago pago) throws ExcepcionesFenix {
		if (pago.getPrestamo() == null) {
			throw new ExcepcionesFenix("No se puede realizar el pago porque NO se encontr� el prestamo");
		} else if (entityManager.find(Pago.class, pago.getId()) != null) {
			throw new ExcepcionesFenix("No se puede realizar el pago porque el id del pago ya existe");
		} else if ((sumaPagosPrestamo(pago.getPrestamo().getPagos()) + pago.getValor()) > pago.getPrestamo()
				.getValorPrestamo()) {
			throw new ExcepcionesFenix("El pago supera el valor del prestamo");
		} else if ((sumaPagosPrestamo(pago.getPrestamo().getPagos()) + pago.getValor()) == pago.getPrestamo()
				.getValorPrestamo()) {
			throw new ExcepcionesFenix("El prestamo ha sido cancelado");
		}

		try {
			entityManager.persist(pago);
			return pago;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Metodo que realiza la sumatoria de pagos de un prestamo
	 * 
	 * @param pagos lista de pagos del prestamo
	 * @return sumatoria de los pagos del prestamo
	 */
	public double sumaPagosPrestamo(List<Pago> pagos) {
		double totalPagado = 0;
		for (Pago p : pagos) {
			totalPagado += p.getValor();
		}

		return totalPagado;

	}

	/**
	 * Metodo que devuelve el consecutivo para el pago
	 * 
	 * @return consecutivo del pago
	 * @throws ExcepcionesFenix si no se genera el id del pago
	 */
	public int consecutivoPago() throws ExcepcionesFenix {
		int consecutivo;
		try {
			Query query = entityManager.createNamedQuery(Pago.OBTENER_CONSECUTIVO_PAGO);
			consecutivo = (int) query.getSingleResult() + 1;
			return consecutivo;
		} catch (Exception e) {
			// System.out.println("e.mesage:" + e.getMessage());
			throw new ExcepcionesFenix("No se puede generar el id del pago");
		}
	}

	/**
	 * Metodo que permite obtener la lista de las asesorias solicitadas por un
	 * cliente
	 * 
	 * @param cedulaCliente cedula del cliente del que se quiere la lista
	 * @return lista de asesorias del cliente
	 */
	public List<Asesoria> listaAsesoriasCliente(String cedulaCliente) {

		TypedQuery<Asesoria> query = entityManager.createNamedQuery(Asesoria.OBTENER_LISTA_ASESORIAS_CLIENTE,
				Asesoria.class);
		query.setParameter("cedula", cedulaCliente);

		List<Asesoria> listaAsesorias = query.getResultList();
		return listaAsesorias;
	}
}
