package proyectofenix.pruebas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
import org.junit.Test;
import org.junit.runner.RunWith;

import proyectofenix.DTO.consulta10DTO;
import proyectofenix.entidades.BienRaiz;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.Prestamo;

/**
 * Clase que contiene los metodos para probar las consultas
 * 
 * @author JJ
 *
 */
@RunWith(Arquillian.class)
public class TestConsultas {
	@PersistenceContext
	private EntityManager entityManager;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	/**
	 * Permite obtener la persona asociada a un bien raiz y a su vez los prestamos
	 * de la persona tipo hipotecario. Item 4 guia 9
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "bienraiz.json", "prestamo.json", "tipoprestamo.json" })
	public void obtenerPersonaConIdBienRaiz() {

		try {
			TypedQuery<Persona> query = entityManager.createNamedQuery(BienRaiz.OBTENER_PERSONA_BIENRAIZ,
					Persona.class);
			query.setParameter("id", "003");

			Persona p = query.getSingleResult();

			// System.out.println(String.format("Cedula:%s, Nombre:%s", p.getCedula(),
			// p.getNombres()));

			Assert.assertEquals("No se encotro el dueño del bien raiz", "6208204", p.getCedula());

		} catch (NoResultException e) {
			Assert.fail(String.format("Error buscandola la persona relacionada con el bien raiz%s", e.getMessage()));
		}

	}

	/**
	 * Permite obtener el numero de cuotas asociadas a un prestamo Item 5 guia 9
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "prestamo.json", "pago.json" })
	public void obtenerPagosPrestamoTest() {

		TypedQuery<Pago> query = entityManager.createNamedQuery(Prestamo.OBTENER_CUOTAS_PRESTAMO, Pago.class);
		query.setParameter("id", 1);

		List<Pago> resultadoPagos = query.getResultList();
		// System.out.println("Tamaño lista:"+resultadoPagos.size());

		Assert.assertEquals(2, resultadoPagos.size());

		/*
		 * for (Pago p : resultadoPagos) {
		 * System.out.println(String.format("Id:%s, valor:%s", p.getId(),
		 * p.getValor())); }
		 */
	}

	/**
	 * Metodo que permite obtener el numero de prestamos asociados a una persona.
	 * Item 6 guia 9
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "prestamo.json" })
	public void obtenerPrestamoPersonaTest() {

		TypedQuery<Prestamo> query = entityManager.createNamedQuery(Persona.OBTENER_PRESTAMOS_PERSONA, Prestamo.class);
		query.setParameter("cedula", "6208204");

		List<Prestamo> resultadoPrestamos = query.getResultList();

		Assert.assertEquals(4, resultadoPrestamos.size());

		/*
		 * for (Prestamo p : resultadoPrestamos) {
		 * System.out.println(String.format("Id:%s, valor:%s", p.getId(),
		 * p.getValorPrestamo())); }
		 */
	}

	/**
	 * Metodo que permita listar las personas y cada uno de sus prestamos,
	 * incluyendo los que no tengan prestamos. Item 7 guia 9
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "prestamo.json" })
	public void obtenerlistaPersonasYPrestamosTest() {

		Query query = entityManager.createNamedQuery(Persona.OBTENER_PRESTAMOS_TODAS_PERSONAS, Object.class);
		// query.setParameter("cedula", "6208204");
		List<Object[]> resultadoPersonas = query.getResultList();

///////////SALE NULL EN EL CAMPO DE LAS CUOTAS ???????

		Object[] objeto = (Object[]) resultadoPersonas.get(0);
		System.out.println("PRIMER USUARIOO campos-> [" + objeto[0] + "]");
		// COMO LISTAR LOS PRESTAMOS DE CADA CLIENTE
		// List<Prestamo> prestamosCliente = (List<Prestamo>) objeto[1];

		// System.out.println("Tamano lista: " + prestamosCliente.size());

		// Persona resultadoPersonas = query.getSingleResult();

		// resultadoPersonas.forEach(r -> System.out.println(Arrays.toString(r)));

		// Assert.assertEquals(4, resultadoPrestamos.size());
		Prestamo prestamo;

		for (Object[] obj : resultadoPersonas) {
			prestamo = (Prestamo) obj[1];
			System.out.println(String.format("Cedula:%s IdPrestamo:%s", obj[0], prestamo.getId()));
		}

	}

	/**
	 * Metodo que permite listar las personas que han hecho un prestamo haciendo uso
	 * de DISTINCT. Item 8 guia 9
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "prestamo.json" })
	public void obtenerTodosPrestamosPersonasTest() {

		TypedQuery<Persona> query = entityManager.createNamedQuery(Prestamo.OBTENER_PRESTAMOS, Persona.class);

		List<Persona> resultadoPrestamos = query.getResultList();

		Assert.assertEquals(7, resultadoPrestamos.size());

		/*
		 * for (Persona p : resultadoPrestamos) {
		 * System.out.println(String.format("cedula:%s, nombres:%s", p.getCedula(),
		 * p.getNombres())); }
		 */

	}

	/**
	 * Metodo que permita listar los prestamos por una fecha, mostrando el id del
	 * prestamo, la cedula del cliente, el email y el id de la cuota pagada. Item 9
	 * guia 9
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "prestamo.json", "pago.json" })
	public void obtenerPrestamoPorFechaTest() {
		// TypedQuery<Pago> queryPagos =
		// entityManager.createNamedQuery(Prestamo.OBTENER_CAMPOS_PRESTAMO, Pago.class);
		Query query = entityManager.createNamedQuery(Prestamo.OBTENER_CAMPOS_PRESTAMO, Object.class);

		Date fechaInicio = null;
		try {
			fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse("2018-09-09");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		query.setParameter("fechaInicio", fechaInicio);

		List<Object[]> resultadoPrestamos = query.getResultList();

		System.out.println("Tamaño de la lista: " + resultadoPrestamos.size());

		// resultadoPrestamos.forEach(r -> System.out.println(Arrays.toString(r)));

		Assert.assertEquals(5, resultadoPrestamos.size());

		int idPrestamo;
		Pago pago;
		for (Object[] obj : resultadoPrestamos) {
			idPrestamo = (int) obj[0];
			pago = (Pago) obj[3];
			System.out.println(
					String.format("Id:%s Cedula:%s Email:%s IdPago:%s", idPrestamo, obj[1], obj[2], pago.getId()));
		}

	}

	/**
	 * Metodo que permita listar los prestamos por una fecha, mostrando el id del
	 * prestamo, la cedula del cliente, el email y el id de la cuota pagada;haciendo
	 * uso de DTO Item. 10 guia 9
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "prestamo.json", "pago.json" })
	public void obtenerPrestamoPorFechaDTOTest() {
		TypedQuery<consulta10DTO> query = entityManager.createNamedQuery(Prestamo.OBTENER_CAMPOS_PRESTAMO_DTO,
				consulta10DTO.class);

		Date fechaInicio = null;
		try {
			fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse("2018-09-09");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		query.setParameter("fechaInicio", fechaInicio);

		List<consulta10DTO> resultadoPrestamos = query.getResultList();

		System.out.println("Tamaño de la lista: " + resultadoPrestamos.size());

		// resultadoPrestamos.forEach(r -> System.out.println(Arrays.toString(r)));

		Assert.assertEquals(5, resultadoPrestamos.size());

		for (consulta10DTO con : resultadoPrestamos) {
			System.out.println(String.format("Id:%s Cedula:%s Email:%s IdPago:%s", con.getIdPrestamo(), con.getCedula(),
					con.getCorreo(), con.getIdPago()));
		}

	}
}
