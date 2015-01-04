/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grogdj.grogshop.core.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author grogdj
 */
@Entity(name = "Profile")
@Table(name = "PROFILE")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_USER"))
    private Long user_id;

    private String introduction;

    private String postcode;

    private String avatar;

    private String realname;

    public Profile(Long user_id) {
        this.user_id = user_id;
    }

    public Profile() {
    }

    public Profile(Long user_id, String introduction, String postcode, String realname) {
        this(user_id);
        this.introduction = introduction;
        this.postcode = postcode;
        this.realname = realname;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

}
