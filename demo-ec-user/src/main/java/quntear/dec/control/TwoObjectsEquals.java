package quntear.dec.control;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.omnifaces.validator.MultiFieldValidator;

@Named
@ApplicationScoped
public class TwoObjectsEquals implements MultiFieldValidator {

	@Override
	public boolean validateValues(FacesContext context, List<UIInput> components, List<Object> values) {
		return values.get(0).equals(values.get(1));
	}

}
