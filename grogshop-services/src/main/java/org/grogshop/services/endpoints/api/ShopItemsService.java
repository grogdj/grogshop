/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.api;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.ejb.Local;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.grogshop.services.exceptions.ServiceException;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author salaboy
 */
@Local
@Path("/items")
public interface ShopItemsService extends Serializable {

    @Path("/new")
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    Response newItem(@NotNull @FormParam("user_id") Long user_id, 
            @NotNull @FormParam("club_id") Long club_id, 
            @NotNull @NotEmpty @FormParam("name") String name, 
            @NotNull @NotEmpty @FormParam("description") String description,
            @NotNull @NotEmpty @FormParam("tags") String interests,
            @NotNull @FormParam("price") String price) throws ServiceException;

    @GET
    @Path("/all")
    @Produces({"application/json"})
    Response getAllItems();
    
    @GET
    @Path("/club/{id}")
    @Produces({"application/json"})
    Response getAllItemsByClub(@PathParam("id") Long club_id);
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    Response get(@PathParam("id") Long item_id) throws ServiceException;
    
    
    
}
