package quntear.dec;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import quntear.dec.entity.User;

@Singleton
@Startup
@DependsOn("Dbms")
public class CreateExampleUser {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private Pbkdf2PasswordHash passwordHash;

	@PostConstruct
	public void initialize() {
		createExampleUser();

		System.out.println("list of users:");
		
		Jsonb jsonb = JsonbBuilder.create();
		em.createNamedQuery("User.findAll", User.class).getResultStream().map(jsonb::toJson).forEach(System.out::println);
	}

	private void createExampleUser() {
		User entity = new User();
		entity.setActive(Boolean.FALSE);
		entity.setEmail("quntear@email.com");
		entity.setFirstName("Thanarat");
		entity.setLastName("Yangsouy");
		entity.setPassword(passwordHash.generate("p@ssw0rd".toCharArray()));
		em.persist(entity);
	}
}
