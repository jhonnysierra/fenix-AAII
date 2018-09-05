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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Clase encargada de representar la informacion de una persona
 * 
 * @author Jhonny_Jorge_Javier
 * @version 1.0 16-agosto-2018
 *
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
		@NamedQuery(name = Persona.OBTENER_PERSONAS_POR_CREDENCIALES, query = "select p from Persona p where p.cedula=:cedula and p.contrasenia=:contrasenia") })
public class Persona implements Serializable {

	/**
	 * Permite identificar de forma unica a la persona
	 */

	public static final String OBTENER_PERSONAS_POR_CREDENCIALES = "PersonasPorCredenciales";
	/**
	 * Permite identificar una persona
	 */
	@Id
	@Column(length = 15, nullable = false)
	@NotNull
	@NotBlank
	private String cedula;

	/**
	 * Nombre una persona
	 */
	@Column(length = 30, nullable = false)
	@NotNull
	@NotBlank
	private String nombres;
	/**
	 * Apellido de una persona
	 */
	@Column(length = 30, nullable = false)
	@NotNull
	@NotBlank
	private String apellidos;

	/**
	 * Genero de una persona
	 */
	@Column(length = 9)
	@Enumerated(EnumType.STRING)
	private Genero genero;

	/**
	 * Ciudad de una persona
	 */
	private Ciudad ciudad;

	/**
	 * Direccion de una persona
	 */
	@Column(length = 30)
	private String direccion;

	/**
	 * Lista que contiene los telefonos asociados a una persona
	 */
	@Column(length = 10)
	@ElementCollection
	private List<String> telefonos;
	/* private Map<Integer,String> telefonos; */

	/**
	 * Correo electronico de una Persona
	 */
	@Column(length = 80, nullable = false)
	@NotNull
	@NotBlank
	private String correo;

	/**
	 * Fecha de nacimiento de una persona
	 */
	@Temporal(TemporalType.DATE)
	private Date fecha_nacimiento;

	/**
	 * Estado de una Persona en el sistema 1 ACTIVO, 0 INACTIVO
	 */
	@Column(length = 1, nullable = false)
	@NotNull
	@NotBlank
	private String estado;

	/**
	 * Contrasenia de una Persona para ingresar al sistema
	 */
	@Column(length = 30, nullable = false)
	@NotNull
	@NotBlank
	private String contrasenia;

	/**
	 * Bien raiz de la persona
	 */
	@OneToOne(mappedBy = "persona")
	private BienRaiz bienRaiz;

	/**
	 * serialVersionUID clase Persona
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Clase enumeracion que contiene las opciones para genero de una persona
	 *
	 */
	public enum Genero {
		masculino, femenino;
	}

	/**
	 * Metodo constructor de la clase Persona
	 */
	public Persona() {
		super();
	}

	/**
	 * Metodo get cedula clase Persona
	 * 
	 * @return cedula
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * Metodo set cedula clase Persona
	 * 
	 * @param cedula
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	/**
	 * Metodo get nombres clase Persona
	 * 
	 * @return nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * Metodo set nombres clase Persona
	 * 
	 * @param nombres
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * Metodo get apellidos clase Persona
	 * 
	 * @return apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Metodo set nombres clase Persona
	 * 
	 * @param apellidos
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Metodo get genero clase Persona
	 * 
	 * @return genero
	 */
	public Genero getGenero() {
		return genero;
	}

	/**
	 * Metodo set genero clase Persona
	 * 
	 * @param genero
	 */
	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	/**
	 * Metodo get ciudad clase Persona
	 * 
	 * @return ciudad
	 */
	public Ciudad getCiudad() {
		return ciudad;
	}

	/**
	 * Metodo set ciudad clase Persona
	 * 
	 * @param ciudad
	 */
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * Metodo get direccion clase Persona
	 * 
	 * @return direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Metodo set direccion clase Persona
	 * 
	 * @param direccion
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Metodo get telefonos clase Persona
	 * 
	 * @return telefonos
	 */
	public List<String> getTelefonos() {
		return telefonos;
	}

	/**
	 * Metodo set telefonos clase Persona
	 * 
	 * @param telefonos
	 */
	public void setTelefonos(List<String> telefonos) {
		this.telefonos = telefonos;
	}

	/**
	 * Metodo get correo clase Persona
	 * 
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Metodo set correo clase Persona
	 * 
	 * @param correo
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Metodo get fecha clase Persona
	 * 
	 * @return the
	 */
	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	/**
	 * Metodo set fecha clase Persona
	 * 
	 * @param fecha_nacimiento
	 */
	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	/**
	 * Metodo get estado clase Persona
	 * 
	 * @return estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Metodo set estado clase Persona
	 * 
	 * @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Metodo get contrasenia clase Persona
	 * 
	 * @return contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * Metodo set contrasenia clase Persona
	 * 
	 * @param contrasenia
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * Metodo get bienRaiz clase Persona
	 * 
	 * @return bienRaiz
	 */
	public BienRaiz getBienRaiz() {
		return bienRaiz;
	}

	/**
	 * Metodo set bienRaiz clase Persona
	 * 
	 * @param bienRaiz
	 */
	public void setBienRaiz(BienRaiz bienRaiz) {
		this.bienRaiz = bienRaiz;
	}

	/**
	 * Metodo get serialversionuid clase Persona
	 * 
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * Metodo hashcode clase Persona
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((bienRaiz == null) ? 0 : bienRaiz.hashCode());
		result = prime * result + ((cedula == null) ? 0 : cedula.hashCode());
		result = prime * result + ((ciudad == null) ? 0 : ciudad.hashCode());
		result = prime * result + ((contrasenia == null) ? 0 : contrasenia.hashCode());
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fecha_nacimiento == null) ? 0 : fecha_nacimiento.hashCode());
		result = prime * result + ((genero == null) ? 0 : genero.hashCode());
		result = prime * result + ((nombres == null) ? 0 : nombres.hashCode());
		result = prime * result + ((telefonos == null) ? 0 : telefonos.hashCode());
		return result;
	}

	/*
	 * Metodo equals clase Persona
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
		Persona other = (Persona) obj;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (bienRaiz == null) {
			if (other.bienRaiz != null)
				return false;
		} else if (!bienRaiz.equals(other.bienRaiz))
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
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
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
