package proyectofenix.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Clase encargada de representar un tipo de Prestamo
 * 
 * @author JJJ
 * @version 1.0 28-agosto-2018
 *
 */

@Entity
public class TipoPrestamo implements Serializable {

	/**
	 * serialVersionUID clase Prestamo
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de un Tipo de Prestamo
	 */
	@Id
	@NotNull
	private int id;

	/**
	 * Nombre del tipo de prestamo
	 */
	@Column(length = 50, unique = true, nullable = false)
	@NotNull
	private String nombre;

	/**
	 * Tasa de interes de acuerdo al tipo de prestamo
	 */
	@NotNull
	private double tasaInteres;

	/**
	 * Descripcion del tipo de prestamo
	 */
	@Column(length = 150)
	private String descripcion;

	/**
	 * Lista de prestamos asociados al tipo de prestamo
	 */
	@OneToMany(mappedBy = "tipoPrestamo")
	private List<Prestamo> prestamo;
	
	@OneToMany(mappedBy = "tipoPrestamo")
	private List<PrestamoRenovado> prestamoRenovado;

	/**
	 * Metodo constructor clase TipoPrestamo
	 */
	public TipoPrestamo() {
		super();
	}

	/**
	 * Metodo get id clase TipoPrestamo
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo set id clase TipoPrestamo
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo get nombre clase TipoPrestamo
	 * 
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo set nombre clase TipoPrestamo
	 * 
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo get tasaInteres clase TipoPrestamo
	 * 
	 * @return tasaInteres
	 */
	public double getTasaInteres() {
		return tasaInteres;
	}

	/**
	 * Metodo set tasaInteres clase TipoPrestamo
	 * 
	 * @param tasaInteres the tasaInteres to set
	 */
	public void setTasaInteres(double tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	/**
	 * Metodo get descripcion clase TipoPrestamo
	 * 
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Metodo set descripcion clase TipoPrestamo
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Metodo get lista prestamos clase TipoPrestamo
	 * 
	 * @return prestamo
	 */
	public List<Prestamo> getPrestamo() {
		return prestamo;
	}

	/**
	 * Metodo set lista prestamos clase TipoPrestamo
	 * 
	 * @param prestamo
	 */
	public void setPrestamo(List<Prestamo> prestamo) {
		this.prestamo = prestamo;
	}
	
	

	/**
	 * Metodo get lista prestamos renovados clase TipoPrestamo
	 * @return prestamoRenovado
	 */
	public List<PrestamoRenovado> getPrestamoRenovado() {
		return prestamoRenovado;
	}

	/**
	 * Metodo set lista prestamos renovados clase TipoPrestamo
	 * @param prestamoRenovado
	 */
	public void setPrestamoRenovado(List<PrestamoRenovado> prestamoRenovado) {
		this.prestamoRenovado = prestamoRenovado;
	}

	/**
	 * Metodo get serialversionuid clase TipoPrestamo
	 * 
	 * @return serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * Metodo hashcode clase TipoPrestamo
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + id;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((prestamo == null) ? 0 : prestamo.hashCode());
		result = prime * result + ((prestamoRenovado == null) ? 0 : prestamoRenovado.hashCode());
		long temp;
		temp = Double.doubleToLongBits(tasaInteres);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/*
	 * Metodo equals clase TipoPrestamo
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoPrestamo other = (TipoPrestamo) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (prestamo == null) {
			if (other.prestamo != null)
				return false;
		} else if (!prestamo.equals(other.prestamo))
			return false;
		if (prestamoRenovado == null) {
			if (other.prestamoRenovado != null)
				return false;
		} else if (!prestamoRenovado.equals(other.prestamoRenovado))
			return false;
		if (Double.doubleToLongBits(tasaInteres) != Double.doubleToLongBits(other.tasaInteres))
			return false;
		return true;
	}

}
