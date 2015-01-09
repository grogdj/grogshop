/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.api;

import java.io.Serializable;
import javax.ejb.Local;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author salaboy
 */
@Local
@Path("/public/clubs")
public interface PublicShopClubsService extends Serializable {

    @GET
    @Path("/all")
    @Produces({"application/json"})
    Response getAllClubs();
    
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    Response get(@PathParam("id") Long club_id) throws ServiceException;
    
    
    
}
