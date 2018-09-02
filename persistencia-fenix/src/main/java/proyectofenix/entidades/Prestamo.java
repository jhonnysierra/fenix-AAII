package proyectofenix.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Clase encargada de representar la informacion de un Prestamo
 * 
 * @author JJJ
 * @version 1.0 28-agosto-2018
 *
 */

@Entity
public class Prestamo implements Serializable {

	/**
	 * serialVersionUID clase Prestamo
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de un prestamo
	 */
	@Id
	@NotNull
	@NotBlank
	private int id;

	/**
	 * Valor del prestamo
	 */
	private double valorPrestamo;

	/**
	 * Fecha de inicio del prestamo
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;

	/**
	 * Fecha de fin del prestamo, de acuerdo al numero de cuotas
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
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
	 * Cliente que realiza el prestamo
	 */
	@ManyToOne
	private Cliente cliente;
	
	/**
	 * Empleado que realiza el prestamo
	 */
	@ManyToOne
	private Empleado empleado;
	
	/**
	 * Administrador que realiza el prestamo
	 */
	@ManyToOne
	private Administrador administrador;
	

	/**
	 * Metodo constructor clase Prestamo
	 */
	public Prestamo() {
		super();
	}
	
	/**
	 * Metodo get id clase Prestamo
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo get id clase Prestamo
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo get valorPrestamo clase Prestamo
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
	 * @return noCuotas
	 */
	public int getNoCuotas() {
		return noCuotas;
	}

	/**
	 * Metodo set noCuotas clase Prestamo
	 * @param noCuotas
	 */
	public void setNoCuotas(int noCuotas) {
		this.noCuotas = noCuotas;
	}

	/**
	 * Metodo get cliente clase Prestamo
	 * @return cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Metodo set cliente clase Prestamo
	 * @param cliente
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	

	/**
	 * Metodo get empleado clase Prestamo
	 * @return empleado
	 */
	public Empleado getEmpleado() {
		return empleado;
	}

	/**
	 * Metodo set empleado clase Prestamo
	 * @param empleado
	 */
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	/**
	 * Metodo get administrador clase Prestamo
	 * @return administrador
	 */
	public Administrador getAdministrador() {
		return administrador;
	}

	/**
	 * Metodo set administrador clase Prestamo
	 * @param administrador
	 */
	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
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
		result = prime * result + ((administrador == null) ? 0 : administrador.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((empleado == null) ? 0 : empleado.hashCode());
		result = prime * result + ((fechaFin == null) ? 0 : fechaFin.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + id;
		result = prime * result + noCuotas;
		result = prime * result + ((pagos == null) ? 0 : pagos.hashCode());
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
		if (administrador == null) {
			if (other.administrador != null)
				return false;
		} else if (!administrador.equals(other.administrador))
			return false;
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
