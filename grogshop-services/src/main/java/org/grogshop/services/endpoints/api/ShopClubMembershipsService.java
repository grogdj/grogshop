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

/**
 *
 * @author salaboy
 */
@Local
@Path("/members")
public interface ShopClubMembershipsService extends Serializable {

    @Path("/join")
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    Response joinClub(@NotNull @FormParam("club_id") Long club_id,
            @NotNull @FormParam("user_id") Long user_id) throws ServiceException;

    @GET
    @Path("/club/{id}")
    @Produces({"application/json"})
    Response getAllMembers(@NotNull @PathParam("id") Long club_id) throws ServiceException;

    @GET
    @Path("/club/{id}/nromembers")
    @Produces({"application/json"})
    Response getNroMembers(@NotNull @PathParam("id") Long club_id) throws ServiceException;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    Response get(@NotNull @PathParam("id") Long user_id) throws ServiceException;

}
