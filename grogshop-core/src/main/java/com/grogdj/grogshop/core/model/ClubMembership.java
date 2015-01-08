/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grogdj.grogshop.core.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author grogdj
 */
@Entity(name = "ClubMembership")
@Table(name = "MEMBERSHIPS")
public class ClubMembership implements Serializable{

    @Id
    private Long userId;
    @Id
    private Long clubId;

    private Date since;
    // filters
    private Double[] priceRange = new Double[]{0d, 10000000d};
    private boolean local = false;
    private String location = "";
    private String distance = "";

    public ClubMembership() {

    }

    public ClubMembership(Long clubId, Long userId) {
        this.clubId = clubId;
        this.userId = userId;
        this.since = new Date();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
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
    
    
    
    

}
