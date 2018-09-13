package proyectofenix.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Clase encargada de representar la informacion de un Pago
 * 
 * @author JJJ
 * @version 1.0 28-agosto-2018
 *
 */

@Entity
public class Pago implements Serializable {

	/**
	 * serialVersionUID clase Pago
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de un pago
	 */
	@Id
	@NotNull
	private int id;

	/**
	 * Valor del pago realizado por un cliente
	 */
	@NotNull
	private double valor;

	/**
	 * Fecha en la que un cliente realizo el pago
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@ManyToOne
	private Prestamo prestamo;

	/**
	 * Metodo constructor clase Pago
	 */
	public Pago() {
		super();
	}

	/**
	 * Metodo get id clase Pago
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo set id clase Pago
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo get valor clase Pago
	 * 
	 * @return valor
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * Metodo set valor clase Pago
	 * 
	 * @param valor
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * Metodo get fecha clase Pago
	 * 
	 * @return fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Metodo set fecha clase Pago
	 * 
	 * @param fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Metodo set prestamo clase Pago
	 * 
	 * @return prestamo
	 */
	public Prestamo getPrestamo() {
		return prestamo;
	}

	/**
	 * Metodo set prestamo clase Pago
	 * 
	 * @param prestamo
	 */
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	/**
	 * Metodo get serialversionuid clase Pago
	 * 
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * Metodo hashcode clase Pago
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + id;
		result = prime * result + ((prestamo == null) ? 0 : prestamo.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/*
	 * Metodo equals clase Pago
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pago other = (Pago) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id != other.id)
			return false;
		if (prestamo == null) {
			if (other.prestamo != null)
				return false;
		} else if (!prestamo.equals(other.prestamo))
			return false;
		if (Double.doubleToLongBits(valor) != Double.doubleToLongBits(other.valor))
			return false;
		return true;
	}
}
