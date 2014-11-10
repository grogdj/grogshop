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

import com.grogdj.grogshop.core.model.Listing;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.grogshop.services.api.ListingsService;
import org.grogshop.services.api.NotificationsService;

/**
 * A simple REST service which is able to say hello to someone using
 * HelloService Please take a look at the web.xml where JAX-RS is enabled
 *
 * @author gbrey@redhat.com
 *
 */
@Path("/listings")
public class ShopListingsServiceImpl {

    @Inject
    ListingsService listingsService;

    @Inject
    MatchingServiceImpl matchingService;

    @Inject
    NotificationsService notificationService;

    @GET
    @Path("/all")
    @Produces({"application/json"})
    public List<Listing> getAllListings() {
        return listingsService.getAllListings();
    }

    @POST
    @Path("/new")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Response newListing(Listing listing) {
        listingsService.newListing(listing);
        matchingService.insert(listing);
        return Response.ok().build();
    }

    @GET
    @Path("/clear")
    public void clearListings() {
        listingsService.clearListings();
    }

    @GET
    @Path("/reset")
    public void resetMatchingService() {
        matchingService.reset();
    }

}
