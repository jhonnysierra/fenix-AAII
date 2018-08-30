package proyectofenix.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;


/**
 * Clase encargada de representar la informacion de una persona
 * @author Jhonny_Jorge_Javier
 * @version 1.0 16-agosto-2018
 *
 */

@Entity
public class Persona implements Serializable {

	/**
	 * Permite identificar de forma unica a la persona
	 */
	
	/**
	 * Permite identificar una persona
	 */
	@Id
	@Column (length=15)
	private String cedula;
	
	/**
	 * Nombre una persona
	 */
	@Column(length=30)
	private String nombres;
	/**
	 * Apellido de una persona
	 */
	@Column(length=30)
	private String apellidos;
	
	/**
	 * Genero de una persona 
	 */
	@Column (length=9)
	@Enumerated(EnumType.STRING)
	private Genero genero;
	
	
	/**
	 * Ciudad de una persona
	 */
	private String ciudad;
	
	/**
	 * Lista que contiene los telefonos asociados a una persona
	 */
	
	@Column(length=10)
	@ElementCollection
	private List<String> telefonos;
	/*private Map<Integer,String> telefonos;*/
	
	
	
	/**
	 * Fecha de nacimiento de una persona 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_nacimiento;
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Clase enumeracion que contiene las opciones para genero de una persona 
	 *
	 */
	private enum Genero{
		masculino, femenino;
	}

	/**
	 * Metodo constructor de la clase Persona 
	 */
	public Persona() {
		super();
	}  
	

	
	
	
   
}
