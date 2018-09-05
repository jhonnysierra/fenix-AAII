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

import proyectofenix.entidades.Asesoria;
import proyectofenix.entidades.BienRaiz;
import proyectofenix.entidades.Ciudad;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Departamento;
import proyectofenix.entidades.Empleado;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.PrestamoRenovado;
import proyectofenix.entidades.TipoAsesoria;
import proyectofenix.entidades.TipoPrestamo;

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
	@UsingDataSet({ "ciudad.json", "departamento.json", "bienraiz.json" })
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
	@UsingDataSet({ "ciudad.json", "departamento.json" })
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
	@UsingDataSet({ "ciudad.json", "departamento.json", "bienraiz.json" })
	public void buscarBienRaizTest() {
		BienRaiz bienRaizBuscar = entityManager.find(BienRaiz.class, "001");
		Assert.assertNotNull(bienRaizBuscar);

		// Probar relacion con ciudad
		Assert.assertEquals("La ciudad no corresponde a la esperada", "01", bienRaizBuscar.getCiudad().getId());

		// Probar relacion con Persona
		Assert.assertEquals("La ciudad no corresponde a la esperada", "1115187219",
				bienRaizBuscar.getPersona().getCedula());
	}

	/**
	 * Metodo para agregar un Bien Raiz
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json", "bienraiz.json" })
	public void agregarBienRaizTest() {
		BienRaiz bienRaizAgregar = new BienRaiz();

		Ciudad ciudad = new Ciudad();
		Persona persona = new Persona();

		ciudad = entityManager.find(Ciudad.class, "03");
		persona = entityManager.find(Persona.class, "1115187219");

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
	@UsingDataSet({ "ciudad.json", "departamento.json", "bienraiz.json" })
	public void modificarBienRaizTest() {
		BienRaiz bienRaizModificar = entityManager.find(BienRaiz.class, "002");
		bienRaizModificar.setDireccion("CRA 5 # 7-32");
		;

		entityManager.merge(bienRaizModificar);

		Assert.assertEquals("NO se cambio la direccion", "CRA 5 # 7-32", bienRaizModificar.getDireccion());
	}

	/**
	 * Metodo para eliminar un bien raiz
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "ciudad.json", "departamento.json", "bienraiz.json" })
	public void eliminarBienRaizTest() {
		BienRaiz bienRaizEliminar = entityManager.find(BienRaiz.class, "002");

		entityManager.remove(bienRaizEliminar);

		bienRaizEliminar = entityManager.find(BienRaiz.class, "002");
		Assert.assertNull("El bien raiz existe", bienRaizEliminar);
	}

	/**
	 * Metodo para buscar un prestamo renovado
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "prestamorenovado.json", "persona.json", "tipoprestamo.json" })
	public void buscarPrestamoRenovadoTest() {
		PrestamoRenovado prestamoRenovadoBuscar = entityManager.find(PrestamoRenovado.class, 1);
		Assert.assertNotNull(prestamoRenovadoBuscar);

		// Probar relacion con tipo de prestamo
		Assert.assertEquals("El tipo de prestamo no corresponde al esperado", "PRESTAMO PERSONAL",
				prestamoRenovadoBuscar.getTipoPrestamo().getNombre());

		// Probar relacion con Cliente
		Assert.assertEquals("La cuenta no corresponde a la esperada", "87889234",
				prestamoRenovadoBuscar.getCliente().getNoCuenta());

	}

	/**
	 * Metodo para agregar un prestamo renovado
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "prestamorenovado.json", "persona.json", "tipoprestamo.json" })
	public void agregarPrestamoRenovadoTest() {
		PrestamoRenovado prestamoRenovadoAgregar = new PrestamoRenovado();
		Cliente cliente = new Cliente();
		TipoPrestamo tipoPrestamo = new TipoPrestamo();

		cliente = entityManager.find(Cliente.class, "6208204");
		tipoPrestamo = entityManager.find(TipoPrestamo.class, 4);

		prestamoRenovadoAgregar.setId(3);
		prestamoRenovadoAgregar.setNoCuotas(36);
		prestamoRenovadoAgregar.setValor(36000000);
		prestamoRenovadoAgregar.setCliente(cliente);
		prestamoRenovadoAgregar.setTipoPrestamo(tipoPrestamo);

		entityManager.persist(prestamoRenovadoAgregar);

		prestamoRenovadoAgregar = entityManager.find(PrestamoRenovado.class, 3);
		Assert.assertNotNull("El objeto prestamo renovado es null", prestamoRenovadoAgregar);
	}

	/**
	 * Metodo para modificar un prestamo renovado
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "prestamorenovado.json", "persona.json", "tipoprestamo.json" })
	public void modificarPrestamoRenovadoTest() {
		PrestamoRenovado prestamoRenovadoModificar = entityManager.find(PrestamoRenovado.class, 2);
		prestamoRenovadoModificar.setNoCuotas(18);

		entityManager.merge(prestamoRenovadoModificar);

		Assert.assertEquals("NO se cambiaron las cuotas", 18, prestamoRenovadoModificar.getNoCuotas());
	}

	/**
	 * Metodo para eliminar un prestamo renovado
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "prestamorenovado.json" })
	public void eliminarPrestamoRenovadoTest() {
		PrestamoRenovado prestamoRenovadoEliminar = entityManager.find(PrestamoRenovado.class, 2);

		entityManager.remove(prestamoRenovadoEliminar);

		prestamoRenovadoEliminar = entityManager.find(PrestamoRenovado.class, 2);
		Assert.assertNull("El prestamo renovado existe", prestamoRenovadoEliminar);
	}

	/**
	 * Metodo para buscar un tipo de asesoria
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "asesoria.json", "tipoasesoria.json" })
	public void buscarTipoAsesoriaTest() {
		TipoAsesoria tipoAsesoriaBuscar = entityManager.find(TipoAsesoria.class, 1);
		Assert.assertNotNull(tipoAsesoriaBuscar);

		// Probar relacion con asesoria
		Assert.assertEquals("ERROR Numero de asesorias no es el esperado", 1,tipoAsesoriaBuscar.getAsesoria().size());

	}
	
	/**
	 * Metodo para agregar un tipo de asesoria
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "asesoria.json", "tipoasesoria.json" })
	public void agregarTipoAsesoriaTest() {
		TipoAsesoria tipoAsesoriaAgregar= new TipoAsesoria();
		
		tipoAsesoriaAgregar.setId(4);
		tipoAsesoriaAgregar.setDescripcion("Nueva asesoria");
		tipoAsesoriaAgregar.setTipo("TARJETAS");

		entityManager.persist(tipoAsesoriaAgregar);

		tipoAsesoriaAgregar = entityManager.find(TipoAsesoria.class, 4);
		Assert.assertNotNull("El objeto tipo asesoria es null", tipoAsesoriaAgregar);
	}	
	
	/**
	 * Metodo para modificar un tipo de asesoria
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "asesoria.json", "tipoasesoria.json" })
	public void modificarTipoAsesoriaTest() {
		TipoAsesoria tipoAsesoriaAgregar= entityManager.find(TipoAsesoria.class, 3);
		
		tipoAsesoriaAgregar.setTipo("CUENTAS EN MORA");

		entityManager.merge(tipoAsesoriaAgregar);

		tipoAsesoriaAgregar = entityManager.find(TipoAsesoria.class, 3);
		Assert.assertEquals("NO se cambio eL nombre del tipo asesoria", "CUENTAS EN MORA", tipoAsesoriaAgregar.getTipo());
	}
	
	/**
	 * Metodo para eliminar un tipo asesoria
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "asesoria.json", "tipoasesoria.json" })
	public void eliminarTipoAsesoriaTest() {
		TipoAsesoria tipoAsesoriaAgregar= entityManager.find(TipoAsesoria.class, 3);

		entityManager.remove(tipoAsesoriaAgregar);

		tipoAsesoriaAgregar = entityManager.find(TipoAsesoria.class, 3);
		Assert.assertNull("El tipo asesoria existe", tipoAsesoriaAgregar);
	}
	
	/**
	 * Metodo para buscar una asesoria
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "asesoria.json", "tipoasesoria.json", "persona.json" })
	public void buscarAsesoriaTest() {
		Asesoria asesoriaBuscar = entityManager.find(Asesoria.class, 1);
		Assert.assertNotNull(asesoriaBuscar);

		// Probar relacion con tipo asesoria
		Assert.assertEquals("ERROR nombre tipo credito", "CREDITOS", asesoriaBuscar.getTipoasesoria().getTipo());
		
		//Probar relacion con Empleado
		Assert.assertEquals("ERROR en empleado de asesoria", "Javier", asesoriaBuscar.getEmpleado().getNombres());
	}
	
	/**
	 * Metodo para agregar una asesoria
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "asesoria.json", "tipoasesoria.json", "persona.json" })
	public void agregarAsesoriaTest() {
		Asesoria asesoriaAgregar = new Asesoria();
		Cliente cliente = new Cliente();
		Empleado empleado = new Empleado();
		
		String fechaAseso = "1995-12-30 08:16:00";
		String horaIni ="08:16:00";
		String horaFi ="08:56:00";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
		
		Date fechaAsesoria = new Date();
		Date horaInicio = new Date();
		Date horaFin = new Date();
		
		try {
			fechaAsesoria = sdf.parse(fechaAseso);
			horaInicio=sdfHora.parse(horaIni);
			horaFin = sdfHora.parse(horaFi);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		cliente= entityManager.find(Cliente.class, "123");
		empleado=entityManager.find(Empleado.class, "1234");
		
		asesoriaAgregar.setId(4);
		asesoriaAgregar.setFecha(fechaAsesoria);
		asesoriaAgregar.setHoraFin(horaFin);
		asesoriaAgregar.setHoraInicio(horaInicio);
		asesoriaAgregar.setCliente(cliente);
		asesoriaAgregar.setEmpleado(empleado);
	
		entityManager.persist(asesoriaAgregar);

		asesoriaAgregar = entityManager.find(Asesoria.class, 4);
		Assert.assertNotNull("El objeto asesoria es null", asesoriaAgregar);
	}
	
	/**
	 * Metodo para modificar una asesoria
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "asesoria.json", "tipoasesoria.json", "persona.json" })
	public void modificarAsesoriaTest() {
		Asesoria asesoriaModificar = entityManager.find(Asesoria.class, 3);
		Cliente cliente = entityManager.find(Cliente.class, "6208204");
		
		asesoriaModificar.setCliente(cliente);

		entityManager.merge(asesoriaModificar);

		Assert.assertEquals("NO se cambio el cliente", "6208204", asesoriaModificar.getCliente().getCedula());
	}
	
	/**
	 * Metodo para eliminar una asesoria
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "asesoria.json", "tipoasesoria.json", "persona.json" })
	public void eliminarAsesoriaTest() {
		Asesoria eliminarAsesoria = entityManager.find(Asesoria.class, 3);
		
		entityManager.remove(eliminarAsesoria);
		
		eliminarAsesoria=entityManager.find(Asesoria.class, 3);
		
		Assert.assertNull("La asesoria existe", eliminarAsesoria);
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

		Assert.assertEquals(5, personas.size());

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
