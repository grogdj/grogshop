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
public class Bid {
    private List<Tag> tags;
    private String userId;
    private double amount;

    public Bid() {
    }

    
    
    public Bid(String userId, double amount) {
        this.userId = userId;
        this.amount = amount;
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

    public double getAmount() {
        return amount;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    

    @Override
    public String toString() {
        return "Bid{" + "tags=" + tags + ", userId=" + userId + ", amount=" + amount + '}';
    }
    
    
    
    
    
}
