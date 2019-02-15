package proyectofenix.web;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import proyecto.fenix.excepciones.ElementoRepetidoExcepcion;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Ciudad;
import proyectofenix.entidades.Cliente;
import proyectofenix.negocio.BancoEJB;

/**
 * Permite generar un registro de cliente
 * 
 * @author JHONNY
 *
 */
@Named
@ApplicationScoped
public class ClienteBean {
	
	@EJB
	private BancoEJB administradorEJB;

	public boolean agregarCliente() {

		try {
			Cliente cliente = new Cliente();

			cliente.setCedula(cedula);
			cliente.setNombres(nombres);
			cliente.setApellidos(apellidos);
			cliente.setContrasenia(contrasenia);
			cliente.setCorreo(correo);
			cliente.setEstado(estado);
			cliente.setDireccion(direccion);
			cliente.setCiudad(null);
			cliente.setNoCuenta(numero_cuenta);
			
			administradorEJB.agregarCliente(cliente);
			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso", "Registro exitoso");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return true;

		} catch (ElementoRepetidoExcepcion | RuntimeException e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e. getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return false;
		}
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
	 * Numero de cuenta de un cliente
	 */
	private String numero_cuenta;

	/**
	 * Estado de una Persona en el sistema 1 ACTIVO, 0 INACTIVO
	 */
	private String estado;

	/**
	 * Contrasenia de una Persona para ingresar al sistema
	 */
	private String contrasenia;
	
	/**
	 * Ciudad de una persona
	 */
	private Ciudad ciudad;

	
	// GET y SET
	
	/**
	 * Metodo get cedula Bean cliente
	 * @return cedula
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * Metodo set cedula Bean cliente
	 * @param cedula
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	/**
	 * Metodo get Nombres Bean cliente
	 * @return nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * Metodo set Nombres Bean cliente
	 * @param nombres
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * Metodo get apellidos Bean cliente
	 * @return apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Metodo set apellidos Bean cliente
	 * @param apellidos
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Metodo get direccion Bean cliente
	 * @return direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Metodo set direccion Bean cliente
	 * @param direccion
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Metodo get correo Bean cliente
	 * @return correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Metodo set correo Bean clienteo
	 * @param correo
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	/**
	 * Metodo get numero de cuenta Bean cliente
	 * @return numero_cuenta
	 */
	public String getNumero_cuenta() {
		return numero_cuenta;
	}

	/**
	 * Metodo set numero de cuenta Bean cliente
	 * @param numero_cuenta
	 */
	public void setNumero_cuenta(String numero_cuenta) {
		this.numero_cuenta = numero_cuenta;
	}

	/**
	 * Metodo get estado Bean cliente
	 * @return estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Metodo set estado Bean cliente
	 * @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Metodo get contasenia Bean cliente
	 * @return contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * Metodo set contasenia Bean cliente
	 * @param contrasenia
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * Metodo get ciudad Bean cliente
	 * @return ciudad
	 */
	public Ciudad getCiudad() {
		return ciudad;
	}

	/**
	 * Metodo set ciudad Bean cliente
	 * @param ciudad
	 */
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}
	
	

}
