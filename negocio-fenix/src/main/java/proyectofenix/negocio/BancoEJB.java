package proyectofenix.negocio;

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
		prestamos.setParameter("tipoPrestamo", tipoPrestamo);

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
	 * Metodo para registrar un prestamo
	 * 
	 * @param Prestamos a registrar
	 * @see proyectofenix.negocio.BancoEJBRemote#registrarPrestamo(proyectofenix.entidades.Prestamo)
	 */
	public Prestamo registrarPrestamo(Prestamo prestamo) throws ExcepcionesFenix {

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
		Long conse;
		try {
			Query query = entityManager.createNamedQuery(Prestamo.OBTENER_CONSECUTIVO_PRESTAMO);
			conse = (Long) query.getSingleResult();
			consecutivo = conse.intValue();
			return consecutivo;
		} catch (Exception e) {
			System.out.println("e.mesage:" + e.getMessage());
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
				System.out.println("Mensaje error remove:" + e.getMessage());
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

		System.out.println("Tamano de lista bien raiz:" + bienraiz.getResultList().size());

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
		
		if (bienRaizEliminar!=null) {
			try {
				entityManager.remove(bienRaizEliminar);
				System.out.println("Bien raiz Banco EJB:" + bienRaizEliminar.getId());
				return true;
			} catch (Exception e) {
				System.out.println("Mensaje error remove:" + e.getMessage());
				e.printStackTrace();
				return false;
			}
		}else {
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
	public BienRaiz listarBienRaizPorId(String id) throws ExcepcionesFenix{
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

}
