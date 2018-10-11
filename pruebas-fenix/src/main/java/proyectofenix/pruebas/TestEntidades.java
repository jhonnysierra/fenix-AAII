package proyectofenix.pruebas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import proyectofenix.entidades.Administrador;
import proyectofenix.entidades.Asesoria;
import proyectofenix.entidades.BienRaiz;
import proyectofenix.entidades.Ciudad;
import proyectofenix.entidades.Cliente;
import proyectofenix.entidades.Departamento;
import proyectofenix.entidades.Empleado;
import proyectofenix.entidades.Pago;
import proyectofenix.entidades.Persona;
import proyectofenix.entidades.Prestamo;
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
		Assert.assertNotNull("No se encontro la persona",personaBuscar);
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

		/*
		 * System.out.println( "<cedula: " + personaModificar.getCedula() +
		 * "> <apellidos: " + personaModificar.getApellidos() + ">");
		 */
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
		Cliente cliente = entityManager.find(Cliente.class, "123");

		// Probar relacion con tipo de prestamo
		Assert.assertEquals("El tipo de prestamo no corresponde al esperado", "PRESTAMO PERSONAL",
				prestamoRenovadoBuscar.getTipoPrestamo().getNombre());

		// Probar relacion con Cliente
		Assert.assertEquals("La cuenta no corresponde a la esperada", "87889234", cliente.getNoCuenta());

	}

	/**
	 * Metodo para agregar un prestamo renovado
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "prestamorenovado.json", "persona.json", "tipoprestamo.json" })
	public void agregarPrestamoRenovadoTest() {
		PrestamoRenovado prestamoRenovadoAgregar = new PrestamoRenovado();
		Persona cliente = new Persona();
		TipoPrestamo tipoPrestamo = new TipoPrestamo();

		cliente = entityManager.find(Persona.class, "6208204");
		tipoPrestamo = entityManager.find(TipoPrestamo.class, 4);

		prestamoRenovadoAgregar.setId(3);
		prestamoRenovadoAgregar.setNoCuotas(36);
		prestamoRenovadoAgregar.setValor(36000000);
		prestamoRenovadoAgregar.setPersona(cliente);
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
		Assert.assertEquals("ERROR Numero de asesorias no es el esperado", 2, tipoAsesoriaBuscar.getAsesoria().size());

	}

	/**
	 * Metodo para agregar un tipo de asesoria
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "asesoria.json", "tipoasesoria.json" })
	public void agregarTipoAsesoriaTest() {
		TipoAsesoria tipoAsesoriaAgregar = new TipoAsesoria();

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
		TipoAsesoria tipoAsesoriaAgregar = entityManager.find(TipoAsesoria.class, 3);

		tipoAsesoriaAgregar.setTipo("CUENTAS EN MORA");

		entityManager.merge(tipoAsesoriaAgregar);

		tipoAsesoriaAgregar = entityManager.find(TipoAsesoria.class, 3);
		Assert.assertEquals("NO se cambio eL nombre del tipo asesoria", "CUENTAS EN MORA",
				tipoAsesoriaAgregar.getTipo());
	}

	/**
	 * Metodo para eliminar un tipo asesoria
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "asesoria.json", "tipoasesoria.json" })
	public void eliminarTipoAsesoriaTest() {
		TipoAsesoria tipoAsesoriaAgregar = entityManager.find(TipoAsesoria.class, 3);

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

		// Probar relacion con Empleado
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
		String horaIni = "08:16:00";
		String horaFi = "08:56:00";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");

		Date fechaAsesoria = new Date();
		Date horaInicio = new Date();
		Date horaFin = new Date();

		try {
			fechaAsesoria = sdf.parse(fechaAseso);
			horaInicio = sdfHora.parse(horaIni);
			horaFin = sdfHora.parse(horaFi);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		cliente = entityManager.find(Cliente.class, "123");
		empleado = entityManager.find(Empleado.class, "1234");

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

		eliminarAsesoria = entityManager.find(Asesoria.class, 3);

		Assert.assertNull("La asesoria existe", eliminarAsesoria);
	}

	/**
	 * Metodo para buscar un tipo de prestamo
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "prestamorenovado.json" })
	public void buscarTipoPrestamoTest() {
		TipoPrestamo tipoPrestamoBuscar = entityManager.find(TipoPrestamo.class, 1);
		Assert.assertNotNull(tipoPrestamoBuscar);

		// Probar relacion con prestamo
		Assert.assertEquals("ERROR Numero de prestamo no es el esperado", 1, tipoPrestamoBuscar.getPrestamo().size());

		// Probar relacion con prestamo renovado
		Assert.assertEquals("ERROR Numero de prestamo renovado no es el esperado", 1,
				tipoPrestamoBuscar.getPrestamoRenovado().size());
	}

	/**
	 * Metodo para agregar un tipo de prestamo
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json" })
	public void agregarTipoPrestamoTest() {
		TipoPrestamo tipoPresatamoAgregar = new TipoPrestamo();

		tipoPresatamoAgregar.setId(5);
		tipoPresatamoAgregar.setDescripcion("Descripcion");
		tipoPresatamoAgregar.setNombre("PRESTAMO INTERCAMBIO");
		tipoPresatamoAgregar.setTasaInteres(0.35);

		entityManager.persist(tipoPresatamoAgregar);

		tipoPresatamoAgregar = entityManager.find(TipoPrestamo.class, 5);
		Assert.assertNotNull("El objeto tipo asesoria es null", tipoPresatamoAgregar);

	}

	/**
	 * Metodo para modificar un tipo de prestamo
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json" })
	public void modificarTipoPrestamoTest() {
		TipoPrestamo tipoPresatamoModificar = entityManager.find(TipoPrestamo.class, 4);
		String tasaInteres;

		tipoPresatamoModificar.setTasaInteres(0.35);

		entityManager.persist(tipoPresatamoModificar);

		tipoPresatamoModificar = entityManager.find(TipoPrestamo.class, 4);
		tasaInteres = String.valueOf(tipoPresatamoModificar.getTasaInteres());
		Assert.assertEquals("La tasa de interes no se modifico", "0.35", tasaInteres);

	}

	/**
	 * Metodo para eliminar un tipo de prestamo
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json" })
	public void eliminarTipoPrestamoTest() {
		TipoPrestamo tipoPresatamoEliminar = entityManager.find(TipoPrestamo.class, 4);

		entityManager.remove(tipoPresatamoEliminar);

		tipoPresatamoEliminar = entityManager.find(TipoPrestamo.class, 4);
		Assert.assertNull("El tipo prestamo existe", tipoPresatamoEliminar);

	}

	/**
	 * Metodo para buscar un prestamo
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json" })
	public void buscarPrestamoTest() {
		Prestamo prestamoBuscar = entityManager.find(Prestamo.class, 2);
		Assert.assertNotNull(prestamoBuscar);

		// Probar la relacion con tipo prestamo
		Assert.assertEquals("ERROR nombre tipo prestamo", "PRESTAMO PERSONAL",
				prestamoBuscar.getTipoPrestamo().getNombre());

		// Probar la relacion con el cliente
		Assert.assertEquals("No corresponde el cliente", "Hector", prestamoBuscar.getPersona().getNombres());

		// Probar relacion con pagos
		Assert.assertEquals("ERROR numero de pagos", 2, prestamoBuscar.getPagos().size());
	}

	/**
	 * Metodo para agregar un prestamo
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json" })
	public void agregarPrestamoTest() {
		Prestamo prestamoAgregar = new Prestamo();
		Persona empleado = entityManager.find(Empleado.class, "12345");
		TipoPrestamo tipoPrestamo = entityManager.find(TipoPrestamo.class, 3);

		String fechaInicio = "2017-12-30 08:16:00";
		String fechaFin = "2018-03-30 08:16:00";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date fechaInicioPrestamo = new Date();
		Date fechaFinPrestamo = new Date();

		try {
			fechaInicioPrestamo = sdf.parse(fechaInicio);
			fechaFinPrestamo = sdf.parse(fechaFin);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		prestamoAgregar.setId(5);
		prestamoAgregar.setPersona(empleado);
		prestamoAgregar.setFechaInicio(fechaInicioPrestamo);
		prestamoAgregar.setFechaFin(fechaFinPrestamo);
		prestamoAgregar.setNoCuotas(48);
		prestamoAgregar.setValorPrestamo(34000000);
		prestamoAgregar.setPersona(empleado);
		prestamoAgregar.setTipoPrestamo(tipoPrestamo);

		entityManager.persist(prestamoAgregar);

		prestamoAgregar = entityManager.find(Prestamo.class, 5);
		Assert.assertNotNull("El objeto prestamo es null", prestamoAgregar);
	}

	/**
	 * Metodo para modificar un prestamo
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json" })
	public void modificarPrestamoTest() {
		Prestamo prestamoModificar = entityManager.find(Prestamo.class, 3);
		String fechaFin = "2019-03-30 08:16:00";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date fechaFinPrestamo = new Date();
		Date fechaAnterior = new Date();

		try {
			fechaFinPrestamo = sdf.parse(fechaFin);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fechaAnterior = prestamoModificar.getFechaFin();

		prestamoModificar.setFechaFin(fechaFinPrestamo);

		entityManager.persist(prestamoModificar);

		prestamoModificar = entityManager.find(Prestamo.class, 3);

		Assert.assertNotEquals("La fecha no se modifico", fechaAnterior, prestamoModificar.getFechaFin());

	}

	/**
	 * Metodo para eliminar un prestamo
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json" })
	public void eliminarPrestamoTest() {
		Prestamo prestamoEliminar = entityManager.find(Prestamo.class, 2);

		entityManager.remove(prestamoEliminar);

		prestamoEliminar = entityManager.find(Prestamo.class, 2);
		Assert.assertNull("El prestamo existe", prestamoEliminar);

	}

	/**
	 * Metodo para buscar un pago
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json" })
	public void buscarPagoTest() {
		Pago pagoBuscar = entityManager.find(Pago.class, 2);
		Assert.assertNotNull(pagoBuscar);

		// Probar relacion de pago con prestamo
		Assert.assertEquals("ERROR prestamo no corresponde al pago", 1, pagoBuscar.getPrestamo().getId());

	}

	/**
	 * Metodo para agregar un pago
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json" })
	public void agregarPagoTest() {
		Pago pagoAgregar = new Pago();
		Prestamo prestamo = entityManager.find(Prestamo.class, 2);
		String fecha = "2016-12-30 08:16:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fechaPago = new Date();

		try {
			fechaPago = sdf.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pagoAgregar.setId(5);
		pagoAgregar.setFecha(fechaPago);
		pagoAgregar.setValor(200000);
		pagoAgregar.setPrestamo(prestamo);

		entityManager.persist(pagoAgregar);

		pagoAgregar = entityManager.find(Pago.class, 5);
		Assert.assertNotNull("El objeto pago es null", pagoAgregar);
	}

	/**
	 * Metodo para modificar un pago
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json" })
	public void modificarPagoTest() {
		Pago pagoModificar = entityManager.find(Pago.class, 2);

		pagoModificar.setValor(250000);

		entityManager.merge(pagoModificar);

		String pagoActual = String.valueOf(pagoModificar.getValor());
		Assert.assertEquals("El pago no se modifico", "250000.0", pagoActual);
	}

	/**
	 * Metodo para eliminar un pago
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json" })
	public void eliminarPagoTest() {
		Pago pagoEliminar = entityManager.find(Pago.class, 2);

		entityManager.remove(pagoEliminar);

		pagoEliminar = entityManager.find(Pago.class, 2);
		Assert.assertNull("El pago existe", pagoEliminar);

	}

	/**
	 * Metodo para buscar una Empleado
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json", "persona_telefonos.json" })
	public void buscarEmpleadoTest() {

		Empleado empleadoBuscar = entityManager.find(Empleado.class, "1234");
		Assert.assertNotNull(empleadoBuscar);
		// Probar que la persona tiene 1 telefono asociado
		Assert.assertEquals(1, empleadoBuscar.getTelefonos().size());

		// Creditos asociados a un empleado
		Assert.assertEquals("El numero esperado no corresponde a los creditos del empleado", 1,
				empleadoBuscar.getPrestamo().size());
	}

	/**
	 * Metodo para modificar una Empleado
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json", "persona_telefonos.json" })
	public void modificarEmpleadoTest() {
		Date fechainicioEmpleado = null, fechaInicioAnterior;

		Empleado empleadoModificar = entityManager.find(Empleado.class, "12345");

		fechaInicioAnterior = empleadoModificar.getFechaInicio();
		String fechaIni = "2012-01-30 09:00:00";

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

		try {
			fechainicioEmpleado = sdf.parse(fechaIni);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		empleadoModificar.setFechaInicio(fechainicioEmpleado);
		entityManager.merge(empleadoModificar);

		Assert.assertNotEquals("La fecha no se modifico", fechaInicioAnterior, empleadoModificar.getFechaInicio());
	}

	/**
	 * Metodo para eliminar una Empleado
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "persona_telefonos.json" })
	public void eliminarEmpleadoTest() {
		Empleado empleadoEliminar = entityManager.find(Empleado.class, "12345");
		Persona personaEliminar = entityManager.find(Persona.class, "12345");

		entityManager.remove(empleadoEliminar);
		entityManager.remove(personaEliminar);

		empleadoEliminar = entityManager.find(Empleado.class, "12345");
		Assert.assertNull("La persona existe", empleadoEliminar);
	}

	/**
	 * Metodo para buscar un Cliente
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json", "persona_telefonos.json" })
	public void buscarClienteTest() {

		Cliente clienteBuscar = entityManager.find(Cliente.class, "6208204");
		Assert.assertNotNull(clienteBuscar);
		// Probar que la persona tiene telefonos asociados
		Assert.assertEquals(0, clienteBuscar.getTelefonos().size());

		// Creditos asociados a un cliente
		Assert.assertEquals("El numero esperado no corresponde a los creditos del cliente", 4,
				clienteBuscar.getPrestamo().size());
	}

	/**
	 * Metodo para modificar una Cliente
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json", "persona_telefonos.json" })
	public void modificarClienteTest() {

		Cliente clienteModificar = entityManager.find(Cliente.class, "6208204");

		clienteModificar.setNoCuenta("234");
		entityManager.merge(clienteModificar);

		clienteModificar = entityManager.find(Cliente.class, "6208204");
		Assert.assertEquals("La cuenta no se modifico", "234", clienteModificar.getNoCuenta());
	}

	/**
	 * Metodo para eliminar una Cliente
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "persona_telefonos.json" })
	public void eliminarClienteTest() {
		Cliente clienteEliminar = entityManager.find(Cliente.class, "6208204");
		Persona personaEliminar = entityManager.find(Persona.class, "6208204");

		entityManager.remove(clienteEliminar);
		entityManager.remove(personaEliminar);

		clienteEliminar = entityManager.find(Cliente.class, "6208204");
		Assert.assertNull("El cliente existe", clienteEliminar);
	}

	/**
	 * Metodo para buscar un Administrador
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json", "persona_telefonos.json" })
	public void buscarAdministradorTest() {

		Administrador administradorBuscar = entityManager.find(Administrador.class, "201856105");
		Assert.assertNotNull(administradorBuscar);
		// Probar que la persona tiene telefonos asociados
		Assert.assertEquals(0, administradorBuscar.getTelefonos().size());

		// Creditos asociados a un cliente
		Assert.assertEquals("El numero esperado no corresponde a los creditos del Administrador", 0,
				administradorBuscar.getPrestamo().size());
	}

	/**
	 * Metodo para modificar una Administrador
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "tipoPrestamo.json", "prestamo.json", "persona.json", "pago.json", "persona_telefonos.json" })
	public void modificarAdministradorTest() {

		Administrador administradorModificar = entityManager.find(Administrador.class, "201856105");

		administradorModificar.setContrasenia("123456");
		entityManager.merge(administradorModificar);

		administradorModificar = entityManager.find(Administrador.class, "201856105");
		Assert.assertEquals("La contrasena no se modifico", "123456", administradorModificar.getContrasenia());
	}

	/**
	 * Metodo para eliminar una Administrador
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "persona_telefonos.json" })
	public void eliminarAdministradorTest() {
		Administrador administradorEliminar = entityManager.find(Administrador.class, "201856105");
		Persona personaEliminar = entityManager.find(Persona.class, "201856105");

		entityManager.remove(administradorEliminar);
		entityManager.remove(personaEliminar);

		administradorEliminar = entityManager.find(Administrador.class, "201856105");
		Assert.assertNull("El Administrador existe", administradorEliminar);
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

		Assert.assertEquals(14, personas.size());

		/*
		 * for (Persona p : personas) {
		 * System.out.println(String.format("CC:%s, nombre:%s", p.getCedula(),
		 * p.getNombres())); }
		 */

	}

	/**
	 * Permite obtener un persona segun sus credenciales de acceso a la plataforma
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json", "persona_telefonos.json" })
	public void obtenerPersonaPorCredencialesTest() {

		// Para que no muestre error si no hay resultados solo para getSingleResult
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
			Assert.fail(String.format("Error buscandola la persona %s", e.getMessage()));
		}

	}

	/**
	 * Permite obtener los clientes creados
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void obtenerClientesTest() {
		TypedQuery<Cliente> clientes = entityManager.createNamedQuery(Cliente.OBTENER_DATOS_CLIENTE, Cliente.class);
		List<Cliente> resultadosClientes = clientes.getResultList();

		Assert.assertEquals("El resultado de la consulta no corresponde al numero de clientes", 6,
				resultadosClientes.size());

		/*
		 * for (Persona p : resultadosClientes) {
		 * System.out.println(String.format("CC:%s, nombre:%s", p.getCedula(),
		 * p.getNombres())); }
		 */

	}

	/**
	 * Permite obtener los empleados creados
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void obtenerEmpleadosTest() {
		TypedQuery<Empleado> empleados = entityManager.createNamedQuery(Empleado.OBTENER_DATOS_EMPLEADO,
				Empleado.class);
		List<Empleado> resultadosEmpleados = empleados.getResultList();

		Assert.assertEquals("El resultado de la consulta no corresponde al numero de empleados", 3,
				resultadosEmpleados.size());

		/*
		 * for (Persona p : resultadosEmpleados) {
		 * System.out.println(String.format("CC:%s, nombre:%s", p.getCedula(),
		 * p.getNombres())); }
		 */

	}

	/**
	 * Permite obtener los administradores creados
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void obtenerAdministradoresTest() {
		TypedQuery<Administrador> administradores = entityManager
				.createNamedQuery(Administrador.OBTENER_DATOS_ADMINISTRADOR, Administrador.class);
		List<Administrador> resultadosAdministradores = administradores.getResultList();

		Assert.assertEquals("El resultado de la consulta no corresponde al numero de administradores", 3,
				resultadosAdministradores.size());

		/*
		 * for (Persona p : resultadosEmpleados) {
		 * System.out.println(String.format("CC:%s, nombre:%s", p.getCedula(),
		 * p.getNombres())); }
		 */
	}

	/**
	 * Permite obtener los clientes creados
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void obtenerPersonasLimitarResultadosTest() {
		// Para que no muestre error si no hay resultados
		try {
			TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.OBTENER_DATOS_PERSONAS, Persona.class);
			query.setMaxResults(3);
			List<Persona> listaPersonas = query.getResultList();

			for (Persona p : listaPersonas) {
				System.out.println(String.format("CC:%s, nombre:%s", p.getCedula(), p.getNombres()));
			}

		} catch (NoResultException e) {
			Assert.fail(String.format("Error: %s", e.getMessage()));
		}

	}

	/**
	 * Permite obtener las personas desde un numero de registro especifico
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void obtenerPersonasFirstResultadosTest() {
		// Para que no muestre error si no hay resultados
		try {
			TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.OBTENER_DATOS_PERSONAS, Persona.class);
			query.setFirstResult(10);
			List<Persona> listaPersonas = query.getResultList();

			for (Persona p : listaPersonas) {
				System.out.println(String.format("CC:%s, nombre:%s", p.getCedula(), p.getNombres()));
			}

		} catch (NoResultException e) {
			Assert.fail(String.format("Error: %s", e.getMessage()));
		}

	}

	/**
	 * Permite hacer filtro de una persona por edad
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void obtenerPersonasFiltroEdadTest() {
		// Para que no muestre error si no hay resultados
		try {
			Query query = entityManager.createNativeQuery("select * from Persona p where (YEAR(CURDATE())-YEAR(p.fecha_nacimiento))>50 and p.estado='1'", Persona.class);
//					"select p from Persona p where (YEAR(CURDATE)-YEAR(p.fecha_nacimiento))>50 and p.estado='1'",
//					Persona.class);
			
			// obterner la informacion en una lista
			List<Persona> personas = query.getResultList();

			for (Persona p : personas) {
				System.out.println(String.format("CC:%s, nombre:%s", p.getCedula(), p.getNombres()));
			}

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
