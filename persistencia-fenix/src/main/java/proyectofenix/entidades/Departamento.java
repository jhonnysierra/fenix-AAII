package proyectofenix.entidades;

import java.util.List;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Clase que representa la informacion de un departamento;
 * 
 * @author JJJ
 * @version 1.0 28-agosto-2018
 */
@Entity
public class Departamento implements Serializable {

	/**
	 * Serializable clase Departamento
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id del Departamento
	 */
	@Id
	@Column(length = 2)
	@NotNull
	@NotBlank
	private String id;

	/**
	 * Nombre del departamento
	 */
	@Column(length = 30, nullable = false, unique = true)
	@NotNull
	@NotBlank
	private String nombre;

	/**
	 * Lista de las ciudades de un Departamento
	 */
	@OneToMany(mappedBy = "departamento")
	private List<Ciudad> ciudades;

	/**
	 * Metodo constructor de la clase Departamento
	 */
	public Departamento() {
		super();
	}

	/**
	 * Metodo get id clase Departamento
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Metodo set id clase Departamento
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Metodo get nombre clase Departamento
	 * 
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo set nombre clase Departamento
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo get ciudades clase Departamento
	 * 
	 * @return the ciudades
	 */
	public List<Ciudad> getCiudades() {
		return ciudades;
	}

	/**
	 * Metodo set ciudades clase Departamento
	 * 
	 * @param ciudades
	 */
	public void setCiudades(List<Ciudad> ciudades) {
		this.ciudades = ciudades;
	}

	/**
	 * Metodo get serialversionuid
	 * 
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * Metodo hashcode clase Departamento
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ciudades == null) ? 0 : ciudades.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	/*
	 * Metodo equals clase Departamento
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		if (ciudades == null) {
			if (other.ciudades != null)
				return false;
		} else if (!ciudades.equals(other.ciudades))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

}
