package quntear.dec.seller.boundry;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import quntear.dec.seller.ApplicationConfig;

@Named
@RequestScoped
public class Logout {

	@Inject
	private HttpServletRequest request;
	
	public String submit() throws ServletException {
		request.logout();
		request.getSession().invalidate();
		
		try {
			var authMechanismDefinition = ApplicationConfig.class.getAnnotation(CustomFormAuthenticationMechanismDefinition.class);
			var loginToContinue = authMechanismDefinition.loginToContinue();
			return loginToContinue.loginPage() + "?faces-redirect=true";
		} catch (NullPointerException e) {
			return "login?faces-redirect=true";
		}
	}
}
