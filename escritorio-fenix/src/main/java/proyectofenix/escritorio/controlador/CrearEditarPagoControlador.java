package proyectofenix.escritorio.controlador;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Prestamo;
import proyectofenix.escritorio.modelo.PagoObservable;
import proyectofenix.escritorio.modelo.PrestamoObservable;
import proyectofenix.escritorio.utilidades.Utilidades;

/**
 * Permite controlar la vista crear_editar pago
 * 
 * @author JJ
 * @version 1.0
 */
public class CrearEditarPagoControlador {

	/**
	 * campo para la cedula
	 */
	@FXML
	private TextField cmpId;
	/**
	 * campo para el persona
	 */
	@FXML
	private TextField cmpPrestamo;
	/**
	 * campo para el valor
	 */
	@FXML
	private TextField cmpValor;

	/**
	 * campo para la fecha de inicio
	 */
	@FXML
	private DatePicker cmpFecha;

	/**
	 * Campo para informacion
	 */
	@FXML
	private Label cmpInfoEncabezado;

	/**
	 * Boton Aceptar de la ventana edicion empleado y crear empleado
	 */
	@FXML
	private Button btnAceptar;

	/**
	 * Boton Aceptar de la ventana edicion cliente
	 */
	@FXML
	private Button btnEditar;

	/**
	 * representa el escenario en donde se agrega la vista
	 */
	private Stage escenarioPago;

	/**
	 * instancia del manejador de los escenario
	 */
	private ManejadorEscenarios manejador;

	/**
	 * para almacenar prestamos observables que se recibe desde detalle pago
	 */
	private ObservableList<PagoObservable> pagosObservablesDetallePago;

	/**
	 * Indice de la posicion en la lista de prestamos observables del prestamo a
	 * editar
	 */
	private int indiceListaPagoObservables;

	/**
	 * para almacenar prestamos observables que se recibe desde detalle prestamo
	 */
	private ObservableList<PrestamoObservable> prestamosObservablesDetallePrestamo;

	/**
	 * Indice de la posicion en la lista de prestamos observables del prestamo a
	 * editar
	 */
	private int indiceListaPrestamoObservables;

	/**
	 * Pago que se envia para editar
	 */
	private Pago pago;

	/**
	 * Prestamo asociado al pago
	 */
	private Prestamo prestamo;

	/**
	 * Caracter a validar
	 */
	private char caracter;

	/**
	 * Metodo constructor
	 */
	public CrearEditarPagoControlador() {

	}

	/**
	 * 
	 */
	@FXML
	private void initialize() {
		cmpValor.requestFocus();
		btnEditar.setVisible(false);
	}

	/**
	 * permite cargar la informacion de un pago para realizar una edicion
	 * 
	 * @param prestamo prestamo observable a modificar
	 */

	public void cargarPagos(PagoObservable pago) {
		btnAceptar.setVisible(false);
		btnEditar.setVisible(true);
		cmpInfoEncabezado.setText("Por favor edite la informaci�n del pago");
		cmpId.setDisable(true);
		cmpPrestamo.setDisable(true);
		cmpValor.requestFocus();

		cmpId.setText(String.valueOf(pago.getId().getValue()));
		cmpPrestamo.setText(String.valueOf(pago.getIdPrestamo().getValue()));
		cmpValor.setText(pago.getValor().getValue().toString());
		cmpFecha.setValue(Utilidades.pasarALocalDate(pago.getFecha().getValue()));

		pagosObservablesDetallePago = manejador.getPagosObservables();

		for (PagoObservable p : pagosObservablesDetallePago) {
			if (p.getId().getValue() == pago.getId().getValue()) {
				indiceListaPagoObservables = pagosObservablesDetallePago.indexOf(p);
			}
		}

		// System.out.println("Indice: " + indiceListaPrestamoObservables);

	}

	/**
	 * permite registrar un prestamo en la base de datos
	 */
	@FXML
	public void registrarPago() {

		if (validarFormulario()) {
			Pago pago = new Pago();

			pago.setId(manejador.consecutivoPago());
			pago.setPrestamo(prestamo);
			pago.setFecha(Utilidades.pasarADate(cmpFecha.getValue()));
			pago.setValor(Double.parseDouble(cmpValor.getText()));

			try {
				if (manejador.registrarPagoCuota(pago)) {
					manejador.agregarPagoALista(pago);

					prestamosObservablesDetallePrestamo = manejador.getPrestamosObservables();

					for (PrestamoObservable p : prestamosObservablesDetallePrestamo) {
						if (p.getId().getValue() == prestamo.getId()) {
							indiceListaPrestamoObservables = prestamosObservablesDetallePrestamo.indexOf(p);
						}
					}
					prestamo.getPagos().add(pago);
					prestamosObservablesDetallePrestamo.set(indiceListaPrestamoObservables,
							new PrestamoObservable(prestamo));

					Utilidades.mostrarMensaje("Registro Pago", "Registro exitoso!!!");
					escenarioPago.close();
				}
			} catch (ExcepcionesFenix e) {
				Utilidades.mostrarMensajeError("Registro Pago", "Error en registro: " + e.getMessage());
			}

		} else {
			Utilidades.mostrarMensajeError("Datos incompletos",
					"Debes ingresar todos los datos. Algunos estan vac�os!");
		}

	}

	/**
	 * Permite editar la informacion de un pago
	 */

	@FXML
	private void editarPago() {
		if (validarFormulario()) {
			pago.setValor(Double.parseDouble(cmpValor.getText()));
			pago.setFecha(Utilidades.pasarADate(cmpFecha.getValue()));

			try {
				if (manejador.modificarPago(pago)) {
					Utilidades.mostrarMensaje("Edici�n", "Se edit� el pago con �xito!");
					pagosObservablesDetallePago.set(indiceListaPagoObservables, new PagoObservable(pago));
					escenarioPago.close();
				} else {
					Utilidades.mostrarMensajeError("Edici�n", "Error en edici�n de prestamo!");
				}
			} catch (ExcepcionesFenix e) {
				e.printStackTrace();
			}
		} else {
			Utilidades.mostrarMensajeError("Datos incompletos",
					"Debes ingresar todos los datos. Algunos estan vac�os!");
		}

	}

	/**
	 * permite cerrar la ventana de editar y crear
	 */
	@FXML
	private void cancelar() {
		escenarioPago.close();
	}

	/**
	 * Permite validar que el texto ingresado solo sean numeros
	 */
	@FXML
	public void validarSoloNumeros(KeyEvent ke) {
		caracter = ke.getCharacter().charAt(0);
		if (!Character.isDigit(caracter)) {
			ke.consume();
		}
	}

	/**
	 * Valida que los campos sean diferentes de vacio
	 * 
	 * @return si todos los campos tienen algo
	 */
	public boolean validarFormulario() {
		if (!cmpId.getText().isEmpty() && !cmpPrestamo.getText().isEmpty() && !cmpValor.getText().isEmpty()
				&& !(cmpFecha.getValue() == null)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * permite cargar el manejador de escenarios
	 * 
	 * @param manejador
	 */
	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
		this.cmpId.requestFocus();
	}

	/**
	 * permite modificar el escenario
	 * 
	 * @param escenarioEditar
	 */
	public void setEscenarioPago(Stage escenarioPago) {
		this.escenarioPago = escenarioPago;
	}

	/**
	 * Metodo get pago
	 * 
	 * @return the pago
	 */
	public Pago getPago() {
		return pago;
	}

	/**
	 * Metodo set pago
	 * 
	 * @param pago the pago to set
	 */
	public void setPago(Pago pago) {
		this.pago = pago;
	}

	/**
	 * Metodo get prestamo
	 * 
	 * @return the prestamo
	 */
	public Prestamo getPrestamo() {
		return prestamo;
	}

	/**
	 * Metodo set prestamo
	 * 
	 * @param prestamo the prestamo to set
	 */
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	/**
	 * Metodo get campo persona
	 * 
	 * @return the cmpPersona
	 */

	public void cargarDatosIniciales() {
		cmpId.setText(String.valueOf(manejador.consecutivoPago()));
		cmpPrestamo.setText(String.valueOf(prestamo.getId()));
	}

}
