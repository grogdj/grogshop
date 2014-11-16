/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Bid;
import com.grogdj.grogshop.core.model.Tag;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.grogshop.services.api.BidsService;
import org.grogshop.services.api.TagsService;

/**
 *
 * @author salaboy
 */
@Singleton
public class BidsServiceImpl implements BidsService {

    @Inject
    private TagsService tagsService;

    private Set<Bid> bids;

    public BidsServiceImpl() {
        bids = new HashSet<Bid>();
    }

    @Override
    public List<Bid> getBids() {
        return new ArrayList<Bid>(bids);
    }

    @Override
    public Long newBid(Bid bid) {
        if (bid != null) {
            for (Tag t : bid.getTags()) {
                tagsService.newTag(t);
            }
            this.bids.add(bid);
            return bid.getId();
        } else {
            System.out.println("> Bid cannot be null :( ");
        }
        return -1l;
    }

    @Override
    public void clearBids() {
        this.bids.clear();
    }

    @Override
    public Bid removeBid(Long bidId) {
        Bid remove = null;
        for (Bid b : this.bids) {
            if (b.getId().equals(bidId)) {
                remove = b;
            }
        }
        if(remove != null){
            this.bids.remove(remove);
            return remove;
        }
        return null;
    }

    @Override
    public Bid getBid(Long bidId) {
        for (Bid b : this.bids) {
            if (b.getId().equals(bidId)) {
                return b;
            }
        }
        return null;
    }

}
