package proyectofenix.DTO;

public class consulta10DTO {
	private int idPrestamo;
	private String cedula;
	private String correo;
	private int idPago;

	public consulta10DTO(int idPrestamo, String cedula, String correo, int idPago) {
		super();
		this.idPrestamo = idPrestamo;
		this.cedula = cedula;
		this.correo = correo;
		this.idPago = idPago;
	}

	/**
	 * @return the idPrestamo
	 */
	public int getIdPrestamo() {
		return idPrestamo;
	}

	/**
	 * @param idPrestamo the idPrestamo to set
	 */
	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	/**
	 * @return the cedula
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @return the idPago
	 */
	public int getIdPago() {
		return idPago;
	}

	/**
	 * @param idPago the idPago to set
	 */
	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}
	
	
}
