/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grogdj.grogshop.core.model;

import java.util.Date;

/**
 *
 * @author grogdj
 */
public class Matching {

    public enum MatchingStatus {

        ACTIVE, CLOSED, REJECTED
    };

    private static Long matchingKeyGenerator = 0L;
    private Long id;
    private ClubMembership membership;
    private Listing listing;
    private String type;
    private Date occurrence;
    private MatchingStatus status = MatchingStatus.ACTIVE;

    public Matching() {
        this.id = this.matchingKeyGenerator++;
    }

    public Matching(Listing listing, ClubMembership membership, String type) {
        this.id = this.matchingKeyGenerator++;
        this.membership = membership;
        this.listing = listing;
        this.type = type;
        this.occurrence = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClubMembership getMembership() {
        return membership;
    }

    public void setMembership(ClubMembership membership) {
        this.membership = membership;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Date occurrence) {
        this.occurrence = occurrence;
    }

    public MatchingStatus getStatus() {
        return status;
    }

    public void setStatus(MatchingStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 29 * hash + (this.membership != null ? this.membership.hashCode() : 0);
        hash = 29 * hash + (this.listing != null ? this.listing.hashCode() : 0);
        hash = 29 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 29 * hash + (this.occurrence != null ? this.occurrence.hashCode() : 0);
        hash = 29 * hash + (this.status != null ? this.status.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Matching other = (Matching) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.membership != other.membership && (this.membership == null || !this.membership.equals(other.membership))) {
            return false;
        }
        if (this.listing != other.listing && (this.listing == null || !this.listing.equals(other.listing))) {
            return false;
        }
        if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type)) {
            return false;
        }
        if (this.occurrence != other.occurrence && (this.occurrence == null || !this.occurrence.equals(other.occurrence))) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Matching{" + "id=" + id + ", bid=" + membership + ", listing=" + listing + ", type=" + type + ", occurrence=" + occurrence + ", status=" + status + '}';
    }

}
