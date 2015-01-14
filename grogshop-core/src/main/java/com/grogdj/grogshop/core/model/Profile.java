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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    private String introduction;

    private String postcode;

    private String avatarFileName;

    @Lob
    @Column(name = "CONTENT")
    private byte[] avatarContent;

    private String realname;

    @ElementCollection
    private List<String> interests;

    public Profile(User user) {
        this.user = user;
    }

    public Profile() {
    }

    public Profile(User user, String introduction, String postcode, String realname) {
        this(user);
        this.introduction = introduction;
        this.postcode = postcode;
        this.realname = realname;
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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public void addInterest(String interest) {
        if (this.interests != null) {
            this.interests.add(interest);
        }
    }

    public String getAvatarFileName() {
        return avatarFileName;
    }

    public void setAvatarFileName(String avatarFileName) {
        this.avatarFileName = avatarFileName;
    }

    public byte[] getAvatarContent() {
        return avatarContent;
    }

    public void setAvatarContent(byte[] avatarContent) {
        this.avatarContent = avatarContent;
    }

   

}
