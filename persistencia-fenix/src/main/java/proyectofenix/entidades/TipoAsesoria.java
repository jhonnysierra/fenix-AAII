package proyectofenix.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * Clase encargada de representar la informacion de un tipo de asesoria
 * 
 * @author JJJ
 * @version 1.0 28-agosto-2018
 *
 */

@Entity
@NamedQueries({
		@NamedQuery(name = TipoAsesoria.TIPO_ASESORIA_POR_CODIGO, query = "select tp from TipoAsesoria tp where tp.id=:idAsesoria"),
		@NamedQuery(name = TipoAsesoria.OBTENER_TIPOASESORIAS_ALL, query = "select tp from TipoAsesoria tp") })
public class TipoAsesoria implements Serializable {

	/**
	 * Permite obtener todos los tipos de prestamos que existen
	 */
	public static final String OBTENER_TIPOASESORIAS_ALL = "TipoAsesoriaAll";
	
	/**
	 * Permite hacer la referencia a la consulta tipo asesoria por codigo
	 */
	public static final String TIPO_ASESORIA_POR_CODIGO = "TipoAsesoria";

	/**
	 * serialVersionUID clase TipoAsesoria
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del TipoAsesoria
	 */
	@Id
	@NotNull
	private int id;

	/**
	 * Nombre del tipo de asesoria
	 */
	@Column(length = 50, nullable = false, unique = true)
	@NotNull
	private String tipo;

	/**
	 * Descripcion del tipo de asesoria
	 */
	@Column(length = 100)
	@NotNull
	private String descripcion;

	/**
	 * Lista de asesorias relacionada con la instancia del tipo de asesoria
	 */
	@OneToMany(mappedBy = "tipoasesoria")
	private List<Asesoria> asesoria;

	/**
	 * METODO CONSTRUCTOR CLASE TipoAsesoria
	 */
	public TipoAsesoria() {
		super();
	}

	/**
	 * Metodo get id clase TipoAsesoria
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo set id clase TipoAsesoria
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo get tipo clase TipoAsesoria
	 * 
	 * @return tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Metodo set tipo clase TipoAsesoria
	 * 
	 * @param tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Metodo get descripcion clase TipoAsesoria
	 * 
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Metodo set descripcion clase TipoAsesoria
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Metodo get asesoria clase TipoAsesoria
	 * 
	 * @return asesoria
	 */
	public List<Asesoria> getAsesoria() {
		return asesoria;
	}

	/**
	 * Metodo set asesoria clase TipoAsesoria
	 * 
	 * @param asesoria
	 */
	public void setAsesoria(List<Asesoria> asesoria) {
		this.asesoria = asesoria;
	}

	/**
	 * Metodo get serialversionuid clase TipoAsesoria
	 * 
	 * @return serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * Metodo hashcode clase TipoAsesoria
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asesoria == null) ? 0 : asesoria.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + id;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	/*
	 * Metodo hashcode clase TipoAsesoria
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoAsesoria other = (TipoAsesoria) obj;
		if (asesoria == null) {
			if (other.asesoria != null)
				return false;
		} else if (!asesoria.equals(other.asesoria))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id != other.id)
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

}
