package proyectofenix.web;

import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Named;

import proyectofenix.negocio.BancoEJB;

/**
 * Bean  para registrar una pregunta asesoria
 * @author JJ
 * @version 1.0
 */
@FacesConfig(version=Version.JSF_2_3)
@Named(value="preguntaBean")
@ApplicationScoped
public class PreguntaBean {
	
	@EJB
	private BancoEJB administradorEJB;
	
	/**
	 * id de la asesoria
	 */
	private int id;
	
	/**
	 * cedula cliente 
	 */
	private String cedula;
	
	/**
	 * Fecha de la asesoria
	 */
	private Date fecha;
	
	
	
	
	

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

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
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	

}
