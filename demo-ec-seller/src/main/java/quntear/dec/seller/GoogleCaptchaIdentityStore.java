package quntear.dec.seller;

import static java.util.Optional.ofNullable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GoogleCaptchaIdentityStore implements IdentityStore {
	
	@Inject
	private ExternalContext externalContext;

	@Inject
	@ConfigProperty(name = "google.recaptcha.secretkey")
	private String recaptchaSecretKey;
	
	@Override
	public CredentialValidationResult validate(Credential credential) {
		var request = (HttpServletRequest) externalContext.getRequest();
		var captchaResponse = ofNullable(request.getParameter("g-recaptcha-response")).map(String::trim).orElse("");
		if (captchaResponse.isEmpty()) {
			return CredentialValidationResult.INVALID_RESULT;
		}

		var client = ClientBuilder.newClient();
		var target = client.target("https://www.google.com/recaptcha/api/siteverify");
		var apiRequest = new MultivaluedHashMap<String, String>();
		apiRequest.add("secret", recaptchaSecretKey);
		apiRequest.add("response", captchaResponse);
		
		var response = target.request(MediaType.APPLICATION_JSON).post(Entity.form(apiRequest), JsonObject.class);
		System.out.println(response);
		
		var success = response.getBoolean("success");
		if (success) {
			return CredentialValidationResult.NOT_VALIDATED_RESULT;
		} else {
			return CredentialValidationResult.INVALID_RESULT;
		}
	}
	
	@Override
	public int priority() {
		return 10;
	}
}
