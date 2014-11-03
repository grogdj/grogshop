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
    private List<Tag> tags;
    private String userId;
    private double price;
    
    

    public Listing() {
    }

    public Listing(String userId, double price) {
        this.userId = userId;
        this.price = price;
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
        return "Listing{" + "tags=" + tags + ", userId=" + userId + ", price=" + price + '}';
    }
    
    
    
    
    
    
    
}
