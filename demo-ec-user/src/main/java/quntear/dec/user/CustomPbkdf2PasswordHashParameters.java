package quntear.dec.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
public class CustomPbkdf2PasswordHashParameters implements PasswordHash {
	
	@Inject
	private Pbkdf2PasswordHash passwordHash;
	
	@PostConstruct
	private void initPasswordHash() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
		parameters.put("Pbkdf2PasswordHash.KeySizeBytes", "64");
		parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        
		passwordHash.initialize(parameters);
	}
	
	public String generate(String password) {
		return generate(password.toCharArray());
	}
	
	public boolean verify(String password, String hashedPassword) {
		return verify(password.toCharArray(), hashedPassword);
	}

	@Override
	public String generate(char[] password) {
		return passwordHash.generate(password);
	}

	@Override
	public boolean verify(char[] password, String hashedPassword) {
		return passwordHash.verify(password, hashedPassword);
	}
}
