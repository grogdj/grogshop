/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import com.grogdj.grogshop.core.model.Interest;
import com.grogdj.grogshop.core.model.Profile;
import java.util.List;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author salaboy
 */
public interface ProfilesService {

    boolean exist(Long user_id) throws ServiceException;
    
    Profile getById(Long user_id) throws ServiceException;

    void create(Long user_id) throws ServiceException;
    
    void update(Long user_id, String username, 
            String location, String bio) throws ServiceException;
    
    void setInterests(Long user_id, List<Interest> interests) throws ServiceException;
    
    List<Interest> getInterests(Long user_id) throws ServiceException;
    
    void updateAvatar(Long user_id, String fileName, byte[] content) throws ServiceException;
    
    byte[] getAvatar(Long user_id) throws ServiceException;
    
    void removeAvatar(Long user_id) throws ServiceException;
}
