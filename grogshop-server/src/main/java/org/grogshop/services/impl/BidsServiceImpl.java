/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Bid;
import com.grogdj.grogshop.core.model.Tag;
import java.util.ArrayList;
import java.util.List;
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
    
    private List<Bid> bids;

    public BidsServiceImpl() {
        bids = new ArrayList<Bid>();
    }
     
     

    @Override
    public List<Bid> getBids() {
        return bids;
    }

       
    @Override
    public void newBid(Bid bid){
        if(bid != null){
            for(Tag t : bid.getTags()){
                tagsService.addTag(t);
            }
            this.bids.add(bid);
        }else{
            System.out.println("> Bid cannot be null :( ");
        }
    }
    
    @Override
    public void clearBids() {
        this.bids.clear();
    }
        
        
}
