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
import proyectofenix.entidades.Pago;
import proyectofenix.escritorio.modelo.PagoObservable;
import proyectofenix.escritorio.utilidades.Utilidades;

/**
 * Controlador de la interfaz detalle pago
 * 
 * @author JJ
 * @version 1.0
 */
public class PagoControlador {

	/**
	 * table donde se almacena la informacion de los pagos
	 */
	@FXML
	private TableView<PagoObservable> tablaPagos;
	/**
	 * hace referencia a la columna con los id de los pagos
	 */
	@FXML
	private TableColumn<PagoObservable, Integer> idColumna;
	/**
	 * hace referencia a la columna con el id del prestamo asociado el pago
	 */
	@FXML
	private TableColumn<PagoObservable, Integer> prestamoColumna;
	/**
	 * etiqueta de id
	 */
	@FXML
	private Label txtId;

	/**
	 * etiqueta de id prestamo
	 */
	@FXML
	private Label txtIdPrestamo;

	/**
	 * etiqueta de fecha pago
	 */
	@FXML
	private Label txtFechaPago;

	/**
	 * etiqueta de valor pago
	 */
	@FXML
	private Label txtValorPago;

	/**
	 * Formateador de fechas
	 */
	private Format formatoFecha;

	/**
	 * instancia del manejador de escenario
	 */
	private ManejadorEscenarios escenarioInicial;

	/**
	 * Metodo constructor
	 */
	public PagoControlador() {
		formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
	}

	/**
	 * permite carga la informacion en las tablas y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		idColumna.setCellValueFactory(pagoCelda -> pagoCelda.getValue().getId().asObject());
		prestamoColumna.setCellValueFactory(pagoCelda -> pagoCelda.getValue().getId().asObject());

		mostrarDetallePago(null);

		tablaPagos.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetallePago(newValue));

	}

	/**
	 * permite obtener una instancia del escenario general
	 * 
	 * @param escenarioInicial
	 */
	public void setEscenarioInicial(ManejadorEscenarios escenarioInicial) {
		this.escenarioInicial = escenarioInicial;
		tablaPagos.setItems(escenarioInicial.getPagosObservables());
	}

	/**
	 * Permite mostrar la informacion del pago seleccionado
	 * 
	 * @param Pago pago al que se le desea mostrar el detalle
	 */
	public void mostrarDetallePago(PagoObservable pago) {

		if (pago != null) {
			// empleadoObservable = empleado;
			txtId.setText(pago.getId().getValue().toString());
			txtIdPrestamo.setText(pago.getIdPrestamo().getValue().toString());
			txtFechaPago.setText(formatoFecha.format(pago.getFecha().getValue()));
			txtValorPago.setText(pago.getValor().getValue().toString());
		} else {
			txtId.setText("");
			txtIdPrestamo.setText("");
			txtFechaPago.setText("");
			txtValorPago.setText("");
		}

	}

	/**
	 * permite mostrar la ventana de editar pago
	 */
	@FXML
	public void editarPago() {

		int indice = tablaPagos.getSelectionModel().getSelectedIndex();

		Pago pago = tablaPagos.getItems().get(indice).getPago();
		
		escenarioInicial.cargarEscenarioEditarPago(pago);
		tablaPagos.refresh();
	}

	/**
	 * Permite eliminar un pago de la bd
	 */
	@FXML
	public void eliminarPago() {

		Alert confirmarEliminar = Utilidades.mensajeConfirmar("Eliminar Pago",
				"�Realmente desea eliminar el pago?");

		Optional<ButtonType> result = confirmarEliminar.showAndWait();

		if (result.get() == ButtonType.OK) {
			int indice = tablaPagos.getSelectionModel().getSelectedIndex();
			
			Pago pago = tablaPagos.getItems().get(indice).getPago();

			try {
				if (escenarioInicial.eliminarPago(pago)) {
					tablaPagos.getItems().remove(indice);
					Utilidades.mostrarMensaje("Eliminar", "El Pago ha sido eliminado con �xito");
				} else {
					Utilidades.mostrarMensaje("Error", "El Pago no pudo ser eliminado");
				}
			} catch (ExcepcionesFenix e) {
				e.printStackTrace();
			}

		}
	}

}
