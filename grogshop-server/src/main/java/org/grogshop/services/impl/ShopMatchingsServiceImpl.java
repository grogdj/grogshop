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

import com.grogdj.grogshop.core.model.Matching;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.grogshop.services.api.MatchingsService;

@Path("/matchings")
public class ShopMatchingsServiceImpl {

    @Inject
    MatchingsService matchingsService;

    public ShopMatchingsServiceImpl() {

    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Matching> getAllMatchings() {
        return matchingsService.getAllMatchings();
    }
    
    @GET
    @Path("/bylisting/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Matching> getMatchingsByListing(@PathParam("id") Long listingId) {
        return matchingsService.getAllMatchingsByListing(listingId);
    }
    
    @GET
    @Path("/bybid/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Matching> getMatchingsByBid(@PathParam("id") Long bidId) {
        return matchingsService.getAllMatchingsByBid(bidId);
    }

    @GET
    @Path("/clear")
    public void clearMatchings() {
        matchingsService.clearMatchings();
    }

    @DELETE
    @Path("{id}")
    public void removeMatching(@PathParam("id") Long id) {
        Matching removedMatching = matchingsService.removeMatching(id);
    }

    @GET
    @Path("{id}")
    public Matching getBid(@PathParam("id") Long id) {
        return matchingsService.getMatching(id);
    }

}
