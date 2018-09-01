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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
	private Ciudad ciudad;
	
	private String direccion;
	
	
	/**
	 * Lista que contiene los telefonos asociados a una persona
	 */
	@Column(length=10)
	@ElementCollection
	private List<String> telefonos;
	/*private Map<Integer,String> telefonos;*/
	
	private String correo;
	
	/**
	 * Fecha de nacimiento de una persona 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_nacimiento;
	
	private char estado;
	
	private String contrasenia;
	
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



	/**
	 * @return the cedula
	 */
	public String getCedula() {
		return cedula;
	}



	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}



	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}



	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}



	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}



	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}



	/**
	 * @return the genero
	 */
	public Genero getGenero() {
		return genero;
	}



	/**
	 * @param genero the genero to set
	 */
	public void setGenero(Genero genero) {
		this.genero = genero;
	}



	/**
	 * @return the ciudad
	 */
	public Ciudad getCiudad() {
		return ciudad;
	}



	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}



	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}



	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	/**
	 * @return the telefonos
	 */
	public List<String> getTelefonos() {
		return telefonos;
	}



	/**
	 * @param telefonos the telefonos to set
	 */
	public void setTelefonos(List<String> telefonos) {
		this.telefonos = telefonos;
	}



	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}



	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}



	/**
	 * @return the fecha_nacimiento
	 */
	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}



	/**
	 * @param fecha_nacimiento the fecha_nacimiento to set
	 */
	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}



	/**
	 * @return the estado
	 */
	public char getEstado() {
		return estado;
	}



	/**
	 * @param estado the estado to set
	 */
	public void setEstado(char estado) {
		this.estado = estado;
	}



	/**
	 * @return the contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}



	/**
	 * @param contrasenia the contrasenia to set
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}



	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((cedula == null) ? 0 : cedula.hashCode());
		result = prime * result + ((ciudad == null) ? 0 : ciudad.hashCode());
		result = prime * result + ((contrasenia == null) ? 0 : contrasenia.hashCode());
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + estado;
		result = prime * result + ((fecha_nacimiento == null) ? 0 : fecha_nacimiento.hashCode());
		result = prime * result + ((genero == null) ? 0 : genero.hashCode());
		result = prime * result + ((nombres == null) ? 0 : nombres.hashCode());
		result = prime * result + ((telefonos == null) ? 0 : telefonos.hashCode());
		return result;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (cedula == null) {
			if (other.cedula != null)
				return false;
		} else if (!cedula.equals(other.cedula))
			return false;
		if (ciudad == null) {
			if (other.ciudad != null)
				return false;
		} else if (!ciudad.equals(other.ciudad))
			return false;
		if (contrasenia == null) {
			if (other.contrasenia != null)
				return false;
		} else if (!contrasenia.equals(other.contrasenia))
			return false;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (estado != other.estado)
			return false;
		if (fecha_nacimiento == null) {
			if (other.fecha_nacimiento != null)
				return false;
		} else if (!fecha_nacimiento.equals(other.fecha_nacimiento))
			return false;
		if (genero != other.genero)
			return false;
		if (nombres == null) {
			if (other.nombres != null)
				return false;
		} else if (!nombres.equals(other.nombres))
			return false;
		if (telefonos == null) {
			if (other.telefonos != null)
				return false;
		} else if (!telefonos.equals(other.telefonos))
			return false;
		return true;
	} 
	
	
   
}
