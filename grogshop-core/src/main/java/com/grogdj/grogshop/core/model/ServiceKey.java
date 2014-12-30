/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grogdj.grogshop.core.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author grogdj
 */
@Entity
public class ServiceKey implements Serializable {
    @Id
    private String key;
    private String email;

    public ServiceKey() {
    }

    public ServiceKey(String key, String email) {
        this.key = key;
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (this.key != null ? this.key.hashCode() : 0);
        hash = 61 * hash + (this.email != null ? this.email.hashCode() : 0);
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
        final ServiceKey other = (ServiceKey) obj;
        if ((this.key == null) ? (other.key != null) : !this.key.equals(other.key)) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ServiceKey{" + "key=" + key + ", email=" + email + '}';
    }
    
    
}
