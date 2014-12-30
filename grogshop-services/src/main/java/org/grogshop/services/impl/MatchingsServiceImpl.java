/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Matching;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import org.grogshop.services.api.MatchingsService;

/**
 *
 * @author grogdj
 */
@Singleton
public class MatchingsServiceImpl implements MatchingsService {

    private List<Matching> matchings;

    public MatchingsServiceImpl() {
        this.matchings = new ArrayList<Matching>();
    }

    public Matching getMatching(Long matchingId) {
        for (Matching m : this.matchings) {
            if (m.getId().equals(matchingId)) {
                return m;
            }
        }
        return null;
    }

    public List<Matching> getAllMatchings() {
        return this.matchings;
    }

    public Long newMatching(Matching matching) {
        if (matching != null) {

            this.matchings.add(matching);
            return matching.getId();
        } else {
            System.out.println(">  Matching cannot be null :( ");
        }
        return -1l;
    }

    public void clearMatchings() {
        this.matchings.clear();
    }

    public Matching removeMatching(Long matchingId) {
        Matching remove = null;
        for (Matching m : this.matchings) {
            if (m.getId().equals(matchingId)) {
                remove = m;
            }
        }
        if (remove != null) {
            this.matchings.remove(remove);
            return remove;
        }
        return null;
    }

    public List<Matching> getAllMatchingsByListing(Long listingId) {
        List<Matching> matchingsByListing = new ArrayList<Matching>();
        for(Matching m : this.matchings){
            if(m.getListing().getId().equals(listingId)){
                matchingsByListing.add(m);
            }
        }
        return matchingsByListing;
        
    }

    public List<Matching> getAllMatchingsByMembership(Long matchingId) {
        List<Matching> matchingsByMembership = new ArrayList<Matching>();
        for(Matching m : this.matchings){
            if(m.getMembership().getId().equals(matchingId)){
                matchingsByMembership.add(m);
            }
        }
        return matchingsByMembership;
    }

}
