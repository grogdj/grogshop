/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.grogshop.processing.util;

import com.grogdj.grogshop.core.model.Bid;
import com.grogdj.grogshop.core.model.Listing;
import com.mycompany.grogshop.processing.Embedded;

/**
 *
 * @author grogdj
 */
public class NotificationUtils {

    private static Embedded embed;

    public static void notifyClubLowOffer(Listing l, Bid b) {
        System.out.println(">> " + l.getUserId() + " there is a new LOW bid (for: " + b.getAmount() + ") matching with your listing");

    }

    public static void notifyBidderHighPrice(Bid b, Listing l) {
        System.out.println(">> " + b.getUserId() + " there is a new HIGH listing (price: " + l.getPrice() + ") matching with your bid");

    }

    public static void notifyClubHighOffer(Listing l, Bid b) {
        System.out.println(">> " + l.getUserId() + " there is a new HIGH bid (for: " + b.getAmount() + ") matching with your listing");

    }

    public static void notifyBidderLowPrice(Bid b, Listing l) {
        System.out.println(">> " + b.getUserId() + " there is a new LOW listing (price: " + l.getPrice() + ") matching with your bid");

    }

    public static void drawLowBid(Bid b) {
        
        embed.translate(100, 100);
       
        embed.addNewBid(Double.valueOf(b.getAmount()).intValue());
       
    }

    public static void drawHighBid(Bid b) {
    
        embed.translate(-100, -100);
        embed.addNewBid(Double.valueOf(b.getAmount()).intValue());
        
    }

    public static void drawListing(Listing l) {
        
        
        embed.setBoxSize(Double.valueOf(l.getPrice()).intValue());
       
    }

    public static void setProcessingRuntime(Embedded embed) {
        NotificationUtils.embed = embed;
    }
}
