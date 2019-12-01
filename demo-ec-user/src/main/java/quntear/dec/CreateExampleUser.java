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

import quntear.dec.control.UserService;
import quntear.dec.entity.User;

@Singleton
@Startup
@DependsOn("Dbms")
public class CreateExampleUser {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserService userService;

	@PostConstruct
	public void initialize() {
		createExampleUser();

		System.out.println("list of users:");
		
		Jsonb jsonb = JsonbBuilder.create();
		em.createNamedQuery("User.findAll", User.class).getResultStream().map(jsonb::toJson).forEach(System.out::println);
	}

	private void createExampleUser() {
		User requestUser = new User();
		requestUser.setEmail("quntear@email.com");
		requestUser.setFirstName("Thanarat");
		requestUser.setLastName("Yangsouy");
		requestUser.setPassword("p@ssw0rd");
		userService.create(requestUser);
	}
}
