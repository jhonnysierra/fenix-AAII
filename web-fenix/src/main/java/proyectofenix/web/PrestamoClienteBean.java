package proyectofenix.web;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.ManagedProperty;
import javax.faces.annotation.FacesConfig.Version;
import javax.inject.Inject;
import javax.inject.Named;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Prestamo;
import proyectofenix.negocio.BancoEJB;
import proyectofenix.negocio.ClienteEJB;

/**
 * Bean para registrar un prestamo
 * 
 * @author JJ
 * @version 1.0
 */
@FacesConfig(version = Version.JSF_2_3)
@Named(value = "prestamoClienteBean")
@ApplicationScoped
public class PrestamoClienteBean implements Serializable {

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
	 * EJB para realizar conexion con la capa de negocio
	 */
	@EJB
	private ClienteEJB clienteEJB;

	/**
	 * Lista de prestamos
	 */
	private List<Prestamo> prestamos;

	/**
	 * Prestamo asociado
	 */
	private Prestamo prestamo;

	/**
	 * Lista de pagos del prestamo
	 */
	private List<Pago> pagos;

	/**
	 * Cantidad de pagos realizados
	 */
	private int cantidadPagos;

	/**
	 * El inyectado desde el login Seguridad Bean
	 */
	@Inject
	@ManagedProperty(value = "#{seguridadBean}")
	private SeguridadBean seguridadBean;

	@PostConstruct
	private void inicializar() {
		new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "COL"));
	}



	/**
	 * Metodo que permite reiniciar las variables del bean
	 */
	public void reiniciarVariables() {

	}

	// Metodos Get y Set

	/**
	 * Metodo get lista de pagos prestamo Bean
	 * 
	 * @return the pagos
	 */
	public List<Pago> getPagos() {
		if (this.prestamo != null) {
			pagos = bancoEJB.listarPagosPrestamo(this.prestamo.getId());
		}

		return pagos;
	}

	/**
	 * Metodo set lista de pagos prestamo Bean
	 * 
	 * @param pagos the pagos to set
	 */
	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	/**
	 * Metodo get lista de prestamos. Actualiza los prestamos con el metodo EJB
	 * 
	 * @return the prestamos
	 */
	public List<Prestamo> getPrestamos() {
		this.prestamos = clienteEJB.listarPrestamosPersona(seguridadBean.getUsuario().getCedula());

		return prestamos;
	}

	/**
	 * Metodo get lista de prestamos
	 * 
	 * @param prestamos the prestamos to set
	 */
	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}

	/**
	 * Metodo get prestamo Prestamo Bean
	 * 
	 * @return the prestamo
	 */
	public Prestamo getPrestamo() {
		return prestamo;
	}

	/**
	 * Metodo set prestamo Prestamo Bean
	 * 
	 * @param prestamo the prestamo to set
	 */
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	/**
	 * Metodo get cantidad de pagos. Se actualiza dependiendo del prestamo actual
	 * 
	 * @return the cantidadPagos
	 */
	public int getCantidadPagos() {
		if (this.prestamo != null) {
			this.prestamo.setPagos(getPagos());
			this.cantidadPagos = this.prestamo.getPagos().size();
		}
		return cantidadPagos;
	}

	/**
	 * Metodo set cantidad de pagos. Se actualiza dependiendo del prestamo actual
	 * 
	 * @param cantidadPagos the cantidadPagos to set
	 */
	public void setCantidadPagos(int cantidadPagos) {
		this.cantidadPagos = cantidadPagos;
	}



	/**
	 *  Metodo get seguridad Bean inyectado desde el bean seguridad
	 *  
	 * @return the seguridadBean
	 */
	public SeguridadBean getSeguridadBean() {
		return seguridadBean;
	}



	/**
	 *  Metodo set seguridad Bean inyectado desde el bean seguridad
	 *  
	 * @param seguridadBean the seguridadBean to set
	 */
	public void setSeguridadBean(SeguridadBean seguridadBean) {
		this.seguridadBean = seguridadBean;
	}

	
}
