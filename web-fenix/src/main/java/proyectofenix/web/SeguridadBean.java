package proyectofenix.web;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Empleado;
import proyectofenix.entidades.Persona;
import proyectofenix.negocio.BancoEJB;

@FacesConfig(version=Version.JSF_2_3)
@Named(value="seguridadBean")
@ApplicationScoped
public class SeguridadBean {
	
	private boolean autenticado;
	private Persona usuario;
	private boolean loginEmpleado;
	private boolean loginCliente;
	
	@EJB
	private BancoEJB bancoEJB;
	
	@PostConstruct
	private void inicializar() {
		usuario= new Persona();
		autenticado=false;
		loginCliente=false;
		loginEmpleado=false;
	}
	
	
	public String login() {
		if (usuario.getCedula() !=null) {
			Persona p = bancoEJB.buscarPersona(usuario.getCedula());
			
			if (p !=null) {
				if (p.getContrasenia().equals(usuario.getContrasenia())) {
					
					if (p.getClass()==Cliente.class) {
						this.usuario=p;
						loginCliente=true;
						autenticado=true;
						System.out.println("Usuario logueado:" + usuario.getCedula());
					}
					else if (p.getClass()==Empleado.class) {
						this.usuario=p;
						loginEmpleado=true;
						autenticado=true;
						System.out.println("Empleado logueado:" + usuario.getCedula());
					}
					
				}else {
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Datos incorrectos", "Los datos ingresados son incorrectos");
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				}
				
			}
		}
		return null;
	}
	
	/**
	 * permite cerrar la sesion del administrador
	 * @return pagina inicial
	 */
	public String cerrarSesion() {
		
		if( autenticado ) {
			autenticado = false;
			loginCliente=false;
			loginEmpleado=false;
			usuario = new Persona();
			return "/index?faces-redirect=true";
		}
		return null;
	}
	
	/**
	 * Metodo get autenticado Seguridad Bean
	 * @return the autenticado
	 */
	public boolean isAutenticado() {
		return autenticado;
	}
	
	/**
	 * Metodo set autenticado Seguridad Bean
	 * 
	 * @param autenticado the autenticado to set
	 */
	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}
	
	/**
	 * Metodo get usuario Seguridad Bean
	 * 
	 * @return the usuario
	 */
	public Persona getUsuario() {
		return usuario;
	}
	
	/**
	 * Metodo set usuario Seguridad Bean
	 * 
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}


	/**
	 * Metodo get login empleado Seguridad Bean
	 * 
	 * @return the loginEmpleado
	 */
	public boolean isLoginEmpleado() {
		return loginEmpleado;
	}


	/**
	 * Metodo set login empleado Seguridad Bean
	 * 
	 * @param loginEmpleado the loginEmpleado to set
	 */
	public void setLoginEmpleado(boolean loginEmpleado) {
		this.loginEmpleado = loginEmpleado;
	}


	/**
	 * Metodo get login cliente Seguridad Bean
	 * 
	 * @return the loginCliente
	 */
	public boolean isLoginCliente() {
		return loginCliente;
	}


	/**
	 * Metodo set login cliente Seguridad Bean
	 * 
	 * @param loginCliente the loginCliente to set
	 */
	public void setLoginCliente(boolean loginCliente) {
		this.loginCliente = loginCliente;
	}
	

}
