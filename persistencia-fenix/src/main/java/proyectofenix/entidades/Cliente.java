package proyectofenix.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
	 * Lista de prestamos de un cliente
	 */
	@OneToMany(mappedBy = "cliente")
	private List<Prestamo> prestamo;
	
	/**
	 * Lista de asesorias de un cliente
	 */
	@OneToMany(mappedBy = "cliente")
	private List<Asesoria> asesoria;
	
	@OneToMany(mappedBy = "cliente")
	private List<PrestamoRenovado> prestamoRenovado; 

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
	 * Metodo get lista prestamos clase Cliente
	 * @return prestamo
	 */
	public List<Prestamo> getPrestamo() {
		return prestamo;
	}

	/**
	 * Metodo set lista prestamos clase Cliente
	 * @param prestamo
	 */
	public void setPrestamo(List<Prestamo> prestamo) {
		this.prestamo = prestamo;
	}

	
	/**
	 * Metodo get lista asesorias clase Cliente
	 * @return asesoria
	 */
	public List<Asesoria> getAsesoria() {
		return asesoria;
	}

	/**
	 * Metodo set lista asesorias clase Cliente
	 * @param asesoria
	 */
	public void setAsesoria(List<Asesoria> asesoria) {
		this.asesoria = asesoria;
	}
	

	/**
	 * Metodo get lista prestamos renovados clase Cliente
	 * @return prestamoRenovado
	 */
	public List<PrestamoRenovado> getPrestamoRenovado() {
		return prestamoRenovado;
	}

	/**
	 * Metodo set lista prestamos renovados clase Cliente
	 * @param prestamoRenovado
	 */
	public void setPrestamoRenovado(List<PrestamoRenovado> prestamoRenovado) {
		this.prestamoRenovado = prestamoRenovado;
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
		result = prime * result + ((asesoria == null) ? 0 : asesoria.hashCode());
		result = prime * result + ((noCuenta == null) ? 0 : noCuenta.hashCode());
		result = prime * result + ((prestamo == null) ? 0 : prestamo.hashCode());
		result = prime * result + ((prestamoRenovado == null) ? 0 : prestamoRenovado.hashCode());
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
		if (asesoria == null) {
			if (other.asesoria != null)
				return false;
		} else if (!asesoria.equals(other.asesoria))
			return false;
		if (noCuenta == null) {
			if (other.noCuenta != null)
				return false;
		} else if (!noCuenta.equals(other.noCuenta))
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
		return true;
	}


}
