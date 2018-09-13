package proyectofenix.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Clase encargada de representar la informacion del credito que se va a renovar
 * @author JJJ
 * @version 1.0 28-agosto-2018
 *
 */

@Entity
public class PrestamoRenovado implements Serializable{

	/**
	 * serialVersionUID clase PrestamoRenovado
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identificador de la clase PrestamoRenovado
	 */
	@Id
	private int id;
	
	/**
	 * Persona que puede renovar credito
	 */
	@NotNull
	@ManyToOne
	private Persona persona;
	
	/**
	 * Valor por el que el Cliente puede renovar el credito
	 */
	@NotNull
	private double valor;
	
	/**
	 * Numero de cuotas del credito a renovar
	 */
	@NotNull
	private int noCuotas;
	
	@ManyToOne
	private TipoPrestamo tipoPrestamo;
	
	
	/**
	 * METODO CONSTRUCTOR CLASE PrestamoRenovado
	 */
	public PrestamoRenovado() {
		super();
	}


	/**
	 * Metodo get id clase PrestamoRenovado
	 * @return id
	 */
	public int getId() {
		return id;
	}


	/**
	 * Metodo set id clase PrestamoRenovado
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * Metodo get persona clase PrestamoRenovado
	 * @return persona
	 */
	public Persona getPersona() {
		return persona;
	}


	/**
	 * Metodo set persona clase PrestamoRenovado
	 * @param persona
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}


	/**
	 * Metodo get valor clase PrestamoRenovado
	 * @return valor
	 */
	public double getValor() {
		return valor;
	}


	/**
	 * Metodo set valor clase PrestamoRenovado
	 * @param valor the valor to set
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}


	/**
	 * Metodo get noCuotas clase PrestamoRenovado
	 * @return noCuotas
	 */
	public int getNoCuotas() {
		return noCuotas;
	}


	/**
	 * Metodo set noCuotas clase PrestamoRenovado
	 * @param noCuotas the noCuotas to set
	 */
	public void setNoCuotas(int noCuotas) {
		this.noCuotas = noCuotas;
	}

	

	/**
	 * Metodo get tipoPrestamo clase PrestamoRenovado
	 * @return tipoPrestamo
	 */
	public TipoPrestamo getTipoPrestamo() {
		return tipoPrestamo;
	}


	/**
	 * Metodo set tipoPrestamo clase PrestamoRenovado
	 * @param tipoPrestamo
	 */
	public void setTipoPrestamo(TipoPrestamo tipoPrestamo) {
		this.tipoPrestamo = tipoPrestamo;
	}


	/**
	 * Metodo get serialversionuid clase PrestamoRenovado
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/* 
	 * Metodo hashcode clase PrestamoRenovado 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + noCuotas;
		result = prime * result + ((persona == null) ? 0 : persona.hashCode());
		result = prime * result + ((tipoPrestamo == null) ? 0 : tipoPrestamo.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	/* 
	 * Metodo equals clase PrestamoRenovado 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrestamoRenovado other = (PrestamoRenovado) obj;
		if (id != other.id)
			return false;
		if (noCuotas != other.noCuotas)
			return false;
		if (persona == null) {
			if (other.persona != null)
				return false;
		} else if (!persona.equals(other.persona))
			return false;
		if (tipoPrestamo == null) {
			if (other.tipoPrestamo != null)
				return false;
		} else if (!tipoPrestamo.equals(other.tipoPrestamo))
			return false;
		if (Double.doubleToLongBits(valor) != Double.doubleToLongBits(other.valor))
			return false;
		return true;
	}

	
}
