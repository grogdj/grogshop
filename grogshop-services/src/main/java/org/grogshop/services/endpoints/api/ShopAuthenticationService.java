/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.api;

import java.io.Serializable;
import javax.ejb.Local;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author salaboy
 */
@Local
@Path("/auth")
public interface ShopAuthenticationService extends Serializable {

    @POST
    @Path("/register")
    @Produces({MediaType.APPLICATION_JSON})
    public String registerUser(
            @FormParam("email") String email, 
            @FormParam("password") String password);

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @Context HttpHeaders httpHeaders,
            @FormParam("email") String email,
            @FormParam("password") String password);
    
    @POST
    @Path("/logout")
    public Response logout(
            @Context HttpHeaders httpHeaders);
}
