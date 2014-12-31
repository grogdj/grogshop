/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grogdj.grogshop.core.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author grogdj
 */
@Entity(name = "Profile")
@Table(name = "PROFILE")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String introduction;

    private String postcode;
    
    private String avatar;
    
    private String realname;
    
    @NotNull
    private String email;
    

    public Profile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.introduction != null ? this.introduction.hashCode() : 0);
        hash = 59 * hash + (this.postcode != null ? this.postcode.hashCode() : 0);
        hash = 59 * hash + (this.avatar != null ? this.avatar.hashCode() : 0);
        hash = 59 * hash + (this.realname != null ? this.realname.hashCode() : 0);
        hash = 59 * hash + (this.email != null ? this.email.hashCode() : 0);
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
        final Profile other = (Profile) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.introduction == null) ? (other.introduction != null) : !this.introduction.equals(other.introduction)) {
            return false;
        }
        if ((this.postcode == null) ? (other.postcode != null) : !this.postcode.equals(other.postcode)) {
            return false;
        }
        if ((this.avatar == null) ? (other.avatar != null) : !this.avatar.equals(other.avatar)) {
            return false;
        }
        if ((this.realname == null) ? (other.realname != null) : !this.realname.equals(other.realname)) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Profile{" + "id=" + id + ", introduction=" + introduction + ", postcode=" + postcode + ", avatar=" + avatar + ", realname=" + realname + ", email=" + email + '}';
    }

    
    

}
