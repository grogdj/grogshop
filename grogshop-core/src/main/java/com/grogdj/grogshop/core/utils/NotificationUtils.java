/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grogdj.grogshop.core.utils;

import com.grogdj.grogshop.core.model.Bid;
import com.grogdj.grogshop.core.model.Listing;

/**
 *
 * @author salaboy
 */
public class NotificationUtils {
    public static void notifyClubLowOffer(Listing l, Bid b){
        System.out.println(">> " + l.getUserId() + " there is a new LOW bid (for: "+b.getAmount()+") matching with your listing");
    }
    
    public static void notifyBidderHighPrice(Bid b, Listing l){
        System.out.println(">> " + b.getUserId() + " there is a new HIGH listing (price: "+l.getPrice()+") matching with your bid");
    }
    
    public static void notifyClubHighOffer(Listing l, Bid b){
        System.out.println(">> " + l.getUserId() + " there is a new HIGH bid (for: "+b.getAmount()+") matching with your listing");
    }
    
    public static void notifyBidderLowPrice(Bid b, Listing l){
        System.out.println(">> " + b.getUserId() + " there is a new LOW listing (price: "+l.getPrice()+") matching with your bid");
    }
}
