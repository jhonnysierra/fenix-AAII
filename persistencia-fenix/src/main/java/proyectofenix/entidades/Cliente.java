package proyectofenix.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Clase encargada de representar la informacion de una Cliente hereda de la
 * clase Persona
 * 
 * @author JJJ
 * @version 1.0 28-agosto-2018
 *
 */

@Entity
public class Cliente extends Persona implements Serializable {

	/**
	 * Numero de cuenta de un cliente
	 */
	@Column(length = 50, nullable = false, unique = true)
	@NotNull
	@NotBlank
	private String noCuenta;

	/**
	 * Serializable clase Cliente
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo constructor clase Cliente
	 */
	public Cliente() {
		super();
	}

	/**
	 * Metodo get numero de cuenta clase Cliente
	 * 
	 * @return numero de cuenta
	 */
	public String getNoCuenta() {
		return noCuenta;
	}

	/**
	 * Metodo set numero de cuenta clase Cliente
	 * 
	 * @param noCuenta
	 */
	public void setNoCuenta(String noCuenta) {
		this.noCuenta = noCuenta;
	}

	/**
	 * Metodo get Serialversionuid clase Cliente
	 * 
	 * @return serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * Metodo hashcode clase Cliente
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((noCuenta == null) ? 0 : noCuenta.hashCode());
		return result;
	}

	/*
	 * Metodo equals clase Cliente
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (noCuenta == null) {
			if (other.noCuenta != null)
				return false;
		} else if (!noCuenta.equals(other.noCuenta))
			return false;
		return true;
	}

}
