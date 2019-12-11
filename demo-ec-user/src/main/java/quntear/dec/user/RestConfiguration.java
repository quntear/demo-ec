package quntear.dec.user;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.auth.LoginConfig;

@LoginConfig(authMethod = "MP-JWT")
@ApplicationPath("resources")
public class RestConfiguration extends Application {

}
