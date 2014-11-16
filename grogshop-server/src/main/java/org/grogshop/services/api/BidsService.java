/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import com.grogdj.grogshop.core.model.Bid;
import java.util.List;

/**
 *
 * @author salaboy
 */
public interface BidsService {
    
    Bid getBid(Long bidId);

    List<Bid> getBids();

    Long newBid(Bid bid);
    
    void clearBids();
    
    Bid removeBid(Long bidId);
    
}
