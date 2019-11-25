package quntear.dec.view;

import java.util.Optional;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;

public class KeywordResolverHelper {

	public static Optional<UIComponent> getNamingContainer(UIComponent base) {
		UIComponent form = base.getNamingContainer();
		while (!(form instanceof UIForm) && form != null) {
			form = form.getNamingContainer();
		}
		
		return Optional.ofNullable(form);
	}
}
