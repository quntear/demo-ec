package quntear.dec.seller.boundry;

import static java.util.stream.Collectors.toSet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.ws.rs.NotAuthorizedException;

import quntear.dec.seller.UserPrincipal;
import quntear.dec.seller.control.UserService;
import quntear.dec.seller.model.UserGroup;

@CustomFormAuthenticationMechanismDefinition(loginToContinue = @LoginToContinue(loginPage = "/login.html", errorPage = ""))
@ApplicationScoped
public class UserServiceIdentityStore implements IdentityStore {
	
	@Inject
	private UserService userService;

	@Override
	public CredentialValidationResult validate(Credential credential) {
		try {
			var user = userService.getUserCredential((UsernamePasswordCredential) credential);
			var groups = user.getUserGroups().stream().map(UserGroup::getGroup).collect(toSet());
			
			return new CredentialValidationResult(new UserPrincipal(user), groups);
		} catch (NotAuthorizedException e) {
			return CredentialValidationResult.INVALID_RESULT;
		}
	}
}
