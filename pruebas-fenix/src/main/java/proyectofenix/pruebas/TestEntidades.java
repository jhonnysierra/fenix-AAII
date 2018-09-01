package proyectofenix.pruebas;

import javax.persistence.EntityManager;
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
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet("persona.json")
	public void buscarPersonaTest() {
		
		Persona persona=entityManager.find(Persona.class, "123456789");
		Assert.assertNotNull(persona);
		
	}
	
	/*@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet("persona.json")
	public void agregarPersona() {
		Persona persona=new Persona();
		persona.setApellidos("perez");
		persona.setCedula("123456789");
		
		entityManager.persist(persona);
		
		persona=entityManager.find(Persona.class, "123456789");
		Assert.assertNotNull(persona);
	}*/
	
	@Test
	public void ejecutarTestEntidades() {
		
	}

}
