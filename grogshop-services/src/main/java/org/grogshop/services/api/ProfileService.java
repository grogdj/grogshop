/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import com.grogdj.grogshop.core.model.Profile;

/**
 *
 * @author salaboy
 */
public interface ProfileService {
    boolean exist(String email);
    
    Profile getByEmail(String email);
    
    String newProfile(Profile profile);
}
