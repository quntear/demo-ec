package quntear.dec.boundry;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Email;

import quntear.dec.control.UserService;
import quntear.dec.entity.User;

@Named
@ViewScoped
public class Register implements Serializable {
	private static final long serialVersionUID = -3024883429735722538L;

	private String firstName;
	
	private String lastName;
	
	@Email
	private String email;
	
	private String password;
	
	private String confirmPassword;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@Inject
	private UserService userService;
	
	@Inject
	private FacesContext facesContext;

	public void submit() {
		User requestUser = new User();
		requestUser.setFirstName(firstName);
		requestUser.setLastName(lastName);
		requestUser.setEmail(email);
		requestUser.setPassword(password);
		
		User created = userService.create(requestUser);
		
		Integer createdUserId = created.getId();
		System.out.println("user has been created id " + createdUserId);
		
		facesContext.getPartialViewContext().getEvalScripts()
			.add("successfullyForward();");
	}
}
