package web;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 */
@Path("loginService")
public class LoginService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LoginService
     */
    public LoginService() {
    }
    
    @GET
    @Path("hello")
    public String helloWorld() {
        //TODO return proper representation object
        return "Hello World";
    }

    /**
     * Retrieves representation of an instance of web.LoginService
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of LoginService
     * @param content representation for the resource
     */
    @PUT
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
