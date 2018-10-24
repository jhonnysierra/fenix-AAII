package proyectofenix.escritorio.modelo;

import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import proyectofenix.entidades.Pago;

/**
 * Permite transformar una Pago en formato observable
 * 
 * @author JJ
 * @version 1.0
 */
public class PagoObservable {

	/**
	 * id observable de un pago
	 */
	private IntegerProperty id;
	
	/**
	 * id de prestamo observable de un pago
	 */
	private IntegerProperty idPrestamo;
	
	/**
	 * valor observable de un pago
	 */
	private DoubleProperty valor;

	/**
	 * fecha de pago observable de un pago
	 */
	private ObjectProperty<Date> fecha;

	/**
	 * Pagos asociado al prestamo
	 */
	private Pago pago;
	

	/**
	 * Constructor
	 */
	public PagoObservable() {

	}

	/**
	 * constructor que genera un pago observable con base a un pago
	 * 
	 * @param Pago que se quiere volver observable
	 */
	public PagoObservable(Pago pago) {
		this.pago=pago;
		
		this.id = new SimpleIntegerProperty(pago.getId());
		this.fecha = new SimpleObjectProperty<>(pago.getFecha());
		this.valor = new SimpleDoubleProperty(pago.getValor());
		this.idPrestamo = new SimpleIntegerProperty(pago.getPrestamo().getId());
	}

	/**
	 * Metodo get id pago observable
	 * @return the id
	 */
	public IntegerProperty getId() {
		return id;
	}

	/**
	 * Metodo set id pago observable
	 * @param id the id to set
	 */
	public void setId(IntegerProperty id) {
		this.id = id;
	}

	/**
	 * Metodo get id prestamo pago observable
	 * @return the idPrestamo
	 */
	public IntegerProperty getIdPrestamo() {
		return idPrestamo;
	}

	/**
	 * Metodo set id prestamo pago observable
	 * @param idPrestamo the idPrestamo to set
	 */
	public void setIdPrestamo(IntegerProperty idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	/**
	 * Metodo get valor pago observable
	 * @return the valor
	 */
	public DoubleProperty getValor() {
		return valor;
	}

	/**
	 * Metodo set valor pago observable
	 * @param valor the valor to set
	 */
	public void setValor(DoubleProperty valor) {
		this.valor = valor;
	}

	/**
	 * Metodo get fecha pago observable
	 * @return the fecha
	 */
	public ObjectProperty<Date> getFecha() {
		return fecha;
	}

	/**
	 * Metodo set fecha pago observable
	 * @param fecha the fecha to set
	 */
	public void setFecha(ObjectProperty<Date> fecha) {
		this.fecha = fecha;
	}

	/**
	 * Metodo get Pago pago observable
	 * @return the pago
	 */
	public Pago getPago() {
		return pago;
	}

	/**
	 * Metodo set Pago pago observable
	 * @param pago the pago to set
	 */
	public void setPago(Pago pago) {
		this.pago = pago;
	}
	
}
