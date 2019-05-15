package proyectofenix.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase encargada de representar la informacion de la asesoria a un cliente
 * 
 * @author JJJ
 * @version 1.0 28-agosto-2018
 *
 */

@Entity
@NamedQueries({ @NamedQuery(name = Asesoria.OBTENER_CONSECUTIVO_ASESORIA, query = "select MAX(a.id) from Asesoria a"),
		@NamedQuery(name = Asesoria.OBTENER_TOTAL_ASESORIAS_EMPLEADO, query = "select new proyectofenix.DTO.ConsultaAtencionEmpleadoDTO(a.empleado.cedula,COUNT(a.empleado.cedula)) from Asesoria a where a.horaInicio!=a.horaFin GROUP BY a.empleado.cedula"),
		@NamedQuery(name = Asesoria.OBTENER_LISTA_ASESORIAS_EMPLEADO, query = "select a from Asesoria a where a.empleado.cedula=:cedula"),
		@NamedQuery(name = Asesoria.OBTENER_LISTA_ASESORIAS_CLIENTE, query = "select a from Asesoria a where a.cliente.cedula=:cedula") })
public class Asesoria implements Serializable {

	
	/**
	 * Permite obtener la lista de asesorias de un cliente
	 */
	public static final String OBTENER_LISTA_ASESORIAS_CLIENTE = "ListaAsesoriasCliente";
	
	/**
	 * Permite obtener la lista de asesorias que tiene un empleado
	 */
	public static final String OBTENER_LISTA_ASESORIAS_EMPLEADO = "ListaAsesoriasEmpleado";

	/**
	 * Permite obtener el maximo id de una asesoria y sumarle 1 para generar el
	 * consecutivo
	 */
	public static final String OBTENER_CONSECUTIVO_ASESORIA = "ConsecutivoIdAsesoria";

	/**
	 * Permite obtener el numero de asesorias atendidas por cada Empleado
	 */
	public static final String OBTENER_TOTAL_ASESORIAS_EMPLEADO = "TotalAsesoriasEmpleado";

	/**
	 * serialVersionUID clase Asesoria
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de una asesoria
	 */
	@Id
	private int id;

	/**
	 * Tipo de asesoria
	 */
	@ManyToOne
	private TipoAsesoria tipoasesoria;

	/**
	 * Empleado que realiza la asesoria
	 */
	@ManyToOne
	private Empleado empleado;

	/**
	 * Cliente que recibe la asesoria
	 */
	@ManyToOne
	private Cliente cliente;

	/**
	 * Hora de inicio de la asesoria
	 */
	@Temporal(TemporalType.TIME)
	private Date horaInicio;

	/**
	 * Hora de fin de la asesoria
	 */
	@Temporal(TemporalType.TIME)
	private Date horaFin;

	/**
	 * Fecha en la que se realiza la asesoria
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	/**
	 * METODO CONSTRUCTOR CLASE Asesoria
	 */
	public Asesoria() {
		super();
	}

	/**
	 * Metodo get id clase Asesoria
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo set id clase Asesoria
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo get tipoasesoria clase Asesoria
	 * 
	 * @return tipoasesoria
	 */
	public TipoAsesoria getTipoasesoria() {
		return tipoasesoria;
	}

	/**
	 * Metodo set tipoasesoria clase Asesoria
	 * 
	 * @param tipoasesoria
	 */
	public void setTipoasesoria(TipoAsesoria tipoasesoria) {
		this.tipoasesoria = tipoasesoria;
	}

	/**
	 * Metodo get empleado clase Asesoria
	 * 
	 * @return empleado
	 */
	public Empleado getEmpleado() {
		return empleado;
	}

	/**
	 * Metodo set empleado clase Asesoria
	 * 
	 * @param empleado
	 */
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	/**
	 * Metodo get cliente clase Asesoria
	 * 
	 * @return cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Metodo set cliente clase Asesoria
	 * 
	 * @param cliente
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Metodo get horaInicio clase Asesoria
	 * 
	 * @return horaInicio
	 */
	public Date getHoraInicio() {
		return horaInicio;
	}

	/**
	 * Metodo set horaInicio clase Asesoria
	 * 
	 * @param horaInicio
	 */
	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	/**
	 * Metodo get horaFin clase Asesoria
	 * 
	 * @return horaFin
	 */
	public Date getHoraFin() {
		return horaFin;
	}

	/**
	 * Metodo set horaFin clase Asesoria
	 * 
	 * @param horaFin
	 */
	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	/**
	 * Metodo get fecha clase Asesoria
	 * 
	 * @return fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Metodo set fecha clase Asesoria
	 * 
	 * @param fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Metodo get serialversionuid clase Asesoria
	 * 
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * Metodo hashcode clase Asesoria
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((empleado == null) ? 0 : empleado.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((horaFin == null) ? 0 : horaFin.hashCode());
		result = prime * result + ((horaInicio == null) ? 0 : horaInicio.hashCode());
		result = prime * result + id;
		result = prime * result + ((tipoasesoria == null) ? 0 : tipoasesoria.hashCode());
		return result;
	}

	/*
	 * Metodo hashcode clase Asesoria
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asesoria other = (Asesoria) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (empleado == null) {
			if (other.empleado != null)
				return false;
		} else if (!empleado.equals(other.empleado))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (horaFin == null) {
			if (other.horaFin != null)
				return false;
		} else if (!horaFin.equals(other.horaFin))
			return false;
		if (horaInicio == null) {
			if (other.horaInicio != null)
				return false;
		} else if (!horaInicio.equals(other.horaInicio))
			return false;
		if (id != other.id)
			return false;
		if (tipoasesoria == null) {
			if (other.tipoasesoria != null)
				return false;
		} else if (!tipoasesoria.equals(other.tipoasesoria))
			return false;
		return true;
	}

}
