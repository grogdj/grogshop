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
 * @author salaboy
 */
public class ClubMembership {

    private static Long CLUB_ID = 0L;

    private Long id;
    private String userId;
    private List<Tag> tags;

    // params
    private Double[] priceRange = new Double[]{0d, 10000000d};
    private boolean local = false;
    private String location = "";
    private String distance = "";

    public ClubMembership() {
        this.id = CLUB_ID++;
    }

    public ClubMembership(String userId, List<Tag> tags) {
        this();
        this.userId = userId;
        this.tags = tags;

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

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final ClubMembership other = (ClubMembership) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

  

    @Override
    public String toString() {
        return "ClubMembership{" + "id=" + id + ", userId=" + userId + ", tags=" + tags + ", priceRange=" + priceRange + ", local=" + local + ", location=" + location + ", distance=" + distance + '}';
    }

    

}
