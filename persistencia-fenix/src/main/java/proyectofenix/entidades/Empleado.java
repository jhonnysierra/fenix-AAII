package proyectofenix.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Clase encargada de representar la informacion de un Empleado hereda de la clase Persona
 * @author JJJ
 * @version 1.0 28-agosto-2018
 *
 */

@Entity
public class Empleado extends Persona implements Serializable{
	
	
	/**
	 * serialVersionUID clase Empleado
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Fecha de inicio de contrato de un Empleado
	 */
	@Column(nullable = false)
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;
	
	/**
	 * Fecha de fin de contrato de un Empleado
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;
	
	/**
	 * Salario de un Empleado
	 */
	private double salario; 
	
	/**
	 * Lista de prestamos de un cliente
	 */
	@OneToMany(mappedBy = "empleado")
	private List<Prestamo> prestamo;
	
	/**
	 * Lista de asesorias de un empleado
	 */
	@OneToMany(mappedBy = "empleado")
	private List<Asesoria> aseoria;
	
	/**
	 * Metodo constructor clase Empleado
	 */
	public Empleado() {
		super();
	}

	/**
	 * Metodo get fecha inicio contrato clase Empleado
	 * @return fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Metodo set fecha inicio contrato clase Empleado
	 * @param fechaInicio
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Metodo get fecha fin contrato clase Empleado
	 * @return fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * Metodo set fecha fin contrato clase Empleado
	 * @param fechaFin
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Metodo get salario clase Empleado
	 * @return salario
	 */
	public double getSalario() {
		return salario;
	}

	/**
	 * Metodo set salario clase Empleado
	 * @param salario
	 */
	public void setSalario(double salario) {
		this.salario = salario;
	}
	
	

	/**
	 * Metodo get lista prestamos clase Empleado
	 * @return prestamo
	 */
	public List<Prestamo> getPrestamo() {
		return prestamo;
	}

	/**
	 * Metodo set lista prestamos clase Empleado
	 * @param prestamo
	 */
	public void setPrestamo(List<Prestamo> prestamo) {
		this.prestamo = prestamo;
	}
	

	/**
	 * Metodo get lista asesorias clase Empleado
	 * @return aseoria
	 */
	public List<Asesoria> getAseoria() {
		return aseoria;
	}

	/**
	 * Metodo set lista asesorias clase Empleado
	 * @param aseoria
	 */
	public void setAseoria(List<Asesoria> aseoria) {
		this.aseoria = aseoria;
	}

	/**
	 * Metodo get serialversionuid clase Empleado
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* 
	 * Metodo hashcode clase Empleado 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((aseoria == null) ? 0 : aseoria.hashCode());
		result = prime * result + ((fechaFin == null) ? 0 : fechaFin.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((prestamo == null) ? 0 : prestamo.hashCode());
		long temp;
		temp = Double.doubleToLongBits(salario);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/*
	 * Metodo equals clase Empleado
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		if (aseoria == null) {
			if (other.aseoria != null)
				return false;
		} else if (!aseoria.equals(other.aseoria))
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
		if (prestamo == null) {
			if (other.prestamo != null)
				return false;
		} else if (!prestamo.equals(other.prestamo))
			return false;
		if (Double.doubleToLongBits(salario) != Double.doubleToLongBits(other.salario))
			return false;
		return true;
	}

}
