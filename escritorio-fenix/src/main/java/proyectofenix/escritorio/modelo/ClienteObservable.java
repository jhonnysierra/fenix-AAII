package proyectofenix.escritorio.modelo;

import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Persona;

/**
 * Permite transformar una persona en formato observable
 * 
 * @author EinerZG
 * @version 1.0
 */
public class ClienteObservable {

	/**
	 * cedula observable de un cliente
	 */
	private StringProperty cedula;
	/**
	 * nombre observable de una persona
	 */
	private StringProperty nombre;
	/**
	 * apellido observable de un cliente
	 */
	private StringProperty apellido;
	/**
	 * email observable de un cliente
	 */
	private StringProperty email;
	/**
	 * clave observable de un cliente
	 */
	private StringProperty clave;
	/**
	 * fecha de nacimiento observable de un cliente
	 */
	private ObjectProperty<Date> fechaNacimiento;
	/**
	 * cliente asociado
	 */
	private Cliente cliente;

	/**
	 * 
	 * @param cedula
	 * @param nombre
	 */
	public ClienteObservable(String cedula, String nombre) {

		this.cedula = new SimpleStringProperty(cedula);
		this.nombre = new SimpleStringProperty(nombre);

		this.apellido = new SimpleStringProperty("Algo");
		this.email = new SimpleStringProperty("algo@mail.com");
		this.clave = new SimpleStringProperty("12345");
		this.fechaNacimiento = new SimpleObjectProperty<>(new Date());

	}

	/**
	 * constructor que genera con cliente observable con base a un cliente
	 * 
	 * @param cliente que se quiere volver observable
	 */
	public ClienteObservable(Persona cliente) {

		this.cliente = (Cliente) cliente;
		this.cedula = new SimpleStringProperty(cliente.getCedula());
		this.nombre = new SimpleStringProperty(cliente.getNombres());
		this.apellido = new SimpleStringProperty(cliente.getApellidos());
		this.email = new SimpleStringProperty(cliente.getCorreo());
		this.clave = new SimpleStringProperty(cliente.getContrasenia());
		this.fechaNacimiento = new SimpleObjectProperty<>(cliente.getFecha_nacimiento());

	}

	/**
	 * permite generar una instanci usando todos los clientes
	 * 
	 * @param cedula
	 * @param nombre
	 * @param apellido
	 * @param email
	 * @param clave
	 * @param fecha
	 */
	public ClienteObservable(String cedula, String nombre, String apellido, String email, String clave, Date fecha) {

		this.cedula = new SimpleStringProperty(cedula);
		this.nombre = new SimpleStringProperty(nombre);
		this.apellido = new SimpleStringProperty(apellido);
		this.email = new SimpleStringProperty(email);
		this.clave = new SimpleStringProperty(clave);
		this.fechaNacimiento = new SimpleObjectProperty<>(fecha);

	}

	/**
	 * @return the cedula
	 */
	public StringProperty getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(StringProperty cedula) {
		this.cedula = cedula;
	}

	/**
	 * @return the nombre
	 */
	public StringProperty getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellido
	 */
	public StringProperty getApellido() {
		return apellido;
	}

	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(StringProperty apellido) {
		this.apellido = apellido;
	}

	/**
	 * @return the email
	 */
	public StringProperty getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(StringProperty email) {
		this.email = email;
	}

	/**
	 * @return the clave
	 */
	public StringProperty getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(StringProperty clave) {
		this.clave = clave;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public ObjectProperty<Date> getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(ObjectProperty<Date> fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
