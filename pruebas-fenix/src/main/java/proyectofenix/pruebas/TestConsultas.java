package proyectofenix.pruebas;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import proyectofenix.DTO.ConsultaAtencionEmpleadoDTO;
import proyectofenix.DTO.consulta10DTO;
import proyectofenix.entidades.Asesoria;
import proyectofenix.entidades.BienRaiz;
import proyectofenix.entidades.Cliente;
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
			Assert.fail(String.format("Error buscando la persona relacionada con el bien raiz%s", e.getMessage()));
		}

	}

	/**
	 * Permite obtener el numero de cuotas asociadas a un prestamo. Item 5 guia 9
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
	 * Metodo que permite obtener el listado de prestamos realizados por una
	 * persona. Item 6 guia 9
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
	 * incluyendo las que no tengan prestamos. Item 7 guia 9
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "prestamo.json" })
	public void obtenerlistaPersonasYPrestamosTest() {

		Query query = entityManager.createNamedQuery(Persona.OBTENER_PRESTAMOS_TODAS_PERSONAS, Object.class);

		List<Object[]> resultadoPersonas = query.getResultList();

		Assert.assertEquals(17, resultadoPersonas.size());

		/*
		 * Prestamo prestamo; String idPrestamo; for (Object[] obj : resultadoPersonas)
		 * { if (obj[1] != null) { prestamo = (Prestamo) obj[1]; idPrestamo =
		 * String.valueOf(prestamo.getId()); } else { idPrestamo =
		 * "No ha realizado prestamos"; }
		 * 
		 * System.out.println(String.format("Cedula:%s IdPrestamo:%s", obj[0],
		 * idPrestamo)); }
		 */

	}

	/**
	 * Metodo que permite listar las personas que han hecho un prestamo. En la
	 * consulta se hace uso de DISTINCT. Item 8 guia 9
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

		Query query = entityManager.createNamedQuery(Prestamo.OBTENER_CAMPOS_PRESTAMO, Object.class);

		Date fechaInicio = null;
		try {
			fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse("2018-09-09");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		query.setParameter("fechaInicio", fechaInicio);

		List<Object[]> resultadoPrestamos = query.getResultList();

		// resultadoPrestamos.forEach(r -> System.out.println(Arrays.toString(r)));

		Assert.assertEquals(5, resultadoPrestamos.size());

/*		int idPrestamo;
		Pago pago;
		for (Object[] obj : resultadoPrestamos) {
			idPrestamo = (int) obj[0];
			pago = (Pago) obj[3];
			System.out.println(
					String.format("Id:%s Cedula:%s Email:%s IdPago:%s", idPrestamo, obj[1], obj[2], pago.getId()));
		}*/

	}

	/**
	 * Metodo que permita listar los prestamos por una fecha, mostrando el id del
	 * prestamo, la cedula del cliente, el email y el id de la cuota pagada;haciendo
	 * uso de DTO. Item 10 guia 9
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

		Assert.assertEquals(5, resultadoPrestamos.size());


/*		for (consulta10DTO con : resultadoPrestamos) {
			System.out.println(String.format("Id:%s Cedula:%s Email:%s IdPago:%s", con.getIdPrestamo(), con.getCedula(),
					con.getCorreo(), con.getPagos().getId()));
		}*/

	}

	/**
	 * Permite obtener el numero de prestamos realizados haciendo uso de COUNT. Item
	 * 1 guia 10
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "prestamo.json" })
	public void obtenerTotalPrestamosRealizadosTest() {
		Long totalPrestamos;
		try {
			Query query = entityManager.createNamedQuery(Prestamo.OBTENER_TOTAL_PRESTAMOS);

			totalPrestamos = (Long) query.getSingleResult();

			Assert.assertEquals("No corresponde al total de prestamos", 10, totalPrestamos.intValue());

		} catch (NoResultException e) {
			Assert.fail(String.format("Error calculando el total de prestamos%s", e.getMessage()));
		}

	}

	/**
	 * Permite obtener el numero de prestamos realizados, agrupados por fecha. Item
	 * 2 guia 10
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "prestamo.json", "persona.json" })
	public void obtenerPrestamosAgrupadosFechaTest() {
		Query query = entityManager.createNamedQuery(Prestamo.OBTENER_PRESTAMOS_AGRUPADOS_FECHA, Object.class);

		List<Object[]> prestamosAgrupadoFecha = query.getResultList();

		Assert.assertEquals("No corresponde al total de prestamos agrupados por fecha", 5,
				prestamosAgrupadoFecha.size());
		
		/*
		 * Date fechaInicio = null; for (Object[] obj : prestamosAgrupadoFecha) {
		 * fechaInicio = (Date) obj[1];
		 * System.out.println(String.format("Cantidad:%s Fecha:%s", obj[0],
		 * fechaInicio)); }
		 */

	}

	/**
	 * Permite obtener el numero clientes que no han solicitado una asesoria. Item 3
	 * guia 10
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "asesoria.json" })
	public void obtenerClientesSinAsesoriaTest() {
		TypedQuery<Cliente> query = entityManager.createNamedQuery(Cliente.OBTENER_CLIENTE_SIN_ASESORIA, Cliente.class);

		List<Cliente> clienteSinAsesoria = query.getResultList();

		Assert.assertEquals("Error: Clientes sin asesoria", 4, clienteSinAsesoria.size());

		/*
		 * for (Cliente c : clienteSinAsesoria) {
		 * System.out.println(String.format("Cedula:%s Nombres:%s", c.getCedula(),
		 * c.getNombres())); }
		 */

	}

	/**
	 * Permite obtener el numero Asesorias atendidas por un Empleado. Una asesoria
	 * ha sido atendida cuando la hora de inicio es diferente de la hora de fin.
	 * Para el caso de nuestro proyecto las asesorias se manejan como citas. Item 4
	 * guia 10
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "asesoria.json" })
	public void obtenerTotalAsesoriasEmpleadoTest() {
		TypedQuery<ConsultaAtencionEmpleadoDTO> query = entityManager
				.createNamedQuery(Asesoria.OBTENER_TOTAL_ASESORIAS_EMPLEADO, ConsultaAtencionEmpleadoDTO.class);

		List<ConsultaAtencionEmpleadoDTO> empleadoAsesoria = query.getResultList();

		Assert.assertEquals("Error asesorias atendidas", 1, empleadoAsesoria.size());

		/*
		 * for (ConsultaAtencionEmpleadoDTO asesoria : empleadoAsesoria) {
		 * System.out.println(String.format("Cedula:%s Asesorias:%s",
		 * asesoria.getCedula(), asesoria.getAsesorias())); }
		 */
	}

	/**
	 * Permite obtener el prestamo con el monto mas alto haciendo uso de max,
	 * despues con otra consulta se obtiene el listado de los prestamos que tengan
	 * ese monto como valor de prestamo. Item 5 guia 10
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "prestamo.json" })
	public void obtenerMaximoPrestamoTest() {
		double prestamoMaximo = 0;
		try {
			Query query = entityManager.createNamedQuery(Prestamo.OBTENER_MAX_VALOR_PRESTAMOS);

			prestamoMaximo = (Double) query.getSingleResult();
			DecimalFormat df = new DecimalFormat("#.#");
			String numero = df.format(prestamoMaximo);


			Assert.assertEquals("No corresponde al valor maximo del prestamo", "120000000", numero);

			// Consultar el prestamo donde sea el valor del maximo

		} catch (NoResultException e) {
			Assert.fail(String.format("Error encontrando el prestamo maximo %s", e.getMessage()));
		}

		TypedQuery<Prestamo> queryPrestamo = entityManager.createNamedQuery(Prestamo.OBTENER_PRESTAMO_POR_VALOR_MAXIMO,
				Prestamo.class);
		queryPrestamo.setParameter("valorPrestamo", prestamoMaximo);
		List<Prestamo> listaPrestamos = queryPrestamo.getResultList();

		Assert.assertEquals("Error: Numero de prestamos con valor maximo", 1, listaPrestamos.size());

	}

	/**
	 * Obtiene el listado de prestamos haciendo uso de una consulta que tiene como
	 * parametro el MAX del atributo valor prestamo y devuelve la lista de prestamos
	 * asociados a ese valor Item 6 guia 10
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "prestamo.json" })
	public void obtenerTodosPrestamosMaximosTest() {

		TypedQuery<Prestamo> queryPrestamo = entityManager.createNamedQuery(Prestamo.OBTENER_PRESTAMOS_MAXIMOS,
				Prestamo.class);

		List<Prestamo> listaPrestamos = queryPrestamo.getResultList();

		Assert.assertEquals("Error: Numero de prestamos con valor maximo", 1, listaPrestamos.size());

		/*
		 * for (Prestamo valorP : listaPrestamos) {
		 * System.out.println(String.format("Id:%s Valor prestamo:%.0f", valorP.getId(),
		 * valorP.getValorPrestamo())); }
		 */
	}

	/*
	 * La mejor manera de hacer la consulta de el prestamos o prestamos con el monto
	 * maximo es con el metodo anterior(obtenerTodosPrestamosMaximosTest) ya que
	 * alli en una sola consulta se obtienen los resultados, lo que genera que se
	 * consuman menos recursos y memoria en el sistema. Mientras que en el metodo
	 * (obtenerMaximoPrestamoTest) se necesita primero consultar el valor maximo y
	 * despues enviarlo como parametro a otra consulta que devuelve el listado, en
	 * el metodo anterior el listado se obtiene en una sola consulta.
	 */

}
