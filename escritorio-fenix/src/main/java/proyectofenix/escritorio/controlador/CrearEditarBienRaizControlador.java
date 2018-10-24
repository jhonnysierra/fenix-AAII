package proyectofenix.escritorio.controlador;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
		cmpInfoEncabezado.setText("Por favor edite la informaci�n del Bien Ra�z");
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

		BienRaiz bienraiz = new BienRaiz();

		bienraiz.setId(cmpId.getText());
		bienraiz.setCiudad(null);
		bienraiz.setAvaluo(Double.parseDouble(cmpAvaluo.getText()));
		bienraiz.setDireccion(cmpDireccion.getText());
		bienraiz.setPersona(persona);

		try {
			if (manejador.agregarBienRaiz(bienraiz)) {
				manejador.agregarBienRaizALista(bienraiz);
				Utilidades.mostrarMensaje("Registro Bien Raiz", "Registro exitoso!!!");
				escenarioBienraiz.close();
			}
		} catch (ExcepcionesFenix e) {
			e.printStackTrace();
		}

	}

	/**
	 * Permite editar la informacion de un bien raiz
	 */
	@FXML
	private void editarBienRaiz() {
		bienraiz.setAvaluo(Double.parseDouble(cmpAvaluo.getText()));
		bienraiz.setDireccion(cmpDireccion.getText());

		if (manejador.modificarBienRaiz(bienraiz)) {
			Utilidades.mostrarMensaje("Edici�n", "Se edit� el bien ra�z con �xito!");
			bienraizObservablesDetalleBienRaiz.set(indiceListaBienRaizObservables, new BienRaizObservable(bienraiz));
			escenarioBienraiz.close();
		} else {
			Utilidades.mostrarMensajeError("Edici�n", "Error en edici�n de bien ra�z!");
		}
	}

	/**
	 * permite cerrar la ventana de editar y crear
	 */
	@FXML
	private void cancelar() {
		escenarioBienraiz.close();
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
