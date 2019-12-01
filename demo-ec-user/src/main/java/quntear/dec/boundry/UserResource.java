package quntear.dec.boundry;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import quntear.dec.control.UserService;

@Path("users")
public class UserResource {
	
	@Inject
	private UserService userService;
	
	@GET
	@Path("{userId}/actions/activate")
	public void activateCustomer(@PathParam("userId") Integer userId) {
		userService.activate(userId);
	}

}
