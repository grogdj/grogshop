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
    
    
    
}
