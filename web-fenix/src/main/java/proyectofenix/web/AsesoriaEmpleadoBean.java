package proyectofenix.web;

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

import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Asesoria;
import proyectofenix.entidades.Empleado;
import proyectofenix.entidades.TipoAsesoria;
import proyectofenix.negocio.BancoEJB;
import proyectofenix.negocio.ClienteEJB;

/**
 * Bean para registrar una asesoria
 * 
 * @author JJ
 * @version 1.0
 */
@FacesConfig(version = Version.JSF_2_3)
@Named(value = "asesoriaEmpleadoBean")
@ApplicationScoped
public class AsesoriaEmpleadoBean {

	/**
	 * EJB para realizar conexion con la capa de negocio
	 */
	@EJB
	private ClienteEJB clienteEJB;

	/**
	 * id de la asesoria
	 */
	private int id;

	/**
	 * cedula cliente
	 */
	private String cedulaCliente;

	/**
	 * cedula empleado
	 */
	private String cedulaEmpleado;

	/**
	 * id del tipo de asesoria solicitada
	 */
	private int tipo_asesoria;

	/**
	 * Fecha de la asesoria
	 */
	private Date fecha;

	/**
	 * Hora de inicio de la asesoria
	 */
	private Date horaInicio;

	/**
	 * Hora de finalizacion de la asesoria
	 */
	private Date horaFin;

	/**
	 * Formateador de fechas
	 */
	private Format formatoFecha;

	/**
	 * Fecha formteada para mostrar en el resumen
	 */
	private String fechaFormateada;

	/**
	 * Conexion a capa de negocio
	 */
	@EJB
	private BancoEJB bancoEJB;

	/**
	 * El inyectado desde el login Seguridad Bean
	 */
	@Inject
	@ManagedProperty(value = "#{seguridadBean}")
	private SeguridadBean seguridadBean;

	/**
	 * Lista de asesorias del empleado
	 */
	private List<Asesoria> asesorias;

	/**
	 * Asesoria
	 */
	private Asesoria asesoria;

	/**
	 * Formateador de hora
	 */
	private Format formatoHora;

	@PostConstruct
	private void inicializar() {
		formatoFecha = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "COL"));
		formatoHora = new SimpleDateFormat("HH:mm:ss", new Locale("es", "COL"));
	}

	/**
	 * Permite registrar una asesoria en la bd
	 * 
	 * @return ruta que muestra la informacion detallada de la asesoria
	 */
	public String crearAsesoria() {

		// Se comenta para que no registre

		// Se formatea la fecha para fecha y hora colombiana
		fechaFormateada = formatoFecha.format(fecha);
		cedulaCliente = seguridadBean.getUsuario().getCedula();

		// No retorna cadena sino se realiza la asesoria
		return null;

	}

	public String atenderAsesoria() {

		Date horaFin = new Date();

		try {
			asesoria.setHoraFin(horaFin);
			bancoEJB.atenderAsesoria(asesoria);


			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de prestamo exitoso",
					"Registro prestamo exitoso");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "/listaAsesoriasEmpleado.xhtml?faces-redirect=true";
		} catch (ExcepcionesFenix e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede realizar el prestamo",
					e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "/listaAsesoriasEmpleado.xhtml?faces-redirect=true";
		}
	}

	public void reiniciarVariables() {
		id = 0;
		cedulaCliente = "";
		cedulaEmpleado = "";
		fecha = null;
		horaInicio = null;
		horaFin = null;
	}
	// ---------------Metodos Get y Set--------------------

	/**
	 * Metodo get id Asesoria Bean
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo set id Asesoria Bean
	 * 
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo get cedula cliente Asesoria Bean
	 * 
	 * @return cedulaCliente
	 */
	public String getCedulaCliente() {
		return cedulaCliente;
	}

	/**
	 * Metodo set cedula cliente Asesoria Bean
	 * 
	 * @param cedulaCliente the cedulaCliente to set
	 */
	public void setCedulaCliente(String cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}

	/**
	 * Metodo get cedula empleado Asesoria Bean
	 * 
	 * @return the cedulaEmpleado
	 */
	public String getCedulaEmpleado() {
		return cedulaEmpleado;
	}

	/**
	 * Metodo set cedula empleado Asesoria Bean
	 * 
	 * @param cedulaEmpleado the cedulaEmpleado to set
	 */
	public void setCedulaEmpleado(String cedulaEmpleado) {
		this.cedulaEmpleado = cedulaEmpleado;
	}

	/**
	 * Metodo get tipo asesoria Asesoria Bean
	 * 
	 * @return tipo_asesoria
	 */
	public int getTipo_asesoria() {
		return tipo_asesoria;
	}

	/**
	 * Metodo set tipo asesoria Asesoria Bean
	 * 
	 * @param tipo_asesoria the tipo_asesoria to set
	 */
	public void setTipo_asesoria(int tipo_asesoria) {
		this.tipo_asesoria = tipo_asesoria;
	}

	/**
	 * Metodo get fecha asesoria Asesoria Bean
	 * 
	 * @return fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Metodo set fecha asesoria Asesoria Bean
	 * 
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Metodo get hora inicio asesoria Asesoria Bean
	 * 
	 * @return horaInicio
	 */
	public Date getHoraInicio() {
		return horaInicio;
	}

	/**
	 * Metodo set hora inicio asesoria Asesoria Bean
	 * 
	 * @param horaInicio the horaInicio to set
	 */
	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	/**
	 * Metodo get hora fin asesoria Asesoria Bean
	 * 
	 * @return the horaFin
	 */
	public Date getHoraFin() {
		return horaFin;
	}

	/**
	 * Metodo set hora fin asesoria Asesoria Bean
	 * 
	 * @param horaFin the horaFin to set
	 */
	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	/**
	 * Metodo get fecha formateada Asesoria Bean
	 * 
	 * @return the fechaFormateada
	 */
	public String getFechaFormateada() {
		return fechaFormateada;
	}

	/**
	 * Metodo set fecha formateada Asesoria Bean
	 * 
	 * @param fechaFormateada the fechaFormateada to set
	 */
	public void setFechaFormateada(String fechaFormateada) {
		this.fechaFormateada = fechaFormateada;
	}

	/**
	 * Metodo get seguridad Bean inyectado desde el bean seguridad
	 * 
	 * @return the seguridadBean
	 */
	public SeguridadBean getSeguridadBean() {
		return seguridadBean;
	}

	/**
	 * Metodo set seguridad Bean inyectado desde el bean seguridad
	 * 
	 * @param seguridadBean the seguridadBean to set
	 */
	public void setSeguridadBean(SeguridadBean seguridadBean) {
		this.seguridadBean = seguridadBean;
	}

	/**
	 * Metodo get lista asesorias
	 * 
	 * @return the asesorias
	 */
	public List<Asesoria> getAsesorias() {
		asesorias = bancoEJB.listaAsesoriaEmpleado(seguridadBean.getUsuario().getCedula());

		return asesorias;
	}

	/**
	 * @param asesorias the asesorias to set
	 */
	public void setAsesorias(List<Asesoria> asesorias) {
		this.asesorias = asesorias;
	}

	/**
	 * @return the asesoria
	 */
	public Asesoria getAsesoria() {
		return asesoria;
	}

	/**
	 * @param asesoria the asesoria to set
	 */
	public void setAsesoria(Asesoria asesoria) {
		this.asesoria = asesoria;
	}

}
