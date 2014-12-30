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
public class Listing {

    public enum ListingStatus {

        ACTIVE, MATCHED, CLOSED
    };

    private static Long LISTING_ID = 0L;

    private Long id;
    private List<Tag> tags;
    private String userId;
    private Double[] priceRange;
    private ListingStatus status = ListingStatus.ACTIVE;

    public Listing() {
        this.id = LISTING_ID++;
    }

    public Listing(String userId, Double price) {
        this();
        this.userId = userId;
        this.priceRange = new Double[]{price, 0.0};
    }

    public Listing(String userId, Double price, Double range) {
        this(userId, price);
        this.priceRange = new Double[]{price, range};
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

    public ListingStatus getStatus() {
        return status;
    }

    public void setStatus(ListingStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 17 * hash + (this.tags != null ? this.tags.hashCode() : 0);
        hash = 17 * hash + (this.userId != null ? this.userId.hashCode() : 0);
        hash = 17 * hash + Arrays.deepHashCode(this.priceRange);
        hash = 17 * hash + (this.status != null ? this.status.hashCode() : 0);
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
        final Listing other = (Listing) obj;
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
        return "Listing{" + "id=" + id + ", tags=" + tags + ", userId=" + userId + ", priceRange=" + priceRange + ", status=" + status + '}';
    }

}
