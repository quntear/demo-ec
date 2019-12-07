package quntear.dec.seller.boundry;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Named
@RequestScoped
public class Login implements Serializable {
	private static final long serialVersionUID = -3024883429735722538L;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	private String password;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String submit() {
		return null;
	}
}
