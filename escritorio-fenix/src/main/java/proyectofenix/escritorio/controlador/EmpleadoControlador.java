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
import proyectofenix.escritorio.utilidades.Utilidades;

/**
 * Controlador de la interfaz detalle empleado
 * 
 * @author JJ
 * @version 1.0
 */
public class EmpleadoControlador {

	/**
	 * table donde se almacena la informacion de los clientes
	 */
	@FXML
	private TableView<EmpleadoObservable> tablaEmpleados;
	/**
	 * hace referencia a la columna con las cedulas de los empleados
	 */
	@FXML
	private TableColumn<EmpleadoObservable, String> cedulaColumna;
	/**
	 * hace referencia a la columna de los nombres de los empleados
	 */
	@FXML
	private TableColumn<EmpleadoObservable, String> nombreColumna;
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
	 * Etiqueta de genero
	 */
	@FXML
	private Label txtGenero;

	/**
	 * Etiqueta de telefono
	 */
	@FXML
	private Label txtTelefono;

	/**
	 * Etiqueta de direccion
	 */
	@FXML
	private Label txtDireccion;

	/**
	 * Etiqueta fecha inicio
	 */
	@FXML
	private Label txtFechaInicio;

	/**
	 * Etiqueta fecha fin
	 */
	@FXML
	private Label txtFechaFin;

	/**
	 * Etiqueta salario
	 */
	@FXML
	private Label txtSalario;

	/**
	 * Formateador de fechas
	 */
	private Format formatoFecha;

	/**
	 * instancia del manejador de escenario
	 */
	private ManejadorEscenarios escenarioInicial;

	/**
	 * Empleado Observable
	 */
	//private EmpleadoObservable empleadoObservable;

	/**
	 * Metodo constructor
	 */
	public EmpleadoControlador() {
		formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
	}

	/**
	 * permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {

		cedulaColumna.setCellValueFactory(empleadoCelda -> empleadoCelda.getValue().getCedula());
		nombreColumna.setCellValueFactory(empleadoCelda -> empleadoCelda.getValue().getNombre());

		mostrarDetalleEmpleado(null);

		tablaEmpleados.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostrarDetalleEmpleado(newValue));

	}

	/**
	 * permite obtener una instancia del escenario general
	 * 
	 * @param escenarioInicial
	 */
	public void setEscenarioInicial(ManejadorEscenarios escenarioInicial) {
		this.escenarioInicial = escenarioInicial;
		tablaEmpleados.setItems(escenarioInicial.getEmpleadosObservables());
	}

	/**
	 * Permite mostrar la informacion del empleado seleccionado
	 * 
	 * @param cliente cliente al que se le desea mostrar el detalle
	 */
	public void mostrarDetalleEmpleado(EmpleadoObservable empleado) {

		if (empleado != null) {
			//empleadoObservable = empleado;
			txtCedula.setText(empleado.getCedula().getValue());
			txtNombre.setText(empleado.getNombre().getValue());
			txtApellido.setText(empleado.getApellido().getValue());
			txtEmail.setText(empleado.getEmail().getValue());
			txtClave.setText(empleado.getClave().getValue());
			txtFechaNacimiento.setText(formatoFecha.format(empleado.getFechaNacimiento().getValue()));
			txtGenero.setText(empleado.getGenero().getValue());
			txtTelefono.setText(empleado.getTelefono().getValue().get(0));
			txtDireccion.setText(empleado.getDireccion().getValue());
			txtFechaInicio.setText(formatoFecha.format(empleado.getFechaInicio().getValue()));
			txtFechaFin.setText(formatoFecha.format(empleado.getFechaFin().getValue()));
			txtSalario.setText(empleado.getSalario().getValue().toString());
		} else {
			txtCedula.setText("");
			txtNombre.setText("");
			txtApellido.setText("");
			txtEmail.setText("");
			txtClave.setText("");
			txtFechaNacimiento.setText("");
			txtGenero.setText("");
			txtTelefono.setText("");
			txtDireccion.setText("");
			txtFechaInicio.setText("");
			txtFechaFin.setText("");
			txtSalario.setText("");
		}

	}

	/**
	 * permite mostrar la ventana de agregar empleado
	 */
	@FXML
	public void agregarEmpleado() {
		escenarioInicial.cargarEscenarioCrearEmpleado();
		tablaEmpleados.refresh();
	}

	/**
	 * permite eliminar un cliente seleccionado
	 */
	@FXML
	public void elimiarEmpleado() {

		Alert confirmarEliminar = Utilidades.mensajeConfirmar("Eliminar Empleado",
				"¿Realmente desea eliminar el empleado?");

		Optional<ButtonType> result = confirmarEliminar.showAndWait();

		if (result.get() == ButtonType.OK) {
			int indice = tablaEmpleados.getSelectionModel().getSelectedIndex();

			//System.out.println(tablaEmpleados.getItems().size());

			Empleado empleado = tablaEmpleados.getItems().get(indice).getEmpleado();

			if (escenarioInicial.eliminarEmpleado(empleado)) {
				tablaEmpleados.getItems().remove(indice);
				Utilidades.mostrarMensaje("Eliminar", "El cliente ha sido eliminado con éxito");
			} else {
				Utilidades.mostrarMensaje("Error", "El cliente no pudo ser eliminado");
			}

		}

	}

	/**
	 * permite mostrar la ventana de editar cliente
	 */
	@FXML
	public void editarEmpleado() {

		int indice = tablaEmpleados.getSelectionModel().getSelectedIndex();

		Empleado empleado = tablaEmpleados.getItems().get(indice).getEmpleado();
		// System.out.println("Cliente seleccionado:" + cliente.getCedula());

		escenarioInicial.cargarEscenarioEditarEmpleado(empleado);
		tablaEmpleados.refresh();
	}
	
	/**
	 * permite mostrar la ventana de crear prestamo
	 */
	@FXML
	public void crearPrestamo() {

		int indice = tablaEmpleados.getSelectionModel().getSelectedIndex();

		Empleado empleado = tablaEmpleados.getItems().get(indice).getEmpleado();
		//System.out.println("Empledo seleccionado:" + cliente.getCedula());

		escenarioInicial.cargarEscenarioCrearPrestamo(empleado);
	}

}
