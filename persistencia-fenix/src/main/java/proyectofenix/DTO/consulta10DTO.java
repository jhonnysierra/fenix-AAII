package proyectofenix.DTO;

import java.util.List;

import proyectofenix.entidades.Pago;

public class consulta10DTO {
	private int idPrestamo;
	private String cedula;
	private String correo;
	private Pago pagos;





	/**
	 * Metodo constructor clase consulta10DTO
	 * @param idPrestamo
	 * @param cedula
	 * @param correo
	 * @param pagos
	 */
	public consulta10DTO(int idPrestamo, String cedula, String correo, Pago pagos) {
		super();
		this.idPrestamo = idPrestamo;
		this.cedula = cedula;
		this.correo = correo;
		this.pagos = pagos;
	}

	/**
	 * Metodo get id prestamo clase consulta10DTO
	 * @return idPrestamo
	 */
	public int getIdPrestamo() {
		return idPrestamo;
	}

	/**
	 * Metodo set id prestamo clase consulta10DTO
	 * @param idPrestamo
	 */
	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	/**
	 * Metodo get cedula clase consulta10DTO
	 * @return the cedula
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * Metodo set cedula clase consulta10DTO
	 * @param cedula
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	/**
	 * Metodo get correo clase consulta10DTO
	 * @return correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Metodo set correo clase consulta10DTO
	 * @param correo
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Metodo get pago clase consulta10DTO
	 * @return pagos
	 */
	public Pago getPagos() {
		return pagos;
	}

	/**
	 * Metodo set pago clase consulta10DTO
	 * @param pagos
	 */
	public void setPagos(Pago pagos) {
		this.pagos = pagos;
	}





	/* 
	 * Metodo hashCode clase consulta10DTO
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cedula == null) ? 0 : cedula.hashCode());
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result + idPrestamo;
		result = prime * result + ((pagos == null) ? 0 : pagos.hashCode());
		return result;
	}



	/* 
	 * Metodo equals clase consulta10DTO
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		consulta10DTO other = (consulta10DTO) obj;
		if (cedula == null) {
			if (other.cedula != null)
				return false;
		} else if (!cedula.equals(other.cedula))
			return false;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		if (idPrestamo != other.idPrestamo)
			return false;
		if (pagos == null) {
			if (other.pagos != null)
				return false;
		} else if (!pagos.equals(other.pagos))
			return false;
		return true;
	}
	
	
}
