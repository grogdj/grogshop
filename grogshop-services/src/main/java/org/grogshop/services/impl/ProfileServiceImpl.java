/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Profile;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.ProfileService;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author salaboy
 */
@Singleton
public class ProfileServiceImpl implements ProfileService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    private final static Logger log = Logger.getLogger(ProfileServiceImpl.class.getName());

    @Override
    public boolean exist(Long user_id) throws ServiceException {
        return (em.find(Profile.class, user_id) != null);
    }

    @Override
    public Profile getById(Long user_id) throws ServiceException {
        return em.find(Profile.class, user_id);
    }

    @Override
    public void create(Long user_id) throws ServiceException {
        em.persist(new Profile(user_id));
    }
    
    

    @Override
    public void update(Long user_id, String username, String location, String bio) throws ServiceException {
        Profile find = em.find(Profile.class, user_id);
        if (find == null) {
            throw new ServiceException("User Profile doesn't exist: " + user_id);
        }
        find.setRealname(username);
        find.setIntroduction(bio);
        find.setPostcode(location);

    }

    @Override
    public void setInterests(Long user_id, String interests) throws ServiceException {
        Profile find = em.find(Profile.class, user_id);
        if (find == null) {
            throw new ServiceException("User Profile doesn't exist: " + user_id);
        }
        find.setInterests(interests);
    }

    @Override
    public String getInterests(Long user_id) throws ServiceException {
        Profile find = em.find(Profile.class, user_id);
        if (find == null) {
            throw new ServiceException("User Profile doesn't exist: " + user_id);
        }
        return find.getInterests();
    }
    
    
    
    
}
