package web;

import Util.Util;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import org.json.JSONObject;
import service.LoginService;

/**
 * REST Web Service
 *
 */
@Path("loginService")
public class LoginWS {

    @Context
    private UriInfo context;
    
    private Connection con;
    
    LoginService loginService = new LoginService();
    
    /**
     * Creates a new instance of LoginService
     */
    public LoginWS() {
    }
    
    @GET
    @Path("hello")
    public String helloWorld() {
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
    
    @POST
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @Path("login")
    public String login(String json) {
        
        System.out.println("jsonRequest" + json);
        String message = loginService.authentication(json);
        
        return message;
    }
}
