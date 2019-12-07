package quntear.dec.seller.boundry;

import static javax.security.enterprise.AuthenticationStatus.SEND_CONTINUE;
import static javax.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static javax.security.enterprise.AuthenticationStatus.SUCCESS;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.omnifaces.util.Messages;

import quntear.dec.seller.view.Ui;
import quntear.dec.seller.view.UiQualifier;

@Named
@RequestScoped
public class Login implements Serializable {
	private static final long serialVersionUID = -3024883429735722538L;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	private String password;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Inject
	private SecurityContext securityContext;
	@Inject
	private ExternalContext externalContext;
	@Inject
	private FacesContext facesContext;
	
	@Inject
	@Ui @UiQualifier(id = {"email", "password"})
	private List<UIInput> inputs;

	public String submit() {
		var request = (HttpServletRequest) externalContext.getRequest();
		var response = (HttpServletResponse) externalContext.getResponse();
		var credential = new UsernamePasswordCredential(email, password);
		var parameters = AuthenticationParameters.withParams().credential(credential);
		var status = securityContext.authenticate(request, response, parameters);
		
		if (SEND_CONTINUE.equals(status)) {
			facesContext.responseComplete();
		} else if (SEND_FAILURE.equals(status)) {
			inputs.forEach(i -> i.setValid(false));
			Messages.addWarn("login_failed", "Login failed");
			this.email = null;
		} else if (SUCCESS.equals(status)) {
			return "welcome.html?faces-redirect=true";
		}
		
		return null;
	}
}
