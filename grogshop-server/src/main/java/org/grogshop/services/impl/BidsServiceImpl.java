/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Bid;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import org.grogshop.services.api.BidsService;

/**
 *
 * @author salaboy
 */
@Singleton
public class BidsServiceImpl implements BidsService {
     private List<Bid> bids;

    public BidsServiceImpl() {
        bids = new ArrayList<Bid>();
        System.out.println("BidsServiceImpl "+this.hashCode());
    }
     
     

    @Override
    public List<Bid> getBids() {
        return bids;
    }

       
    @Override
    public void newBid(Bid bid){
        this.bids.add(bid);
    }
        
        
}
