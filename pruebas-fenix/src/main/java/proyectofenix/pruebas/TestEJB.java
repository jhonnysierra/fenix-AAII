package proyectofenix.pruebas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import proyecto.fenix.excepciones.ExcepcionesFenix;
import proyectofenix.entidades.Ciudad;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Empleado;
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
		Ciudad ciudad = entityManager.find(Ciudad.class, "01");

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

		/*
		 * entityManager.persist(cliente);
		 * 
		 * Cliente consultarCliente=entityManager.find(Cliente.class, "9");
		 * Assert.assertNotNull(consultarCliente);
		 */
		try {
			Assert.assertTrue(banco.agregarCliente(cliente) != null);
		} catch (Exception e) {
			Assert.fail(String.format("Error: %s", e.getMessage()));
		}

	}

	/**
	 * Permite probar el agregar empleado de BancoEJB
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "ciudad.json" })
	public void agregarEmpleadoTest() {
		Ciudad ciudad = entityManager.find(Ciudad.class, "02");
		Date fechaInicio = null;
		try {
			fechaInicio = new SimpleDateFormat("yyy-MM-dd").parse("2018-01-01");
		} catch (ParseException e1) {
			Assert.fail(String.format("Error: %s", e1.getMessage()));
		}

		Empleado empleado = new Empleado();
		empleado.setCedula("1");
		empleado.setNombres("Carlos");
		empleado.setApellidos("Sanchez ");
		empleado.setContrasenia("123456");
		empleado.setCorreo("carlossanchez1@gmail.com");
		empleado.setEstado("1");
		empleado.setCiudad(ciudad);
		empleado.setDireccion("cra 23 # 34-55");
		empleado.setFechaInicio(fechaInicio);
		empleado.setFechaFin(fechaInicio);

		try {
			Assert.assertTrue(banco.agregarEmpleado(empleado) != null);
		} catch (Exception e) {
			Assert.fail(String.format("Error: %s", e.getMessage()));
		}

	}

	/**
	 * Permite probar el buscar empleado de BancoEJB
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void buscarEmpleadoTest() {
		
		try {
			Assert.assertNotNull(banco.buscarEmpleado("1234"));
		} catch (Exception e) {
			Assert.fail(String.format("Error Metodo: %s", e.getMessage()));
		}

	}
	
	/**
	 * Permite probar el buscar empleado de BancoEJB
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void modificarEmpleadoTest() {
		
		Empleado empleadoModificar = entityManager.find(Empleado.class, "1234");
		
		empleadoModificar.setSalario(3000000);
		
		try {
			banco.modificarEmpleado(empleadoModificar);
		} catch (ExcepcionesFenix e1) {
			Assert.fail(String.format("Error modificarEmpleado: %s", e1.getMessage()));
		}
		
		Empleado empleadoModificado = entityManager.find(Empleado.class, "1234");
		
		Assert.assertEquals("El salario de empleado no se modificó", "3000000.0", String.valueOf(empleadoModificado.getSalario()));
		

	}
	
	/**
	 * Permite probar el metodo eliminar empleado de BancoEJB
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void eliminarEmpleadoTest() {
				
		try {
			banco.eliminarEmpleado("1234");
		} catch (ExcepcionesFenix e1) {
			Assert.fail(String.format("Error modificarEmpleado: %s", e1.getMessage()));
		}
		
		Empleado empleado = entityManager.find(Empleado.class, "1234");
		
		Assert.assertNull("Empleado es diferente de null",empleado);
		

	}
	
	/**
	 * Permite probar el metodo listar empleados de BancoEJB
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void listarEmpleadosTest() {
		List<Empleado> lista = banco.listarEmpleados();	
		
		Assert.assertEquals("Error: La lista no tiene los empleados esperados ",3, lista.size());
	}
}
