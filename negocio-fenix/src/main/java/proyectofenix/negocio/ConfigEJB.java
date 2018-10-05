package proyectofenix.negocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import proyectofenix.entidades.Administrador;
import proyectofenix.entidades.Ciudad;

/**
 * Session Bean implementation class ConfigEJB
 */
@Singleton
@LocalBean
@Startup
public class ConfigEJB {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Se debe dejar por defecto Default constructor.
	 */
	public ConfigEJB() {

	}

	/**
	 * Permite agregar un administrador en caso de no estar registrado Se ejecuta
	 * despues de inicializar la clase. Despues de constructor. Se carga para
	 * generar el contexto por esta razon o se llama en ninguna otra clase
	 */
	@PostConstruct
	public void init() {
		TypedQuery<Long> query = entityManager.createNamedQuery(Administrador.CONTAR_ADMIN, Long.class);

		if (query.getSingleResult() == 0) {
			String fechaNaci = "1990-07-30";

			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");

			Date fechaNacimiento = null;

			try {
				fechaNacimiento = sdf.parse(fechaNaci);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Administrador administrador = new Administrador();
			administrador.setCedula("1115187");
			administrador.setNombres("JhonnyEJBinit");
			administrador.setApellidos("Sierra Parra");
			administrador.setCorreo("configejbuser@gmail.com");
			administrador.setDireccion("Cra 8 # 13-06");
			administrador.setContrasenia("45678");
			administrador.setEstado("1");
			administrador.setFecha_nacimiento(new Date());
			administrador.setFecha_nacimiento(fechaNacimiento);
			administrador.setCiudad(entityManager.find(Ciudad.class, "01"));
			administrador.setGenero(administrador.getGenero().masculino);
			

			entityManager.persist(administrador);
		}
	}
}
