package quntear.dec.user.control;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import quntear.dec.user.CustomPbkdf2PasswordHashParameters;
import quntear.dec.user.entity.User;
import quntear.dec.user.event.UserCreated;

@Stateless
public class UserService {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private CustomPbkdf2PasswordHashParameters passwordHash;
	
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
		requestUser.getUserGroups().forEach(ug -> entity.addGroup(ug));
		
		String encodedPassword = passwordHash.generate(requestUser.getPassword());
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
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Optional<User> getByEmailAndPassword(String email, String password) {
		try {
			User found = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email)
					.getSingleResult();
			
			return passwordHash.verify(password, found.getPassword()) ? Optional.of(found) : Optional.empty();
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
}
