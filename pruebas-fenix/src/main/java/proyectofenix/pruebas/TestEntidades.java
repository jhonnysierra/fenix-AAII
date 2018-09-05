package proyectofenix.pruebas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.GenerationType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.derby.tools.sysinfo;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.core.spi.Typed;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import proyectofenix.entidades.BienRaiz;
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
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "persona_telefonos.json" })
	public void buscarPersonaTest() {

		Persona personaBuscar = entityManager.find(Persona.class, "123456789");
		Assert.assertNotNull(personaBuscar);
		// Probar que la persona tiene 1 telefono asociado
		Assert.assertEquals(1, personaBuscar.getTelefonos().size());
	}

	/**
	 * Metodo para agregar una Persona
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "persona_telefonos.json" })
	public void agregarPersonaTest() {

		String fechaNaci = "1990-07-30";

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");

		Date fechaNacimiento = null;

		try {
			fechaNacimiento = sdf.parse(fechaNaci);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Ciudad ciudad = new Ciudad();
		ciudad.setId("01");
		ciudad.setNombre("Caicedonia");

		Persona personaAgregar = new Persona();

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

		personaAgregar = entityManager.find(Persona.class, "1115187219");
		Assert.assertEquals("jhonnysierrap@gmail.com", personaAgregar.getCorreo());
		System.out.println(personaAgregar.getGenero());
		Assert.assertNotNull(personaAgregar);
	}

	/**
	 * Metodo para modificar una Persona
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "persona_telefonos.json" })
	public void modificarPersonaTest() {
		Persona personaModificar = entityManager.find(Persona.class, "123456789");
		personaModificar.setApellidos("Perez Padilla");

		entityManager.merge(personaModificar);

		System.out.println(
				"<cedula: " + personaModificar.getCedula() + "> <apellidos: " + personaModificar.getApellidos() + ">");
	}

	/**
	 * Metodo para eliminar una Persona
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "persona_telefonos.json" })
	public void eliminarPersonaTest() {
		Persona personaEliminar = entityManager.find(Persona.class, "123456789");

		entityManager.remove(personaEliminar);

		personaEliminar = entityManager.find(Persona.class, "123456789");
		Assert.assertNull("La persona existe", personaEliminar);
	}

	/**
	 * Metodo para buscar una ciudad
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json","bienraiz.json"})
	public void buscarCiudadTest() {
		Ciudad ciudadBuscar = entityManager.find(Ciudad.class, "01");
		Assert.assertNotNull(ciudadBuscar);

		// Probar que la ciudad tiene bienes raices asociados
		Assert.assertEquals("Error cantidad de bienes raices", 1, ciudadBuscar.getBienRaiz().size());

	}

	/**
	 * Metodo para agregar una Ciudad
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json"})
	public void agregarCiudadTest() {
		Departamento depart = new Departamento();
		depart = entityManager.find(Departamento.class, "01");

		Ciudad ciudadAgregar = new Ciudad();
		ciudadAgregar.setId("03");
		ciudadAgregar.setNombre("Caicedonia");
		ciudadAgregar.setDepartamento(depart);

		entityManager.persist(ciudadAgregar);

		ciudadAgregar = entityManager.find(Ciudad.class, "03");
		Assert.assertNotNull(ciudadAgregar);
	}

	/**
	 * Metodo para modificar una Ciudad
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json" })
	public void modificarCiudadTest() {
		Ciudad ciudadModificar = entityManager.find(Ciudad.class, "04");
		ciudadModificar.setNombre("CIRCASIA");

		entityManager.merge(ciudadModificar);

		Assert.assertEquals("NO se cambio el nombre de la ciudad", "CIRCASIA", ciudadModificar.getNombre());
	}

	/**
	 * Metodo para eliminar una Ciudad
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json" })
	public void eliminarCiudadTest() {
		Ciudad ciudadEliminar = entityManager.find(Ciudad.class, "04");

		entityManager.remove(ciudadEliminar);

		ciudadEliminar = entityManager.find(Ciudad.class, "04");
		Assert.assertNull("La ciudad existe", ciudadEliminar);
	}

	/**
	 * Metodo para buscar un Departamento
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json" })
	public void buscarDepartamentoTest() {
		Departamento departamentoBuscar = entityManager.find(Departamento.class, "02");
		Assert.assertNotNull(departamentoBuscar);

		List<Ciudad> ciudadesDepartamento = departamentoBuscar.getCiudades();

		/*
		 * for (Ciudad cd : ciudadesDepartamento) {
		 * 
		 * System.out.println(String.format("CODIGO:%s, NOMBRE:%s", cd.getId(),
		 * cd.getNombre())); }
		 */

		// Probar que la Departamento tiene ciudades asociadas
		Assert.assertEquals("La lista no corresponde al tamaño esperado", 2, ciudadesDepartamento.size());

	}

	/**
	 * Metodo para agregar un Departamento
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json" })
	public void agregarDepartamentoTest() {
		Departamento departamentoAgregar = new Departamento();
		Ciudad ciudad = new Ciudad();
		List<Ciudad> ciudadesDepartamento = new ArrayList<>();

		ciudad.setId("05");
		ciudad.setNombre("PEREIRA");
		ciudadesDepartamento.add(ciudad);

		departamentoAgregar.setId("03");
		departamentoAgregar.setNombre("RISARALDA");
		departamentoAgregar.setCiudades(ciudadesDepartamento);

		entityManager.persist(departamentoAgregar);

		departamentoAgregar = entityManager.find(Departamento.class, "03");
		Assert.assertNotNull("El objeto departamento es null", departamentoAgregar);
	}
	
	/**
	 * Metodo para modificar un Departamento
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json" })
	public void modificarDepartamentoTest() {
		Departamento departamentoModificar = entityManager.find(Departamento.class, "01");
		departamentoModificar.setNombre("VALLE");

		entityManager.merge(departamentoModificar);

		Assert.assertEquals("NO se cambio el nombre de la ciudad", "VALLE", departamentoModificar.getNombre());
	}
	
	/**
	 * Metodo para eliminar una Departamento
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json" })
	public void eliminarDepartamentoTest() {
		Departamento departamentoEliminar = entityManager.find(Departamento.class, "02");

		entityManager.remove(departamentoEliminar);

		departamentoEliminar = entityManager.find(Departamento.class, "04");
		Assert.assertNull("El departamento existe", departamentoEliminar);
	}

	/**
	 * Metodo para buscar un Bien raiz
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json","bienraiz.json"})
	public void buscarBienRaizTest() {
		BienRaiz bienRaizBuscar = entityManager.find(BienRaiz.class, "001");
		Assert.assertNotNull(bienRaizBuscar);

		// Probar relacion con ciudad
		Assert.assertEquals("La ciudad no corresponde a la esperada", "01", bienRaizBuscar.getCiudad().getId());

		// Probar relacion con Persona
		Assert.assertEquals("La ciudad no corresponde a la esperada", "1115187219", bienRaizBuscar.getPersona().getCedula());
	}
	
	/**
	 * Metodo para agregar un Bien Raiz
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json","bienraiz.json"})
	public void agregarBienRaizTest() {
		BienRaiz bienRaizAgregar = new BienRaiz();
		
		Ciudad ciudad = new Ciudad();
		Persona persona =new Persona();
		
		ciudad=entityManager.find(Ciudad.class,"03");
		persona=entityManager.find(Persona.class, "1115187219");
		
		bienRaizAgregar.setId("003");
		bienRaizAgregar.setDireccion("Cra 6N 23-32");
		bienRaizAgregar.setAvaluo(2350000);
		bienRaizAgregar.setCiudad(ciudad);
		bienRaizAgregar.setPersona(persona);
		
		entityManager.persist(bienRaizAgregar);

		bienRaizAgregar = entityManager.find(BienRaiz.class, "003");
		Assert.assertNotNull("El objeto departamento es null", bienRaizAgregar);
	}
	
	/**
	 * Metodo para modificar un bien raiz
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json","bienraiz.json"})
	public void modificarBienRaizTest() {
		BienRaiz bienRaizModificar = entityManager.find(BienRaiz.class, "002");
		bienRaizModificar.setDireccion("CRA 5 # 7-32");;

		entityManager.merge(bienRaizModificar);

		
		Assert.assertEquals("NO se cambio la direccion", "CRA 5 # 7-32",bienRaizModificar.getDireccion());
	}
	
	/**
	 * Metodo para eliminar un bien raiz
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json","bienraiz.json"})
	public void eliminarBienRaizTest() {
		BienRaiz bienRaizEliminar = entityManager.find(BienRaiz.class, "002");

		entityManager.remove(bienRaizEliminar);

		bienRaizEliminar = entityManager.find(BienRaiz.class, "002");
		Assert.assertNull("El bien raiz existe", bienRaizEliminar);
	}

	////////////////////////// CLASE CONSULTAS
	////////////////////////// /////////////////////////////////////////
	/**
	 * Permite listar la informacion de todas las personas
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "persona_telefonos.json" })
	public void listarClientesTest() {
		TypedQuery<Persona> query = entityManager.createQuery("select p1 from Persona p1", Persona.class);
		// obterner la informacion en una lista
		List<Persona> personas = query.getResultList();

		Assert.assertEquals(2, personas.size());

		for (Persona p : personas) {
			System.out.println(String.format("CC:%s, nombre:%s", p.getCedula(), p.getNombres()));
		}

	}

	/**
	 * Permite obtener un persona segun sus credenciales de acceso a la plataforma
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "persona_telefonos.json" })
	public void obtenerPersonaPorCredencialesTest() {

		// Para que no muestre error si no hay resultados
		try {
			TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.OBTENER_PERSONAS_POR_CREDENCIALES,
					Persona.class);
			query.setParameter("cedula", "123456789");
			query.setParameter("contrasenia", "12345");

			// Trae solo una memoria. Si no encuentra hay que manejar una excepcion
			Persona p = query.getSingleResult();

			Assert.assertNotNull(p);
			// System.out.println(p);

		} catch (NoResultException e) {
			Assert.fail(String.format("Error: %s", e.getMessage()));
		}

	}

	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "pago.json", "bienraiz.json", "tipoprestamo.json", "ciudad.json" })
	public void ejecutarTestEntidades() {

	}

}
