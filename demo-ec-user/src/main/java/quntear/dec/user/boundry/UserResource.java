package quntear.dec.user.boundry;

import java.security.Principal;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.jwt.JsonWebToken;

import quntear.dec.user.control.UserService;

@RolesAllowed("USER_INFO")
@Path("users")
public class UserResource {
	
	@Inject
	private UserService userService;
	
	@Inject
    private JsonWebToken token;
	
	@Inject
	private Principal principal;
	
	@GET
	@Path("{userId}/actions/activate")
	public void activateCustomer(@PathParam("userId") Integer userId) {
		userService.activate(userId);
	}

	@RolesAllowed("USER_CREDENTIAL")
	@GET
	@Path("credential")
	@Produces(MediaType.APPLICATION_JSON)
	public Response activateCustomer(@QueryParam("user") String email, @QueryParam("password") String password) {
		return userService.getByEmailAndPassword(email, password)
				.map(Response::ok).map(ResponseBuilder::build)
				.orElseGet(() -> Response.status(Status.NOT_FOUND).entity("user/password not found").build());
	}
}
