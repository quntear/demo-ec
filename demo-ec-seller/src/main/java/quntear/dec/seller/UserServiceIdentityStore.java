package quntear.dec.seller;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import quntear.dec.seller.model.User;

@CustomFormAuthenticationMechanismDefinition(loginToContinue = @LoginToContinue(loginPage = "/login.html", errorPage = ""))
@ApplicationScoped
public class UserServiceIdentityStore implements IdentityStore {

	@Override
	public CredentialValidationResult validate(Credential credential) {
//		Optional<User> optionalUser = findUser(credential);

//		if (optionalUser.isPresent()) {
//			User user = optionalUser.get();
			Set<String> groups = new HashSet<>();
			groups.add("SUPERMAN");
			User user = new User();
			user.setEmail("quntear@gmail.com");
			return new CredentialValidationResult(new UserPrincipal(user), groups);
//		} else {
//			return CredentialValidationResult.INVALID_RESULT;
//		}
	}

}
