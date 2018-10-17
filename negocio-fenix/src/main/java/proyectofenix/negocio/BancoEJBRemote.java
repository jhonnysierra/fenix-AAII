package proyectofenix.negocio;

import java.util.List;

import javax.ejb.Remote;

import proyecto.fenix.excepciones.ElementoRepetidoExcepcion;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Empleado;
import proyectofenix.entidades.Pago;
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
	 * @param cedula
	 * @return
	 */
	public List<String> listarTelefonosPersona(String cedula);
	
	/**
	 * Genera el consecutivo del prestamo
	 * @return consecutivo del prestamo
	 * @throws ExcepcionesFenix
	 */
	public int consecutivoPrestamo() throws ExcepcionesFenix; 
	
	/**
	 * Devuelve el tipo de prestamo buscado por codigo
	 * @param id del prestamo
	 * @return tipo de prestamo
	 */
	public TipoPrestamo tipoPrestamoPorCodigo(int idPrestamo);

	/**
	 * Permite listar todos los tipos de prestamos existentes
	 * @return Lista con los tipos de prestamos
	 */
	public List<TipoPrestamo> listarTodosTipoPrestamo();
}
