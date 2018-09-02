package proyectofenix.entidades;

import java.io.Serializable;

import javax.persistence.Entity;

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
	
	private String observacion;
	
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
	 * Metodo set serialversionuid clase Administrador
	 * @return serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* 
	 * Metodo hashcode clase Administrador
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((observacion == null) ? 0 : observacion.hashCode());
		return result;
	}

	/* 
	 * Metodo equals clase Administrador
	 * 
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
		return true;
	}

}
