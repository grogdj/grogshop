/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grogdj.grogshop.core;

import com.grogdj.grogshop.core.model.Bid;
import com.grogdj.grogshop.core.model.Listing;
import javax.inject.Inject;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;

/**
 *
 * @author salaboy
 */
public class ListingRuntime {
    @Inject
    @KSession("ksession1")
    KieSession kSession;

    public void addNewListing(Listing listing) {
        kSession.insert(listing);
        kSession.fireAllRules();
    }
    
    public void newBid(Bid bid){
        kSession.insert(bid);
        kSession.fireAllRules();
    
    }

    public static void main(String[] args) {
        Weld w = new Weld();

        WeldContainer wc = w.initialize();
        ListingRuntime bean = wc.instance().select(ListingRuntime.class).get();
        Listing listing = new Listing("salaboy", 4000);
        listing.addTag("Car");
        listing.addTag("PT Cruiser");
        listing.addTag("Blue");
        
        bean.addNewListing(listing);
        
        
        Bid bid = new Bid("xxx", 3000);
        bid.addTag("Car");
        bid.addTag("PT Cruiser");
        bid.addTag("Blue");
        
        bean.newBid(bid);
        
        Bid bid2 = new Bid("yyy", 4500);
        bid2.addTag("Car");
        bid2.addTag("PT Cruiser");
        bid2.addTag("Blue");
        
        bean.newBid(bid2);
        
        
        w.shutdown();
    }

}
