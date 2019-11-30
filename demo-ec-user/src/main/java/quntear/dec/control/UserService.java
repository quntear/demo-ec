package quntear.dec.control;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserService {

	@PersistenceContext
	private EntityManager em;

	public boolean exist(String value) {
		Long count = em.createNamedQuery("User.countByEmail", Long.class).setParameter("email", value)
				.getSingleResult();

		return count > 0;
	}

}
