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
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.Prestamo;
import proyectofenix.entidades.TipoPrestamo;
import proyectofenix.negocio.BancoEJB;

/**
 * Bean para registrar un prestamo
 * 
 * @author JJ
 * @version 1.0
 */
@FacesConfig(version = Version.JSF_2_3)
@Named(value = "prestamoBean")
@ApplicationScoped
public class PrestamoBean implements Serializable {

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
	@Min(value = 1)
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
	@Min(value = 1)
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
	 * Formateador de fechas
	 */
	private Format formatoFecha;

	/**
	 * Fecha formteada para mostrar en el resumen
	 */
	private String fechaFormateada;

	/**
	 * Cantidad de pagos realizados
	 */
	private int cantidadPagos;

	/**
	 * Instancia de cliente Bean para asignar el prestamo al cliente seleccionado
	 */
	@Inject
	@ManagedProperty(value = "#{clienteBean1}")
	private ClienteBean clienteBean;

	@PostConstruct
	private void inicializar() {
		tiposPrestamo = bancoEJB.listarTodosTipoPrestamo();
		formatoFecha = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "COL"));
	}

	/**
	 * Metodo que permite registrar un prestamo
	 * 
	 * @return ruta a donde se redirige
	 */
	public String registrarPrestamo() {
		Calendar sumaFecha = Calendar.getInstance();

		sumaFecha.setTime(fechaInicio);
		sumaFecha.add(Calendar.MONTH, numeroCuotas);
		fechaFin = sumaFecha.getTime();

		try {
			bancoEJB.realizarPrestamo(clienteBean.getCliente().getCedula(), valorPrestamo, fechaInicio, fechaFin,
					numeroCuotas, tipoPrestamo.getId());
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de prestamo exitoso",
					"Registro prestamo exitoso");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			reiniciarVariables();
			return "/listaClientes";
		} catch (ExcepcionesFenix e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede realizar el prestamo",
					e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "";
		}

	}

	/**
	 * Metodo que permite reiniciar las variables del bean
	 */
	public void reiniciarVariables() {
		persona = null;
		valorPrestamo = 0;
		numeroCuotas = 0;
		fechaInicio = null;
		fechaFin = null;
		tipoPrestamo = null;
	}

	// Metodos Get y Set

	/**
	 * Metodo get id prestamo Bean
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo set id prestamo Bean
	 * 
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo get persona prestamo Bean
	 * 
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * Metodo set persona prestamo Bean
	 * 
	 * @param persona the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * Metodo get valor prestamo prestamo Bean
	 * 
	 * @return the valorPrestamo
	 */
	public double getValorPrestamo() {
		return valorPrestamo;
	}

	/**
	 * Metodo set valor prestamo prestamo Bean
	 * 
	 * @param valorPrestamo the valorPrestamo to set
	 */
	public void setValorPrestamo(double valorPrestamo) {
		this.valorPrestamo = valorPrestamo;
	}

	/**
	 * Metodo get fecha inicio prestamo Bean
	 * 
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Metodo set fecha inicio prestamo Bean
	 * 
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Metodo get fecha fin prestamo Bean
	 * 
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * Metodo set fecha fin prestamo Bean
	 * 
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Metodo get numero cuotas prestamo Bean
	 * 
	 * @return the numeroCuotas
	 */
	public int getNumeroCuotas() {
		return numeroCuotas;
	}

	/**
	 * Metodo set numero cuotas prestamo Bean
	 * 
	 * @param numeroCuotas the numeroCuotas to set
	 */
	public void setNumeroCuotas(int numeroCuotas) {
		this.numeroCuotas = numeroCuotas;
	}

	/**
	 * Metodo get tipo prestamo prestamo Bean
	 * 
	 * @return the tipoPrestamo
	 */
	public TipoPrestamo getTipoPrestamo() {
		return tipoPrestamo;
	}

	/**
	 * Metodo set tipo prestamo prestamo Bean
	 * 
	 * @param tipoPrestamo the tipoPrestamo to set
	 */
	public void setTipoPrestamo(TipoPrestamo tipoPrestamo) {
		this.tipoPrestamo = tipoPrestamo;
	}

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
	 * Metodo get tipo prestamo prestamo Bean
	 * 
	 * @return the tiposPrestamo
	 */
	public List<TipoPrestamo> getTiposPrestamo() {
		return tiposPrestamo;
	}

	/**
	 * Metodo set tipo prestamo prestamo Bean
	 * 
	 * @param tiposPrestamo the tiposPrestamo to set
	 */
	public void setTiposPrestamo(List<TipoPrestamo> tiposPrestamo) {
		this.tiposPrestamo = tiposPrestamo;
	}

	/**
	 * Metodo get fecha formateada prestamo Bean
	 * 
	 * @return the fechaFormateada
	 */
	public String getFechaFormateada() {
		return fechaFormateada;
	}

	/**
	 * Metodo set fecha formateada prestamo Bean
	 * 
	 * @param fechaFormateada the fechaFormateada to set
	 */
	public void setFechaFormateada(String fechaFormateada) {
		this.fechaFormateada = fechaFormateada;
	}

	/**
	 * Metodo get clienteBean Cliente Bean
	 * 
	 * @return the clienteBean
	 */
	public ClienteBean getClienteBean() {
		return clienteBean;
	}

	/**
	 * Metodo set clienteBean Cliente Bean
	 * 
	 * @param clienteBean the clienteBean to set
	 */
	public void setClienteBean(ClienteBean clienteBean) {
		this.clienteBean = clienteBean;
	}

	/**
	 * Metodo get lista de prestamos. Actualiza los prestamos con el metodo EJB
	 * 
	 * @return the prestamos
	 */
	public List<Prestamo> getPrestamos() {
		this.prestamos = bancoEJB.listarAllPrestamos();

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

}
