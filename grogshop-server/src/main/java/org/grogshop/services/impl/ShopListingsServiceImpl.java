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
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.grogshop.services.api.ListingsService;

/**
 * A simple REST service which is able to say hello to someone using HelloService Please take a look at the web.xml where JAX-RS
 * is enabled
 * 
 * @author gbrey@redhat.com
 * 
 */

@Path("/listings")
public class ShopListingsServiceImpl {
    @Inject
    ListingsService listingsService;

    @GET
    @Path("/all")
    @Produces({ "application/json" })
    public List<Listing> getAllListings() {
        
        //TODO: use listingsService here
        List<Listing> listings = new ArrayList<Listing>();
        Listing listing1 = new Listing("xxx", 200000);
        listing1.addTag("#car");
        listing1.addTag("#2014");
        listing1.addTag("#ferrari");
        listing1.addTag("#blue");
        listings.add(listing1);
        
        Listing listing2 = new Listing("yyy", 4000);
        listing2.addTag("#car");
        listing2.addTag("#2000");
        listing2.addTag("#ford");
        listing2.addTag("#red");
        listings.add(listing2);
        
        Listing listing3 = new Listing("zzz", 4500);
        listing3.addTag("#car");
        listing3.addTag("#2003");
        listing3.addTag("#ford");
        listing3.addTag("#blue");
        listings.add(listing3);
        
        
        return listings;
    }


}
