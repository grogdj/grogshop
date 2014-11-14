/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Listing;
import com.grogdj.grogshop.core.model.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.grogshop.services.api.ListingsService;
import org.grogshop.services.api.TagsService;

/**
 *
 * @author salaboy
 */
@Singleton
public class ListingsServiceImpl implements ListingsService {

    @Inject
    private TagsService tagsService;

    private List<Listing> listings;

    public ListingsServiceImpl() {
        this.listings = new ArrayList<Listing>();
    }

    @Override
    public List<Listing> getAllListings() {
        return listings;
    }

    @Override
    public Long newListing(Listing listing) {
        if (listing != null) {
            for (Tag t : listing.getTags()) {
                tagsService.newTag(t);
            }
            this.listings.add(listing);
            return listing.getId();
        }else{
            System.out.println(">  Listing cannot be null :( ");
        }
        return -1l;
    }

    @Override
    public void clearListings() {
        this.listings.clear();
    }

    @Override
    public void removeListing(Long listingId) {
        for (Listing l : this.listings) {
            if (l.getId().equals(listingId)) {
                this.listings.remove(l);
            }
        }
    }

    @Override
    public Listing getListing(Long listingId) {
        for (Listing l : this.listings) {
            if (l.getId().equals(listingId)) {
                return l;
            }
        }
        return null;
    }
    
    

}
