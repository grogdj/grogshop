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
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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

    private String avatarFileName;
    
    @Lob
    @Column( name = "CONTENT" )
    private byte[] avatarContent;

    private String realname;

    @ElementCollection
    private List<String> interests;

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
    
    public void addInterest(String interest){
        if(this.interests != null){
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

    @Override
    public String toString() {
        return "Profile{" + "user_id=" + user_id + ", introduction=" + introduction + ", postcode=" + postcode + ", avatarFileName=" + avatarFileName + ", realname=" + realname + ", interests=" + interests + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (this.user_id != null ? this.user_id.hashCode() : 0);
        hash = 31 * hash + (this.introduction != null ? this.introduction.hashCode() : 0);
        hash = 31 * hash + (this.postcode != null ? this.postcode.hashCode() : 0);
        hash = 31 * hash + (this.avatarFileName != null ? this.avatarFileName.hashCode() : 0);
        hash = 31 * hash + (this.realname != null ? this.realname.hashCode() : 0);
        hash = 31 * hash + (this.interests != null ? this.interests.hashCode() : 0);
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
        if (this.user_id != other.user_id && (this.user_id == null || !this.user_id.equals(other.user_id))) {
            return false;
        }
        if ((this.introduction == null) ? (other.introduction != null) : !this.introduction.equals(other.introduction)) {
            return false;
        }
        if ((this.postcode == null) ? (other.postcode != null) : !this.postcode.equals(other.postcode)) {
            return false;
        }
        if ((this.avatarFileName == null) ? (other.avatarFileName != null) : !this.avatarFileName.equals(other.avatarFileName)) {
            return false;
        }
        if ((this.realname == null) ? (other.realname != null) : !this.realname.equals(other.realname)) {
            return false;
        }
        if ((this.interests == null) ? (other.interests != null) : !this.interests.equals(other.interests)) {
            return false;
        }
        return true;
    }

   

}
