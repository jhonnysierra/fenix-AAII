package proyectofenix.pruebas;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import proyecto.fenix.excepciones.ElementoRepetidoExcepcion;
import proyectofenix.entidades.Ciudad;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Persona;
import proyectofenix.negocio.BancoEJB;

@RunWith(Arquillian.class)
public class TestEJB {

	/**
	 * Objeto de la clase BancoEJB donde se encuentran los metodos para agregar los
	 * clientes
	 */
	@EJB
	private BancoEJB banco;

	@PersistenceContext
	private EntityManager entityManager;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class).addClass(BancoEJB.class).addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	/**
	 * Permite probar el agregar cliente de BancoEJB
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "ciudad.json" })
	public void agregarClienteTest() {
		Ciudad ciudad=entityManager.find(Ciudad.class, "01");
		
		Cliente cliente = new Cliente();
		cliente.setCedula("9");
		cliente.setNombres("jhonny");
		cliente.setApellidos("Sierra Parra");
		cliente.setContrasenia("123456");
		cliente.setCorreo("prueba@gmail.com");
		cliente.setEstado("1");
		cliente.setCiudad(ciudad);
		cliente.setDireccion("cra 8 # 13-06");
		cliente.setNoCuenta("1238475");
		
		/*entityManager.persist(cliente);
		
		Cliente consultarCliente=entityManager.find(Cliente.class, "9");
		Assert.assertNotNull(consultarCliente);
*/
		try {
			Assert.assertTrue(banco.agregarCliente(cliente) != null);
		} catch (Exception e) {
			Assert.fail(String.format("Error: %s", e.getMessage()));
		}

	}
}
