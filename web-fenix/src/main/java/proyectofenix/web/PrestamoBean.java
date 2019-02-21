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
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.Min;

import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.Prestamo;
import proyectofenix.entidades.TipoPrestamo;
import proyectofenix.negocio.BancoEJB;
import proyectofenix.negocio.ClienteEJB;

/**
 * Bean para registrar un prestamo
 * 
 * @author JJ
 * @version 1.0
 */
@FacesConfig(version = Version.JSF_2_3)
@Named(value = "prestamoBean")
@ApplicationScoped
public class PrestamoBean implements Serializable{
	
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
	 * id de la asesoria
	 */
	private int id;
	
	/**
	 * Persona que realiza el prestamo
	 */
	private Persona persona;
	
	/**
	 * Valor del prestamo
	 */
	@Min(value=1)
	private double valorPrestamo;
	
	/**
	 * Fecha en la que se realiza el prestamo
	 */
	private Date fechaInicio;
	
	/**
	 * Fecha en la que se vence el prestamo
	 */
	private Date fechaFin;
	
	/**
	 * Numero de cuotas del prestamo
	 */
	@Min(value=1)
	private int numeroCuotas;
	
	/**
	 * Lista de los tipos de prestamo
	 */
	private List<TipoPrestamo> tiposPrestamo;
	
	/**
	 * Tipo de prestamo del prestamo
	 */
	private TipoPrestamo tipoPrestamo;
	
	/**
	 * Lista de pagos del prestamo
	 */
	private List<Pago> pagos;
	
	/**
	 * Formateador de fechas
	 */
	private Format formatoFecha;
	
	/**
	 * Fecha formteada para mostrar en el resumen
	 */
	private String fechaFormateada;
	
	
	
	
	@PostConstruct
	private void inicializar() {
		tiposPrestamo = bancoEJB.listarTodosTipoPrestamo();
		formatoFecha = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy",new Locale("es","COL"));
		valorPrestamo=0;
		
	}
	
	/**
	 * Metodo que permite registrar un prestamo
	 * 
	 * @return ruta a donde se redirige
	 */
	public String registrarPrestamo() {
		String cedula="1"; 
		Calendar sumaFecha = Calendar.getInstance();

		sumaFecha.setTime(fechaInicio);
		sumaFecha.add(Calendar.MONTH, numeroCuotas);
		fechaFin = sumaFecha.getTime();

		
		try {
			bancoEJB.realizarPrestamo(cedula, valorPrestamo, fechaInicio, fechaFin, numeroCuotas, tipoPrestamo.getId());
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de prestamo exitoso",
					"Registro prestamo exitoso");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			id=0;
			persona=null;
			valorPrestamo=0;
			fechaInicio=null;
			fechaFin=null;
			tipoPrestamo=null;
			return "";
		} catch (ExcepcionesFenix e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			e.printStackTrace();
			return "";
		}
		
	}
	
	
	// Metodos Get y Set
	

	/**
	 * Metodo get id prestamo Bean
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo set id prestamo Bean
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo get persona prestamo Bean
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * Metodo set persona prestamo Bean
	 * @param persona the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * Metodo get valor prestamo prestamo Bean
	 * @return the valorPrestamo
	 */
	public double getValorPrestamo() {
		return valorPrestamo;
	}

	/**
	 * Metodo set valor prestamo prestamo Bean
	 * @param valorPrestamo the valorPrestamo to set
	 */
	public void setValorPrestamo(double valorPrestamo) {
		this.valorPrestamo = valorPrestamo;
	}

	/**
	 * Metodo get fecha inicio prestamo Bean
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Metodo set fecha inicio prestamo Bean
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Metodo get fecha fin prestamo Bean
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * Metodo set fecha fin prestamo Bean
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Metodo get numero cuotas prestamo Bean
	 * @return the numeroCuotas
	 */
	public int getNumeroCuotas() {
		return numeroCuotas;
	}

	/**
	 * Metodo set numero cuotas prestamo Bean
	 * @param numeroCuotas the numeroCuotas to set
	 */
	public void setNumeroCuotas(int numeroCuotas) {
		this.numeroCuotas = numeroCuotas;
	}

	/**
	 * Metodo get tipo prestamo prestamo Bean
	 * @return the tipoPrestamo
	 */
	public TipoPrestamo getTipoPrestamo() {
		return tipoPrestamo;
	}

	/**
	 * Metodo set tipo prestamo prestamo Bean
	 * @param tipoPrestamo the tipoPrestamo to set
	 */
	public void setTipoPrestamo(TipoPrestamo tipoPrestamo) {
		this.tipoPrestamo = tipoPrestamo;
	}

	/**
	 * Metodo get lista de pagos prestamo Bean
	 * @return the pagos
	 */
	public List<Pago> getPagos() {
		return pagos;
	}

	/**
	 * Metodo set lista de pagos prestamo Bean
	 * @param pagos the pagos to set
	 */
	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	/**
	 * Metodo get tipo prestamo prestamo Bean
	 * @return the tiposPrestamo
	 */
	public List<TipoPrestamo> getTiposPrestamo() {
		return tiposPrestamo;
	}

	/**
	 * Metodo set tipo prestamo prestamo Bean
	 * @param tiposPrestamo the tiposPrestamo to set
	 */
	public void setTiposPrestamo(List<TipoPrestamo> tiposPrestamo) {
		this.tiposPrestamo = tiposPrestamo;
	}

	/**
	 * Metodo get fecha formateada prestamo Bean
	 * @return the fechaFormateada
	 */
	public String getFechaFormateada() {
		return fechaFormateada;
	}

	/**
	 * Metodo set fecha formateada prestamo Bean
	 * @param fechaFormateada the fechaFormateada to set
	 */
	public void setFechaFormateada(String fechaFormateada) {
		this.fechaFormateada = fechaFormateada;
	}
	
	

}
