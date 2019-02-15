package proyectofenix.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries({ @NamedQuery(name = Empleado.OBTENER_DATOS_EMPLEADO, query = "select e from Empleado e") })
public class Empleado extends Persona implements Serializable{
	
	/**
	 * Permite obtener los datos de los empleados
	 */
	public static final String OBTENER_DATOS_EMPLEADO = "DatosEmpleado";
	
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
