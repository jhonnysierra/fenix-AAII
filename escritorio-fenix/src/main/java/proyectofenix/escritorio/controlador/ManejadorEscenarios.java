package proyectofenix.escritorio.controlador;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Administrador;
import proyectofenix.entidades.BienRaiz;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Empleado;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.Prestamo;
import proyectofenix.entidades.TipoPrestamo;
import proyectofenix.escritorio.main.Main;
import proyectofenix.escritorio.modelo.BancoDelegado;
import proyectofenix.escritorio.modelo.BienRaizObservable;
import proyectofenix.escritorio.modelo.ClienteObservable;
import proyectofenix.escritorio.modelo.EmpleadoObservable;
import proyectofenix.escritorio.modelo.PagoObservable;
import proyectofenix.escritorio.modelo.PrestamoObservable;
import proyectofenix.escritorio.utilidades.Utilidades;

/**
 * Permite manejar los escenarios que tiene la aplicacion
 * 
 * @author JJ
 * @version 1.0
 */
public class ManejadorEscenarios {

	/**
	 * contenedor prinpipal de la aplicacion
	 */
	private Stage escenario;
	/**
	 * tipo de panel inicial
	 */
	private BorderPane bordePanel;
	/**
	 * para almacenar clientes observables
	 */
	private ObservableList<ClienteObservable> clientesObservables;

	/**
	 * Lista de empleados observables
	 */
	private ObservableList<EmpleadoObservable> empleadosObservables;

	/**
	 * Lista de prestamos observables
	 */
	private ObservableList<PrestamoObservable> prestamosObservables;

	/**
	 * Lista de bienes raices observables
	 */
	private ObservableList<BienRaizObservable> bienraizObservables;

	/**
	 * Lista de pagos observables
	 */
	private ObservableList<PagoObservable> pagosObservables;

	/**
	 * conexion con capa de negocio
	 */
	private BancoDelegado bancoDelegado;

	/**
	 * recibe el escenario principla de la aplicacion
	 * 
	 * @param escenario inicial
	 */
	public ManejadorEscenarios(Stage escenario) {

		this.escenario = escenario;
		
		bancoDelegado = BancoDelegado.bancoDelegado;
		clientesObservables = FXCollections.observableArrayList();
		empleadosObservables = FXCollections.observableArrayList();
		prestamosObservables = FXCollections.observableArrayList();
		bienraizObservables = FXCollections.observableArrayList();
		pagosObservables = FXCollections.observableArrayList();
		
		
		/*
		 * bancoDelegado = BancoDelegado.bancoDelegado; clientesObservables =
		 * FXCollections.observableArrayList(); empleadosObservables =
		 * FXCollections.observableArrayList(); prestamosObservables =
		 * FXCollections.observableArrayList(); bienraizObservables =
		 * FXCollections.observableArrayList(); pagosObservables =
		 * FXCollections.observableArrayList();
		 * 
		 * try { // se inicializa el escenario escenario.setTitle("Te lo prestamos");
		 * 
		 * // se carga la vista FXMLLoader loader = new FXMLLoader();
		 * loader.setLocation(Main.class.getResource("../vista/inicio.fxml"));
		 * 
		 * bordePanel = (BorderPane) loader.load();
		 * 
		 * // se carga la escena Scene scene = new Scene(bordePanel);
		 * escenario.setScene(scene); escenario.show();
		 * 
		 * // se carga el controlador del inicio InicioControlador inicioControlador =
		 * loader.getController(); inicioControlador.setEscenarioInicial(this);
		 * 
		 * // cargarEscenaDetalleCliente();
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 */
		
		cargarEscenaInicio(this);
	}

	
	/**
	 * Permite cargar la escena de inicio
	 * @param manejador manejador de escenarios
	 */
	public void cargarEscenaLogin(ManejadorEscenarios manejador) {

		try {
			// se inicializa el escenario
			escenario.setTitle("Te lo prestamos");

			// se carga la vista
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../vista/inicio.fxml"));

			bordePanel = (BorderPane) loader.load();

			// se carga la escena
			Scene scene = new Scene(bordePanel);
			escenario.setScene(scene);
			escenario.show();

			// se carga el controlador del inicio
			InicioControlador inicioControlador = loader.getController();
			inicioControlador.setEscenarioInicial(this);

			// cargarEscenaDetalleCliente();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Permite cargar la escena de inicio
	 * 
	 * @param manejador manejador de escenarios
	 */
	public void cargarEscenaInicio(ManejadorEscenarios manejador) {


		try {
			// se inicializa el escenario
			escenario.setTitle("Te lo prestamos");

			// se carga la vista
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../vista/inicio.fxml"));

			bordePanel = (BorderPane) loader.load();

			// se carga la escena
			Scene scene = new Scene(bordePanel);
			escenario.setScene(scene);
			escenario.show();

			// se carga el controlador del inicio
			InicioControlador inicioControlador = loader.getController();
			inicioControlador.setEscenarioInicial(this);

			// cargarEscenaDetalleCliente();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * carga una escena en el centro del escenario
	 */
	public void cargarEscenaDetalleCliente() {

		try {

			clientesObservables = bancoDelegado.listarClientesObservables();

			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(Main.class.getResource("../vista/detalle_cliente.fxml"));
			AnchorPane panelAncho = (AnchorPane) loader2.load();
			bordePanel.setCenter(panelAncho);

			ClienteControlador controlador = loader2.getController();
			controlador.setEscenarioInicial(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Carga la escena de detalle empleado en el centro del escenario.
	 */
	public void cargarEscenaDetalleEmpleado() {

		try {

			empleadosObservables = bancoDelegado.listarEmpleadosObservables();
			// System.out.println("Empleado observables:" + empleadosObservables);

			FXMLLoader loader3 = new FXMLLoader();
			loader3.setLocation(Main.class.getResource("../vista/detalle_empleado.fxml"));
			AnchorPane panelAncho = (AnchorPane) loader3.load();
			bordePanel.setCenter(panelAncho);

			EmpleadoControlador controlador = loader3.getController();
			controlador.setEscenarioInicial(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Carga la escena de detalle prestamo en el centro del escenario.
	 */
	public void cargarEscenaDetallePrestamo() {

		try {

			prestamosObservables = bancoDelegado.listarPrestamosObservables();
			// System.out.println("Prestamos observables:" + prestamosObservables);

			FXMLLoader loader4 = new FXMLLoader();
			loader4.setLocation(Main.class.getResource("../vista/detalle_prestamo.fxml"));
			AnchorPane panelAncho = (AnchorPane) loader4.load();
			bordePanel.setCenter(panelAncho);

			PrestamoControlador controlador = loader4.getController();
			controlador.setEscenarioInicial(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Carga la escena de detalle prestamo por tipo en el centro del escenario.
	 */
	public void cargarEscenaDetallePrestamoPorTipo(int idTipoPrestamo) {

		try {

			prestamosObservables = bancoDelegado.listarPorTipoPrestamosObservables(idTipoPrestamo);
			// System.out.println("Prestamos observables:" + prestamosObservables);

			FXMLLoader loader7 = new FXMLLoader();
			loader7.setLocation(Main.class.getResource("../vista/detalle_prestamo.fxml"));
			AnchorPane panelAncho = (AnchorPane) loader7.load();
			bordePanel.setCenter(panelAncho);

			PrestamoControlador controlador = loader7.getController();
			controlador.setEscenarioInicial(this);
			controlador.cargarDatosInicialesPrestamosPorTipo(tipoPrestamoPorId(idTipoPrestamo).getNombre());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Carga la escena de detalle prestamo en el centro del escenario.
	 */
	public void cargarEscenaDetalleBienRaiz() {

		try {

			bienraizObservables = bancoDelegado.listarBienRaizObservables();
			// System.out.println("Prestamos observables:" + prestamosObservables);

			FXMLLoader loader5 = new FXMLLoader();
			loader5.setLocation(Main.class.getResource("../vista/detalle_bienraiz.fxml"));
			AnchorPane panelAncho = (AnchorPane) loader5.load();
			bordePanel.setCenter(panelAncho);

			BienRaizControlador controlador = loader5.getController();
			controlador.setEscenarioInicial(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void cargarEscenaDetallePago() {

		try {

			pagosObservables = bancoDelegado.listarPagosObservables();
			// System.out.println("Pagos observables:" + pagosObservables.size());

			FXMLLoader loader6 = new FXMLLoader();
			loader6.setLocation(Main.class.getResource("../vista/detalle_pago.fxml"));
			AnchorPane panelAncho = (AnchorPane) loader6.load();
			bordePanel.setCenter(panelAncho);

			PagoControlador controlador = loader6.getController();
			controlador.setEscenarioInicial(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * muestra el escenario para crear un cliente nuevo
	 */
	public void cargarEscenarioCrearCliente() {

		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../vista/crear_cliente.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenarioCrear = new Stage();
			escenarioCrear.setTitle("Registrar Cliente");
			Scene scene = new Scene(page);
			escenarioCrear.setScene(scene);

			// se carga el controlador
			EdicionClienteControlador clienteControlador = loader.getController();
			clienteControlador.setEscenarioEditar(escenarioCrear);
			clienteControlador.setManejador(this);

			// se crea el escenario
			escenarioCrear.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Muestra el escenario para crear un empleado nuevo
	 */
	public void cargarEscenarioCrearEmpleado() {

		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../vista/crear_editar_empleado.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenarioCrear = new Stage();
			escenarioCrear.setTitle("Registrar Empleado");
			Scene scene = new Scene(page);
			escenarioCrear.setScene(scene);

			// se carga el controlador
			CrearEditarEmpleadoControlador empleadoControlador = loader.getController();
			empleadoControlador.setEscenarioEditar(escenarioCrear);
			empleadoControlador.setManejador(this);

			// se crea el escenario
			escenarioCrear.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Muestra el escenario para crear un empleado nuevo
	 */
	public void cargarEscenarioCrearPrestamo(Persona persona) {
		// System.out.println("Cliente recibido:"+ persona.getCedula());
		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../vista/crear_editar_prestamo.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenarioCrear = new Stage();
			escenarioCrear.setTitle("Registrar Prestamo");
			Scene scene = new Scene(page);
			escenarioCrear.setScene(scene);

			// se carga el controlador
			CrearEditarPrestamoControlador prestamoControlador = loader.getController();
			prestamoControlador.setEscenarioPrestamo(escenarioCrear);
			prestamoControlador.setManejador(this);
			prestamoControlador.setPersona(persona);
			prestamoControlador.cargarDatosIniciales();
			/*
			 * prestamoControlador.getCmpId().setText(String.valueOf(consecutivoPrestamo()))
			 * ; prestamoControlador.getCmpPersona() .setText(persona.getCedula() + " - " +
			 * persona.getNombres() + " " + persona.getApellidos());
			 */

			// se crea el escenario
			escenarioCrear.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Carga la escena crear bien raiz
	 * 
	 * @param persona persona asociada al bien raiz
	 */
	public void cargarEscenarioCrearBienRaiz(Persona persona) {

		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../vista/crear_editar_bienraiz.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenarioCrear = new Stage();
			escenarioCrear.setTitle("Crear Bien Raiz");
			Scene scene = new Scene(page);
			escenarioCrear.setScene(scene);

			// se carga el controlador
			CrearEditarBienRaizControlador bienraizControlador = loader.getController();
			bienraizControlador.setEscenarioBienraiz(escenarioCrear);
			bienraizControlador.setManejador(this);
			bienraizControlador.setPersona(persona);
			bienraizControlador.cargarDatosIniciales();

			// se crea el escenario
			escenarioCrear.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void cargarEscenarioCrearPago(Prestamo prestamo) {
		// System.out.println("Cliente recibido:"+ persona.getCedula());
		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../vista/crear_editar_pago.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenarioCrear = new Stage();
			escenarioCrear.setTitle("Registrar Pago");
			Scene scene = new Scene(page);
			escenarioCrear.setScene(scene);

			// se carga el controlador
			CrearEditarPagoControlador pagoControlador = loader.getController();
			pagoControlador.setEscenarioPago(escenarioCrear);
			pagoControlador.setManejador(this);
			pagoControlador.setPrestamo(prestamo);
			pagoControlador.cargarDatosIniciales();

			// se crea el escenario
			escenarioCrear.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Carga la escena de editar cliente
	 * 
	 * @param cliente
	 */
	public void cargarEscenarioEditarCliente(Cliente cliente) {

		try {

			// Se crea un cliente observable
			ClienteObservable clienteObservableEditar = new ClienteObservable(cliente);

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../vista/crear_cliente.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenarioEditar = new Stage();
			escenarioEditar.setTitle("Editar Cliente");
			Scene scene = new Scene(page);
			escenarioEditar.setScene(scene);

			// se carga el controlador
			EdicionClienteControlador clienteControlador = loader.getController();
			clienteControlador.setEscenarioEditar(escenarioEditar);
			clienteControlador.setManejador(this);
			clienteControlador.cargarPersona(clienteObservableEditar);

			// se crea el escenario
			escenarioEditar.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Carga la escena de editar empleado
	 * 
	 * @param empleado empleado a editar
	 */
	public void cargarEscenarioEditarEmpleado(Empleado empleado) {

		try {

			// Se crea un empleado observable
			EmpleadoObservable empleadoObservableEditar = new EmpleadoObservable(empleado);

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../vista/crear_editar_empleado.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenarioEditar = new Stage();
			escenarioEditar.setTitle("Editar Empleado");
			Scene scene = new Scene(page);
			escenarioEditar.setScene(scene);

			// se carga el controlador
			CrearEditarEmpleadoControlador empleadoControlador = loader.getController();
			empleadoControlador.setEscenarioEditar(escenarioEditar);
			empleadoControlador.setManejador(this);
			empleadoControlador.cargarPersona(empleadoObservableEditar);

			// se crea el escenario
			escenarioEditar.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Caraga la escena de editar prestamo
	 * 
	 * @param prestamo prestamo a editar
	 */
	public void cargarEscenarioEditarPrestamo(Prestamo prestamo) {

		try {

			// Se crea un presatmo observable
			PrestamoObservable prestamoObservableEditar = new PrestamoObservable(prestamo);

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../vista/crear_editar_prestamo.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenarioEditar = new Stage();
			escenarioEditar.setTitle("Editar Prestamo");
			Scene scene = new Scene(page);
			escenarioEditar.setScene(scene);

			// se carga el controlador
			CrearEditarPrestamoControlador prestamoControlador = loader.getController();
			prestamoControlador.setEscenarioPrestamo(escenarioEditar);
			prestamoControlador.setManejador(this);
			prestamoControlador.setPersona(prestamo.getPersona());
			prestamoControlador.cargarPrestamo(prestamoObservableEditar);
			prestamoControlador.setPrestamo(prestamo);

			// se crea el escenario
			escenarioEditar.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Caraga la escena de editar bien raiz
	 * 
	 * @param bienraiz bien raiz a editar
	 */
	public void cargarEscenarioEditarBienRaiz(BienRaiz bienraiz) {

		try {

			// Se crea un presatmo observable
			BienRaizObservable bienRaizObservableEditar = new BienRaizObservable(bienraiz);

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../vista/crear_editar_bienraiz.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenarioEditar = new Stage();
			escenarioEditar.setTitle("Editar Bien Raiz");
			Scene scene = new Scene(page);
			escenarioEditar.setScene(scene);

			// se carga el controlador
			CrearEditarBienRaizControlador bienraizControlador = loader.getController();
			bienraizControlador.setEscenarioBienraiz(escenarioEditar);
			bienraizControlador.setManejador(this);
			bienraizControlador.setPersona(bienraiz.getPersona());
			bienraizControlador.cargarBienRaiz(bienRaizObservableEditar);
			bienraizControlador.setBienraiz(bienraiz);
			bienraizControlador.cargarDatosIniciales();

			// se crea el escenario
			escenarioEditar.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param clientesObservables the clientesObservables to set
	 */
	public void setClientesObservables(ObservableList<ClienteObservable> clientesObservables) {
		this.clientesObservables = clientesObservables;
	}

	/**
	 * 
	 * @return clientes observables
	 */
	public ObservableList<ClienteObservable> getClientesObservables() {
		return clientesObservables;
	}

	/**
	 * Metodo get empleados observables
	 * 
	 * @return the empleadosObservables
	 */
	public ObservableList<EmpleadoObservable> getEmpleadosObservables() {
		return empleadosObservables;
	}

	/**
	 * Metodo set empleados observables
	 * 
	 * @param empleadosObservables the empleadosObservables to set
	 */
	public void setEmpleadosObservables(ObservableList<EmpleadoObservable> empleadosObservables) {
		this.empleadosObservables = empleadosObservables;
	}

	/**
	 * Metodo get lista prestamos observables
	 * 
	 * @return the prestamosObservables
	 */
	public ObservableList<PrestamoObservable> getPrestamosObservables() {
		return prestamosObservables;
	}

	/**
	 * Metodo set lista prestamos observables
	 * 
	 * @param prestamosObservables the prestamosObservables to set
	 */
	public void setPrestamosObservables(ObservableList<PrestamoObservable> prestamosObservables) {
		this.prestamosObservables = prestamosObservables;
	}

	/**
	 * Metodo get lista bien raiz observables
	 * 
	 * @return the bienraizObservables
	 */
	public ObservableList<BienRaizObservable> getBienraizObservables() {
		return bienraizObservables;
	}

	/**
	 * Metodo set lista bien raiz observables
	 * 
	 * @param bienraizObservables the bienraizObservables to set
	 */
	public void setBienraizObservables(ObservableList<BienRaizObservable> bienraizObservables) {
		this.bienraizObservables = bienraizObservables;
	}

	/**
	 * Metodo get lista pagos observables
	 * 
	 * @return the pagosObservables
	 */
	public ObservableList<PagoObservable> getPagosObservables() {
		return pagosObservables;
	}

	/**
	 * Metodo set lista pagos observables
	 * 
	 * @param pagosObservables the pagosObservables to set
	 */
	public void setPagosObservables(ObservableList<PagoObservable> pagosObservables) {
		this.pagosObservables = pagosObservables;
	}

	/**
	 * permite agregar una cliente a la lista obsevable
	 * 
	 * @param cliente
	 */
	public void agregarALista(Cliente cliente) {
		clientesObservables.add(new ClienteObservable(cliente));
	}

	/**
	 * permite agregar una empleado a la lista obsevable
	 * 
	 * @param empleado
	 */
	public void agregarEmpleadoALista(Empleado empleado) {
		empleadosObservables.add(new EmpleadoObservable(empleado));
	}

	/**
	 * permite agregar un prestamos a la lista obsevable
	 * 
	 * @param empleado
	 */
	public void agregarPrestamoALista(Prestamo prestamo) {
		prestamosObservables.add(new PrestamoObservable(prestamo));
	}

	/**
	 * permite agregar un bien raiz a la lista obsevable
	 * 
	 * @param bienraiz bien raiz a agregar
	 */
	public void agregarBienRaizALista(BienRaiz bienraiz) {
		bienraizObservables.add(new BienRaizObservable(bienraiz));
	}

	/**
	 * Permite agregar un pago a la lista obsevable
	 * 
	 * @param pago pago a agregar a la lista
	 */
	public void agregarPagoALista(Pago pago) {
		pagosObservables.add(new PagoObservable(pago));
	}

	/**
	 * deveulve una instancia del escenario
	 * 
	 * @return escenario
	 */
	public Stage getEscenario() {
		return escenario;
	}

	/**
	 * permite registrar una persona en la base de datos
	 * 
	 * @param cliente a registrar
	 * @return true si quedo registrado
	 */
	public boolean registrarCliente(Cliente cliente) {
		try {
			return bancoDelegado.agregarCliente(cliente) != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * permite eliminar un cliente
	 * 
	 * @param cliente a ser eliminado
	 * @return true si fue eliminado false si no
	 */
	public boolean eliminarCliente(Cliente cliente) {
		try {
			return bancoDelegado.eliminarCliente(cliente.getCedula());
		} catch (ExcepcionesFenix e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * permite editar una persona en la base de datos
	 * 
	 * @param cliente a editar
	 * @return true si quedo registrado
	 */
	public boolean editarCliente(Cliente cliente) {
		try {
			return bancoDelegado.modificarCliente(cliente) != null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error por: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Permite registrar un empleado en la base de datos
	 * 
	 * @param empleado a registrar
	 * @return true si quedo registrado false si no se registro
	 */
	public boolean registrarEmpleado(Empleado empleado) {
		try {
			return bancoDelegado.agregarEmpleado(empleado) != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Permite editar un empleado en la base de datos
	 * 
	 * @param empleado a editar
	 * @return true si se edito o false si no
	 */
	public boolean editarEmpleado(Empleado empleado) {
		try {
			return bancoDelegado.modificarEmpleado(empleado) != null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error por: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Permite eliminar un empleado
	 * 
	 * @param empleado empleado a eliminar
	 * @return true si se elimino false si no
	 */
	public boolean eliminarEmpleado(Empleado empleado) {
		try {
			return bancoDelegado.eliminarEmpleado(empleado.getCedula());
		} catch (ExcepcionesFenix e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Permite obtener el consecutivo del prestamo nuevo
	 * 
	 * @return consecutivo del prestamo nuevo
	 */
	public int consecutivoPrestamo() {
		try {
			return bancoDelegado.consecutivoPrestamo();
		} catch (ExcepcionesFenix e) {
			// System.out.println("Mesaje manejador: " + e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Busca un tipo de prestamo por id
	 * 
	 * @param idTipoPrestamo
	 * @return tipo de prestamo
	 */
	public TipoPrestamo tipoPrestamoPorId(int idTipoPrestamo) {
		return bancoDelegado.tipoPrestamoPorCodigo(idTipoPrestamo);
	}

	public List<TipoPrestamo> listarTodosTipoPrestamo() {
		return bancoDelegado.listarTodosTipoPrestamo();
	}

	/**
	 * Metodo que permite registrar el prestamo
	 * 
	 * @param prestamo prestamo a registrar
	 * @return true si se registro el prestamo, false si no.
	 * @throws ExcepcionesFenix
	 * @see proyectofenix.escritorio.modelo.BancoDelegado#registrarPrestamo(proyectofenix.entidades.Prestamo)
	 */
	public boolean registrarPrestamo(Prestamo prestamo) throws ExcepcionesFenix {
		try {
			return bancoDelegado.registrarPrestamo(prestamo) != null;
		} catch (ExcepcionesFenix e) {
			Utilidades.mostrarMensajeError("Registro Prestamo", "Error en registro de prestamo: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Metodo que permite eliminar un prestamo
	 * 
	 * @param prestamo prestamo a eliminar
	 * @return true si se elimino o false si no
	 * @throws ExcepcionesFenix si el prestamo a eliminar es null
	 * @see proyectofenix.escritorio.modelo.BancoDelegado#eliminarPrestamo(proyectofenix.entidades.Prestamo)
	 */
	public boolean eliminarPrestamo(Prestamo prestamo) throws ExcepcionesFenix {
		try {
			return bancoDelegado.eliminarPrestamo(prestamo);
		} catch (Exception e) {
			Utilidades.mostrarMensajeError("Eliminar Prestamo", "Error en eliminar prestamo");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Metodo que permite editar un prestamo
	 * 
	 * @param prestamo prestamo a editar
	 * @return prestamo editado
	 */
	public boolean modificarPrestamo(Prestamo prestamo) {
		try {
			return bancoDelegado.modificarPrestamo(prestamo) != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Permite crear un bien raiz asociado a un cliente
	 * 
	 * @param bienraiz bien raiz a agregar
	 * @return bien raiz agregado
	 * @throws ExcepcionesFenix si el identificador ya existe
	 * @see proyectofenix.escritorio.modelo.BancoDelegado#agregarBienRaiz(proyectofenix.entidades.BienRaiz)
	 */
	public boolean agregarBienRaiz(BienRaiz bienraiz) throws ExcepcionesFenix {
		try {
			return bancoDelegado.agregarBienRaiz(bienraiz) != null;
		} catch (ExcepcionesFenix e) {
			Utilidades.mostrarMensajeError("Registro Bien Raiz", "Error en registro: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Metodo que permite eliminar un bien raiz
	 * 
	 * @param bienraiz bien raiz a eliminar
	 * @return true si se elimino o false si no
	 * @throws ExcepcionesFenix
	 * @see proyectofenix.escritorio.modelo.BancoDelegado#eliminarBienRaiz(proyectofenix.entidades.BienRaiz)
	 */
	public boolean eliminarBienRaiz(BienRaiz bienraiz) throws ExcepcionesFenix {
		try {
			return bancoDelegado.eliminarBienRaiz(bienraiz);
		} catch (ExcepcionesFenix e) {
			System.out.println("Error manejador:" + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Metodo qeu permite modificar un bien raiz
	 * 
	 * @param bienraiz bien raiz a modificar
	 * @return true si se modifico o false si no
	 */
	public boolean modificarBienRaiz(BienRaiz bienraiz) {
		try {
			return bancoDelegado.modificarBienRaiz(bienraiz) != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Permite generar el consecutivo de un pago
	 * 
	 * @return consecutivo con del pago
	 * @throws ExcepcionesFenix
	 * @see proyectofenix.escritorio.modelo.BancoDelegado#consecutivoPago()
	 */
	public int consecutivoPago() {
		try {
			return bancoDelegado.consecutivoPago();
		} catch (ExcepcionesFenix e) {
			Utilidades.mostrarMensajeError("Registro Pago", "Error en registro: " + e.getMessage());
			return -1;
		}

	}

	/**
	 * Permite realizar el pago de un prestamo
	 * 
	 * @param pago pago a realizar
	 * @return Pago realizado
	 * @throws ExcepcionesFenix Si el pago no tiene asociado un prestamo, si el id
	 *                          del pago ya existe o si no se ejecuta el registro
	 *                          del pago
	 * @see proyectofenix.escritorio.modelo.BancoDelegado#registrarPagoCuota(proyectofenix.entidades.Pago)
	 */
	public boolean registrarPagoCuota(Pago pago) throws ExcepcionesFenix {
		try {
			return bancoDelegado.registrarPagoCuota(pago) != null;
		} catch (ExcepcionesFenix e) {
			Utilidades.mostrarMensajeError("Registro Pago", "Error en registro: " + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Permite eliminar un pago
	 * 
	 * @param pago pago a eliminar
	 * @return true si se elimino o false si no
	 * @throws ExcepcionesFenix si el pago es null o no se puede eliminar
	 */
	public boolean eliminarPago(Pago pago) throws ExcepcionesFenix {
		try {
			return bancoDelegado.eliminarPago(pago);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Permite validar un adminstrador en el sistema
	 * 
	 * @param cedula      cedula del administrador
	 * @param contrasenia contrasenia del administrador
	 * @return true si es valido o false si no
	 * @throws ExcepcionesFenix
	 * @see proyectofenix.escritorio.modelo.BancoDelegado#login(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean login(String cedula, String contrasenia) throws ExcepcionesFenix {
		try {
			return bancoDelegado.login(cedula, contrasenia);
		} catch (ExcepcionesFenix e) {
			Utilidades.mostrarMensajeError("Login", "Error: " + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Permite buscar un administrador por cedula
	 * 
	 * @param cedula cedula del administrador
	 * @return Administrador encontrado
	 * @throws ExcepcionesFenix si no encuentra un administrador
	 * @see proyectofenix.escritorio.modelo.BancoDelegado#listarAdministradorPorId(java.lang.String)
	 */
	public Administrador listarAdministradorPorId(String cedula) throws ExcepcionesFenix {
		try {
			return bancoDelegado.listarAdministradorPorId(cedula);
		} catch (ExcepcionesFenix e) {
			Utilidades.mostrarMensajeError("Login", "Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	

}
