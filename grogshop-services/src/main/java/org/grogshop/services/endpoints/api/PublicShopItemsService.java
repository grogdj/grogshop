/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.api;

import java.io.Serializable;
import javax.ejb.Local;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
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
@Path("/public/items")
public interface PublicShopItemsService extends Serializable {

    @GET
    @Path("/all")
    @Produces({"application/json"})
    Response getAllItems() throws ServiceException;
    
    @GET
    @Path("/club/{id}")
    @Produces({"application/json"})
    Response getAllItemsByClub(@PathParam("id") Long club_id) throws ServiceException;
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    Response get(@PathParam("id") Long item_id) throws ServiceException;
  
    
    @Path("{id}/image")
    @GET
    @Consumes({MediaType.MULTIPART_FORM_DATA})
            @Produces({MediaType.APPLICATION_OCTET_STREAM})
    Response getItemImage(@NotNull @PathParam("id") Long item_id) throws ServiceException;
    
}
