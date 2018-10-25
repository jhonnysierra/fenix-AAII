package proyectofenix.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Clase encargada de representar la informacion de un Administrador hereda de
 * la clase Persona
 * 
 * @author JJJ
 * @version 1.0 28-agosto-2018
 *
 */

@Entity
@NamedQueries({ @NamedQuery(name = Administrador.OBTENER_DATOS_ADMINISTRADOR, query = "select a from Administrador a"),
		@NamedQuery(name = Administrador.CONTAR_ADMIN, query = "select count(a) from Administrador a"),
		@NamedQuery(name = Administrador.ADMIN_POR_ID, query = "select a from Administrador a where a.cedula=:cedula") })
public class Administrador extends Persona implements Serializable {


	/**
	 * Permite hacer la referencia a la consulta consultar admin por id
	 */
	public static final String ADMIN_POR_ID = "AdminPorId";
	
	
	/**
	 * Permite hacer la referencia a la consulta contar administradores
	 */
	public static final String CONTAR_ADMIN = "ContarAdmin";

	/**
	 * Permite obtener los datos de los administradores
	 */
	public static final String OBTENER_DATOS_ADMINISTRADOR = "DatosAdministrador";

	/**
	 * serialVersionUID Clase Administrador
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Observacion de un Administrador
	 */
	@Column(length = 100)
	private String observacion;

	/**
	 * Metodo constructor clase Administrador
	 */
	public Administrador() {
		super();
	}

	/**
	 * Metodo get observacion clase Administrador
	 * 
	 * @return observavion
	 */
	public String getObservavion() {
		return observacion;
	}

	/**
	 * Metodo set observacion clase Administrador
	 * 
	 * @param observavion
	 */
	public void setObservavion(String observavion) {
		this.observacion = observavion;
	}

	/**
	 * Metodo set serialversionuid clase Administrador
	 * 
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
		return true;
	}

}
