/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.MembershipsService;
import org.grogshop.services.endpoints.api.ShopClubMembershipsService;
import org.grogshop.services.exceptions.ServiceException;


@Stateless
public class ShopClubMembershipsServiceImpl implements ShopClubMembershipsService {

    @Inject
    private MembershipsService membershipsService;

    @Override
    public Response get(@NotNull @PathParam("id") Long user_id) throws ServiceException {
        List<Long> clubIds = membershipsService.get(user_id);
        JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
        for (Long id : clubIds) {
            jsonArrayBuilderInterest.add(id);
        }
        JsonArray build = jsonArrayBuilderInterest.build();
        System.out.println("Array: "+build.toString());
        return Response.ok(build.toString()).build();
    }

    @Override
    public Response create(@NotNull @FormParam("club_id") Long club_id,
            @NotNull @FormParam("user_id") Long user_id) throws ServiceException {
        membershipsService.createMembership(club_id, user_id);
        return Response.ok().build();
    }

    @Override
    public Response getAllMembers(@NotNull @PathParam("id") Long club_id) throws ServiceException {
        List<Long> memberIds = membershipsService.getAllMembers(club_id);
        JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
        for (Long id : memberIds) {
            jsonArrayBuilderInterest.add(id);
        }
        JsonArray build = jsonArrayBuilderInterest.build();
        return Response.ok(build.toString()).build();
    }

    @Override
    public Response getMembersCount(@NotNull @PathParam("id") Long club_id) throws ServiceException {
        Long nroMembers = membershipsService.getMembersCount(club_id);
        return Response.ok(nroMembers.toString()).build();
    }

    public Response cancel(@NotNull @FormParam("club_id") Long club_id,
            @NotNull @FormParam("user_id") Long user_id) throws ServiceException {
        membershipsService.cancelMembership(club_id, user_id);
        return Response.ok().build();
    }

}
