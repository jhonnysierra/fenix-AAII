package proyectofenix.escritorio.modelo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import proyectofenix.entidades.BienRaiz;
import proyectofenix.entidades.Persona;

/**
 * Permite transformar una bien raiz en formato observable
 * 
 * @author JJ
 * @version 1.0
 */
public class BienRaizObservable {

	/**
	 * id observable de un bien raiz
	 */
	private StringProperty id;
	/**
	 * avaluo observable de un bien raiz
	 */
	private DoubleProperty avaluo;

	/**
	 * direccion observable de un bien raiz
	 */
	private StringProperty direccion;

	/**
	 * nombre persona observable de un bien raiz
	 */
	private StringProperty cedNombpersona;


	/**
	 * Persona asociada al bien raiz
	 */
	private Persona persona;



	/**
	 * Constructor
	 */
	public BienRaizObservable() {

	}

	/**
	 * constructor que genera un bien raiz observable con base a un bien raiz
	 * 
	 * @param prestamo que se quiere volver observable
	 */
	public BienRaizObservable(BienRaiz bienraiz) {
		this.persona=bienraiz.getPersona();
		
		this.id = new SimpleStringProperty(bienraiz.getId());
		this.avaluo = new SimpleDoubleProperty(bienraiz.getAvaluo());
		this.direccion = new SimpleStringProperty(bienraiz.getDireccion());
		this.cedNombpersona = new SimpleStringProperty(persona.getCedula()+" - "+
				persona.getNombres() + " " + persona.getApellidos());
	}

	/**
	 * Metodo get id observable bien raiz
	 * @return the id
	 */
	public StringProperty getId() {
		return id;
	}

	/**
	 * Metodo set id observable bien raiz
	 * @param id the id to set
	 */
	public void setId(StringProperty id) {
		this.id = id;
	}

	/**
	 * Metodo get avaluo observable bien raiz
	 * @return the avaluo
	 */
	public DoubleProperty getAvaluo() {
		return avaluo;
	}

	/**
	 * Metodo set avaluo observable bien raiz
	 * @param avaluo the avaluo to set
	 */
	public void setAvaluo(DoubleProperty avaluo) {
		this.avaluo = avaluo;
	}

	/**
	 * Metodo get direccion observable bien raiz
	 * @return the direccion
	 */
	public StringProperty getDireccion() {
		return direccion;
	}

	/**
	 * Metodo set direccion observable bien raiz
	 * @param direccion the direccion to set
	 */
	public void setDireccion(StringProperty direccion) {
		this.direccion = direccion;
	}

	/**
	 * Metodo get datos persona observable bien raiz
	 * @return the cedNombpersona
	 */
	public StringProperty getCedNombpersona() {
		return cedNombpersona;
	}

	/**
	 * Metodo set datos persona observable bien raiz
	 * @param cedNombpersona the cedNombpersona to set
	 */
	public void setCedNombpersona(StringProperty cedNombpersona) {
		this.cedNombpersona = cedNombpersona;
	}

	/**
	 * Metodo get persona observable bien raiz
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * Metodo set persona observable bien raiz
	 * @param persona the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	

}
