package proyectofenix.escritorio.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional.TxType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Empleado;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.Persona.Genero;
import proyectofenix.entidades.Prestamo;
import proyectofenix.entidades.TipoPrestamo;
import proyectofenix.escritorio.modelo.EmpleadoObservable;
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
	private Cliente cliente;

	private List<TipoPrestamo> listaTipoPrestamos;

	/**
	 * Metodo constructor
	 */
	public CrearEditarPrestamoControlador() {
		itemsTipoPrestamo = FXCollections.observableArrayList();
		itemsTipoPrestamo.addAll("PRESTAMO DE CONSUMO", "PRESTAMO PERSONAL", "PRESTAMO DE ESTUDIO",
				"PRESTAMO HIPOTECARIO");
	}



	public void cargarDatos() {
		System.out.println("Persona actual:" + cliente);
	}
	/**
	 * 
	 */
	@FXML
	private void initialize() {
		cmpId.requestFocus();
		cmpTipo.getItems().addAll(itemsTipoPrestamo);
		btnEditar.setVisible(false);
		//cmpPersona.setText(persona.getNombres()+" " + persona.getApellidos());
		//cmpId.setText(String.valueOf(manejador.consecutivoPrestamo()));
	}

	/**
	 * permite cargar la informacion de un persona para realizar una edicion
	 * 
	 * @param cliente cliente a editar
	 */
	/*
	 * public void cargarPersona(EmpleadoObservable empleado) {
	 * 
	 * btnAceptar.setVisible(false); btnEditar.setVisible(true);
	 * cmpInfoEncabezado.setText("Por favor edite la información del empleado");
	 * cmpCedula.setDisable(true); cmpEmail.setEditable(false);
	 * cmpNombre.requestFocus();
	 * 
	 * cmpCedula.setText(empleado.getCedula().getValue());
	 * cmpNombre.setText(empleado.getNombre().getValue());
	 * cmpApellido.setText(empleado.getApellido().getValue());
	 * cmpEmail.setText(empleado.getEmail().getValue());
	 * cmpClave.setText(empleado.getClave().getValue());
	 * cmpFechaNacimiento.setValue(Utilidades.pasarALocalDate(empleado.
	 * getFechaNacimiento().getValue())); //
	 * cmpGenero.getSelectionModel().select(empleado.getGenero().getValue()); if
	 * (empleado.getGenero().getValue() == "masculino") {
	 * cmpGenero.getSelectionModel().select(0); } else {
	 * cmpGenero.getSelectionModel().select(1); }
	 * 
	 * cmpTelefono.setText(empleado.getTelefono().getValue().get(0));
	 * cmpDireccion.setText(empleado.getDireccion().getValue());
	 * cmpFechaInicio.setValue(Utilidades.pasarALocalDate(empleado.getFechaInicio().
	 * getValue()));
	 * cmpFechaFin.setValue(Utilidades.pasarALocalDate(empleado.getFechaFin().
	 * getValue()));
	 * cmpSalario.setText(empleado.getSalario().getValue().toString());
	 * 
	 * empleadosObservablesDetalleCliente = manejador.getEmpleadosObservables();
	 * 
	 * for (EmpleadoObservable e : manejador.getEmpleadosObservables()) { if
	 * (e.getCedula().getValue() == empleado.getCedula().getValue()) {
	 * indiceListaEmpleadosObservables =
	 * empleadosObservablesDetalleCliente.indexOf(e); } }
	 * 
	 * // System.out.println("Indice: " + indiceListaClientesObservables);
	 * 
	 * }
	 */

	/**
	 * permite registrar un prestamo en la base de datos
	 */
	@FXML
	public void registrarPrestamo() {

		int seleccionTipo;
		Calendar sumaFecha = Calendar.getInstance();
		Date fechaFin = null;

		TipoPrestamo tipoPrestamo;

		Prestamo prestamo = new Prestamo();
		System.out.println("Cliente:" + cliente.getCedula());
		prestamo.setId(manejador.consecutivoPrestamo());
		prestamo.setPersona((Persona)cliente);
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

		prestamo.setPagos(null);
		
		System.out.println("Fecha fin" +  fechaFin);
		//System.out.println("Persona actual:" + cliente);

/*		if (manejador.registrarPrestamo(prestamo)) {
			manejador.agregarPrestamoALista(prestamo);
			Utilidades.mostrarMensaje("Registro Prestamo", "Registro exitoso!!!");
			escenarioPrestamo.close();
		} else {
			Utilidades.mostrarMensaje("Registro Prestamo", "Error en registro!!!");
		}*/

	}

	/**
	 * Permite editar la informacion de un empleado
	 */
	/*
	 * @FXML private void editarEmpleado() { int seleccionGenero;
	 * 
	 * Empleado empleado = new Empleado(); empleado.setCedula(cmpCedula.getText());
	 * empleado.setNombres(cmpNombre.getText());
	 * empleado.setApellidos(cmpApellido.getText());
	 * empleado.setContrasenia(cmpClave.getText());
	 * empleado.setCorreo(cmpEmail.getText());
	 * empleado.setFecha_nacimiento(Utilidades.pasarADate(cmpFechaNacimiento.
	 * getValue())); empleado.setCiudad(null); telefonos.add(0,
	 * cmpTelefono.getText()); empleado.setTelefonos(telefonos);
	 * empleado.setDireccion(cmpDireccion.getText()); empleado.setEstado("1");
	 * seleccionGenero = cmpGenero.getSelectionModel().getSelectedIndex(); //
	 * System.out.println("Seleccion genero empleado:" + seleccionGenero); if
	 * (seleccionGenero == 0) { empleado.setGenero(Genero.masculino); } else {
	 * empleado.setGenero(Genero.femenino); }
	 * empleado.setFechaInicio(Utilidades.pasarADate(cmpFechaInicio.getValue()));
	 * empleado.setFechaFin(Utilidades.pasarADate(cmpFechaFin.getValue()));
	 * empleado.setSalario(Double.parseDouble(cmpSalario.getText()));
	 * 
	 * if (manejador.editarEmpleado(empleado)) {
	 * Utilidades.mostrarMensaje("Edición", "Se editó el empleado con éxito!");
	 * empleadosObservablesDetalleCliente.set(indiceListaEmpleadosObservables, new
	 * EmpleadoObservable(empleado));
	 * 
	 * escenarioEditar.close(); } else { Utilidades.mostrarMensajeError("Edición",
	 * "Error en edición de empleado!"); } }
	 */

	/**
	 * permite cerrar la ventana de editar y crear
	 */
	@FXML
	private void cancelar() {
		escenarioPrestamo.close();
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
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}


	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}



}
