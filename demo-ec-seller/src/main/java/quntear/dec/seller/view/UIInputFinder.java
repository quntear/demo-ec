package quntear.dec.seller.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@RequestScoped
public class UIInputFinder {
	
	@Inject
	private FacesContext facesContext;

	@Produces @Ui
	public UIInput getInputById(final InjectionPoint ip) {
		UiQualifier qualifier = getRequireUiQualifierAnnotation(ip);
		
		if (qualifier.postback() && !facesContext.isPostback()) {
			return null;
		}
		
		var ids = Arrays.asList(qualifier.id());
		var found = getInputsList(ids);
		
		return found.isEmpty() ? null : found.get(0);
	}
	
	@Produces @Ui
	public List<UIInput> getInputsById(final InjectionPoint ip) {
		UiQualifier qualifier = getRequireUiQualifierAnnotation(ip);
		
		if (qualifier.postback() && !facesContext.isPostback()) {
			return null;
		}
		
		List<String> ids = Arrays.asList(qualifier.id());
		return getInputsList(ids);
	}
	
	private UiQualifier getRequireUiQualifierAnnotation(final InjectionPoint ip) {
		UiQualifier qualifier = ip.getAnnotated().getAnnotation(UiQualifier.class);
		if (qualifier == null) {
			throw new QualifierException("required @UiQualifier");
		}
		
		return qualifier;
	}
	
	private List<UIInput> getInputsList(List<String> ids) {
		List<UIInput> componentCollector = new ArrayList<>();
		
		facesContext.getViewRoot().visitTree(VisitContext.createVisitContext(facesContext), (visit, target) -> {
			if (ids.contains(target.getId()) && target instanceof UIInput) {
				UIInput input = (UIInput) target;
				componentCollector.add(input);
			}
			return VisitResult.ACCEPT;
		});
		
		return componentCollector;
	}
}
