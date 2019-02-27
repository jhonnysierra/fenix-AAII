package proyectofenix.web;

import java.io.Serializable;
import java.util.Date;
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
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Prestamo;
import proyectofenix.negocio.ClienteEJB;

/**
 * Bean para registrar un pago de cuota
 * 
 * @author JJ
 * @version 1.0
 */
@FacesConfig(version = Version.JSF_2_3)
@Named(value = "pagoCuotaClienteBean")
@ApplicationScoped
public class PagoCuotaClienteBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * EJB para realizar conexion con la capa de negocio
	 */
	@EJB
	private ClienteEJB clienteEJB;
	
	/**
	 * Valor del pago
	 */
	@Min(value = 1)
	private double valorPago;
	

	/**
	 * Fecha en la que se realiza el pago
	 */
	private Date fechaPago;

	/**
	 * Prestamo 
	 */
	private Prestamo prestamo;

	/**
	 * Instancia de prestamo Bean para asiganar el pago al prestamo seleccionado
	 */
	@Inject
	@ManagedProperty(value = "#{prestamoClienteBean}")
	private PrestamoClienteBean prestamoClienteBean;
	
	@PostConstruct
	private void inicializar() {
		
	}

	/**
	 * Metodo que permite registrar un pago de una cuota
	 * 
	 * @return ruta a donde se redirige
	 */
	public String registrarPagoCuota() {
		Pago pago = new Pago();
		
		try {
			pago.setId(clienteEJB.consecutivoPago());
			pago.setFecha(fechaPago);
			pago.setPrestamo(prestamoClienteBean.getPrestamo());
			pago.setValor(valorPago);
			
			clienteEJB.registrarPagoCuota(pago);
			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Pago", "Se registró el pago");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			reiniciarVariables();
			return "/listaPrestamosCliente";
		} catch (ExcepcionesFenix e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Id pago", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "";
		}
		
	}

	/**
	 * Metodo que permite reiniciar las variables del bean
	 */
	public void reiniciarVariables() {
		prestamo = null;
		valorPago = 0;
		fechaPago=null;
	}

	/**
	 * Metodo get valor del pago Pago Cuota Bean
	 * @return the valorPago
	 */
	public double getValorPago() {
		return valorPago;
	}

	/**
	 * Metodo set valor del pago Pago Cuota Bean
	 * @param valorPago the valorPago to set
	 */
	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	/**
	 * Metodo get fecha pago del pago Pago Cuota Bean
	 * @return the fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}

	/**
	 * Metodo set fecha pago del pago Pago Cuota Bean
	 * 
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * Metodo get prestamo del pago Pago Cuota Bean. Se asigna el seteado por el prestamo seleccinado
	 * @return the prestamo
	 */
	public Prestamo getPrestamo() {
		return prestamo;
	}

	/**
	 * Metodo set prestamo del pago Pago Cuota Bean. Se asigna el seteado por el prestamo seleccinado
	 * @param prestamo the prestamo to set
	 */
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	/**
	 * Metodo get prestamo cliente inyectado
	 * @return the prestamoClienteBean
	 */
	public PrestamoClienteBean getPrestamoClienteBean() {
		return prestamoClienteBean;
	}

	/**
	 * Metodo set prestamo cliente inyectado
	 * 
	 * @param prestamoClienteBean the prestamoClienteBean to set
	 */
	public void setPrestamoClienteBean(PrestamoClienteBean prestamoClienteBean) {
		this.prestamoClienteBean = prestamoClienteBean;
	}



}
