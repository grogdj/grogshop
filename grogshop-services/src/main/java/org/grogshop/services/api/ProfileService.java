/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import com.grogdj.grogshop.core.model.Profile;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author salaboy
 */
public interface ProfileService {

    boolean exist(Long user_id) throws ServiceException;
    
    Profile getById(Long user_id) throws ServiceException;

    void create(Long user_id) throws ServiceException;
    
    void update(Long user_id, String username, 
            String location, String bio) throws ServiceException;
}
