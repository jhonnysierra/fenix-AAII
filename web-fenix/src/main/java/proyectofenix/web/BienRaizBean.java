package proyectofenix.web;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.ManagedProperty;
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Min;

import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.BienRaiz;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.Prestamo;
import proyectofenix.entidades.TipoPrestamo;
import proyectofenix.negocio.BancoEJB;
import proyectofenix.negocio.ClienteEJB;

/**
 * Bean para registrar un pago de cuota
 * 
 * @author JJ
 * @version 1.0
 */
@FacesConfig(version = Version.JSF_2_3)
@Named(value = "bienRaizBean")
@ApplicationScoped
public class BienRaizBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * EJB para realizar conexion con la capa de negocio
	 */
	@EJB
	private BancoEJB bancoEJB;
	
	/**
	 * Identificador del bien raiz
	 */
	private String id;
	
	/**
	 * Valor del pago
	 */
	@Min(value = 1)
	private double avaluo;
	
	/**
	 * Direccion inmueble
	 */
	private String direccion;
	
	/**
	 * Instancia de cliente Bean para asignar el prestamo al cliente seleccionado
	 */
	@Inject
	@ManagedProperty(value = "#{clienteBean1}")
	private ClienteBean clienteBean;
	
	@PostConstruct
	private void inicializar() {
		
	}

	/**
	 * Metodo que permite registrar un bien raiz
	 * 
	 * @return ruta a donde se redirige
	 */
	public String registrarBienRaiz() {
		BienRaiz bienRaiz = new BienRaiz();
		
		
		try {
			bienRaiz.setId(id);
			bienRaiz.setAvaluo(avaluo);
			bienRaiz.setDireccion(direccion);
			bienRaiz.setCiudad(null);
			bienRaiz.setPersona(clienteBean.getCliente());

			
			bancoEJB.agregarBienRaiz(bienRaiz);
			// Por que no se actualiza la entidad sin setearla
			//clienteBean.getCliente().setBienRaiz(bancoEJB.agregarBienRaiz(bienRaiz));
			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Exitoso", "Se registró el bien raíz");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			reiniciarVariables();
			return "/listaClientes";
		} catch (ExcepcionesFenix e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "";
		}
		
	}

	/**
	 * Metodo que permite reiniciar las variables del bean
	 */
	public void reiniciarVariables() {
		id="";
		avaluo=0;
		direccion="";
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the avaluo
	 */
	public double getAvaluo() {
		return avaluo;
	}

	/**
	 * @param avaluo the avaluo to set
	 */
	public void setAvaluo(double avaluo) {
		this.avaluo = avaluo;
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
	 * @return the clienteBean
	 */
	public ClienteBean getClienteBean() {
		return clienteBean;
	}

	/**
	 * @param clienteBean the clienteBean to set
	 */
	public void setClienteBean(ClienteBean clienteBean) {
		this.clienteBean = clienteBean;
	}

	
	

}
