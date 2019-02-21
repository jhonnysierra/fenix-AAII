package proyectofenix.web;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;

import proyectofenix.entidades.Persona;
import proyectofenix.negocio.BancoEJB;

@FacesConfig(version=Version.JSF_2_3)
@Named(value="seguridadBean")
@ApplicationScoped
public class SeguridadBean {
	
	private boolean autenticado;
	private Persona usuario;
	
	@EJB
	private BancoEJB bancoEJB;
	
	@PostConstruct
	private void inicializar() {
		usuario= new Persona();
		autenticado=false;
	}
	
	
	public String login() {
		if (usuario.getCedula() !=null) {
			if (bancoEJB.buscarPersona(usuario.getCedula()) !=null) {
				autenticado=true;
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
			usuario = new Persona();
			return "/index";
		}
		return null;
	}
	
	/**
	 * @return the autenticado
	 */
	public boolean isAutenticado() {
		return autenticado;
	}
	/**
	 * @param autenticado the autenticado to set
	 */
	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
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
	

}
