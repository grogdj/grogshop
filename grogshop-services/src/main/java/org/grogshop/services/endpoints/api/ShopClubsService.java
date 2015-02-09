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
@Path("/clubs")
public interface ShopClubsService extends Serializable {

    @Path("/new")
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    Response newClub(@NotNull @NotEmpty @FormParam("name") String name, 
            @NotNull @NotEmpty @FormParam("description") String description,
            @NotNull @NotEmpty @FormParam("interest") String interest,
            @NotNull @NotEmpty @FormParam("tags") String tags, 
            @NotNull @FormParam("founderId") Long founderId,
            @NotNull @NotEmpty @FormParam("image") String image,
            @FormParam("longitude") Double longitude,
            @FormParam("latitude") Double latitude) throws ServiceException;
   
    @GET
    @Path("/all")
    @Produces({"application/json"})
    Response getAllClubs() throws ServiceException;
    
    @GET
    @Path("/byinterests")
    @Produces({"application/json"})
    Response getAllClubsByInterests(@NotNull @NotEmpty @FormParam("interests") String interests) throws ServiceException;
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    Response get(@PathParam("id") Long club_id) throws ServiceException;
    
    
    
}
