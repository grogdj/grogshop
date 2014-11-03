/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Listing;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import org.grogshop.services.api.ListingsService;

/**
 *
 * @author salaboy
 */
@Singleton
public class ListingsServiceImpl implements ListingsService{
    
    private List<Listing> listings;

    public ListingsServiceImpl() {
         //TODO: use listingsService here
        listings = new ArrayList<Listing>();
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
    }

    
    
    @Override
    public List<Listing> getAllListings() {
        return listings;
    }
    
    @Override
    public void newListing(Listing listing){
        this.listings.add(listing);
    }

    @Override
    public void clearListings() {
        this.listings.clear();
    }
    
}
