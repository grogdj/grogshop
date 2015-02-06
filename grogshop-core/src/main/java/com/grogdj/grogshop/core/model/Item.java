/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grogdj.grogshop.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Entity(name = "Item")
@Table(name = "ITEMS")
@Indexed
public class Item implements Serializable {

    public enum ItemType {

        POST,
        REQUEST,
        TOPIC
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(unique = true)
    @NotNull
    @NotEmpty
    @Field
    private String name;

    private Date since;

    @NotNull
    @NotEmpty
    @Field
    private String description;

    @NotNull
    @NotEmpty
    @ElementCollection
    @Field
    private List<String> tags;

    private String imageFileName;

    @Lob
    @Column(name = "CONTENT")
    private byte[] imageContent;

    @NotNull
    @Field
    private BigDecimal minPrice;

    @NotNull
    @Field
    private BigDecimal maxPrice;

    @Enumerated(EnumType.STRING)
    @Field
    private ItemType type;

    public Item() {
    }

    public Item(User user, Club club, String type, String name,
            String description, List<String> tags, BigDecimal minPrice, BigDecimal maxPrice) {

        this.user = user;
        this.club = club;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.type = ItemType.valueOf(type);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.since = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
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

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public byte[] getImageContent() {
        return imageContent;
    }

    public void setImageContent(byte[] imageContent) {
        this.imageContent = imageContent;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public boolean hasImage() {
        return (imageContent != null && imageFileName != null 
                && !imageFileName.equals(""));
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", user=" + user + ", clubId=" + club.getId() + ", name=" + name + ", since=" + since + ", description=" + description + ", tags=" + tags + ", imageFileName=" + imageFileName + ", imageContent=" + imageContent + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice + ", type=" + type + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 97 * hash + (this.user != null ? this.user.hashCode() : 0);
        hash = 97 * hash + (this.club != null ? this.club.hashCode() : 0);
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.since != null ? this.since.hashCode() : 0);
        hash = 97 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 97 * hash + (this.tags != null ? this.tags.hashCode() : 0);
        hash = 97 * hash + (this.imageFileName != null ? this.imageFileName.hashCode() : 0);
        hash = 97 * hash + Arrays.hashCode(this.imageContent);
        hash = 97 * hash + (this.minPrice != null ? this.minPrice.hashCode() : 0);
        hash = 97 * hash + (this.maxPrice != null ? this.maxPrice.hashCode() : 0);
        hash = 97 * hash + (this.type != null ? this.type.hashCode() : 0);
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
        final Item other = (Item) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.user != other.user && (this.user == null || !this.user.equals(other.user))) {
            return false;
        }
        if (this.club != other.club && (this.club == null || !this.club.equals(other.club))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.since != other.since && (this.since == null || !this.since.equals(other.since))) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if (this.tags != other.tags && (this.tags == null || !this.tags.equals(other.tags))) {
            return false;
        }
        if ((this.imageFileName == null) ? (other.imageFileName != null) : !this.imageFileName.equals(other.imageFileName)) {
            return false;
        }
        if (!Arrays.equals(this.imageContent, other.imageContent)) {
            return false;
        }
        if (this.minPrice != other.minPrice && (this.minPrice == null || !this.minPrice.equals(other.minPrice))) {
            return false;
        }
        if (this.maxPrice != other.maxPrice && (this.maxPrice == null || !this.maxPrice.equals(other.maxPrice))) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    
}
