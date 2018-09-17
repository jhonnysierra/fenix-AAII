package proyectofenix.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@NamedQueries({ @NamedQuery(name = Cliente.OBTENER_DATOS_CLIENTE, query = "select c from Cliente c"),
	@NamedQuery(name = Cliente.OBTENER_CLIENTE_SIN_ASESORIA, query = "select c from Cliente c where c.asesoria is EMPTY")
		 })
public class Cliente extends Persona implements Serializable {

	/**
	 * Permite obtener los datos de los clientes
	 */
	public static final String OBTENER_DATOS_CLIENTE = "DatosCliente";

	/**
	 * Permite obtener los datos de los clientes
	 */
	public static final String OBTENER_CLIENTE_SIN_ASESORIA = "ClienteSinAsesoria";
	
	/**
	 * Numero de cuenta de un cliente
	 */
	@Column(length = 50, nullable = false, unique = true)
	@NotNull
	private String noCuenta;

	/**
	 * Lista de asesorias de un cliente
	 */
	@OneToMany(mappedBy = "cliente")
	private List<Asesoria> asesoria;

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
	 * Metodo get lista asesorias clase Cliente
	 * 
	 * @return asesoria
	 */
	public List<Asesoria> getAsesoria() {
		return asesoria;
	}

	/**
	 * Metodo set lista asesorias clase Cliente
	 * 
	 * @param asesoria
	 */
	public void setAsesoria(List<Asesoria> asesoria) {
		this.asesoria = asesoria;
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
		return true;
	}

}
