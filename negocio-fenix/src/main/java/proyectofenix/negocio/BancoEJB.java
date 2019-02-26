package proyectofenix.negocio;

import java.util.ArrayList;
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
import proyectofenix.entidades.Administrador;
import proyectofenix.entidades.Asesoria;
import proyectofenix.entidades.BienRaiz;
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
	 * Metodo que permite buscar una persona por su numero de cedula
	 * 
	 * @param cedula a buscar
	 * @return Persona encontrada o null si no encuentra nada
	 */
	public Persona buscarPersona(String cedula) {
		Persona personaBuscar = entityManager.find(Persona.class, cedula);

		return personaBuscar;
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
	 * Permite listar los clientes activos en el banco
	 * 
	 * @return
	 */
	public List<Cliente> listarclientesActivos() {
		TypedQuery<Cliente> clientes = entityManager.createNamedQuery(Cliente.CLIENTES_POR_ESTADO, Cliente.class);
		clientes.setParameter("estado", "1");

		return clientes.getResultList();

	}

	public List<Persona> listarPersonas() {
		TypedQuery<Persona> personas = entityManager.createNamedQuery(Persona.OBTENER_DATOS_PERSONAS, Persona.class);

		return personas.getResultList();
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
	 * Metodo que permite obtener una lista con todos los prestamos dependiendo del
	 * tipo de prestamo realizados por los clientes del banco
	 * 
	 * @param idTipoPrestamo id del tipo de prestamo por el que se quiere filtrar
	 *                       los prestamos
	 * @return List<Prestamo> lista de todos los prestamos realizados de tipo
	 */
	public List<Prestamo> listarPrestamosPorTipo(int idTipoPrestamo) throws ExcepcionesFenix {
		TipoPrestamo tipoPrestamo = entityManager.find(TipoPrestamo.class, idTipoPrestamo);

		if (tipoPrestamo == null) {
			throw new ExcepcionesFenix("No se encontró el id del prestamo");
		}

		TypedQuery<Prestamo> prestamos = entityManager.createNamedQuery(Prestamo.OBTENER_PRESTAMOS_POR_TIPO,
				Prestamo.class);
		prestamos.setParameter("idtipoPrestamo", tipoPrestamo.getId());

		return prestamos.getResultList();

	}

	/**
	 * Metodo que permite buscar un prestamo por id
	 * 
	 * @param id del prestamo a buscar
	 * @return Prestamo, prestamo encontrado o null si no encuentra nada
	 */
	public Prestamo listarPrestamoPorId(int id) throws ExcepcionesFenix {

		try {
			TypedQuery<Prestamo> query = entityManager.createNamedQuery(Prestamo.OBTENER_PRESTAMO_POR_ID,
					Prestamo.class);
			query.setParameter("id", id);
			Prestamo prestamo = query.getSingleResult();
			return prestamo;
		} catch (NoResultException e) {
			throw new ExcepcionesFenix("No se encontró el prestamo " + e.getMessage());
		}

	}

	/**
	 * Metodo que permite registrar un pago
	 * 
	 * @param pago a realizar
	 * @return Pago, pago realizado
	 */
	public Pago registrarPagoCuota(Pago pago) throws ExcepcionesFenix {
		if (pago.getPrestamo() == null) {
			throw new ExcepcionesFenix("No se puede realizar el pago porque NO se encontró el prestamo");
		} else if (entityManager.find(Pago.class, pago.getId()) != null) {
			throw new ExcepcionesFenix("No se puede realizar el pago porque el id del pago ya existe");
		} else if ((sumaPagosPrestamo(pago.getPrestamo().getPagos()) + pago.getValor()) > pago.getPrestamo()
				.getValorPrestamo()) {
			throw new ExcepcionesFenix("El pago supera el valor del prestamo");
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
	 * Metodo para registrar un prestamo
	 * 
	 * @param Prestamos a registrar
	 * @see proyectofenix.negocio.BancoEJBRemote#registrarPrestamo(proyectofenix.entidades.Prestamo)
	 */
	public Prestamo registrarPrestamo(Prestamo prestamo) throws ExcepcionesFenix {
		// System.out.println("Bien raiz de persona:" +
		// prestamo.getPersona().getBienRaiz().getId()!=null);
		if (entityManager.find(Prestamo.class, prestamo.getId()) != null) {
			throw new ExcepcionesFenix("No se puede realizar el prestamo porque el id del prestamo ya existe");
		}
		// Si el prestamo es hipotecario(4) y la persona no tiene bien raiz hay una
		// excepcion
		if (prestamo.getTipoPrestamo().getId() == 4 && prestamo.getPersona().getBienRaiz() == null) {
			throw new ExcepcionesFenix(
					"No se puede realizar el prestamo hipotecario porque el cliente no tiene un bien asociado");
		}

		try {
			entityManager.persist(prestamo);
			return prestamo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Metodo que permite realizar un prestamo, dependiendo de los parametros
	 * 
	 * @param cedula         de la persona que realiza el credito
	 * @param valorPrestamo  monto del prestamo
	 * @param fechaInicio    fecha en la que se realiza el prestamo
	 * @param fechaFin       fecha en la que vence el prestamo
	 * @param numeroCuotas   numero de cuotas del prestamo
	 * @param idTipoPrestamo id del tipo de prestamo
	 * @return Prestamo realizado
	 * @throws ExcepcionesFenix Si no se genera el id del prestamo, si no existe la
	 *                          persona, el tipo de id, las cuotas son <=0,
	 */
	public Prestamo realizarPrestamo(String cedula, double valorPrestamo, Date fechaInicio, Date fechaFin,
			int numeroCuotas, int idTipoPrestamo) throws ExcepcionesFenix {
		Prestamo prestamo = new Prestamo();

		/*
		 * System.out.println("Datos metodo: " + cedula + "," + valorPrestamo + "," +
		 * fechaInicio + "," + fechaFin + "," + numeroCuotas + "," + idTipoPrestamo);
		 */

		// Genera consecutivo
		prestamo.setId(consecutivoPrestamo());

		// Buscar persona
		Persona persona = buscarPersona(cedula);

		// Busca tipo de prestamo
		TipoPrestamo tipoPrestamo = tipoPrestamoPorCodigo(idTipoPrestamo);

		// Crear una lista de pagos vacia
		List<Pago> listaPagos = new ArrayList<Pago>();

		if (entityManager.find(Prestamo.class, prestamo.getId()) != null) {
			throw new ExcepcionesFenix("No se puede realizar el prestamo porque el id del prestamo ya existe");
		} else if (persona == null) {
			throw new ExcepcionesFenix("No se puede realizar el prestamo porque la persona no existe");
		} else if (persona.getEstado().equals("0")) {
			throw new ExcepcionesFenix("No se puede realizar el prestamo porque la persona esta INACTIVA");
		} else if (fechaInicio == null) {
			throw new ExcepcionesFenix("No se puede realizar el prestamo porque la fecha no es valida");
		} else if (numeroCuotas <= 0) {
			throw new ExcepcionesFenix("No se puede realizar el prestamo porque las cuotas no son validas");
		} else if (tipoPrestamo == null) {
			throw new ExcepcionesFenix("No se puede realizar el prestamo porque el tipo de prestamo es invalido");
		}
		// Si el prestamo es hipotecario(4) y la persona no tiene bien raiz hay una
		// excepcion
		if (tipoPrestamo.getId() == 4 && persona.getBienRaiz() == null) {
			throw new ExcepcionesFenix(
					"No se puede realizar el prestamo hipotecario porque el cliente no tiene un bien asociado");
		}

		prestamo.setPersona(persona);
		prestamo.setValorPrestamo(valorPrestamo);
		prestamo.setFechaInicio(fechaInicio);
		prestamo.setFechaFin(fechaFin);
		prestamo.setNoCuotas(numeroCuotas);
		prestamo.setTipoPrestamo(tipoPrestamo);
		prestamo.setPagos(listaPagos);

		try {
			entityManager.persist(prestamo);
			return prestamo;
		} catch (Exception e) {
			throw new ExcepcionesFenix("No se puede realizar el prestamo: " + e.getMessage());
		}

	}

	/**
	 * Metodo que permite listar los telefonos de un clinte
	 * 
	 * @param cedula numero de documento del cliente
	 * @return Lista con los numero de telefono asociados al cliente
	 */
	public List<String> listarTelefonosPersona(String cedula) {

		try {
			Query query = entityManager.createNamedQuery(Persona.OBTENER_TELEFONOS_PERSONA);
			query.setParameter(1, cedula);
			// System.out.println("Consulta:" + query.toString());
			List<String> telefonos = query.getResultList();
			return telefonos;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Metodo que devuelve el consecutivo para el prestamo
	 * 
	 * @return
	 */
	public int consecutivoPrestamo() throws ExcepcionesFenix {
		int consecutivo;
		// Long conse;
		try {
			Query query = entityManager.createNamedQuery(Prestamo.OBTENER_CONSECUTIVO_PRESTAMO);
			/*
			 * conse = (Long) query.getSingleResult(); consecutivo = conse.intValue();
			 */
			consecutivo = (int) query.getSingleResult() + 1;
			// System.out.println("Ide prestmo EJB:" + consecutivo);
			return consecutivo;
		} catch (Exception e) {
			throw new ExcepcionesFenix("No se puede generar el id del prestamo");
		}
	}

	/**
	 * Devuelve un tipo de prestamo buscado por su codigo
	 * 
	 * @return tipo prestamo
	 */
	public TipoPrestamo tipoPrestamoPorCodigo(int idPrestamo) {
		TypedQuery<TipoPrestamo> queryTipoPrestamo = entityManager
				.createNamedQuery(TipoPrestamo.TIPO_PRESTAMO_POR_CODIGO, TipoPrestamo.class);
		queryTipoPrestamo.setParameter("idPrestamo", idPrestamo);
		return queryTipoPrestamo.getSingleResult();
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
	 * Metodo que permite listar los pagos realizados a un prestamo
	 * 
	 * @param idPrestamo identificador del prestamo
	 * @return lista con los pagos asociados a un prestamo
	 */
	public List<Pago> listarPagosPrestamo(int idPrestamo) {

		try {
			TypedQuery<Pago> query = entityManager.createNamedQuery(Prestamo.OBTENER_CUOTAS_PRESTAMO, Pago.class);
			query.setParameter("id", idPrestamo);

			List<Pago> listaPagos = query.getResultList();
			// System.out.println("lista pagos- bancoejb:" + listaPagos.size());
			return listaPagos;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Metodo que permite eliminar un prestamo
	 * 
	 * @param prestamo prestamo a eliminar
	 * @return true si se elimino o false si no
	 * @throws ExcepcionesFenix si el prestamo a eliminar no se encuentra
	 */
	public boolean eliminarPrestamo(Prestamo prestamo) throws ExcepcionesFenix {
		Prestamo prestamoEliminar = listarPrestamoPorId(prestamo.getId());
		if (prestamoEliminar != null) {
			try {
				entityManager.remove(prestamoEliminar);
				// prestamoEliminar.setEstado("0");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			throw new ExcepcionesFenix("El Prestamo a eliminar es null");
		}

	}

	/**
	 * Metodo que permite modificar la informacion de un prestamo
	 * 
	 * @param prestamo prestamo a modificar
	 * @return prestamo modificado
	 * @throws ExcepcionesFenix si el prestamo a eliminar es null
	 */
	public Prestamo modificarPrestamo(Prestamo prestamo) throws ExcepcionesFenix {

		if (prestamo != null) {
			try {
				entityManager.merge(prestamo);
				return prestamo;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			throw new ExcepcionesFenix("El prestamo a modificar es null");
		}

	}

	/**
	 * Permite crear un bien raiz asociado a un cliente
	 * 
	 * @param bienraiz bien raiz a agregar
	 * @return bien raiz agregado
	 * @throws ExcepcionesFenix si el identificador ya existe
	 */
	public BienRaiz agregarBienRaiz(BienRaiz bienraiz) throws ExcepcionesFenix {
		if (entityManager.find(BienRaiz.class, bienraiz.getId()) != null) {
			throw new ExcepcionesFenix("Error: ya se ha registrado una bien con este identificador");
		}

		try {
			entityManager.persist(bienraiz);
			return bienraiz;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

	/**
	 * Metodo que permite obtener una lista con todos los bienes raiz
	 * 
	 * @return List<BienRaiz> lista de todos los bienes raiz en el banco
	 */
	public List<BienRaiz> listarAllBienRaiz() {
		TypedQuery<BienRaiz> bienraiz = entityManager.createNamedQuery(BienRaiz.OBTENER_ALL_BIENRAIZ, BienRaiz.class);

		// System.out.println("Tamano de lista bien raiz:" +
		// bienraiz.getResultList().size());

		return bienraiz.getResultList();
	}

	/**
	 * Metodo que permite eliminar un bien raiz
	 * 
	 * @param bienraiz bien raiz a eliminar
	 * @return true si se elimino o false si no
	 * @throws ExcepcionesFenix
	 */
	public boolean eliminarBienRaiz(BienRaiz bienraiz) throws ExcepcionesFenix {
		BienRaiz bienRaizEliminar = listarBienRaizPorId(bienraiz.getId());

		if (bienRaizEliminar != null) {
			try {
				entityManager.remove(bienRaizEliminar);
				// System.out.println("Bien raiz Banco EJB:" + bienRaizEliminar.getId());
				return true;
			} catch (Exception e) {
				// System.out.println("Mensaje error remove:" + e.getMessage());
				e.printStackTrace();
				return false;
			}
		} else {
			throw new ExcepcionesFenix("El Bien raiz a eliminar es null");
		}

	}

	/**
	 * Permite listar un bien raiz buscando por id
	 * 
	 * @param id identificador bien raiz
	 * @return bien raiz encontrado
	 * @throws ExcepcionesFenix
	 */
	public BienRaiz listarBienRaizPorId(String id) throws ExcepcionesFenix {
		try {
			TypedQuery<BienRaiz> query = entityManager.createNamedQuery(BienRaiz.OBTENER_BIENRAIZ_POR_ID,
					BienRaiz.class);
			query.setParameter("id", id);

			BienRaiz bienraiz = query.getSingleResult();
			return bienraiz;
		} catch (NoResultException e) {
			throw new ExcepcionesFenix("No se encontró el bien raíz " + e.getMessage());
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
	 * Metodo que permite modificar un bien raiz
	 * 
	 * @param bienraiz bien raiz a modificar
	 * @return bien raiz modificado
	 * @throws ExcepcionesFenix
	 */
	public BienRaiz modificarBienRaiz(BienRaiz bienraiz) throws ExcepcionesFenix {

		if (bienraiz != null) {
			try {
				entityManager.merge(bienraiz);
				return bienraiz;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			throw new ExcepcionesFenix("El bien raiz a modificar es null");
		}

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
	 * Permite listar todos los pagos
	 * 
	 * @return lista con los todos los pagos
	 */
	public List<Pago> listarAllPagos() {
		TypedQuery<Pago> pagos = entityManager.createNamedQuery(Pago.OBTENER_PAGOS_ALL, Pago.class);

		return pagos.getResultList();
	}

	/**
	 * Permite lista un pago buscando por el id
	 * 
	 * @param id identificador del pago
	 * @return pago encontrado
	 * @throws ExcepcionesFenix
	 */
	public Pago listarPagoPorId(int id) throws ExcepcionesFenix {
		try {
			TypedQuery<Pago> query = entityManager.createNamedQuery(Pago.OBTENER_PAGO_POR_ID, Pago.class);
			query.setParameter("id", id);

			Pago pago = query.getSingleResult();
			return pago;
		} catch (NoResultException e) {
			throw new ExcepcionesFenix("No se encontró el pago: " + e.getMessage());
		}
	}

	/**
	 * Permite eliminar un pago
	 * 
	 * @param pago pago a eliminar
	 * @return true si se elimino o false si no
	 * @throws ExcepcionesFenix si el pago es null o no se puede eliminar
	 */
	public boolean eliminarPago(Pago pago) throws ExcepcionesFenix {
		Pago pagoEliminar = listarPagoPorId(pago.getId());

		if (pagoEliminar != null) {
			try {
				entityManager.remove(pagoEliminar);
				return true;
			} catch (Exception e) {
				// System.out.println("Mensaje error remove:" + e.getMessage());
				e.printStackTrace();
				throw new ExcepcionesFenix("El pago no se pudo eliminar");
			}
		} else {
			throw new ExcepcionesFenix("El pago a eliminar es null");
		}

	}

	/**
	 * Permite modificar un pago
	 * 
	 * @param pago pago a modificar
	 * @return pago modificado
	 * @throws ExcepcionesFenix
	 */
	public Pago modificarPago(Pago pago) throws ExcepcionesFenix {

		if (pago != null) {
			try {
				entityManager.merge(pago);
				return pago;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			throw new ExcepcionesFenix("El pago a modificar es null");
		}

	}

	/**
	 * Permite buscar un administrador por cedula
	 * 
	 * @param cedula cedula del administrador
	 * @return Administrador encontrado
	 * @throws ExcepcionesFenix si no encuentra un administrador
	 */
	public Administrador listarAdministradorPorId(String cedula) throws ExcepcionesFenix {
		try {
			TypedQuery<Administrador> query = entityManager.createNamedQuery(Administrador.ADMIN_POR_ID,
					Administrador.class);
			query.setParameter("cedula", cedula);
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new ExcepcionesFenix("No se encontró el Administrador");
		}
	}

	/**
	 * Permite validar un adminstrador en el sistema
	 * 
	 * @param cedula      cedula del administrador
	 * @param contrasenia contrasenia del administrador
	 * @return true si es valido o false si no
	 * @throws ExcepcionesFenix
	 */
	public boolean login(String cedula, String contrasenia) throws ExcepcionesFenix {
		Administrador administrador = listarAdministradorPorId(cedula);

		if (administrador != null) {
			if (administrador.getEstado().equals("1")) {
				if (administrador.getCedula().equals(cedula) && administrador.getContrasenia().equals(contrasenia)) {
					return true;
				} else {
					throw new ExcepcionesFenix("Los datos no son correctos");
				}
			} else {
				throw new ExcepcionesFenix("El administrador se encuentra INACTIVO");
			}
		} else {
			throw new ExcepcionesFenix("Ocurrió un error!!! No se pudo validar la información");
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
	public List<Asesoria> listaAsesoriaEmpleado(String cedulaEmpleado) {

		TypedQuery<Asesoria> query = entityManager.createNamedQuery(Asesoria.OBTENER_LISTA_ASESORIAS_EMPLEADO,
				Asesoria.class);
		query.setParameter("cedula", cedulaEmpleado);

		List<Asesoria> listaAsesorias = query.getResultList();
		return listaAsesorias;
	}

	/**
	 * Metodo que permite actualizar la hora final de una asesoria, es decir, cuando
	 * el empleado ya atendio al cliente.
	 * 
	 * @param asesoria asesoria a editar
	 * @return asesoria editada
	 * @throws ExcepcionesFenix
	 */
	public Asesoria atenderAsesoria(Asesoria asesoria) throws ExcepcionesFenix {

		if (asesoria != null) {
			try {
				entityManager.merge(asesoria);
				return asesoria;
			} catch (Exception e) {
				throw new ExcepcionesFenix("La asesoria no se pudo actualizar");
			}
		} else {
			throw new ExcepcionesFenix("La asesoria es null");
		}
	}

}
