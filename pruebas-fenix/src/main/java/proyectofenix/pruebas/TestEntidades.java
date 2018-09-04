package proyectofenix.pruebas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.GenerationType;
import javax.persistence.PersistenceContext;
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
import org.junit.Test;
import org.junit.runner.RunWith;

import proyectofenix.entidades.Ciudad;
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
	 * Busca una persona
	 */
	/*
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet("persona.json")
	public void buscarPersonaTest() {
		
		Persona persona=entityManager.find(Persona.class, "123456789");
		Assert.assertNotNull(persona);
		
	}*/
	
	
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet("persona.json")
	public void agregarPersona() {
		
		String fechaNaci="1990-07-30";
		
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");
		
		Date fechaNacimiento = null;
		
		try {
			fechaNacimiento = sdf.parse(fechaNaci);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Ciudad ciudad=new Ciudad();
		ciudad.setId("01");
		ciudad.setNombre("Caicedonia");
		
		Persona persona=new Persona();
		
		
		persona.setCedula("123456789");
		persona.setApellidos("perez");
		persona.setContrasenia("12345");
		persona.setCorreo("jhonnysierrap@gmail.com");
		persona.setDireccion("cra 8");
		persona.setEstado("1");
		persona.setFecha_nacimiento(fechaNacimiento);
		persona.setGenero(persona.getGenero().masculino);
		persona.setNombres("jhonny");
		persona.setCiudad(ciudad);
		
		entityManager.persist(persona);
		
		persona=entityManager.find(Persona.class, "123456789");
		Assert.assertNotNull(persona);
	}
	
	public void modificarPersona() {
		
	}
	
	@Test
	public void ejecutarTestEntidades() {
		
	}

}
