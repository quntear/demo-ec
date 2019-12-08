package quntear.dec.seller;

import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.JsonbBuilder;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import quntear.dec.seller.model.User;
import quntear.dec.seller.model.UserGroup;

@CustomFormAuthenticationMechanismDefinition(loginToContinue = @LoginToContinue(loginPage = "/login.html", errorPage = ""))
@ApplicationScoped
public class UserServiceIdentityStore implements IdentityStore {

	@Override
	public CredentialValidationResult validate(Credential credential) {
		UsernamePasswordCredential _credential = (UsernamePasswordCredential) credential;
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/demo-ec-user/resources/users/credential?user={user}&password={password}")
				.resolveTemplate("user", _credential.getCaller())
				.resolveTemplate("password", _credential.getPasswordAsString());
		
		try {
			String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
			User user = JsonbBuilder.create().fromJson(response, User.class);
			Set<String> groups = user.getUserGroups().stream().map(UserGroup::getGroup).collect(Collectors.toSet());
			
			return new CredentialValidationResult(new UserPrincipal(user), groups);
		} catch (NotAuthorizedException e) {
			return CredentialValidationResult.INVALID_RESULT;
		}
	}
}
