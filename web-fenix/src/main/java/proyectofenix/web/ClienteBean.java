package proyectofenix.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

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
public class ClienteBean {

	@EJB
	private BancoEJB administradorEJB;

	@EJB
	private ClienteEJB clienteEJB;

	public ClienteBean() {
		telefonos = new ArrayList<>();
	}

	@PostConstruct
	private void inicializar() {

	}


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
	 * Metodo que permite registrar un cliente
	 *  
	 * @return true si registra el cliente false si no 
	 */
	public boolean agregarCliente() {
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

			clienteEJB.agregarCliente(cliente);

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso",
					"Registro exitoso");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return true;

		} catch (ElementoRepetidoExcepcion | RuntimeException e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return false;
		}
	}
	
	/**
	 * Metodo que permite modificar la informacion de un cliente
	 * 
	 * @return true si se modifico o false si no 
	 */
	public boolean modificarCliente() {
		
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
			
			clienteEJB.modificarCliente(cliente);
			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modificó el cliente",
					"Modificar exitoso");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return true;
			
		} catch (ExcepcionesFenix e) {
			e.printStackTrace();
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return false;
		}
	}
	
	public boolean eliminarCliente() {
		
		try {
			// Cambiar el parametro de la cedula de la clase a uno enviado por el metodo si es necesario
			clienteEJB.eliminarCliente(cedula);
			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se eliminó el cliente",
					"Eliminar cliente exitoso");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return true;
			
		} catch (ExcepcionesFenix e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return false;
		}
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
	 * Metodo set genero  nacimiento Bean cliente
	 * @param genero the genero to set
	 */
	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	/**
	 * Metodo get telefono nacimiento Bean cliente
	 * 
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Metodo set telefono nacimiento Bean cliente
	 * 
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the telefonos
	 */
	public List<String> getTelefonos() {
		return telefonos;
	}

	/**
	 * @param telefonos the telefonos to set
	 */
	public void setTelefonos(List<String> telefonos) {
		this.telefonos = telefonos;
	}
}
