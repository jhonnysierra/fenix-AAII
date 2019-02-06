package proyectofenix.web;

import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyecto.fenix.excepciones.InformacionNoEncontradaException;
import proyectofenix.entidades.Asesoria;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Empleado;
import proyectofenix.entidades.TipoAsesoria;
import proyectofenix.negocio.ClienteEJB;

/**
 * Bean  para registrar una pregunta asesoria
 * @author JJ
 * @version 1.0
 */
/**
 * @author JHONNY
 *
 */
@FacesConfig(version = Version.JSF_2_3)
@Named(value = "preguntaBean")
@ApplicationScoped
public class PreguntaBean {

	@EJB
	private ClienteEJB clienteEJB;

	/**
	 * id de la asesoria
	 */
	private int id;

	/**
	 * cedula cliente
	 */
	private String cedula;

	/**
	 * cedula empleado
	 */
	private String empleado;

	/**
	 * id del tipo de asesoria solicitada
	 */
	private int tipo_asesoria;

	/**
	 * Fecha de la asesoria
	 */
	private Date fecha;

	/**
	 * Permite registrar una asesoria en la bd
	 * 
	 * @return ruta que muestra la informacion detallada de la asesoria
	 */
	public String crearAsesoria() {

		Asesoria asesoria = new Asesoria();
		asesoria.setId(id);
		asesoria.setFecha(fecha);

		Cliente cliente = new Cliente();
		Empleado empleado = new Empleado();
		TipoAsesoria tipo = new TipoAsesoria();

/*		try {
			System.out.println("Entro al try de cliente");
			cliente = clienteEJB.buscarcliente(cedula);
		} catch (ExcepcionesFenix e1) {
			System.out.println("Entro a la excepcion de cliente");
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, e1.getMessage(), e1.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			e1.printStackTrace();
		}*/
		
		/**
		 * Enviar al metodo realizar asesoria los parametros 
		 * para que se hagan las excepciones desde negocio
		 */
		
		cliente.setCedula(cedula);
		empleado.setCedula(this.empleado);
		tipo = clienteEJB.tipoAsesoriaPorCodigo(tipo_asesoria);

		asesoria.setCliente(cliente);
		asesoria.setEmpleado(empleado);
		asesoria.setTipoasesoria(tipo);

		try {
			clienteEJB.realizarAsesoria(asesoria);
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso",
					"Registro exitoso");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "/infoPregunta";
		} catch (NullPointerException | InformacionNoEncontradaException e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (ExcepcionesFenix e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		
		//No retorna cadena sino se realiza la asesoria
		return null;

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the cedula
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	/**
	 * @return the empleado
	 */
	public String getEmpleado() {
		return empleado;
	}

	/**
	 * @param empleado the empleado to set
	 */
	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	/**
	 * @return the tipo_asesoria
	 */
	public int getTipo_asesoria() {
		return tipo_asesoria;
	}

	/**
	 * @param tipo_asesoria the tipo_asesoria to set
	 */
	public void setTipo_asesoria(int tipo_asesoria) {
		this.tipo_asesoria = tipo_asesoria;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
