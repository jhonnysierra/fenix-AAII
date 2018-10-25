/**
 * 
 */
package proyectofenix.escritorio.controlador;


import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import proyectofenix.escritorio.utilidades.Utilidades;

/**
 * Permite controlar la vista inicio
 * @author JJ
 */
public class InicioControlador {

	/**
	 * Barra menu
	 */
	@FXML
	private MenuBar menuBar;
	
	/**
	 * Escenario inicial
	 */
	private ManejadorEscenarios escenarioInicial;
	
	/**
	 * Constructor
	 */
	public InicioControlador() {
		
	}
	
	@FXML
	private void initialize() {

	}

	/**
	 * Permite mostrar la escena de detalle cliente
	 */
	@FXML
	public void cargarGestionarCliente() {
		escenarioInicial.cargarEscenaDetalleCliente();
	}

	/**
	 * Permite mostrar la escena de detalle empleado
	 */
	@FXML
	public void cargarGestionarEmpleado() {
		escenarioInicial.cargarEscenaDetalleEmpleado();
	}
	
	/**
	 * Carga la escena detalle prestamos
	 */
	@FXML
	public void cargarListarPrestamos() {
		escenarioInicial.cargarEscenaDetallePrestamo();
	}
	
	/**
	 * Carga la escena detalle prestamos consumo
	 */
	@FXML
	public void cargarListarPrestamosConsumo() {
		escenarioInicial.cargarEscenaDetallePrestamoPorTipo(1);
	}
	
	/**
	 * Carga la escena detalle prestamos personal
	 */
	@FXML
	public void cargarListarPrestamosPersonal() {
		escenarioInicial.cargarEscenaDetallePrestamoPorTipo(2);
	}
	
	/**
	 * Carga la escena detalle prestamos estudio
	 */
	@FXML
	public void cargarListarPrestamosEstudio() {
		escenarioInicial.cargarEscenaDetallePrestamoPorTipo(3);
	}
	
	/**
	 * Carga la escena detalle prestamos hipotecario
	 */
	@FXML
	public void cargarListarPrestamosHipotecario() {
		escenarioInicial.cargarEscenaDetallePrestamoPorTipo(4);
	}
	
	/**
	 * Carga la escena detalle bien raiz
	 */
	@FXML
	public void cargarListarBienRaiz() {
		escenarioInicial.cargarEscenaDetalleBienRaiz();
	}
	
	/**
	 * Carga la escena detalle pagos
	 */
	@FXML
	public void cargarListarPagos() {
		escenarioInicial.cargarEscenaDetallePago();
	}
	
	/**
	 * Permite cerrar la aplicacación
	 */
	@FXML
	public void cerrarAplicacion() {
		escenarioInicial.getEscenario().close();
	}
	
	/**
	 * Muestra el mensaje de acerca de 
	 */
	@FXML
	public void acercade() {
		Utilidades.mostrarMensaje("Acerca de te lo prestamos fenix", "Esta aplicación fue creada por:\nJHONNY SIERRA PARRA\nJORGE ALEXANDER MESA CASTAÑO");
	}
	
	

	/**
	 * @return the escenarioInicial
	 */
	public ManejadorEscenarios getEscenarioInicial() {
		return escenarioInicial;
	}

	/**
	 * @param escenarioInicial the escenarioInicial to set
	 */
	public void setEscenarioInicial(ManejadorEscenarios escenarioInicial) {
		this.escenarioInicial = escenarioInicial;
	}

}
