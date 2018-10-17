/**
 * 
 */
package proyectofenix.escritorio.controlador;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import proyectofenix.entidades.Empleado;

import proyectofenix.escritorio.modelo.EmpleadoObservable;
import proyectofenix.escritorio.modelo.PrestamoObservable;
import proyectofenix.escritorio.utilidades.Utilidades;

/**
 * Controlador de la interfaz detalle prestamo
 * 
 * @author JJ
 * @version 1.0
 */
public class PrestamoControlador {

	/**
	 * table donde se almacena la informacion de los clientes
	 */
	@FXML
	private TableView<PrestamoObservable> tablaPrestamos;
	/**
	 * hace referencia a la columna con los id de los prestamos
	 */
	@FXML
	private TableColumn<PrestamoObservable, Integer> idColumna;
	/**
	 * hace referencia a la columna con la cedula de la persona que realizo el
	 * prestamo
	 */
	@FXML
	private TableColumn<PrestamoObservable, String> personaColumna;
	/**
	 * etiqueta de id
	 */
	@FXML
	private Label txtId;
	
	/**
	 * etiqueta de valor
	 */
	@FXML
	private Label txtValor;
	
	/**
	 * etiqueta de fecha inicio
	 */
	@FXML
	private Label txtFechaInicio;
	
	/**
	 * etiqueta de fecha fin
	 */
	@FXML
	private Label txtFechaFin;
	/**
	 * etiqueta de cantidad pagos
	 */
	@FXML
	private Label txtCantidadPagos;
	/**
	 * etiqueta de numero cuotas
	 */
	@FXML
	private Label txtNumeroCuotas;
	
	/**
	 * etiqueta de tipo prestamo
	 */
	@FXML
	private Label txtTipo;
	
	/**
	 * Etiqueta de nombre persona
	 */
	@FXML
	private Label txtNombrePersona;

	
	/**
	 * Formateador de fechas
	 */
	private Format formatoFecha;

	/**
	 * instancia del manejador de escenario
	 */
	private ManejadorEscenarios escenarioInicial;

	/**
	 * Prestamo Observable
	 */
	// private PrestamoObservable prestamoObservable;

	/**
	 * Metodo constructor
	 */
	public PrestamoControlador() {
		formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		idColumna.setCellValueFactory(prestamoCelda -> prestamoCelda.getValue().getId().asObject());
		personaColumna.setCellValueFactory(prestamoCelda -> prestamoCelda.getValue().getNombrePersona());

		mostrarDetallePrestamo(null);

		tablaPrestamos.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetallePrestamo(newValue));

	}

	/**
	 * permite obtener una instancia del escenario general
	 * 
	 * @param escenarioInicial
	 */
	public void setEscenarioInicial(ManejadorEscenarios escenarioInicial) {
		this.escenarioInicial = escenarioInicial;
		tablaPrestamos.setItems(escenarioInicial.getPrestamosObservables());
	}

	/**
	 * Permite mostrar la informacion del empleado seleccionado
	 * 
	 * @param cliente cliente al que se le desea mostrar el detalle
	 */
	public void mostrarDetallePrestamo(PrestamoObservable prestamo) {

		if (prestamo != null) {
			// empleadoObservable = empleado;
			txtId.setText(prestamo.getId().getValue().toString());
			txtValor.setText(prestamo.getValor().getValue().toString());
			txtFechaInicio.setText(formatoFecha.format(prestamo.getFechaInicio().getValue()));
			txtFechaFin.setText(formatoFecha.format(prestamo.getFechaFin().getValue()));
			txtCantidadPagos.setText(prestamo.getCantidadPagos().getValue().toString());
			txtNumeroCuotas.setText(prestamo.getNumeroCuotas().getValue().toString());
			txtNombrePersona.setText(prestamo.getNombrePersona().getValue());
			txtTipo.setText(prestamo.getTipo().getValue());

		} else {
			txtId.setText("");
			txtValor.setText("");
			txtFechaInicio.setText("");
			txtFechaFin.setText("");
			txtCantidadPagos.setText("");
			txtNumeroCuotas.setText("");
			txtNombrePersona.setText("");
			txtTipo.setText("");
		}

	}
	
	/**
	 * permite mostrar la ventana de agregar prestamo
	 */
	@FXML
	public void agregarPrestamo() {
		escenarioInicial.cargarEscenarioCrearEmpleado();
		tablaPrestamos.refresh();
	}

/*	*//**
	 * permite eliminar un cliente seleccionado
	 *//*
	@FXML
	public void elimiarEmpleado() {

		Alert confirmarEliminar = Utilidades.mensajeConfirmar("Eliminar Empleado",
				"¿Realmente desea eliminar el empleado?");

		Optional<ButtonType> result = confirmarEliminar.showAndWait();

		if (result.get() == ButtonType.OK) {
			int indice = tablaPrestamos.getSelectionModel().getSelectedIndex();

			// System.out.println(tablaEmpleados.getItems().size());

			Empleado empleado = tablaPrestamos.getItems().get(indice).getEmpleado();

			if (escenarioInicial.eliminarEmpleado(empleado)) {
				tablaPrestamos.getItems().remove(indice);
				Utilidades.mostrarMensaje("Eliminar", "El cliente ha sido eliminado con éxito");
			} else {
				Utilidades.mostrarMensaje("Error", "El cliente no pudo ser eliminado");
			}

		}

	}

	*//**
	 * permite mostrar la ventana de editar cliente
	 *//*
	@FXML
	public void editarEmpleado() {

		int indice = tablaPrestamos.getSelectionModel().getSelectedIndex();

		Empleado empleado = tablaPrestamos.getItems().get(indice).getEmpleado();
		// System.out.println("Cliente seleccionado:" + cliente.getCedula());

		escenarioInicial.cargarEscenarioEditarEmpleado(empleado);
		tablaPrestamos.refresh();
	}*/

}
