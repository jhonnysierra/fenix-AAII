package proyectofenix.escritorio.modelo;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.fenix.excepciones.ElementoRepetidoExcepcion;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Administrador;
import proyectofenix.entidades.BienRaiz;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Empleado;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Prestamo;
import proyectofenix.entidades.TipoPrestamo;
import proyectofenix.negocio.BancoEJBRemote;

/**
 * Delegado que permite conectar la capa de negocio con la de presentacion
 * 
 * @author JJ
 * @version 1.0
 */
public class BancoDelegado {

	/**
	 * Instancia que representa el EJB remoto de Banco
	 */
	private BancoEJBRemote bancoEJB;

	/**
	 * permite manejar una unica instancia para le manejo de delegados
	 */
	public static BancoDelegado bancoDelegado = instancia();

	/**
	 * constructor para conectar con la capa de negocio
	 */
	private BancoDelegado() {
		try {
			bancoEJB = (BancoEJBRemote) new InitialContext().lookup(BancoEJBRemote.JNDI);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permite devolver una unica instancia de delegado
	 * 
	 * @return instancia unica del delegado
	 */
	private static BancoDelegado instancia() {

		if (bancoDelegado == null) {
			bancoDelegado = new BancoDelegado();
			return bancoDelegado;
		}
		return bancoDelegado;
	}

	/**
	 * Permite agregar un cliente al Banco
	 * 
	 * @param cliente cliente a agregar
	 * @return cliente agregado o null si no agrega nada
	 * @throws ElementoRepetidoExcepcion si el cliente a agregar ya existe
	 * @see proyectofenix.negocio.BancoEJBRemote#agregarCliente(proyectofenix.entidades.Cliente)
	 */
	public Cliente agregarCliente(Cliente cliente) throws ElementoRepetidoExcepcion {
		return bancoEJB.agregarCliente(cliente);
	}

	/**
	 * Permite buscar un cliente
	 * 
	 * @param cedula numero de cedula del cliente
	 * @return Cliente o null si no encuntra nada
	 * @throws ExcepcionesFenix
	 * @see proyectofenix.negocio.BancoEJBRemote#buscarcliente(java.lang.String)
	 */
	public Cliente buscarcliente(String cedula) throws ExcepcionesFenix {
		return bancoEJB.buscarcliente(cedula);
	}

	/**
	 * Permite actualizar la informacion de un cliente
	 * 
	 * @param cliente cliente a modificar
	 * @return cliente modificado
	 * @throws ExcepcionesFenix
	 * @see proyectofenix.negocio.BancoEJBRemote#modificarCliente(proyectofenix.entidades.Cliente)
	 */
	public Cliente modificarCliente(Cliente cliente) throws ExcepcionesFenix {
		return bancoEJB.modificarCliente(cliente);
	}

	/**
	 * Permite eliminar un cliente
	 * 
	 * @param cedula numero de documento del cliente
	 * @return true si elimina el cliente, false si no lo elimina
	 * @throws ExcepcionesFenix si el cliente a eliminar el null
	 * @see proyectofenix.negocio.BancoEJBRemote#eliminarCliente(java.lang.String)
	 */
	public boolean eliminarCliente(String cedula) throws ExcepcionesFenix {
		return bancoEJB.eliminarCliente(cedula);
	}

	/**
	 * Permite listar todos los clientes del banco
	 * 
	 * @return Lista con los clientes del banco
	 * @see proyectofenix.negocio.BancoEJBRemote#listarclientes()
	 */
	public List<Cliente> listarclientes() {
		return bancoEJB.listarclientes();
	}

	/**
	 * Permite agregar un empleado al banco
	 * 
	 * @param empleado empleado a agregar
	 * @return empleado agregado
	 * @throws ElementoRepetidoExcepcion Se ejecuta si el empleado ya existe
	 * @see proyectofenix.negocio.BancoEJBRemote#agregarEmpleado(proyectofenix.entidades.Empleado)
	 */
	public Empleado agregarEmpleado(Empleado empleado) throws ElementoRepetidoExcepcion {
		return bancoEJB.agregarEmpleado(empleado);
	}

	/**
	 * Permite buscar un empleado por numero de documento
	 * 
	 * @param cedula numero de documento del empleado a buscar
	 * @return empleado encontrado o null si no encuentra nada
	 * @throws ExcepcionesFenix
	 * @see proyectofenix.negocio.BancoEJBRemote#buscarEmpleado(java.lang.String)
	 */
	public Empleado buscarEmpleado(String cedula) throws ExcepcionesFenix {
		return bancoEJB.buscarEmpleado(cedula);
	}

	/**
	 * Permite modificar un empleado
	 * 
	 * @param empleado empleado a modificar
	 * @return empleado modificado
	 * @throws ExcepcionesFenix
	 * @see proyectofenix.negocio.BancoEJBRemote#modificarEmpleado(proyectofenix.entidades.Empleado)
	 */
	public Empleado modificarEmpleado(Empleado empleado) throws ExcepcionesFenix {
		return bancoEJB.modificarEmpleado(empleado);
	}

	/**
	 * Permite eliminar un cliente
	 * 
	 * @param cedula numero de documento del cliente a eliminar
	 * @return devuelve true si fue eliminado o false si no
	 * @throws ExcepcionesFenix Se ejecuta si el empleado a eliminar es null
	 * @see proyectofenix.negocio.BancoEJBRemote#eliminarEmpleado(java.lang.String)
	 */
	public boolean eliminarEmpleado(String cedula) throws ExcepcionesFenix {
		return bancoEJB.eliminarEmpleado(cedula);
	}

	/**
	 * Permite listar los empleados del banco
	 * 
	 * @return Lista con los empleados del banco
	 * @see proyectofenix.negocio.BancoEJBRemote#listarEmpleados()
	 */
	public List<Empleado> listarEmpleados() {
		return bancoEJB.listarEmpleados();
	}

	/**
	 * permite obtener una lista con todos los prestamos realizados por los clientes
	 * del banco
	 * 
	 * @return Lista con todos los prestamos realizados
	 * @see proyectofenix.negocio.BancoEJBRemote#listarAllPrestamos()
	 */
	public List<Prestamo> listarAllPrestamos() {
		return bancoEJB.listarAllPrestamos();
	}

	/**
	 * Metodo que permite obtener una lista con todos los bienes raiz
	 * 
	 * @return List<BienRaiz> lista de todos los bienes raiz en el banco
	 * @see proyectofenix.negocio.BancoEJBRemote#listarAllBienRaiz()
	 */
	public List<BienRaiz> listarAllBienRaiz() {
		return bancoEJB.listarAllBienRaiz();
	}

	/**
	 * Permite lista los prestamos por tipo
	 * 
	 * @param idTipoPrestamo tipo de prestamo por el cual desea filtrar los
	 *                       prestamos
	 * @return Lista con los prestamos del tipo ingresado
	 * @throws ExcepcionesFenix Se ejecuta si no encuentra el id del prestamo por el
	 *                          cual desea filtrar
	 * @see proyectofenix.negocio.BancoEJBRemote#listarPrestamosPorTipo(int)
	 */
	public List<Prestamo> listarPrestamosPorTipo(int idTipoPrestamo) throws ExcepcionesFenix {
		return bancoEJB.listarPrestamosPorTipo(idTipoPrestamo);
	}

	/**
	 * Permite buscar un prestamo por su id
	 * 
	 * @param id identificador del prestamo
	 * @return Prestamo encontrado o null si no encuentra nada
	 * @throws ExcepcionesFenix Se ejecuta si el resultado de la consulta arroja
	 *                          resultado
	 * @see proyectofenix.negocio.BancoEJBRemote#listarPrestamoPorId(int)
	 */
	public Prestamo listarPrestamoPorId(int id) throws ExcepcionesFenix {
		return bancoEJB.listarPrestamoPorId(id);
	}

	/**
	 * Permite realizar el pago de un prestamo
	 * 
	 * @param pago pago a realizar
	 * @return Pago realizado
	 * @throws ExcepcionesFenix Si el pago no tiene asociado un prestamo, si el id
	 *                          del pago ya existe o si no se ejecuta el registro
	 *                          del pago
	 * @see proyectofenix.negocio.BancoEJBRemote#registrarPagoCuota(proyectofenix.entidades.Pago)
	 */
	public Pago registrarPagoCuota(Pago pago) throws ExcepcionesFenix {
		return bancoEJB.registrarPagoCuota(pago);
	}

	/**
	 * Permite registrar un prestamo
	 * 
	 * @param prestamo prestamo a registrar
	 * @return Prestamo registrado
	 * @throws ExcepcionesFenix Se ejecuta si el id del prestamo ya existe o si es
	 *                          un prestamo hipotecario y no tiene bien raiz
	 *                          asociado
	 * @see proyectofenix.negocio.BancoEJBRemote#registrarPrestamo(proyectofenix.entidades.Prestamo)
	 */
	public Prestamo registrarPrestamo(Prestamo prestamo) throws ExcepcionesFenix {
		return bancoEJB.registrarPrestamo(prestamo);
	}

	/**
	 * genera una lista de clientes observables
	 * 
	 * @return todos los clientes obsevables
	 */
	public ObservableList<ClienteObservable> listarClientesObservables() {
		List<Cliente> clientes = listarclientes();

		// System.out.println("Lista telefonos:" + clientes.get(0).getCedula());
		ObservableList<ClienteObservable> clientesObservables = FXCollections.observableArrayList();
		for (Cliente c : clientes) {
			c.setTelefonos(bancoEJB.listarTelefonosPersona(c.getCedula()));
			clientesObservables.add(new ClienteObservable(c));
		}
		return clientesObservables;
	}

	/**
	 * Genera una lista de empleados observables
	 * 
	 * @return todos los empleados obsevables
	 */
	public ObservableList<EmpleadoObservable> listarEmpleadosObservables() {
		List<Empleado> empleados = listarEmpleados();

		// System.out.println("Lista telefonos:" + empleados.get(0).getCedula());
		ObservableList<EmpleadoObservable> empleadosObservables = FXCollections.observableArrayList();
		for (Empleado e : empleados) {
			e.setTelefonos(bancoEJB.listarTelefonosPersona(e.getCedula()));
			empleadosObservables.add(new EmpleadoObservable(e));
		}
		return empleadosObservables;
	}

	/**
	 * Genera una lista de prestamos observables
	 * 
	 * @return todos los prestamos obsevables
	 */
	public ObservableList<PrestamoObservable> listarPrestamosObservables() {
		List<Prestamo> prestamos = listarAllPrestamos();

		ObservableList<PrestamoObservable> prestamosObservables = FXCollections.observableArrayList();
		for (Prestamo p : prestamos) {
			p.getPersona().setTelefonos(bancoEJB.listarTelefonosPersona(p.getPersona().getCedula()));
			p.setPagos(bancoEJB.listarPagosPrestamo(p.getId()));
			prestamosObservables.add(new PrestamoObservable(p));
		}
		return prestamosObservables;
	}

	/**
	 * Genera una lista de bien raiz observable
	 * 
	 * @return lista de bien raiz observables
	 */
	public ObservableList<BienRaizObservable> listarBienRaizObservables() {
		List<BienRaiz> bienraiz = listarAllBienRaiz();

		ObservableList<BienRaizObservable> bienRaizObservables = FXCollections.observableArrayList();
		for (BienRaiz br : bienraiz) {
			br.getPersona().setTelefonos(bancoEJB.listarTelefonosPersona(br.getPersona().getCedula()));
			br.getPersona().setPrestamo(bancoEJB.listarPrestamosPersona(br.getPersona().getCedula()));
			bienRaizObservables.add(new BienRaizObservable(br));
		}
		return bienRaizObservables;
	}

	/**
	 * Genera una lista de pagos observables
	 * 
	 * @return lista de pagos observables
	 */
	public ObservableList<PagoObservable> listarPagosObservables() {
		List<Pago> listaPagos = listarAllPagos();

		ObservableList<PagoObservable> pagosObservables = FXCollections.observableArrayList();
		for (Pago p : listaPagos) {
			pagosObservables.add(new PagoObservable(p));
		}
		return pagosObservables;
	}

	/**
	 * Genera una lista de prestamos observables
	 * 
	 * @param idtipoprestamo identificador del prestamo por que el que se quiere
	 *                       filtrar los prestamos
	 * @return lista de prestamos de un tipo determinado
	 */
	public ObservableList<PrestamoObservable> listarPorTipoPrestamosObservables(int idtipoprestamo) {
		List<Prestamo> prestamos;
		try {
			prestamos = listarPrestamosPorTipo(idtipoprestamo);
			ObservableList<PrestamoObservable> prestamosObservables = FXCollections.observableArrayList();
			for (Prestamo p : prestamos) {
				p.getPersona().setTelefonos(bancoEJB.listarTelefonosPersona(p.getPersona().getCedula()));
				p.setPagos(bancoEJB.listarPagosPrestamo(p.getId()));
				prestamosObservables.add(new PrestamoObservable(p));
			}
			return prestamosObservables;
		} catch (ExcepcionesFenix e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Metodo que permite generar el consecutivo del prestamo nuevo
	 * 
	 * @return consecutivo del prestamo nuevo
	 * @throws ExcepcionesFenix
	 * @see proyectofenix.negocio.BancoEJBRemote#consecutivoPrestamo()
	 */
	public int consecutivoPrestamo() throws ExcepcionesFenix {
		return bancoEJB.consecutivoPrestamo();
	}

	/**
	 * Permite obtener un tipo de prestamo buscando por id
	 * 
	 * @param idPrestamo id del tipo de prestamo
	 * @return tipo de prestamo
	 * @see proyectofenix.negocio.BancoEJBRemote#tipoPrestamoPorCodigo(int)
	 */
	public TipoPrestamo tipoPrestamoPorCodigo(int idPrestamo) {
		return bancoEJB.tipoPrestamoPorCodigo(idPrestamo);
	}

	/**
	 * Permite listar todos los tipos de prestamos existentes
	 * 
	 * @return Lista con los tipos de prestamos
	 * @see proyectofenix.negocio.BancoEJBRemote#listarTodosTipoPrestamo()
	 */
	public List<TipoPrestamo> listarTodosTipoPrestamo() {
		return bancoEJB.listarTodosTipoPrestamo();
	}

	/**
	 * Metodo que permite eliminar un prestamo
	 * 
	 * @param prestamo prestamo a eliminar
	 * @return true si se elimino o false si no
	 * @throws ExcepcionesFenix si el prestamo a eliminar es null
	 * @see proyectofenix.negocio.BancoEJBRemote#eliminarPrestamo(proyectofenix.entidades.Prestamo)
	 */
	public boolean eliminarPrestamo(Prestamo prestamo) throws ExcepcionesFenix {
		return bancoEJB.eliminarPrestamo(prestamo);
	}

	/**
	 * Metodo que permite modificar la informacion de un prestamo
	 * 
	 * @param prestamo prestamo a modificar
	 * @return prestamo modificado
	 * @throws ExcepcionesFenix si el prestamo a eliminar es null
	 * @see proyectofenix.negocio.BancoEJBRemote#modificarPrestamo(proyectofenix.entidades.Prestamo)
	 */
	public Prestamo modificarPrestamo(Prestamo prestamo) throws ExcepcionesFenix {
		return bancoEJB.modificarPrestamo(prestamo);
	}

	/**
	 * Permite crear un bien raiz asociado a un cliente
	 * 
	 * @param bienraiz bien raiz a agregar
	 * @return bien raiz agregado
	 * @throws ExcepcionesFenix si el identificador ya existe
	 * @see proyectofenix.negocio.BancoEJBRemote#agregarBienRaiz(proyectofenix.entidades.BienRaiz)
	 */
	public BienRaiz agregarBienRaiz(BienRaiz bienraiz) throws ExcepcionesFenix {
		return bancoEJB.agregarBienRaiz(bienraiz);
	}

	/**
	 * Metodo que permite eliminar un bien raiz
	 * 
	 * @param bienraiz bien raiz a eliminar
	 * @return true si se elimino o false si no
	 * @throws ExcepcionesFenix
	 * @see proyectofenix.negocio.BancoEJBRemote#eliminarBienRaiz(proyectofenix.entidades.BienRaiz)
	 */
	public boolean eliminarBienRaiz(BienRaiz bienraiz) throws ExcepcionesFenix {
		return bancoEJB.eliminarBienRaiz(bienraiz);
	}

	/**
	 * Metodo que permite modificar un bien raiz
	 * 
	 * @param bienraiz bien raiz a modificar
	 * @return bien raiz modificado
	 * @throws ExcepcionesFenix
	 * @see proyectofenix.negocio.BancoEJBRemote#modificarBienRaiz(proyectofenix.entidades.BienRaiz)
	 */
	public BienRaiz modificarBienRaiz(BienRaiz bienraiz) throws ExcepcionesFenix {
		return bancoEJB.modificarBienRaiz(bienraiz);
	}

	/**
	 * Metodo que devuelve el consecutivo para el pago
	 * 
	 * @return consecutivo del pago
	 * @throws ExcepcionesFenix si no se genera el id del pago
	 * @see proyectofenix.negocio.BancoEJBRemote#consecutivoPago()
	 */
	public int consecutivoPago() throws ExcepcionesFenix {
		return bancoEJB.consecutivoPago();
	}

	/**
	 * Permite listar todos los pagos
	 * 
	 * @return lista con los todos los pagos
	 * @see proyectofenix.negocio.BancoEJBRemote#listarAllPagos()
	 */
	public List<Pago> listarAllPagos() {
		return bancoEJB.listarAllPagos();
	}

	/**
	 * Permite eliminar un pago
	 * 
	 * @param pago pago a eliminar
	 * @return true si se elimino o false si no
	 * @throws ExcepcionesFenix si el pago es null o no se puede eliminar
	 * @see proyectofenix.negocio.BancoEJBRemote#eliminarPago(proyectofenix.entidades.Pago)
	 */
	public boolean eliminarPago(Pago pago) throws ExcepcionesFenix {
		return bancoEJB.eliminarPago(pago);
	}

	/**
	 * Permite modificar un pago
	 * 
	 * @param pago pago a modificar
	 * @return pago modificado
	 * @throws ExcepcionesFenix
	 * @see proyectofenix.negocio.BancoEJBRemote#modificarPago(proyectofenix.entidades.Pago)
	 */
	public Pago modificarPago(Pago pago) throws ExcepcionesFenix {
		return bancoEJB.modificarPago(pago);
	}

	/**
	 * Permite validar un adminstrador en el sistema
	 * 
	 * @param cedula      cedula del administrador
	 * @param contrasenia contrasenia del administrador
	 * @return true si es valido o false si no
	 * @throws ExcepcionesFenix
	 * @see proyectofenix.negocio.BancoEJBRemote#login(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean login(String cedula, String contrasenia) throws ExcepcionesFenix {
		return bancoEJB.login(cedula, contrasenia);
	}

	/**
	 * Permite buscar un administrador por cedula
	 * 
	 * @param cedula cedula del administrador
	 * @return Administrador encontrado
	 * @throws ExcepcionesFenix si no encuentra un administrador
	 * @see proyectofenix.negocio.BancoEJBRemote#listarAdministradorPorId(java.lang.String)
	 */
	public Administrador listarAdministradorPorId(String cedula) throws ExcepcionesFenix {
		return bancoEJB.listarAdministradorPorId(cedula);
	}
	
	

}
