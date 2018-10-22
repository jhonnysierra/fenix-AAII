/**
 * 
 */
package proyectofenix.escritorio.controlador;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.BienRaiz;
import proyectofenix.escritorio.modelo.BienRaizObservable;
import proyectofenix.escritorio.utilidades.Utilidades;

/**
 * Controlador de la interfaz detalle bien raiz
 * 
 * @author JJ
 * @version 1.0
 */
public class BienRaizControlador {

	/**
	 * table donde se almacena la informacion de los clientes
	 */
	@FXML
	private TableView<BienRaizObservable> tablaBienRaiz;
	/**
	 * hace referencia a la columna con los id de los bienes raices
	 */
	@FXML
	private TableColumn<BienRaizObservable, String> idColumna;
	/**
	 * hace referencia a la columna con la persona que asociada al id
	 */
	@FXML
	private TableColumn<BienRaizObservable, String> personaColumna;
	/**
	 * etiqueta de id
	 */
	@FXML
	private Label txtId;

	/**
	 * etiqueta de avaluo
	 */
	@FXML
	private Label txtAvaluo;

	/**
	 * etiqueta de persona
	 */
	@FXML
	private Label txtPersona;
	/**
	 * etiqueta de direccion
	 */
	@FXML
	private Label txtDireccion;

	/**
	 * instancia del manejador de escenario
	 */
	private ManejadorEscenarios escenarioInicial;

	/**
	 * Metodo constructor
	 */
	public BienRaizControlador() {

	}

	/**
	 * permite carga la informacion en la tabla y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		idColumna.setCellValueFactory(bienraizCelda -> bienraizCelda.getValue().getId());
		personaColumna.setCellValueFactory(bienraizCelda -> bienraizCelda.getValue().getCedNombpersona());

		mostrarDetalleBienRaiz(null);

		tablaBienRaiz.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleBienRaiz(newValue));

	}

	/**
	 * permite obtener una instancia del escenario general
	 * 
	 * @param escenarioInicial
	 */
	public void setEscenarioInicial(ManejadorEscenarios escenarioInicial) {
		this.escenarioInicial = escenarioInicial;
		tablaBienRaiz.setItems(escenarioInicial.getBienraizObservables());
	}

	/**
	 * Permite mostrar la informacion del bien raiz seleccionado
	 * 
	 * @param bienraiz bien raiz observable
	 */
	public void mostrarDetalleBienRaiz(BienRaizObservable bienraiz) {

		if (bienraiz != null) {
			// empleadoObservable = empleado;
			txtId.setText(bienraiz.getId().getValue());
			txtPersona.setText(bienraiz.getCedNombpersona().getValue());
			txtAvaluo.setText(String.format("%.0f", bienraiz.getAvaluo().getValue()));
			txtDireccion.setText(bienraiz.getDireccion().getValue());
		} else {
			txtId.setText("");
			txtAvaluo.setText("");
			txtPersona.setText("");
			txtDireccion.setText("");
		}

	}

	/**
	 * permite mostrar la ventana de editar bien raiz
	 */
	@FXML
	public void editarBienRaiz() {

		int indice = tablaBienRaiz.getSelectionModel().getSelectedIndex();

		BienRaiz bienraiz = tablaBienRaiz.getItems().get(indice).getBienraiz();

		escenarioInicial.cargarEscenarioEditarBienRaiz(bienraiz);
		tablaBienRaiz.refresh();
	}

	@FXML
	public void eliminarBienRaiz() {

		Alert confirmarEliminar = Utilidades.mensajeConfirmar("Eliminar Bien Raiz",
				"¿Realmente desea eliminar el bien raiz?");

		Optional<ButtonType> result = confirmarEliminar.showAndWait();

		if (result.get() == ButtonType.OK) {
			int indice = tablaBienRaiz.getSelectionModel().getSelectedIndex();

			BienRaiz bienraiz = tablaBienRaiz.getItems().get(indice).getBienraiz();

			try {
				if (escenarioInicial.eliminarBienRaiz(bienraiz)) {
					tablaBienRaiz.getItems().remove(indice);
					Utilidades.mostrarMensaje("Eliminar", "El Bien raíz ha sido eliminado con éxito");
				} else {
					Utilidades.mostrarMensaje("Error", "El bien raíz no pudo ser eliminado");
				}
			} catch (ExcepcionesFenix e) {
				e.printStackTrace();
			}

		}
	}

}
