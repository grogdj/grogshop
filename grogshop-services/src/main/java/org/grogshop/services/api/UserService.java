/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import com.grogdj.grogshop.core.model.User;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author salaboy
 */
public interface UserService {

    public Long newUser(User user) throws ServiceException;

    public boolean existKey(String serviceKey);

    public String getKey(String serviceKey);

    public boolean exist(String email) ;

    public User getByEmail(String email);
    
}
