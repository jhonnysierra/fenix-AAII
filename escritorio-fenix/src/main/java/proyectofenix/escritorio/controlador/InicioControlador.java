/**
 * 
 */
package proyectofenix.escritorio.controlador;


import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

/**
 * Permite controlas la vista inicio
 * @author JJ
 */
public class InicioControlador {

	@FXML
	private MenuBar menuBar;
	
	private ManejadorEscenarios escenarioInicial;
	
	public InicioControlador() {
		
	}
	
	@FXML
	private void initialize() {

	}

	@FXML
	public void cargarGestionarCliente() {
		escenarioInicial.cargarEscenaDetalleCliente();
	}

	@FXML
	public void cerrarAplicacion() {
		escenarioInicial.getEscenario().close();
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
