/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grogdj.grogshop.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salaboy
 */
public class Listing {
    private static Long LISTING_ID = 0L;

    private Long id;
    private List<Tag> tags;
    private String userId;
    private double price;
    
    

    public Listing() {
        this.id = LISTING_ID++;
    }

    public Listing(String userId, double price) {
        this.id = LISTING_ID++;
        this.userId = userId;
        this.price = price;
    }

    public Long getId() {
        return id;
    }
  
    
    public void addTag(String tag){
        if(this.tags == null){
            this.tags = new ArrayList<Tag>();
        }
        tags.add(new Tag(tag));
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getUserId() {
        return userId;
    }

    public double getPrice() {
        return price;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Listing{" + "id=" + id + ", tags=" + tags + ", userId=" + userId + ", price=" + price + '}';
    }

    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.tags != null ? this.tags.hashCode() : 0);
        hash = 59 * hash + (this.userId != null ? this.userId.hashCode() : 0);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
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
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        return true;
    }

   
    
}
