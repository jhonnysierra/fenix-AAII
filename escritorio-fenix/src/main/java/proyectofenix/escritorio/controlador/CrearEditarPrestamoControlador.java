package proyectofenix.escritorio.controlador;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Persona;

import proyectofenix.entidades.Prestamo;
import proyectofenix.entidades.TipoPrestamo;
import proyectofenix.escritorio.modelo.PrestamoObservable;
import proyectofenix.escritorio.utilidades.Utilidades;

/**
 * Permite controlar la vista crear_editar prestamo
 * 
 * @author JJ
 * @version 1.0
 */
public class CrearEditarPrestamoControlador {

	/**
	 * campo para la cedula
	 */
	@FXML
	private TextField cmpId;
	/**
	 * campo para el persona
	 */
	@FXML
	private TextField cmpPersona;
	/**
	 * campo para el valor
	 */
	@FXML
	private TextField cmpValor;

	/**
	 * campo para la fecha de inicio
	 */
	@FXML
	private DatePicker cmpFechaInicio;

	/**
	 * campo para el numero de cuotas
	 */
	@FXML
	private TextField cmpNumeroCuotas;

	/**
	 * Combobox para el tipo de prestamo
	 */
	@FXML
	private ComboBox<String> cmpTipo;

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
	private Stage escenarioPrestamo;

	/**
	 * instancia del manejador de los escenario
	 */
	private ManejadorEscenarios manejador;

	/**
	 * Lista observable con las opciones del tipo de prestamo
	 */
	private ObservableList<String> itemsTipoPrestamo;

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
	 * Persona asociada al prestamo
	 */
	private Persona persona;

	/**
	 * Prestamo que se envia para editar
	 */
	private Prestamo prestamo;

	/**
	 * Caracter a validar
	 */
	private static char caracter;

	// private List<TipoPrestamo> listaTipoPrestamos;

	/**
	 * Metodo constructor
	 */
	public CrearEditarPrestamoControlador() {
		itemsTipoPrestamo = FXCollections.observableArrayList();
		itemsTipoPrestamo.addAll("PRESTAMO DE CONSUMO", "PRESTAMO PERSONAL", "PRESTAMO DE ESTUDIO",
				"PRESTAMO HIPOTECARIO");
	}

	/**
	 * 
	 */
	@FXML
	private void initialize() {
		cmpId.requestFocus();
		cmpTipo.getItems().addAll(itemsTipoPrestamo);
		btnEditar.setVisible(false);
	}

	/**
	 * permite cargar la informacion de un prestamo para realizar una edicion
	 * 
	 * @param prestamo prestamo observable a modificar
	 */
	public void cargarPrestamo(PrestamoObservable prestamo) {
		btnAceptar.setVisible(false);
		btnEditar.setVisible(true);
		cmpInfoEncabezado.setText("Por favor edite la información del prestamo");
		cmpId.setDisable(true);
		cmpPersona.setEditable(false);
		cmpValor.requestFocus();

		cmpId.setText(String.valueOf(prestamo.getId().getValue()));
		cmpPersona.setText(persona.getCedula() + " - " + persona.getNombres() + " " + persona.getApellidos());
		cmpValor.setText(prestamo.getValor().getValue().toString());
		cmpFechaInicio.setValue(Utilidades.pasarALocalDate(prestamo.getFechaInicio().getValue()));
		cmpNumeroCuotas.setText(String.valueOf(prestamo.getNumeroCuotas().getValue()));
		cmpTipo.getSelectionModel().select(prestamo.getPrestamo().getTipoPrestamo().getId() - 1);

		prestamosObservablesDetallePrestamo = manejador.getPrestamosObservables();

		for (PrestamoObservable p : prestamosObservablesDetallePrestamo) {
			if (p.getId().getValue() == prestamo.getId().getValue()) {
				indiceListaPrestamoObservables = prestamosObservablesDetallePrestamo.indexOf(p);
			}
		}

		// System.out.println("Indice: " + indiceListaPrestamoObservables);

	}

	/**
	 * permite registrar un prestamo en la base de datos
	 */
	@FXML
	public void registrarPrestamo() {
		int seleccionTipo;

		if (validarFormulario()) {
			Calendar sumaFecha = Calendar.getInstance();
			Date fechaFin = null;

			TipoPrestamo tipoPrestamo;
			List<Pago> listaPagos = new ArrayList<Pago>();

			Prestamo prestamo = new Prestamo();

			prestamo.setId(manejador.consecutivoPrestamo());
			prestamo.setPersona(persona);
			prestamo.setValorPrestamo(Double.parseDouble(cmpValor.getText()));
			prestamo.setFechaInicio(Utilidades.pasarADate(cmpFechaInicio.getValue()));
			prestamo.setNoCuotas(Integer.parseInt(cmpNumeroCuotas.getText()));

			sumaFecha.setTime(prestamo.getFechaInicio());
			sumaFecha.add(Calendar.MONTH, prestamo.getNoCuotas());
			fechaFin = sumaFecha.getTime();

			prestamo.setFechaFin(fechaFin);

			seleccionTipo = cmpTipo.getSelectionModel().getSelectedIndex();
			tipoPrestamo = manejador.tipoPrestamoPorId(seleccionTipo + 1);
			prestamo.setTipoPrestamo(tipoPrestamo);

			prestamo.setPagos(listaPagos);

			try {
				if (manejador.registrarPrestamo(prestamo)) {
					manejador.agregarPrestamoALista(prestamo);
					Utilidades.mostrarMensaje("Registro Prestamo", "Registro exitoso!!!");
					escenarioPrestamo.close();
				}
			} catch (ExcepcionesFenix e) {
				e.printStackTrace();
			}
		} else {
			Utilidades.mostrarMensajeError("Datos incompletos",
					"Debes ingresar todos los datos. Algunos estan vacíos!");
		}

	}

	/**
	 * Permite editar la informacion de un prestamo
	 */

	@FXML
	private void editarPrestamo() {
		int seleccionTipo;

		if (validarFormulario()) {
			Calendar sumaFecha = Calendar.getInstance();
			Date fechaFin = null;

			TipoPrestamo tipoPrestamo;

			prestamo.setValorPrestamo(Double.parseDouble(cmpValor.getText()));
			prestamo.setFechaInicio(Utilidades.pasarADate(cmpFechaInicio.getValue()));
			prestamo.setNoCuotas(Integer.parseInt(cmpNumeroCuotas.getText()));

			sumaFecha.setTime(prestamo.getFechaInicio());
			sumaFecha.add(Calendar.MONTH, prestamo.getNoCuotas());
			fechaFin = sumaFecha.getTime();

			prestamo.setFechaFin(fechaFin);

			seleccionTipo = cmpTipo.getSelectionModel().getSelectedIndex();
			tipoPrestamo = manejador.tipoPrestamoPorId(seleccionTipo + 1);
			prestamo.setTipoPrestamo(tipoPrestamo);

			if (manejador.modificarPrestamo(prestamo)) {
				Utilidades.mostrarMensaje("Edición", "Se editó el prestamo con éxito!");
				prestamosObservablesDetallePrestamo.set(indiceListaPrestamoObservables,
						new PrestamoObservable(prestamo));
				escenarioPrestamo.close();
			} else {
				Utilidades.mostrarMensajeError("Edición", "Error en edición de prestamo!");
			}
		} else {
			Utilidades.mostrarMensajeError("Datos incompletos",
					"Debes ingresar todos los datos. Algunos estan vacíos!");
		}

	}

	/**
	 * permite cerrar la ventana de editar y crear
	 */
	@FXML
	private void cancelar() {
		escenarioPrestamo.close();
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
		if (!cmpId.getText().isEmpty() && !cmpPersona.getText().isEmpty() && !cmpValor.getText().isEmpty()
				&& !(cmpFechaInicio.getValue() == null) && !cmpNumeroCuotas.getText().isEmpty()
				&& !cmpTipo.getSelectionModel().isEmpty()) {
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
	public void setEscenarioPrestamo(Stage escenarioEditar) {
		this.escenarioPrestamo = escenarioEditar;
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
	 * @return the cmpId
	 */
	public TextField getCmpId() {
		return cmpId;
	}

	/**
	 * @param cmpId the cmpId to set
	 */
	public void setCmpId(TextField cmpId) {
		this.cmpId = cmpId;
	}

	/**
	 * Metodo get campo persona
	 * 
	 * @return the cmpPersona
	 */
	public TextField getCmpPersona() {
		return cmpPersona;
	}

	/**
	 * Metodo set campo persona
	 * 
	 * @param cmpPersona the cmpPersona to set
	 */
	public void setCmpPersona(TextField cmpPersona) {
		this.cmpPersona = cmpPersona;
	}

	/**
	 * Metodo get prestamo controlador crear_editar
	 * 
	 * @return the prestamo
	 */
	public Prestamo getPrestamo() {
		return prestamo;
	}

	/**
	 * Metodo set prestamo controlador crear_editar
	 * 
	 * @param prestamo the prestamo to set
	 */
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	/**
	 * Carga datos iniciales en el escenario
	 */
	public void cargarDatosIniciales() {
		cmpId.setText(String.valueOf(manejador.consecutivoPrestamo()));
		cmpPersona.setText(persona.getCedula() + " - " + persona.getNombres() + " " + persona.getApellidos());
	}

}
