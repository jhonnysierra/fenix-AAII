package proyectofenix.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
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
	@NotBlank
	private Date fechaInicio;
	
	/**
	 * Fecha de fin de contrato de un Empleado
	 */
	private Date fechaFin;
	
	/**
	 * Salario de un Empleado
	 */
	private double salario; 
	
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
		result = prime * result + ((fechaFin == null) ? 0 : fechaFin.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
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
		if (Double.doubleToLongBits(salario) != Double.doubleToLongBits(other.salario))
			return false;
		return true;
	}
	
	

}