package proyectofenix.negocio;

import java.util.List;

import javax.ejb.Remote;

import proyecto.fenix.excepciones.ElementoRepetidoExcepcion;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Administrador;
import proyectofenix.entidades.BienRaiz;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Empleado;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.Prestamo;
import proyectofenix.entidades.TipoPrestamo;

/**
 * Permite crear una interfaz para el acceso del delegado a los metodos
 * 
 * @author JJ
 * @version 1.0
 */
@Remote
public interface BancoEJBRemote {
	// Agregar los metodos de banco aqui

	String JNDI = "java:global/ear-fenix/negocio-fenix/BancoEJB!proyectofenix.negocio.BancoEJBRemote";

	/**
	 * Permite agregar un cliente al Banco
	 * 
	 * @param cliente cliente a agregar
	 * @return cliente agregado o null si no agrega nada
	 * @throws ElementoRepetidoExcepcion si el cliente a agregar ya existe
	 */
	public Cliente agregarCliente(Cliente cliente) throws ElementoRepetidoExcepcion;

	/**
	 * Permite buscar un cliente por numero de documento
	 * 
	 * @param cedula numero de cedula del cliente
	 * @return Cliente o null si no encuntra nada
	 * @throws ExcepcionesFenix
	 */
	public Cliente buscarcliente(String cedula) throws ExcepcionesFenix;

	/**
	 * Permite actualizar la informacion de un cliente
	 * 
	 * @param cliente cliente a modificar
	 * @return cliente modificado
	 * @throws ExcepcionesFenix
	 */
	public Cliente modificarCliente(Cliente cliente) throws ExcepcionesFenix;

	/**
	 * Permite eliminar un cliente
	 * 
	 * @param cedula numero de documento del cliente
	 * @return true si elimina el cliente, false si no lo elimina
	 * @throws ExcepcionesFenix si el cliente a eliminar el null
	 */
	public boolean eliminarCliente(String cedula) throws ExcepcionesFenix;

	/**
	 * Permite listar todos los clientes del banco
	 * 
	 * @return Lista con los clientes del banco
	 */
	public List<Cliente> listarclientes();
	

	/**
	 * Permite listar todas las personas del banco
	 * @return Lista con las personas 
	 */
	public List<Persona> listarPersonas();
	
	/**
	 * Metodo que permite buscar una persona por su numero de cedula
	 * @param cedula a buscar
	 * @return Persona encontrada o null si no encuentra nada
	 */
	public Persona buscarPersona(String cedula);

	/**
	 * Permite agregar un empleado al banco
	 * 
	 * @param empleado empleado a agregar
	 * @return empleado agregado
	 * @throws ElementoRepetidoExcepcion Se ejecuta si el empleado ya existe
	 */
	public Empleado agregarEmpleado(Empleado empleado) throws ElementoRepetidoExcepcion;

	/**
	 * Permite buscar un empleado por numero de documento
	 * 
	 * @param cedula numero de documento del empleado a buscar
	 * @return empleado encontrado o null si no encuentra nada
	 * @throws ExcepcionesFenix
	 */
	public Empleado buscarEmpleado(String cedula) throws ExcepcionesFenix;

	/**
	 * Permite modificar un empleado
	 * 
	 * @param empleado empleado a modificar
	 * @return empleado modificado
	 * @throws ExcepcionesFenix
	 */
	public Empleado modificarEmpleado(Empleado empleado) throws ExcepcionesFenix;

	/**
	 * Permite eliminar un cliente
	 * 
	 * @param cedula numero de documento del cliente a eliminar
	 * @return devuelve true si fue eliminado o false si no
	 * @throws ExcepcionesFenix Se ejecuta si el empleado a eliminar es null
	 */
	public boolean eliminarEmpleado(String cedula) throws ExcepcionesFenix;

	/**
	 * Permite listar los empleados del banco
	 * 
	 * @return Lista con los empleados del banco
	 */
	public List<Empleado> listarEmpleados();

	/**
	 * permite obtener una lista con todos los prestamos realizados por los clientes
	 * del banco
	 * 
	 * @return Lista con todos los prestamos realizados
	 */
	public List<Prestamo> listarAllPrestamos();

	/**
	 * Permite lista los prestamos por tipo
	 * 
	 * @param idTipoPrestamo tipo de prestamo por el cual desea filtrar los
	 *                       prestamos
	 * @return Lista con los prestamos del tipo ingresado
	 * @throws ExcepcionesFenix Se ejecuta si no encuentra el id del prestamo por el
	 *                          cual desea filtrar
	 */
	List<Prestamo> listarPrestamosPorTipo(int idTipoPrestamo) throws ExcepcionesFenix;

	/**
	 * Permite buscar un prestamo por su id
	 * 
	 * @param id identificador del prestamo
	 * @return Prestamo encontrado o null si no encuentra nada
	 * @throws ExcepcionesFenix Se ejecuta si el resultado de la consulta arroja
	 *                          resultado
	 */
	public Prestamo listarPrestamoPorId(int id) throws ExcepcionesFenix;

	/**
	 * Permite realizar el pago de un prestamo
	 * 
	 * @param pago pago a realizar
	 * @return Pago realizado
	 * @throws ExcepcionesFenix Si el pago no tiene asociado un prestamo, si el id
	 *                          del pago ya existe o si no se ejecuta el registro
	 *                          del pago
	 */
	public Pago registrarPagoCuota(Pago pago) throws ExcepcionesFenix;

	/**
	 * Permite registrar un prestamo
	 * 
	 * @param prestamo prestamo a registrar
	 * @return Prestamo registrado
	 * @throws ExcepcionesFenix Se ejecuta si el id del prestamo ya existe o si es
	 *                          un prestamo hipotecario y no tiene bien raiz
	 *                          asociado
	 */
	public Prestamo registrarPrestamo(Prestamo prestamo) throws ExcepcionesFenix;

	/**
	 * Permite consultar los telefonos asociados a una persona
	 * 
	 * @param cedula
	 * @return
	 */
	public List<String> listarTelefonosPersona(String cedula);

	/**
	 * Genera el consecutivo del prestamo
	 * 
	 * @return consecutivo del prestamo
	 * @throws ExcepcionesFenix
	 */
	public int consecutivoPrestamo() throws ExcepcionesFenix;

	/**
	 * Devuelve el tipo de prestamo buscado por codigo
	 * 
	 * @param id del prestamo
	 * @return tipo de prestamo
	 */
	public TipoPrestamo tipoPrestamoPorCodigo(int idPrestamo);

	/**
	 * Permite listar todos los tipos de prestamos existentes
	 * 
	 * @return Lista con los tipos de prestamos
	 */
	public List<TipoPrestamo> listarTodosTipoPrestamo();

	/**
	 * Permite obtener la lista de pagos asociados a un prestamo
	 * 
	 * @param idPrestamo
	 * @return lista de pagos
	 */
	public List<Pago> listarPagosPrestamo(int idPrestamo);

	/**
	 * Metodo que permite eliminar un prestamo
	 * 
	 * @param prestamo prestamo a eliminar
	 * @return true si se elimino o false si no
	 * @throws ExcepcionesFenix si el prestamo a eliminar no se encuentra
	 * 
	 */
	public boolean eliminarPrestamo(Prestamo prestamo) throws ExcepcionesFenix;

	/**
	 * Metodo que permite modificar la informacion de un prestamo
	 * 
	 * @param prestamo prestamo a modificar
	 * @return prestamo modificado
	 * @throws ExcepcionesFenix si el prestamo a eliminar es null
	 */
	public Prestamo modificarPrestamo(Prestamo prestamo) throws ExcepcionesFenix;

	/**
	 * Permite crear un bien raiz asociado a un cliente
	 * 
	 * @param bienraiz bien raiz a agregar
	 * @return bien raiz agregado
	 * @throws ExcepcionesFenix si el identificador ya existe
	 */
	public BienRaiz agregarBienRaiz(BienRaiz bienraiz) throws ExcepcionesFenix;

	/**
	 * Metodo que permite obtener una lista con todos los bienes raiz
	 * 
	 * @return List<BienRaiz> lista de todos los bienes raiz en el banco
	 */
	public List<BienRaiz> listarAllBienRaiz();

	/**
	 * Metodo que permite eliminar un bien raiz
	 * 
	 * @param bienraiz bien raiz a eliminar
	 * @return true si se elimino o false si no
	 * @throws ExcepcionesFenix
	 */
	public boolean eliminarBienRaiz(BienRaiz bienraiz) throws ExcepcionesFenix;

	/**
	 * Permite listar un bien raiz buscando por id
	 * 
	 * @param id identificador bien raiz
	 * @return bien raiz encontrado
	 * @throws ExcepcionesFenix
	 */
	public BienRaiz listarBienRaizPorId(String id) throws ExcepcionesFenix;

	/**
	 * Permite lista los prestamo que ha realizado una persona
	 * 
	 * @param cedula cedula de la persona
	 * @return lista de los prestamos de una persona
	 */
	public List<Prestamo> listarPrestamosPersona(String cedula);

	/**
	 * Metodo que permite modificar un bien raiz
	 * 
	 * @param bienraiz bien raiz a modificar
	 * @return bien raiz modificado
	 * @throws ExcepcionesFenix
	 */
	public BienRaiz modificarBienRaiz(BienRaiz bienraiz) throws ExcepcionesFenix;

	/**
	 * Metodo que devuelve el consecutivo para el pago
	 * 
	 * @return consecutivo del pago
	 * @throws ExcepcionesFenix si no se genera el id del pago
	 */
	public int consecutivoPago() throws ExcepcionesFenix;

	/**
	 * Permite listar todos los pagos
	 * 
	 * @return lista con los todos los pagos
	 */
	public List<Pago> listarAllPagos();

	/**
	 * Permite eliminar un pago
	 * 
	 * @param pago pago a eliminar
	 * @return true si se elimino o false si no
	 * @throws ExcepcionesFenix si el pago es null o no se puede eliminar
	 */
	public boolean eliminarPago(Pago pago) throws ExcepcionesFenix;

	/**
	 * Permite modificar un pago
	 * 
	 * @param pago pago a modificar
	 * @return pago modificado
	 * @throws ExcepcionesFenix
	 */
	public Pago modificarPago(Pago pago) throws ExcepcionesFenix;

	/**
	 * Permite buscar un administrador por cedula
	 * 
	 * @param cedula cedula del administrador
	 * @return Administrador encontrado
	 * @throws ExcepcionesFenix si no encuentra un administrador
	 */
	public Administrador listarAdministradorPorId(String cedula) throws ExcepcionesFenix;

	/**
	 * Permite validar un adminstrador en el sistema
	 * 
	 * @param cedula      cedula del administrador
	 * @param contrasenia contrasenia del administrador
	 * @return true si es valido o false si no
	 * @throws ExcepcionesFenix
	 */
	public boolean login(String cedula, String contrasenia) throws ExcepcionesFenix;

}
