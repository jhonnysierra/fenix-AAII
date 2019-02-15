package utilidades.web;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import proyectofenix.entidades.Persona;
import proyectofenix.negocio.BancoEJBRemote;

@FacesConverter("personaConverter")
public class EmpleadoConverter implements Converter<Persona> {

	private BancoEJBRemote bancoEJB;

	public EmpleadoConverter() {
		try {
			bancoEJB = (BancoEJBRemote) new InitialContext().lookup(BancoEJBRemote.JNDI);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Persona getAsObject(FacesContext context, UIComponent arg1, String id) {
		Persona empleado = null;
		if (id != null && !"".equals(id)) {
			try {
				empleado = bancoEJB.buscarPersona(id);
			} catch (Exception e) {
				throw new ConverterException(new FacesMessage(arg1.getClientId() +

						":ID no valido"));
			}
		}
		return empleado;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent componente, Persona empleado) {
		if (empleado!=null) 
			return empleado.getCedula();
		return"";
	}

}
