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
 * Permite generar un
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
			cliente.setNoCuenta("0008475");
			
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

	/**
	 * @return the cedula
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * @param contrasenia the contrasenia to set
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * @return the ciudad
	 */
	public Ciudad getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}
	
	

}
