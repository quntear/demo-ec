package quntear.dec.control;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import quntear.dec.entity.User;
import quntear.dec.event.UserCreated;

@Stateless
public class UserService {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private Pbkdf2PasswordHash passwordHash;
	
	@Inject
	@UserCreated
	private Event<User> event;

	public boolean exist(String value) {
		Long count = em.createNamedQuery("User.countByEmail", Long.class).setParameter("email", value)
				.getSingleResult();

		return count > 0;
	}
	
	public User create(User requestUser) {
		User entity = new User();
		entity.setActive(Boolean.FALSE);
		entity.setEmail(requestUser.getEmail());
		entity.setFirstName(requestUser.getFirstName());
		entity.setLastName(requestUser.getLastName());
		
		String encodedPassword = passwordHash.generate(requestUser.getPassword().toCharArray());
		entity.setPassword(encodedPassword);
		
		em.persist(entity);
		
		event.fireAsync(entity);
		
		return entity;
	}
	
	public void activate(Integer userId) {
		User found = em.find(User.class, userId);
		
		found.setActive(Boolean.TRUE);
		em.merge(found);
	}
}
