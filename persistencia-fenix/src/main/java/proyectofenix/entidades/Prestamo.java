package proyectofenix.entidades;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * Clase encargada de representar la informacion de un Prestamo
 * @author JJJ
 * @version 1.0 28-agosto-2018
 *
 */

@Entity
public class Prestamo implements Serializable{

	/**
	 * serialVersionUID clase Prestamo
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;

}
