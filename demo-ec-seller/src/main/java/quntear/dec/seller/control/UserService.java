package quntear.dec.seller.control;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import quntear.dec.seller.model.User;

@RequestScoped
public class UserService {

	@Inject
	@ConfigProperty(name = "user.api.token")
	private String token;
	
	@Inject
	@ConfigProperty(name = "user.credential.endpoint")
	private String userCredentialEndpoint;

	public User getUserCredential(UsernamePasswordCredential credential) {
		var client = ClientBuilder.newClient();
		var target = client.target(userCredentialEndpoint)
				.resolveTemplate("user", credential.getCaller())
				.resolveTemplate("password", credential.getPasswordAsString());
		
		var headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		
		var response = target.request(MediaType.APPLICATION_JSON).headers(headers).get(String.class);
		return JsonbBuilder.create().fromJson(response, User.class);
	}
}
