package proyectofenix.escritorio.modelo;

import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.Prestamo;
import proyectofenix.entidades.TipoPrestamo;

/**
 * Permite transformar una prestamo en formato observable
 * 
 * @author JJ
 * @version 1.0
 */
public class PrestamoObservable {

	/**
	 * id observable de un prestamo
	 */
	private IntegerProperty id;
	/**
	 * valor observable de un prestamo
	 */
	private DoubleProperty valor;

	/**
	 * fecha de inicio observable de un prestamo
	 */
	private ObjectProperty<Date> fechaInicio;

	/**
	 * fecha de fin observable de un prestamo
	 */
	private ObjectProperty<Date> fechaFin;

	/**
	 * cantidad pagos observable de un prestamo
	 */
	private IntegerProperty cantidadPagos;

	/**
	 * numero cuotas observable de un prestamo
	 */
	private IntegerProperty numeroCuotas;

	/**
	 * nombre persona observable de un prestamo
	 */
	private StringProperty nombrePersona;

	/**
	 * tipo observable de un prestamo
	 */
	private StringProperty tipo;

	/**
	 * Persona asociada al prestamo
	 */
	private Persona persona;

	/**
	 * tipo prestamo
	 */
	private TipoPrestamo tipoPrestamo;

	/**
	 * Pagos asociados al prestamo
	 */
	private Pago pagos;

	/**
	 * Constructor
	 */
	public PrestamoObservable() {

	}

	/**
	 * constructor que genera un prestamo observable con base a un prestamo
	 * 
	 * @param prestamo que se quiere volver observable
	 */
	public PrestamoObservable(Prestamo prestamo) {
		this.id = new SimpleIntegerProperty(prestamo.getId());
		this.valor = new SimpleDoubleProperty(prestamo.getValorPrestamo());
		this.fechaInicio = new SimpleObjectProperty<>(prestamo.getFechaInicio());
		this.fechaFin = new SimpleObjectProperty<>(prestamo.getFechaFin());
		this.cantidadPagos = new SimpleIntegerProperty(prestamo.getPagos().size());
		this.numeroCuotas = new SimpleIntegerProperty(prestamo.getNoCuotas());
		this.nombrePersona = new SimpleStringProperty(
				prestamo.getPersona().getNombres() + " " + prestamo.getPersona().getApellidos());
		this.tipo = new SimpleStringProperty(prestamo.getTipoPrestamo().getNombre());
	}

	/**
	 * Metodo get id prestamo observable
	 * @return the id
	 */
	public IntegerProperty getId() {
		return id;
	}

	/**
	 * Metodo set id prestamo observable
	 * @param id the id to set
	 */
	public void setId(IntegerProperty id) {
		this.id = id;
	}

	/**
	 * Metodo get valor prestamo observable
	 * @return the valor
	 */
	public DoubleProperty getValor() {
		return valor;
	}

	/**
	 * Metodo set valor prestamo observable
	 * @param valor the valor to set
	 */
	public void setValor(DoubleProperty valor) {
		this.valor = valor;
	}

	/**
	 * Metodo get fecha inicio prestamo observable
	 * @return the fechaInicio
	 */
	public ObjectProperty<Date> getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Metodo set fecha inicio prestamo observable
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(ObjectProperty<Date> fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Metodo get fecha fin prestamo observable
	 * @return the fechaFin
	 */
	public ObjectProperty<Date> getFechaFin() {
		return fechaFin;
	}

	/**
	 * Metodo set fecha fin prestamo observable
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(ObjectProperty<Date> fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Metodo get cantida pagos prestamo observable
	 * @return the cantidadPagos
	 */
	public IntegerProperty getCantidadPagos() {
		return cantidadPagos;
	}

	/**
	 * Metodo set cantida pagos prestamo observable
	 * @param cantidadPagos the cantidadPagos to set
	 */
	public void setCantidadPagos(IntegerProperty cantidadPagos) {
		this.cantidadPagos = cantidadPagos;
	}

	/**
	 * Metodo get numero cuotas prestamo observable
	 * @return the numeroCuotas
	 */
	public IntegerProperty getNumeroCuotas() {
		return numeroCuotas;
	}

	/**
	 * Metodo set numero cuotas prestamo observable
	 * @param numeroCuotas the numeroCuotas to set
	 */
	public void setNumeroCuotas(IntegerProperty numeroCuotas) {
		this.numeroCuotas = numeroCuotas;
	}

	/**
	 * Metodo get persona prestamo observable
	 * @return the nombrePersona
	 */
	public StringProperty getNombrePersona() {
		return nombrePersona;
	}

	/**
	 * Metodo set persona prestamo observable
	 * @param nombrePersona the nombrePersona to set
	 */
	public void setNombrePersona(StringProperty nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	/**
	 * Metodo get tipo prestamo observable
	 * @return the tipo
	 */
	public StringProperty getTipo() {
		return tipo;
	}

	/**
	 * Metodo set tipo prestamo observable
	 * @param tipo the tipo to set
	 */
	public void setTipo(StringProperty tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param persona the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * @return the tipoPrestamo
	 */
	public TipoPrestamo getTipoPrestamo() {
		return tipoPrestamo;
	}

	/**
	 * @param tipoPrestamo the tipoPrestamo to set
	 */
	public void setTipoPrestamo(TipoPrestamo tipoPrestamo) {
		this.tipoPrestamo = tipoPrestamo;
	}

	/**
	 * @return the pagos
	 */
	public Pago getPagos() {
		return pagos;
	}

	/**
	 * @param pagos the pagos to set
	 */
	public void setPagos(Pago pagos) {
		this.pagos = pagos;
	}

}
