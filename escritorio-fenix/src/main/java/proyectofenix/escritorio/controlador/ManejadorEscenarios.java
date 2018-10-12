package proyectofenix.escritorio.controlador;

import java.io.IOException;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Persona;
import proyectofenix.escritorio.main.Main;
import proyectofenix.escritorio.modelo.BancoDelegado;
import proyectofenix.escritorio.modelo.ClienteObservable;
/**
 * Permite manejar los escenarios que tiene la aplicacion
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
	 * conexion con capa de negocio
	 */
	private BancoDelegado bancoDelegado;
	
	/**
	 * recibe el escenario principla de la aplicacion
	 * 
	 * @param escenario
	 *            inicial
	 */
	public ManejadorEscenarios(Stage escenario) {

		this.escenario = escenario;

		bancoDelegado = BancoDelegado.bancoDelegado;
		clientesObservables = FXCollections.observableArrayList();

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

			
			//se carga el controlador del inicio
			InicioControlador inicioControlador = loader.getController();
			inicioControlador.setEscenarioInicial(this);
			
			//cargarEscenaDetalleCliente();

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
			
			//se carga el controlador
			EdicionClienteControlador clienteControlador = loader.getController();
			clienteControlador.setEscenarioEditar(escenarioCrear);
			clienteControlador.setManejador(this);
			
			//se crea el escenario
			escenarioCrear.showAndWait();
			
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void cargarEscenarioEditarCliente(Cliente cliente) {

		try {
			
			//Se crea un cliente observable
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
			
			//se carga el controlador
			EdicionClienteControlador clienteControlador = loader.getController();
			clienteControlador.setEscenarioEditar(escenarioEditar);
			clienteControlador.setManejador(this);
			clienteControlador.cargarPersona(clienteObservableEditar);
		
			
			//se crea el escenario
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
	 * permite agrega una cliente a la lista obsevable
	 * @param cliente
	 */
	public void agregarALista(Cliente cliente) {
		clientesObservables.add(new ClienteObservable(cliente));
	}
	
	/**
	 * deveulve una instancia del escenario
	 * @return escenario
	 */
	public Stage getEscenario() {
		return escenario;
	}

	/**
	 * permite registrar una persona en la base de datos
	 * @param cliente a registrar
	 * @return true si quedo registrado
	 */
	public boolean registrarCliente(Cliente cliente){
		try {
			return bancoDelegado.agregarCliente(cliente)!=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * permite eliminar un cliente 
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
	 * @param cliente a editar
	 * @return true si quedo registrado
	 */
	public boolean editarCliente(Cliente cliente){
		try {
			return bancoDelegado.modificarCliente(cliente)!=null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error por: " + e.getMessage());
		}
		return false;
	}
	
}
