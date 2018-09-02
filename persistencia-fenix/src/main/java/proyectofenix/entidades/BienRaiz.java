package proyectofenix.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Clase encargada de representar la informacion de un Bien Raiz de una persona
 * @author JJJ
 * @version 1.0 28-agosto-2018
 *
 */

@Entity
public class BienRaiz implements Serializable{

	/**
	 * serialVersionUID clase BienRaiz
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Numero de indentificacion del bien raiz
	 */
	@Id
	private String id;
	
	/**
	 * Direccion del bien raiz
	 */
	@Column(length = 30)
	private String direccion;
	
	/**
	 * ciudad del bien raiz
	 */
	private Ciudad ciudad;
	
	/**
	 * Avaluo del bien raiz
	 */
	private double avaluo;
	
	/**
	 * Propietario del bien raiz. Es una persona
	 */
	@OneToOne
	private Persona persona;
	
	/**
	 * Metodo constructor clase BienRaiz
	 */
	public BienRaiz() {
		super();
	}

	/**
	 * Metodo get id clase BienRaiz
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Metodo set id clase BienRaiz
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Metodo get direccion clase BienRaiz
	 * @return direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Metodo set id clase BienRaiz
	 * @param direccion
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Metodo get ciudad clase BienRaiz
	 * @return ciudad
	 */
	public Ciudad getCiudad() {
		return ciudad;
	}

	/**
	 * Metodo set ciudad clase BienRaiz
	 * @param ciudad
	 */
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * Metodo get avaluo clase BienRaiz
	 * @return avaluo
	 */
	public double getAvaluo() {
		return avaluo;
	}

	/**
	 * Metodo set avaluo clase BienRaiz
	 * @param avaluo
	 */
	public void setAvaluo(double avaluo) {
		this.avaluo = avaluo;
	}

	/**
	 * Metodo get persona clase BienRaiz
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * Metodo set persona clase BienRaiz
	 * @param persona
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * Metodo get serialversionuid clase BienRaiz
	 * @return serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* 
	 * Metodo hashcode clase BienRaiz
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(avaluo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((ciudad == null) ? 0 : ciudad.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((persona == null) ? 0 : persona.hashCode());
		return result;
	}

	/* 
	 * Metodo equals clase BienRaiz
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BienRaiz other = (BienRaiz) obj;
		if (Double.doubleToLongBits(avaluo) != Double.doubleToLongBits(other.avaluo))
			return false;
		if (ciudad == null) {
			if (other.ciudad != null)
				return false;
		} else if (!ciudad.equals(other.ciudad))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (persona == null) {
			if (other.persona != null)
				return false;
		} else if (!persona.equals(other.persona))
			return false;
		return true;
	}
	
}
