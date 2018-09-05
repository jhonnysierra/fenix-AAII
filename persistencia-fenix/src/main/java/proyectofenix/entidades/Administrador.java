package proyectofenix.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Clase encargada de representar la informacion de un Administrador hereda de la clase Persona
 * @author JJJ
 * @version 1.0 28-agosto-2018
 *
 */

@Entity
public class Administrador extends Persona implements Serializable{

	/**
	 * serialVersionUID Clase Administrador
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Observacion de un Administrador
	 */
	@Column(length=100)
	private String observacion;
	
	/**
	 * Lista de prestamos de un Administrador
	 */
	@OneToMany(mappedBy = "administrador")
	private List<Prestamo> prestamo;
	
	/**
	 * Metodo constructor clase Administrador
	 */
	public Administrador() {
		super();
	}

	/**
	 * Metodo get observacion clase Administrador
	 * @return observavion
	 */
	public String getObservavion() {
		return observacion;
	}

	/**
	 * Metodo set observacion clase Administrador
	 * @param observavion
	 */
	public void setObservavion(String observavion) {
		this.observacion = observavion;
	}

	/**
	 * Metodo get lista prestamos clase Administrador
	 * @return prestamo
	 */
	public List<Prestamo> getPrestamo() {
		return prestamo;
	}

	/**
	 * Metodo set lista prestamos clase Administrador
	 * @param prestamo
	 */
	public void setPrestamo(List<Prestamo> prestamo) {
		this.prestamo = prestamo;
	}

	/**
	 * Metodo set serialversionuid clase Administrador
	 * @return serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* 
	 * Metodo hashcode clase Administrador
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((observacion == null) ? 0 : observacion.hashCode());
		result = prime * result + ((prestamo == null) ? 0 : prestamo.hashCode());
		return result;
	}

	/* 
	 * Metodo equals clase Administrador
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Administrador other = (Administrador) obj;
		if (observacion == null) {
			if (other.observacion != null)
				return false;
		} else if (!observacion.equals(other.observacion))
			return false;
		if (prestamo == null) {
			if (other.prestamo != null)
				return false;
		} else if (!prestamo.equals(other.prestamo))
			return false;
		return true;
	}
}
