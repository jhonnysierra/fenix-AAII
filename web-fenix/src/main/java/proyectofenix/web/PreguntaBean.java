package proyectofenix.web;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
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
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.TipoAsesoria;
import proyectofenix.negocio.BancoEJB;
import proyectofenix.negocio.ClienteEJB;

/**
 * Bean para registrar una asesoria
 * 
 * @author JJ
 * @version 1.0
 */
@FacesConfig(version = Version.JSF_2_3)
@Named(value = "preguntaBean")
@ApplicationScoped
public class PreguntaBean {

	/**
	 * EJB para realizar conexion con la capa de negocio
	 */
	@EJB
	private ClienteEJB clienteEJB;

	/**
	 * id de la asesoria
	 */
	private int id;

	/**
	 * cedula cliente
	 */
	private String cedulaCliente;

	/**
	 * cedula empleado
	 */
	private String cedulaEmpleado;

	/**
	 * id del tipo de asesoria solicitada
	 */
	private int tipo_asesoria;

	/**
	 * Fecha de la asesoria
	 */
	private Date fecha;

	/**
	 * Lista de personas a la que se puede hacer pregunta
	 */
	private List<Persona> personas;

	/**
	 * Persona a la que se le realiza la pregunta
	 */
	private Persona persona;

	@EJB
	private BancoEJB bancoEJB;

	@PostConstruct
	private void inicializar() {
		personas = (List<Persona>) bancoEJB.listarPersonas();
	}

	/**
	 * Permite registrar una asesoria en la bd
	 * 
	 * @return ruta que muestra la informacion detallada de la asesoria
	 */
	public String crearAsesoria() {

		Asesoria asesoria = new Asesoria();
		asesoria.setId(id);
		asesoria.setFecha(fecha);

		System.out.println(String.format("La persona es: %s", persona));

		// Se comenta para que no registre

		try {
			clienteEJB.crearAsesoria(tipo_asesoria, cedulaEmpleado, cedulaCliente, fecha);
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso",
					"Registro exitoso");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "/infoPregunta";
		} catch (ExcepcionesFenix e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			e.printStackTrace();
		}

/*		Cliente cliente = new Cliente();
		Empleado empleado = new Empleado();
		TipoAsesoria tipo = new TipoAsesoria();

		try {
			System.out.println("Entro al try de cliente");
			cliente = clienteEJB.buscarcliente(cedulaCliente);
		} catch (ExcepcionesFenix e1) {
			System.out.println("Entro a la excepcion de cliente");
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, e1.getMessage(), e1.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			e1.printStackTrace();
		}*/

		/**
		 * Enviar al metodo realizar asesoria los parametros para que se hagan las
		 * excepciones desde negocio
		 */

		// No retorna cadena sino se realiza la asesoria
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
	 * @return the cedulaCliente
	 */
	public String getCedulaCliente() {
		return cedulaCliente;
	}

	/**
	 * @param cedulaCliente the cedulaCliente to set
	 */
	public void setCedulaCliente(String cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}

	/**
	 * @return the cedulaEmpleado
	 */
	public String getCedulaEmpleado() {
		return cedulaEmpleado;
	}

	/**
	 * @param cedulaEmpleado the cedulaEmpleado to set
	 */
	public void setCedulaEmpleado(String cedulaEmpleado) {
		this.cedulaEmpleado = cedulaEmpleado;
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

	/**
	 * @return the personas
	 */
	public List<Persona> getPersonas() {
		return personas;
	}

	/**
	 * @param personas the personas to set
	 */
	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param persona the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

}
