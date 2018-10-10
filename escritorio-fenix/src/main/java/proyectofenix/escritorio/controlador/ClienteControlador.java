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
import proyectofenix.entidades.Cliente;
import proyectofenix.escritorio.modelo.ClienteObservable;
import proyectofenix.escritorio.utilidades.Utilidades;

/**
 * @author EinerZG
 */
public class ClienteControlador {

	/**
	 * table donde se almacena la informacion de los clientes
	 */
	@FXML
	private TableView<ClienteObservable> tablaClientes;
	/**
	 * hace referencia a la columna con las cedulas
	 */
	@FXML
	private TableColumn<ClienteObservable, String> cedulaColumna;
	/**
	 * hace referencia a la columna de los nombres de los clientes
	 */
	@FXML
	private TableColumn<ClienteObservable, String> nombreColumna;
	/**
	 * etiqueta de cedula
	 */
	@FXML
	private Label txtCedula;
	/**
	 * etiqueta de nombre
	 */
	@FXML
	private Label txtNombre;
	/**
	 * etiqueta de apellido
	 */
	@FXML
	private Label txtApellido;
	/**
	 * etiqueta de email
	 */
	@FXML
	private Label txtEmail;
	/**
	 * etiqueta de clave
	 */
	@FXML
	private Label txtClave;
	/**
	 * etiqueta de fecha
	 */
	@FXML
	private Label txtFechaNacimiento;
	/**
	 * instancia del manejador de escenario
	 */
	private ManejadorEscenarios escenarioInicial;

	private ClienteObservable clienteObservable;

	public ClienteControlador() {
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		cedulaColumna.setCellValueFactory(clienteCelda -> clienteCelda.getValue().getCedula());
		nombreColumna.setCellValueFactory(clienteCelda -> clienteCelda.getValue().getNombre());

		mostrarDetalleCliente(null);

		tablaClientes.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleCliente(newValue));

	}

	/**
	 * permite obtener una instancia del escenario general
	 * 
	 * @param escenarioInicial
	 */
	public void setEscenarioInicial(ManejadorEscenarios escenarioInicial) {
		this.escenarioInicial = escenarioInicial;
		tablaClientes.setItems(escenarioInicial.getClientesObservables());
	}

	/**
	 * permite mostrar la informacion del cliente seleccionado
	 * 
	 * @param cliente cliente al que se le desea mostrar el detalle
	 */
	public void mostrarDetalleCliente(ClienteObservable cliente) {

		if (cliente != null) {
			clienteObservable = cliente;
			txtCedula.setText(cliente.getCedula().getValue());
			txtNombre.setText(cliente.getNombre().getValue());
			txtApellido.setText(cliente.getApellido().getValue());
			txtEmail.setText(cliente.getEmail().getValue());
			txtClave.setText(cliente.getClave().getValue());
			txtFechaNacimiento.setText(cliente.getFechaNacimiento().getValue().toString());
		} else {
			txtCedula.setText("");
			txtNombre.setText("");
			txtApellido.setText("");
			txtEmail.setText("");
			txtClave.setText("");
			txtFechaNacimiento.setText("");
		}

	}

	/**
	 * permite mostrar la ventana de agregar cliente
	 */
	@FXML
	public void agregarCliente() {
		escenarioInicial.cargarEscenarioCrearCliente();
		tablaClientes.refresh();
	}

	/**
	 * permite eliminar un cliente seleccionado
	 */
	@FXML
	public void elimiarCliente() {

		Alert confirmarEliminar = Utilidades.mensajeConfirmar("Eliminar Cliente",
				"�Realmente desea eliminar el cliente?");

		Optional<ButtonType> result = confirmarEliminar.showAndWait();
		
		if (result.get() == ButtonType.OK) {
			int indice = tablaClientes.getSelectionModel().getSelectedIndex();

			System.out.println(tablaClientes.getItems().size());

			Cliente cliente = tablaClientes.getItems().get(indice).getCliente();

			if (escenarioInicial.eliminarCliente(cliente)) {
				tablaClientes.getItems().remove(indice);
				Utilidades.mostrarMensaje("Eliminar", "El cliente ha sido eliminado con exito");
			} else {
				Utilidades.mostrarMensaje("Error", "El cliente no pudo ser eliminado");
			}
		}

	}

}
