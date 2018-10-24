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
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Prestamo;
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
	 * permite mostrar la ventana de editar prestamo
	 */
	@FXML
	public void editarPrestamo() {

		int indice = tablaPrestamos.getSelectionModel().getSelectedIndex();

		Prestamo prestamo = tablaPrestamos.getItems().get(indice).getPrestamo();
		/*System.out.println("Prestamo seleccionado:" + prestamo.getId());
		System.out.println("Tipo Prestamo seleccionado:" + prestamo.getTipoPrestamo().getId());*/

		escenarioInicial.cargarEscenarioEditarPrestamo(prestamo);
		tablaPrestamos.refresh();
	}
	
	/**
	 * Permite eliminar un prestamo de la bd
	 */
	@FXML
	public void eliminarPrestamo() {

		Alert confirmarEliminar = Utilidades.mensajeConfirmar("Eliminar Prestamo",
				"¿Realmente desea eliminar el prestamo?");

		Optional<ButtonType> result = confirmarEliminar.showAndWait();

		if (result.get() == ButtonType.OK) {
			int indice = tablaPrestamos.getSelectionModel().getSelectedIndex();

			//System.out.println(tablaEmpleados.getItems().size());

			Prestamo prestamo = tablaPrestamos.getItems().get(indice).getPrestamo();

			try {
				if (escenarioInicial.eliminarPrestamo(prestamo)) {
					tablaPrestamos.getItems().remove(indice);
					Utilidades.mostrarMensaje("Eliminar", "El Prestamo ha sido eliminado con éxito");
				}
			} catch (ExcepcionesFenix e) {
				Utilidades.mostrarMensajeError("Eliminar Prestamo", "Error en eliminar prestamo: " + e.getMessage());
				e.printStackTrace();
			}

		}
	}
	
	@FXML
	public void agregarPago() {

		int indice = tablaPrestamos.getSelectionModel().getSelectedIndex();

		Prestamo prestamo = tablaPrestamos.getItems().get(indice).getPrestamo();

		escenarioInicial.cargarEscenarioCrearPago(prestamo);
		
		tablaPrestamos.refresh();
	}
}
