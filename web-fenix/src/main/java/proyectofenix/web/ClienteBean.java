package proyectofenix.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Past;

import proyecto.fenix.excepciones.ElementoRepetidoExcepcion;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Ciudad;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.Persona.Genero;
import proyectofenix.negocio.BancoEJB;
import proyectofenix.negocio.ClienteEJB;

/**
 * Permite generar un registro de cliente
 * 
 * @author JJ
 *
 */
@Named
@ApplicationScoped
public class ClienteBean implements Serializable {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private BancoEJB administradorEJB;

	@EJB
	private ClienteEJB clienteEJB;

	/**
	 * Permite identificar una persona
	 */
	private String cedula;

	/**
	 * Nombre una persona
	 */
	private String nombres;
	/**
	 * Apellido de una persona
	 */
	private String apellidos;

	/**
	 * Direccion de una persona
	 */
	private String direccion;

	/**
	 * Correo electronico de una Persona
	 */
	private String correo;

	/**
	 * Fecha de nacimiento de un cliente
	 */
	@Past
	private Date fechaNacimiento;

	/**
	 * Genero de un cliente
	 */
	private Genero genero;

	/**
	 * Lista para los telefonos
	 */
	private List<String> telefonos;

	/**
	 * Telefono de un cliente
	 */
	private String telefono;
	
	/**
	 * Telefono del cliente que se muestra en la lista
	 */
	private String telefonoLista;
	/**
	 * Numero de cuenta de un cliente
	 */
	private String numero_cuenta;

	/**
	 * Contrasenia de una Persona para ingresar al sistema
	 */
	private String contrasenia;

	/**
	 * Ciudad de una persona
	 */
	private Ciudad ciudad;

	/**
	 * Lista de clientes
	 */
	private List<Cliente> clientes;

	/**
	 * Cliente
	 */
	private Cliente cliente;
	
	@Inject 
	@ManagedProperty(value="#{seguridadBean.usuario}")
	private Persona usuario;

	/**
	 * Metodo constructor
	 */
	public ClienteBean() {
		telefonos = new ArrayList<>();
	}

	@PostConstruct
	private void inicializar() {
		clientes = administradorEJB.listarclientesActivos();
		/*for (Cliente c : clientes) {
			System.out.println(String.format("Cedula:%s, estado:%s", c.getCedula(), c.getEstado()));
		}*/
	}

	/**
	 * Metodo que permite registrar un cliente
	 * 
	 * @return URL registrar cliente
	 */
	public String agregarCliente() {
		try {
			Cliente cliente = new Cliente();

			cliente.setCedula(cedula);
			cliente.setNombres(nombres);
			cliente.setApellidos(apellidos);
			cliente.setContrasenia(contrasenia);
			cliente.setCorreo(correo);
			cliente.setEstado("1");
			cliente.setDireccion(direccion);
			cliente.setCiudad(null);
			cliente.setNoCuenta(numero_cuenta);

			cliente.setGenero(genero);
			telefonos.add(telefono);
			cliente.setTelefonos(telefonos);
			cliente.setFecha_nacimiento(fechaNacimiento);
			this.clientes.add(cliente);

			clienteEJB.agregarCliente(cliente);

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso",
					"Registro exitoso");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
			reiniciarVariables();
			
			return "/registrarCliente";

		} catch (ElementoRepetidoExcepcion | RuntimeException e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "";
		}
	}

	/**
	 * Metodo que permite modificar la informacion de un cliente
	 * 
	 * @return true si se modifico o false si no
	 */
	public String modificarCliente() {
		
		try {

			Cliente cliente = new Cliente();

			cliente.setCedula(this.cliente.getCedula());
			cliente.setNombres(this.cliente.getNombres());
			cliente.setApellidos(this.cliente.getApellidos());
			cliente.setContrasenia(this.cliente.getContrasenia());
			cliente.setCorreo(this.cliente.getCorreo());
			cliente.setEstado("1");
			cliente.setDireccion(this.cliente.getDireccion());
			cliente.setCiudad(null);
			cliente.setNoCuenta(this.cliente.getNoCuenta());

			cliente.setGenero(this.cliente.getGenero());
			telefonos.add(telefonoLista);
			cliente.setTelefonos(telefonos);
			cliente.setFecha_nacimiento(this.cliente.getFecha_nacimiento());

			clienteEJB.modificarCliente(cliente);

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modificó el cliente",
					"Modificar exitoso");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
			reiniciarVariables();
			return "/listaClientes";

		} catch (ExcepcionesFenix e) {
			e.printStackTrace();
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "";
		}
	}

	
	/**
	 * Metodo que permite eliminar un cliente
	 * 
	 * @return URL lista clientes
	 */
	public String eliminarCliente() {

		try {
			// Cambiar el parametro de la cedula de la clase a uno enviado por el metodo si
			// es necesario

			clienteEJB.eliminarCliente(cliente.getCedula());

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se eliminó el cliente",
					"Eliminar cliente exitoso");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

			inicializar();
			// volver a cargar lista
			return "listaClientes";

		} catch (ExcepcionesFenix e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return null;
		}
	}

	/**
	 * Metodo que permite reiniciar los valores de las variables
	 */
	public void reiniciarVariables() {
		cedula="";
		nombres="";
		apellidos="";
		direccion="";
		correo="";
		fechaNacimiento=null;
		genero=null;
		telefonos=new ArrayList<>();
		telefono="";
		numero_cuenta="";
		contrasenia="";
		cliente=null;
		
	}
	
	// GET y SET

	/**
	 * Metodo get cedula Bean cliente
	 * 
	 * @return cedula
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * Metodo set cedula Bean cliente
	 * 
	 * @param cedula
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	/**
	 * Metodo get Nombres Bean cliente
	 * 
	 * @return nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * Metodo set Nombres Bean cliente
	 * 
	 * @param nombres
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * Metodo get apellidos Bean cliente
	 * 
	 * @return apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Metodo set apellidos Bean cliente
	 * 
	 * @param apellidos
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Metodo get direccion Bean cliente
	 * 
	 * @return direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Metodo set direccion Bean cliente
	 * 
	 * @param direccion
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Metodo get correo Bean cliente
	 * 
	 * @return correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Metodo set correo Bean cliente
	 * 
	 * @param correo
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Metodo get numero de cuenta Bean cliente
	 * 
	 * @return numero_cuenta
	 */
	public String getNumero_cuenta() {
		return numero_cuenta;
	}

	/**
	 * Metodo set numero de cuenta Bean cliente
	 * 
	 * @param numero_cuenta
	 */
	public void setNumero_cuenta(String numero_cuenta) {
		this.numero_cuenta = numero_cuenta;
	}

	/**
	 * Metodo get contasenia Bean cliente
	 * 
	 * @return contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * Metodo set contasenia Bean cliente
	 * 
	 * @param contrasenia
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * Metodo get ciudad Bean cliente
	 * 
	 * @return ciudad
	 */
	public Ciudad getCiudad() {
		return ciudad;
	}

	/**
	 * Metodo set ciudad Bean cliente
	 * 
	 * @param ciudad
	 */
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * Metodo get fecha nacimiento Bean cliente
	 * 
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Metodo set fecha nacimiento Bean cliente
	 * 
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Metodo get genero nacimiento Bean cliente
	 * 
	 * @return the genero
	 */
	public Genero getGenero() {
		return genero;
	}

	/**
	 * Metodo set genero nacimiento Bean cliente
	 * 
	 * @param genero the genero to set
	 */
	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	/**
	 * Metodo get telefono Bean cliente Se modifica para asignarles el primer numero
	 * del array de telefonos
	 * 
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Metodo set telefono Bean cliente
	 * 
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Metodo get lista telefonos Bean cliente
	 * 
	 * @return the telefonos
	 */
	public List<String> getTelefonos() {
		return telefonos;
	}

	/**
	 * Metodo set lista telefonos Bean cliente
	 * 
	 * @param telefonos the telefonos to set
	 */
	public void setTelefonos(List<String> telefonos) {
		this.telefonos = telefonos;
	}

	/**
	 * Metodo get lista clientes Bean cliente
	 * 
	 * @return the clientes
	 */
	public List<Cliente> getClientes() {
		return clientes;
	}

	/**
	 * Metodo set lista clientes Bean cliente
	 * 
	 * @param clientes the clientes to set
	 */
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	/**
	 * Metodo get cliente Bean cliente
	 * 
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Metodo set cliente Bean cliente
	 * 
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the usuario
	 */
	public Persona getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}

	/**
	 * Metodo get que permite obtener el primer telefono del array de telefonos
	 * 
	 * @return telefonoLista
	 */
	public String getTelefonoLista() {
		if (this.cliente!=null)
			telefonoLista = cliente.getTelefonos().get(0);
		
		return telefonoLista;
	}

	/**
	 * Metodo set que permite obtener el primer telefono del array de telefonos
	 * 
	 * @param telefonoLista the telefonoLista to set
	 */
	public void setTelefonoLista(String telefonoLista) {
		this.telefonoLista = telefonoLista;
	}
	
	

}
