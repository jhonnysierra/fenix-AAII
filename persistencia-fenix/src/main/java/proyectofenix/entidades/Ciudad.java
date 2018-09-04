package proyectofenix.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Clase que representa la informacion de una ciudad;
 * @author JJJ
 * @version 1.0 28-agosto-2018
 */
@Entity
public class Ciudad implements Serializable{

	/**
	 * Serializable clase Ciudad
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id de una ciudad
	 */
	@Id
	@Column(length = 2)
	@NotNull
	@NotBlank
	private String id;

	/**
	 * Nombre de una ciudad
	 */
	@Column(length = 30,nullable=false,unique=true)
	@NotNull
	@NotBlank
	private String nombre;

	/**
	 * Departamento al que corresponde la ciudad
	 */
	@ManyToOne
	private Departamento departamento;

	/**
	 * Metodo constructor de la clase Ciudad
	 */
	public Ciudad() {
		super();
	}

	/**
	 * Metodo get id clase Ciudad
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Metodo set id clase Ciudad
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Metodo get nombre clase Ciudad
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo set nombre clase Ciudad
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo get departamento clase Ciudad
	 * @return departamento
	 */
	public Departamento getDepartamento() {
		return departamento;
	}

	/**
	 * Metodo set departamento clase Ciudad
	 * @param departamento
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	
	/**
	 * Metodo get serialversionui
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/* 
	 * Metodo hashcode clase Ciudad
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((departamento == null) ? 0 : departamento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	/* 
	 * Metodo equals clase Ciudad
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ciudad other = (Ciudad) obj;
		if (departamento == null) {
			if (other.departamento != null)
				return false;
		} else if (!departamento.equals(other.departamento))
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
