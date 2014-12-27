/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.ClubMembership;
import java.security.Principal;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.grogshop.services.api.ClubMembershipsService;
import org.grogshop.services.api.NotificationsService;
import org.grogshop.services.api.RulesService;

@Path("/secured/memberships")
public class ShopClubMembershipsServiceImpl {

    @Inject
    ClubMembershipsService membershipsService;

    @Inject
    NotificationsService notificationService;

    @Inject
    RulesService matchingService;

    @Inject
    Principal principal;

    @Inject
    HttpSession session;

    public ShopClubMembershipsServiceImpl() {

    }

    @POST
    @Path("/join")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Long joinClub(ClubMembership membership) {
        Long id = membershipsService.joinClub(principal.getName(), membership);
        matchingService.insert(membership);
        return id;
    }
    
    @POST
    @Path("/update")
    @Consumes({MediaType.APPLICATION_JSON})
    public void updateClubMembership(ClubMembership membership) {
        membershipsService.updateClubMembership(membership);
        matchingService.update(membership);
        
    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ClubMembership> getAllClubMemberships() {
        return membershipsService.getClubMemberships(principal.getName());
    }

    @GET
    @Path("/clear")
    public void clearClubMemberships() {
        membershipsService.clearClubMemberships();
    }

    @DELETE
    @Path("{id}")
    public void removeClubMembership(@PathParam("id") Long id) {
        ClubMembership removedMembership = membershipsService.removeClubMembership(id);
        matchingService.retract(removedMembership);
    }

    @GET
    @Path("{id}")
    public ClubMembership getClubMembership(@PathParam("id") Long id) {
        return membershipsService.getClubMembership(id);
    }

}
