package proyectofenix.pruebas;

import javax.ejb.EJB;
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
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Persona;
import proyectofenix.negocio.ClienteEJB;

@RunWith(Arquillian.class)
public class TestEJBCliente {

	/**
	 * Objeto de la clase ClienteEJB donde se encuentran los metodos para la
	 * interfaz de cliente
	 */
	@EJB
	private ClienteEJB banco;

	@PersistenceContext
	private EntityManager entityManager;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class).addClass(ClienteEJB.class).addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	/**
	 * Permite probar el metodo registrar una asesoria de ClienteEJB
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "Asesoria.json" })
	public void consecutivoAsesoriaTest() {
		
		try {
			Assert.assertEquals(5, banco.consecutivoAsesoria());
		} catch (ExcepcionesFenix e) {
			Assert.fail(String.format("Error generando consecutivo %s", e.getMessage()));
		}

	}
}
