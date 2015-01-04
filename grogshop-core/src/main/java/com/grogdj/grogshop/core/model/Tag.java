/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grogdj.grogshop.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;



/**
 *
 * @author grogdj
 */

@Entity(name = "Tag")
@Table(name = "TAG")
public class Tag {
    @Id
    @SequenceGenerator(name = "persons_seq", sequenceName = "persons_seq", initialValue = 4)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "persons_seq")
    private Long id;
    
    @Size(min = 3, max = 20, message = "A tag  must contain between 3 and 20 characters")
    @NotNull
    @NotEmpty
    private String name;
    
    private String imageURL;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Tag{" + "id=" + id + ", name=" + name + ", imageURL=" + imageURL + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 31 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 31 * hash + (this.imageURL != null ? this.imageURL.hashCode() : 0);
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
        final Tag other = (Tag) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.imageURL == null) ? (other.imageURL != null) : !this.imageURL.equals(other.imageURL)) {
            return false;
        }
        return true;
    }
    

}
