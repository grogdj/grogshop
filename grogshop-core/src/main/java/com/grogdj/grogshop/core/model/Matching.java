/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grogdj.grogshop.core.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.search.annotations.Indexed;

/**
 *
 * @author grogdj
 */
@Entity(name = "Matching")
@Table(name = "MATCHINGS")
@Indexed
public class Matching implements Serializable {

    public enum MatchingType {
        FULL
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "item_matched_id")
    private Item itemMatched;

    private Date matchedSince;

    public Matching() {
    }

    public Matching(Club club, Item item, Item itemMatched) {
        this.club = club;
        this.item = item;
        this.itemMatched = itemMatched;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItemMatched() {
        return itemMatched;
    }

    public void setItemMatched(Item itemMatched) {
        this.itemMatched = itemMatched;
    }

    public Date getMatchedSince() {
        return matchedSince;
    }

    public void setMatchedSince(Date matchedSince) {
        this.matchedSince = matchedSince;
    }

    @Override
    public String toString() {
        return "Matching{" + "id=" + id + ", club=" + club + ", item=" + item + ", itemMatched=" + itemMatched + ", matchedSince=" + matchedSince + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 53 * hash + (this.club != null ? this.club.hashCode() : 0);
        hash = 53 * hash + (this.item != null ? this.item.hashCode() : 0);
        hash = 53 * hash + (this.itemMatched != null ? this.itemMatched.hashCode() : 0);
        hash = 53 * hash + (this.matchedSince != null ? this.matchedSince.hashCode() : 0);
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
        if (this.club != other.club && (this.club == null || !this.club.equals(other.club))) {
            return false;
        }
        if (this.item != other.item && (this.item == null || !this.item.equals(other.item))) {
            return false;
        }
        if (this.itemMatched != other.itemMatched && (this.itemMatched == null || !this.itemMatched.equals(other.itemMatched))) {
            return false;
        }
        if (this.matchedSince != other.matchedSince && (this.matchedSince == null || !this.matchedSince.equals(other.matchedSince))) {
            return false;
        }
        return true;
    }
    
    

}
