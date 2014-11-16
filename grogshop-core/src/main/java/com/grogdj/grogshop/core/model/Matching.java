/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grogdj.grogshop.core.model;

import java.util.Date;

/**
 *
 * @author salaboy
 */
public class Matching {

    private static Long matchingKeyGenerator = 0L;
    private Long id;
    private Long bidId;
    private Long listingId;
    private String listingUserId;
    private String bidUserId;
    private String type;
    private Date occurrence;

    public Matching() {
        this.id = this.matchingKeyGenerator++;
    }

    public Matching(Long bidId, Long listingId, String bidUserId, String listingUserId, String type) {
        this.id = this.matchingKeyGenerator++;
        this.bidId = bidId;
        this.listingId = listingId;
        this.listingUserId = listingUserId;
        this.bidUserId = bidUserId;
        this.type = type;
        this.occurrence = new Date();
    }

    public Long getId() {
        return id;
    }

    public Long getBidId() {
        return bidId;
    }

    public Long getListingId() {
        return listingId;
    }

    public String getListingUserId() {
        return listingUserId;
    }

    public String getBidUserId() {
        return bidUserId;
    }

    public String getType() {
        return type;
    }

    public Date getOccurrence() {
        return occurrence;
    }

}
