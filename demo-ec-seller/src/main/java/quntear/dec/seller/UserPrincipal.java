package quntear.dec.seller;

import javax.security.enterprise.CallerPrincipal;

import quntear.dec.seller.model.User;

public class UserPrincipal extends CallerPrincipal {

	private final User user;

	public UserPrincipal(User user) {
		super(user.getEmail());
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return user.getFirstName();
	}
}
