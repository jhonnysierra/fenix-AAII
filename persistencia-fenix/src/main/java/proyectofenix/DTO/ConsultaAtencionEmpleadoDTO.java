package proyectofenix.DTO;

/**
 * Clase que representa el resultado de la consulta de las veces que un empleado ha atendido una cita
 * @author JJ
 *
 */
public class ConsultaAtencionEmpleadoDTO {
	private String cedula;
	private Long asesorias;
	/**
	 * @param cedula
	 * @param asesorias
	 */
	public ConsultaAtencionEmpleadoDTO(String cedula, Long asesorias) {
		super();
		this.cedula = cedula;
		this.asesorias = asesorias;
	}
	/**
	 * Metodo get cedula empleado
	 * @return cedula
	 */
	public String getCedula() {
		return cedula;
	}
	/**
	 * Metodo set cedula empleado
	 * @param cedula
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	/**
	 * Metodo get cantidad asesorias empleado
	 * @return asesorias
	 */
	public Long getAsesorias() {
		return asesorias;
	}
	/**
	 * Metodo set cantidad asesorias empleado
	 * @param asesorias
	 */
	public void setAsesorias(Long asesorias) {
		this.asesorias = asesorias;
	}
	/* 
	 * Metodo hashcode Clase ConsultaAtencionEmpleadoDTO
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asesorias == null) ? 0 : asesorias.hashCode());
		result = prime * result + ((cedula == null) ? 0 : cedula.hashCode());
		return result;
	}

	/* 
	 * Metodo equals Clase ConsultaAtencionEmpleadoDTO
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConsultaAtencionEmpleadoDTO other = (ConsultaAtencionEmpleadoDTO) obj;
		if (asesorias == null) {
			if (other.asesorias != null)
				return false;
		} else if (!asesorias.equals(other.asesorias))
			return false;
		if (cedula == null) {
			if (other.cedula != null)
				return false;
		} else if (!cedula.equals(other.cedula))
			return false;
		return true;
	}

	
	

}
