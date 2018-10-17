package proyectofenix.escritorio.modelo;

import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import proyectofenix.entidades.Empleado;

/**
 * Permite transformar un empleado en formato observable
 * 
 * @author JJ
 * @version 1.0
 */
public class EmpleadoObservable {

	/**
	 * cedula observable de un empleado
	 */
	private StringProperty cedula;
	/**
	 * nombre observable de un empleado
	 */
	private StringProperty nombre;
	/**
	 * apellido observable de un empleado
	 */
	private StringProperty apellido;
	/**
	 * email observable de un empleado
	 */
	private StringProperty email;
	/**
	 * clave observable de un empleado
	 */
	private StringProperty clave;
	/**
	 * fecha de nacimiento observable de un empleado
	 */
	private ObjectProperty<Date> fechaNacimiento;

	/**
	 * Genero observable de un empleado
	 */
	private StringProperty genero;

	/**
	 * telefono observable de un empleado
	 */
	private ListProperty<String> telefono;
	
	/**
	 * Direccion observable de un empleado
	 */
	private StringProperty direccion;

	/**
	 * fecha de inicio de contrato observable de un empleado
	 */
	private ObjectProperty<Date> fechaInicio;
	
	/**
	 * fecha de fin observable de un empleado
	 */
	private ObjectProperty<Date> fechaFin;
	
	/**
	 * salario observable de un empleado
	 */
	private DoubleProperty salario;
	

	/**
	 * Empleado asociado
	 */
	private Empleado empleado;

	/**
	 * Metodo constructor 
	 */
	public EmpleadoObservable() {

	}

	/**
	 * constructor que genera con cliente observable con base a un cliente
	 * 
	 * @param cliente que se quiere volver observable
	 */
	public EmpleadoObservable(Empleado empleado) {		
		this.empleado = empleado;
		this.cedula = new SimpleStringProperty(empleado.getCedula());
		this.nombre = new SimpleStringProperty(empleado.getNombres());
		this.apellido = new SimpleStringProperty(empleado.getApellidos());
		this.email = new SimpleStringProperty(empleado.getCorreo());
		this.clave = new SimpleStringProperty(empleado.getContrasenia());
		this.fechaNacimiento = new SimpleObjectProperty<>(empleado.getFecha_nacimiento());
		this.genero = new SimpleStringProperty(empleado.getGenero().name());
		this.telefono = new SimpleListProperty<>(FXCollections.observableArrayList(empleado.getTelefonos()));
		//telefono.set(FXCollections.observableArrayList(cliente.getTelefonos()));
		this.direccion = new SimpleStringProperty(empleado.getDireccion());
		this.fechaInicio = new SimpleObjectProperty<>(empleado.getFechaInicio());
		this.fechaFin = new SimpleObjectProperty<>(empleado.getFechaFin());
		this.salario = new SimpleDoubleProperty(empleado.getSalario());
			
	}

	/**
	 * Metodo get cedula empleado observable
	 * @return the cedula
	 */
	public StringProperty getCedula() {
		return cedula;
	}

	/**
	 * Metodo set cedula empleado observable
	 * @param cedula the cedula to set
	 */
	public void setCedula(StringProperty cedula) {
		this.cedula = cedula;
	}

	/**
	 * Metodo get nombres empleado observable
	 * @return the nombre
	 */
	public StringProperty getNombre() {
		return nombre;
	}

	/**
	 * Metodo set nombres empleado observable
	 * @param nombre the nombre to set
	 */
	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo get apellidos empleado observable
	 * @return the apellido
	 */
	public StringProperty getApellido() {
		return apellido;
	}

	/**
	 * Metodo set apellidos empleado observable
	 * @param apellido the apellido to set
	 */
	public void setApellido(StringProperty apellido) {
		this.apellido = apellido;
	}

	/**
	 * Metodo get email empleado observable
	 * @return the email
	 */
	public StringProperty getEmail() {
		return email;
	}

	/**
	 * Metodo set email empleado observable
	 * @param email the email to set
	 */
	public void setEmail(StringProperty email) {
		this.email = email;
	}

	/**
	 * Metodo get clave empleado observable
	 * @return the clave
	 */
	public StringProperty getClave() {
		return clave;
	}

	/**
	 * Metodo set clave empleado observable
	 * @param clave the clave to set
	 */
	public void setClave(StringProperty clave) {
		this.clave = clave;
	}

	/**
	 * Metodo get fecha de nacimiento empleado observable
	 * @return the fechaNacimiento
	 */
	public ObjectProperty<Date> getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Metodo set fecha de nacimiento empleado observable
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(ObjectProperty<Date> fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Metodo get genero empleado observable
	 * @return the genero
	 */
	public StringProperty getGenero() {
		return genero;
	}

	/**
	 * Metodo set genero empleado observable
	 * @param genero the genero to set
	 */
	public void setGenero(StringProperty genero) {
		this.genero = genero;
	}

	/**
	 * Metodo get telefonos empleado observable
	 * @return the telefono
	 */
	public ListProperty<String> getTelefono() {
		return telefono;
	}

	/**
	 * Metodo set telefonos empleado observable
	 * @param telefono the telefono to set
	 */
	public void setTelefono(ListProperty<String> telefono) {
		this.telefono = telefono;
	}

	/**
	 * Metodo get direccion empleado observable
	 * @return the direccion
	 */
	public StringProperty getDireccion() {
		return direccion;
	}

	/**
	 * Metodo set direccion empleado observable
	 * @param direccion the direccion to set
	 */
	public void setDireccion(StringProperty direccion) {
		this.direccion = direccion;
	}

	/**
	 * Metodo get fecha inicio de contrato empleado observable
	 * @return the fechaInicio
	 */
	public ObjectProperty<Date> getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Metodo set fecha inicio de contrato empleado observable
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(ObjectProperty<Date> fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Metodo get fecha fin de contrato empleado observable
	 * @return the fechaFin
	 */
	public ObjectProperty<Date> getFechaFin() {
		return fechaFin;
	}

	/**
	 * Metodo set fecha fin de contrato empleado observable
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(ObjectProperty<Date> fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
	/**
	 * Metodo get salario empleado observable
	 * @return the salario
	 */
	public DoubleProperty getSalario() {
		return salario;
	}

	/**
	 * Metodo set salario empleado observable
	 * @param salario the salario to set
	 */
	public void setSalario(DoubleProperty salario) {
		this.salario = salario;
	}

	/**
	 * Metodo get empleado empleado observable
	 * @return the empleado
	 */
	public Empleado getEmpleado() {
		return empleado;
	}

	/**
	 * Metodo set empleado empleado observable
	 * @param empleado the empleado to set
	 */
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

}
