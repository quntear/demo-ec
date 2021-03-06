package quntear.dec.user.view;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import quntear.dec.user.control.UserService;

@FacesValidator(value = "uniqueEmailValidator", managed = true)
public class UniqueEmailValidator implements Validator<String> {
	
	@Inject
	private UserService userService;

	@Override
	public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
		if (value == null || value.isBlank()) {
			return;
		}
		
		String oldValue = (String) ((UIInput) component).getValue();
		if (!value.equals(oldValue) && userService.exist(value)) {
			throw new ValidatorException(new FacesMessage(getMessage(context, "email-already-in-use")));
		}
	}

	private String getMessage(FacesContext context, String key) {
		Locale locale = context.getViewRoot().getLocale();
		return ResourceBundle.getBundle(context.getApplication().getMessageBundle(), locale).getString(key);
	}

}
