/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grogdj.grogshop.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author grogdj
 */
public class Bid {

    public enum BidStatus {

        ACTIVE, MATCHED, REJECTED, CLOSED
    };

    private static Long BID_ID = 0L;

    private Long id;
    private List<Tag> tags;
    private String userId;
    private Double[] priceRange;
    private BidStatus status = BidStatus.ACTIVE;

    public Bid() {
        this.id = BID_ID++;
    }

    public Bid(String userId, double amount) {
        this();
        this.userId = userId;
        this.priceRange = new Double[]{amount, 0.0};
    }

    public Bid(String userId, double amount, double range) {
        this(userId, amount);
        this.priceRange = new Double[]{amount, range};
    }

    public void addTag(String tag) {
        if (this.tags == null) {
            this.tags = new ArrayList<Tag>();
        }
        tags.add(new Tag(tag));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double[] getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(Double[] priceRange) {
        this.priceRange = priceRange;
    }

    public BidStatus getStatus() {
        return status;
    }

    public void setStatus(BidStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 19 * hash + (this.tags != null ? this.tags.hashCode() : 0);
        hash = 19 * hash + (this.userId != null ? this.userId.hashCode() : 0);
        hash = 19 * hash + Arrays.deepHashCode(this.priceRange);
        hash = 19 * hash + (this.status != null ? this.status.hashCode() : 0);
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
        final Bid other = (Bid) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.tags != other.tags && (this.tags == null || !this.tags.equals(other.tags))) {
            return false;
        }
        if ((this.userId == null) ? (other.userId != null) : !this.userId.equals(other.userId)) {
            return false;
        }
        if (!Arrays.deepEquals(this.priceRange, other.priceRange)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bid{" + "id=" + id + ", tags=" + tags + ", userId=" + userId + ", priceRange=" + priceRange + ", status=" + status + '}';
    }

}
