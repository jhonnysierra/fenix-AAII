package proyectofenix.pruebas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.GenerationType;
import javax.persistence.PersistenceContext;

import org.apache.derby.tools.sysinfo;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import proyectofenix.entidades.Ciudad;
import proyectofenix.entidades.Departamento;
import proyectofenix.entidades.Persona;

@RunWith(Arquillian.class)
public class TestEntidades {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	
	
	/**
	 * Metodo para buscar una Persona
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json","persona_telefonos.json"})
	public void buscarPersonaTest() {
		
		Persona personaBuscar=entityManager.find(Persona.class, "123456789");
		Assert.assertNotNull(personaBuscar);
		
	}
	
	
	/**
	 * Metodo para agregar una Persona
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json","persona_telefonos.json"})
	public void agregarPersona() {
		
		String fechaNaci="1990-07-30";
		
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");
		
		Date fechaNacimiento = null;
		
		try {
			fechaNacimiento = sdf.parse(fechaNaci);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Ciudad ciudad=new Ciudad();
		ciudad.setId("01");
		ciudad.setNombre("Caicedonia");
		
		Persona personaAgregar=new Persona();
		
		personaAgregar.setCedula("1115187219");
		personaAgregar.setApellidos("sierra parra");
		personaAgregar.setContrasenia("12345");
		personaAgregar.setCorreo("jhonnysierrap@gmail.com");
		personaAgregar.setDireccion("cra 8");
		personaAgregar.setEstado("1");
		personaAgregar.setFecha_nacimiento(fechaNacimiento);
		personaAgregar.setGenero(personaAgregar.getGenero().masculino);
		personaAgregar.setNombres("jhonny");
		personaAgregar.setCiudad(ciudad);
		
		entityManager.persist(personaAgregar);
		
		personaAgregar=entityManager.find(Persona.class, "1115187219");
		Assert.assertEquals("jhonnysierrap@gmail.com",personaAgregar.getCorreo());
		System.out.println(personaAgregar.getGenero());
		Assert.assertNotNull(personaAgregar);
	}
	
	/**
	 * Metodo para modificar una Persona
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json","persona_telefonos.json"})
	public void modificarPersona() {
		Persona personaModificar=entityManager.find(Persona.class, "123456789");
		personaModificar.setApellidos("Perez Padilla");
		
		entityManager.merge(personaModificar);
		
		System.out.println("<cedula: "+personaModificar.getCedula() + "> <apellidos: " + personaModificar.getApellidos()+">");
	}

	/**
	 * Metodo para eliminar una Persona
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json","persona_telefonos.json"})
	public void eliminarPersona() {
		Persona personaEliminar=entityManager.find(Persona.class, "123456789");
		
		entityManager.remove(personaEliminar);
		
		personaEliminar=entityManager.find(Persona.class, "123456789");
		Assert.assertNull("La persona existe",personaEliminar);
	}
	
	/**
	 * Metodo para buscar una ciudad
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"ciudad.json"})
	public void buscarCiudad() {
		Ciudad ciudadBuscar=entityManager.find(Ciudad.class, "01");
		Assert.assertNotNull(ciudadBuscar);
		
	}
	/**
	 * Metodo para agregar una Ciudad
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"ciudad.json","departamento.json"})
	public void agregarCiudad() {
		Departamento depart=new Departamento();
		depart=entityManager.find(Departamento.class, "01");
		
		Ciudad ciudadAgregar=new Ciudad();
		ciudadAgregar.setId("03");
		ciudadAgregar.setNombre("Caicedonia");
		ciudadAgregar.setDepartamento(depart);
		
		entityManager.persist(ciudadAgregar);
		
		ciudadAgregar=entityManager.find(Ciudad.class, "03");
		System.out.println(ciudadAgregar.getId());
		Assert.assertNotNull(ciudadAgregar);
	}
	
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"pago.json","bienraiz.json","tipoprestamo.json","ciudad.json"})
	public void ejecutarTestEntidades() {
		
	}

}
