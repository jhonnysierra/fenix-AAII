package proyectofenix.escritorio.controlador;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.BienRaiz;
import proyectofenix.entidades.Persona;

import proyectofenix.escritorio.modelo.BienRaizObservable;
import proyectofenix.escritorio.utilidades.Utilidades;

/**
 * Permite controlar la vista crear_editar bien raiz
 * 
 * @author JJ
 * @version 1.0
 */
public class CrearEditarBienRaizControlador {

	/**
	 * campo para el id
	 */
	@FXML
	private TextField cmpId;
	/**
	 * campo para el persona
	 */
	@FXML
	private TextField cmpPersona;
	/**
	 * campo para el avaluo
	 */
	@FXML
	private TextField cmpAvaluo;

	/**
	 * campo para la direccion
	 */
	@FXML
	private TextField cmpDireccion;

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
	private Stage escenarioBienraiz;

	/**
	 * instancia del manejador de los escenario
	 */
	private ManejadorEscenarios manejador;

	/**
	 * para almacenar bien observables que se recibe desde detalle bien raiz
	 */
	private ObservableList<BienRaizObservable> bienraizObservablesDetalleBienRaiz;

	/**
	 * Indice de la posicion en la lista de bien raiz observables del bien raiz a
	 * editar
	 */
	private int indiceListaBienRaizObservables;

	/**
	 * Persona asociada al bien raiz
	 */
	private Persona persona;

	/**
	 * Bien raiz
	 */
	private BienRaiz bienraiz;

	/**
	 * Caracter para la validacion
	 */
	private char caracter;

	/**
	 * Metodo constructor
	 */
	public CrearEditarBienRaizControlador() {

	}

	/**
	 * 
	 */
	@FXML
	private void initialize() {
		cmpId.requestFocus();
		btnEditar.setVisible(false);
	}

	/**
	 * permite cargar la informacion de un prestamo para realizar una edicion
	 * 
	 * @param prestamo prestamo observable a modificar
	 */
	public void cargarBienRaiz(BienRaizObservable bienraiz) {
		btnAceptar.setVisible(false);
		btnEditar.setVisible(true);
		cmpInfoEncabezado.setText("Por favor edite la información del Bien Raíz");
		cmpId.setDisable(true);
		cmpPersona.setDisable(true);
		cmpAvaluo.requestFocus();

		cmpId.setText(String.valueOf(bienraiz.getId().getValue()));
		cmpPersona.setText(persona.getCedula() + " - " + persona.getNombres() + " " + persona.getApellidos());
		cmpAvaluo.setText(String.format("%.0f", bienraiz.getAvaluo().getValue()));
		cmpDireccion.setText(bienraiz.getDireccion().getValue());

		bienraizObservablesDetalleBienRaiz = manejador.getBienraizObservables();

		for (BienRaizObservable b : bienraizObservablesDetalleBienRaiz) {
			if (b.getId().getValue() == bienraiz.getId().getValue()) {
				indiceListaBienRaizObservables = bienraizObservablesDetalleBienRaiz.indexOf(b);
			}
		}

		// System.out.println("Indice: " + indiceListaPrestamoObservables);

	}

	/**
	 * permite registrar un bien raiz en la base de datos
	 */
	@FXML
	public void registrarBienRaiz() {

		if (validarFormulario()) {
			BienRaiz bienraiz = new BienRaiz();

			bienraiz.setId(cmpId.getText());
			bienraiz.setCiudad(null);
			bienraiz.setAvaluo(Double.parseDouble(cmpAvaluo.getText()));
			bienraiz.setDireccion(cmpDireccion.getText());
			bienraiz.setPersona(persona);

			try {
				if (manejador.agregarBienRaiz(bienraiz)) {
					manejador.agregarBienRaizALista(bienraiz);
					persona.setBienRaiz(bienraiz);
					Utilidades.mostrarMensaje("Registro Bien Raiz", "Registro exitoso!!!");
					escenarioBienraiz.close();
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
	 * Permite editar la informacion de un bien raiz
	 */
	@FXML
	private void editarBienRaiz() {
		if (validarFormulario()) {
			bienraiz.setAvaluo(Double.parseDouble(cmpAvaluo.getText()));
			bienraiz.setDireccion(cmpDireccion.getText());

			if (manejador.modificarBienRaiz(bienraiz)) {
				Utilidades.mostrarMensaje("Edición", "Se editó el bien raíz con éxito!");
				bienraizObservablesDetalleBienRaiz.set(indiceListaBienRaizObservables,
						new BienRaizObservable(bienraiz));
				escenarioBienraiz.close();
			} else {
				Utilidades.mostrarMensajeError("Edición", "Error en edición de bien raíz!");
			}
		} else {
			Utilidades.mostrarMensajeError("Datos incompletos",
					"Debes ingresar todos los datos. Algunos estan vacíos!");
		}

	}

	/**
	 * Permite cerrar la ventana de editar y crear
	 */
	@FXML
	private void cancelar() {
		escenarioBienraiz.close();
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
	 * Permite validar que el texto ingresado solo sean letras, numeros, # o -
	 */
	@FXML
	public void validarDireccion(KeyEvent ke) {
		caracter = ke.getCharacter().charAt(0);
		if (!Character.isAlphabetic(caracter) && !Character.isDigit(caracter) && !Character.isWhitespace(caracter)
				&& !(caracter == '#') && !(caracter == '-')) {
			ke.consume();
		}
	}

	/**
	 * Permite validar el id
	 */
	@FXML
	public void validarId(KeyEvent ke) {
		caracter = ke.getCharacter().charAt(0);
		if (!Character.isDigit(caracter) && !Character.isAlphabetic(caracter)) {
			ke.consume();
		}
	}

	/**
	 * Valida que los campos sean diferentes de vacio
	 * 
	 * @return si todos los campos tienen algo
	 */
	public boolean validarFormulario() {
		if (!cmpId.getText().isEmpty() && !cmpPersona.getText().isEmpty() && !cmpAvaluo.getText().isEmpty()
				&& !cmpDireccion.getText().isEmpty()) {
			return true;

		} else {
			return false;
		}
	}

	/**
	 * Permite cargar el manejador de escenarios
	 * 
	 * @param manejador
	 */
	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
	}

	/**
	 * permite modificar el escenario
	 * 
	 * @param escenarioBienraiz the escenarioBienraiz to set
	 */
	public void setEscenarioBienraiz(Stage escenarioBienraiz) {
		this.escenarioBienraiz = escenarioBienraiz;
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

	public void cargarDatosIniciales() {
		// cmpId.setText(String.valueOf(manejador.consecutivoPrestamo()));
		cmpPersona.setText(persona.getCedula() + " - " + persona.getNombres() + " " + persona.getApellidos());
	}

	/**
	 * @return the bienraiz
	 */
	public BienRaiz getBienraiz() {
		return bienraiz;
	}

	/**
	 * @param bienraiz the bienraiz to set
	 */
	public void setBienraiz(BienRaiz bienraiz) {
		this.bienraiz = bienraiz;
	}

}
