package proyectofenix.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Clase encargada de representar la informacion de un Prestamo
 * 
 * @author JJJ
 * @version 1.0 28-agosto-2018
 *
 */

@Entity
@NamedQueries({
		@NamedQuery(name = Prestamo.OBTENER_CUOTAS_PRESTAMO, query = "select pagos from Prestamo p,IN(p.pagos) pagos where p.id=:id"),
		@NamedQuery(name = Prestamo.OBTENER_TIPO_PRESTAMO, query = "select p.tipoPrestamo from Prestamo p where p.tipoPrestamo=:tipoPrestamo"),
		@NamedQuery(name = Prestamo.OBTENER_PRESTAMOS, query = "select DISTINCT p.persona from Prestamo p"),
		@NamedQuery(name = Prestamo.OBTENER_CAMPOS_PRESTAMO, query = "select p.id,p.persona.cedula,p.persona.correo,pagos from Prestamo p,IN(p.pagos) pagos where p.fechaInicio=:fechaInicio"),
		@NamedQuery(name = Prestamo.OBTENER_CAMPOS_PRESTAMO_DTO, query = "select new proyectofenix.DTO.consulta10DTO(p.id,p.persona.cedula,p.persona.correo,pagos) from Prestamo p,IN(p.pagos) pagos where p.fechaInicio=:fechaInicio"),
		@NamedQuery(name = Prestamo.OBTENER_TOTAL_PRESTAMOS, query = "select COUNT(p.id) from Prestamo p"),
		@NamedQuery(name = Prestamo.OBTENER_PRESTAMOS_AGRUPADOS_FECHA, query = "select COUNT(DISTINCT p.persona.cedula),p.fechaInicio from Prestamo p GROUP BY p.fechaInicio"),
		@NamedQuery(name = Prestamo.OBTENER_MAX_VALOR_PRESTAMOS, query = "select MAX(p.valorPrestamo) from Prestamo p"),
		@NamedQuery(name = Prestamo.OBTENER_MAXIMOS_PRESTAMOS, query = "select p.valorPrestamo from Prestamo p ORDER BY p.valorPrestamo DESC"),
		@NamedQuery(name = Prestamo.OBTENER_PRESTAMO_POR_VALOR_MAXIMO, query = "select p from Prestamo p where p.valorPrestamo=:valorPrestamo"),
		@NamedQuery(name = Prestamo.OBTENER_PRESTAMOS_MAXIMOS, query = "select p from Prestamo p where p.valorPrestamo=select MAX(p.valorPrestamo) from Prestamo p"),
		@NamedQuery(name = Prestamo.OBTENER_PRESTAMOS_ALL, query = "select p from Prestamo p"),
		@NamedQuery(name = Prestamo.OBTENER_PRESTAMOS_POR_TIPO, query = "select p from Prestamo p where p.tipoPrestamo=:tipoPrestamo"),
		@NamedQuery(name = Prestamo.OBTENER_PRESTAMO_POR_ID, query = "select p from Prestamo p where p.id=:id"),
		@NamedQuery(name = Prestamo.OBTENER_CONSECUTIVO_PRESTAMO, query = "select (COUNT(p.id)+1) from Prestamo p")})
public class Prestamo implements Serializable {

	/**
	 * Permite obtener un prestamo el id más alto y sumarle 1 para generar el consecutivo
	 */
	public static final String OBTENER_CONSECUTIVO_PRESTAMO = "ConsecutivoIdPrestamo";
	/**
	 * Permite obtener un prestamo por por Id de prestamo 
	 */
	public static final String OBTENER_PRESTAMO_POR_ID = "PrestamosPorId";
	
	/**
	 * Permite obtener un prestamo por tipo 
	 */
	public static final String OBTENER_PRESTAMOS_POR_TIPO = "PrestamosPorTipo";
	
	/**
	 * Permite obtener todos los prestamos
	 */
	public static final String OBTENER_PRESTAMOS_ALL = "TodosLosPrestamos";
	
	/**
	 * Permite obtener las cuotas de un prestamo
	 */
	public static final String OBTENER_CUOTAS_PRESTAMO = "CuotasPrestamo";

	/**
	 * Permite obtener las cuotas de un prestamo
	 */
	public static final String OBTENER_TIPO_PRESTAMO = "TipoPrestamo";

	/**
	 * Permite obtener los prestamos hechos por un cliente
	 */
	public static final String OBTENER_PRESTAMOS = "ClientesTodosLosPrestamos";

	/**
	 * Permite obtener los prestamos hechos por un cliente dependiendo de la fecha
	 */
	public static final String OBTENER_CAMPOS_PRESTAMO = "ObtenerAlgunosCampos";

	/**
	 * Permite obtener los prestamos hechos por un cliente dependiendo de la fecha
	 * usando DOT
	 */
	public static final String OBTENER_CAMPOS_PRESTAMO_DTO = "ObtenerAlgunosCamposDTO";

	/**
	 * Permite obtener el total de los prestamos
	 */
	public static final String OBTENER_TOTAL_PRESTAMOS = "ObtenerTotalPrestamos";

	/**
	 * Permite obtener el total de los prestamos agrupados por fecha
	 */
	public static final String OBTENER_PRESTAMOS_AGRUPADOS_FECHA = "ObtenerPrestamosAgrupadosFecha";

	/**
	 * Permite obtener el prestamo con el valor maximo de los prestamos
	 */
	public static final String OBTENER_MAX_VALOR_PRESTAMOS = "ObtenerPrestamoMaximo";

	/**
	 * Permite obtener los prestamo con el valor maximo ordenados descendente
	 */
	public static final String OBTENER_MAXIMOS_PRESTAMOS = "ObtenerPrestamosMaximos";

	/**
	 * Permite obtener los prestamo dado el valor maximo
	 */
	public static final String OBTENER_PRESTAMO_POR_VALOR_MAXIMO = "ObtenerPrestamosDadoMaximo";

	/**
	 * Permite el listado de prestamos con el mayor monto
	 */
	public static final String OBTENER_PRESTAMOS_MAXIMOS = "ObtenerTodosPrestamosMaximos";

	/**
	 * serialVersionUID clase Prestamo
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de un prestamo
	 */
	@Id
	@NotNull
	private int id;

	/**
	 * Valor del prestamo
	 */
	private double valorPrestamo;

	/**
	 * Fecha de inicio del prestamo
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;

	/**
	 * Fecha de fin del prestamo, de acuerdo al numero de cuotas
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaFin;

	/**
	 * Tipo de prestamo
	 */
	@ManyToOne
	private TipoPrestamo tipoPrestamo;

	/**
	 * Lista de pagos asociados al prestamo
	 */
	@OneToMany(mappedBy = "prestamo")
	private List<Pago> pagos;

	/**
	 * Numero de cuotas del credito
	 */
	@Column(nullable = false)
	private int noCuotas;

	/**
	 * Persona que realiza un prestamo
	 */
	@ManyToOne
	private Persona persona;

	/**
	 * Metodo constructor clase Prestamo
	 */
	public Prestamo() {
		super();
	}

	/**
	 * Metodo get id clase Prestamo
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo get id clase Prestamo
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo get valorPrestamo clase Prestamo
	 * 
	 * @return valorPrestamo
	 */
	public double getValorPrestamo() {
		return valorPrestamo;
	}

	/**
	 * Metodo set valorPrestamo clase Prestamo
	 * 
	 * @param valorPrestamo
	 */
	public void setValorPrestamo(double valorPrestamo) {
		this.valorPrestamo = valorPrestamo;
	}

	/**
	 * Metodo get fechaInicio clase Prestamo
	 * 
	 * @return fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Metodo set fechaInicio clase Prestamo
	 * 
	 * @param fechaInicio
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Metodo get fechaFin clase Prestamo
	 * 
	 * @return fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * Metodo set fechaFin clase Prestamo
	 * 
	 * @param fechaFin
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Metodo get tipoPrestamo clase Prestamo
	 * 
	 * @return tipoPrestamo
	 */
	public TipoPrestamo getTipoPrestamo() {
		return tipoPrestamo;
	}

	/**
	 * Metodo set tipoPrestamo clase Prestamo
	 * 
	 * @param tipoPrestamo
	 */
	public void setTipoPrestamo(TipoPrestamo tipoPrestamo) {
		this.tipoPrestamo = tipoPrestamo;
	}

	/**
	 * Metodo get lista pagos clase Prestamo
	 * 
	 * @return the pagos
	 */
	public List<Pago> getPagos() {
		return pagos;
	}

	/**
	 * Metodo set lista pagos clase Prestamo
	 * 
	 * @param pagos
	 */
	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	/**
	 * Metodo get noCuotas clase Prestamo
	 * 
	 * @return noCuotas
	 */
	public int getNoCuotas() {
		return noCuotas;
	}

	/**
	 * Metodo set noCuotas clase Prestamo
	 * 
	 * @param noCuotas
	 */
	public void setNoCuotas(int noCuotas) {
		this.noCuotas = noCuotas;
	}

	/**
	 * Metodo get Persona clase Prestamo
	 * 
	 * @return persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * Metodo set Persona clase Prestamo
	 * 
	 * @param persona
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * Metodo get serialversionuid clase Prestamo
	 * 
	 * @return serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * Metodo hashcode clase Prestamo
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaFin == null) ? 0 : fechaFin.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + id;
		result = prime * result + noCuotas;
		result = prime * result + ((pagos == null) ? 0 : pagos.hashCode());
		result = prime * result + ((persona == null) ? 0 : persona.hashCode());
		result = prime * result + ((tipoPrestamo == null) ? 0 : tipoPrestamo.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorPrestamo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/*
	 * Metodo equals clase Prestamo
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prestamo other = (Prestamo) obj;
		if (fechaFin == null) {
			if (other.fechaFin != null)
				return false;
		} else if (!fechaFin.equals(other.fechaFin))
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		if (id != other.id)
			return false;
		if (noCuotas != other.noCuotas)
			return false;
		if (pagos == null) {
			if (other.pagos != null)
				return false;
		} else if (!pagos.equals(other.pagos))
			return false;
		if (persona == null) {
			if (other.persona != null)
				return false;
		} else if (!persona.equals(other.persona))
			return false;
		if (tipoPrestamo == null) {
			if (other.tipoPrestamo != null)
				return false;
		} else if (!tipoPrestamo.equals(other.tipoPrestamo))
			return false;
		if (Double.doubleToLongBits(valorPrestamo) != Double.doubleToLongBits(other.valorPrestamo))
			return false;
		return true;
	}

}
