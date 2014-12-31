/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import com.grogdj.grogshop.core.model.User;

/**
 *
 * @author salaboy
 */
public interface UserService {

    public String registerUser(User user);

    public boolean existKey(String serviceKey);

    public String getKey(String serviceKey);

    public boolean exist(String email);

    public User getByEmail(String email);
    
}
