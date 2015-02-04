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
@Path("/matchings")
public interface ShopMatchingsService extends Serializable {

    @GET
    @Path("/all")
    @Produces({"application/json"})
    Response getAllMatchings() throws ServiceException;
    
    @GET
    @Path("/item/{id}")
    @Produces({"application/json"})
    Response getAllMatchingsByItem(@PathParam("id") Long itemId) throws ServiceException;
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    Response get(@PathParam("id") Long item_id) throws ServiceException;
    
    
}
