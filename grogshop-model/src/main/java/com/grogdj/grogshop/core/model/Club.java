/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grogdj.grogshop.core.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author grogdj
 */
@Entity(name = "Club")
@Table(name = "CLUBS")
@Indexed
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull
    @NotEmpty
    @Field
    private String name;

    @NotNull
    @NotEmpty
    @Field
    private String description;

    @NotNull
    @NotEmpty
    @ElementCollection
    private List<String> tags;

    @ManyToOne
    @JoinColumn(name = "interest_id")
    private Interest interest;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "founder_id")
    private User founder;

    @NotNull
    @NotEmpty
    private String image;

    @Field
    private Double longitude = 0.0;

    @Field
    private Double latitude = 0.0;

    public Club() {
    }

    public Club(String name, String description, Interest interest, List<String> tags, User founder, String image) {
        this.name = name;
        this.description = description;
        this.interest = interest;
        this.tags = tags;
        this.founder = founder;
        this.image = image;
    }

    public Club(String name, String description, Interest interest, List<String> tags, User founder, String image, Double longitude, Double latitude) {
        this(name, description, interest, tags, founder, image);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getFounder() {
        return founder;
    }

    public void setFounder(User founder) {
        this.founder = founder;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 23 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 23 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 23 * hash + (this.tags != null ? this.tags.hashCode() : 0);
        hash = 23 * hash + (this.interest != null ? this.interest.hashCode() : 0);
        hash = 23 * hash + (this.founder != null ? this.founder.hashCode() : 0);
        hash = 23 * hash + (this.image != null ? this.image.hashCode() : 0);
        hash = 23 * hash + (this.latitude != null ? this.latitude.hashCode() : 0);
        hash = 23 * hash + (this.longitude != null ? this.longitude.hashCode() : 0);
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
        final Club other = (Club) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if (this.tags != other.tags && (this.tags == null || !this.tags.equals(other.tags))) {
            return false;
        }
        if (this.interest != other.interest && (this.interest == null || !this.interest.equals(other.interest))) {
            return false;
        }
        if (this.founder != other.founder && (this.founder == null || !this.founder.equals(other.founder))) {
            return false;
        }
        if ((this.image == null) ? (other.image != null) : !this.image.equals(other.image)) {
            return false;
        }
        if (this.latitude != other.latitude && (this.latitude == null || !this.latitude.equals(other.latitude))) {
            return false;
        }
        if (this.longitude != other.longitude && (this.longitude == null || !this.longitude.equals(other.longitude))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Club{" + "id=" + id + ", name=" + name + ", description=" + description + ", tags=" + tags + ", interest=" + interest
                + ", founder=" + founder + ", image=" + image + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }

}
