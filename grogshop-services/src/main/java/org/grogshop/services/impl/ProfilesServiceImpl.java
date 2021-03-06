/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Interest;
import com.grogdj.grogshop.core.model.Profile;
import com.grogdj.grogshop.core.model.User;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.ProfilesService;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class ProfilesServiceImpl implements ProfilesService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    private final static Logger log = Logger.getLogger(ProfilesServiceImpl.class.getName());

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
        User user = em.find(User.class, user_id);
        if (user == null) {
            throw new ServiceException("Profile wasn't created because: there is no user with id: " + user_id);
        }
        em.persist(new Profile(user));
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
    public void setInterests(Long user_id, List<Interest> interests) throws ServiceException {
        Profile profile = em.find(Profile.class, user_id);
        if (profile == null) {
            throw new ServiceException("User Profile doesn't exist: " + user_id);
        }
        log.info("Storing to the database: " + interests);
        profile.setInterests(interests);
        em.merge(profile);
    }

    @Override
    public List<Interest> getInterests(Long user_id) throws ServiceException {
        Profile profile = em.find(Profile.class, user_id);
        if (profile == null) {
            throw new ServiceException("User Profile doesn't exist: " + user_id);
        }
        log.info("Interest from the database: " + profile.getInterests());
        return profile.getInterests();
    }

    @Override
    public void updateAvatar(Long user_id, String fileName, byte[] content) throws ServiceException {
        Profile find = em.find(Profile.class, user_id);
        if (find == null) {
            throw new ServiceException("User Profile doesn't exist: " + user_id);
        }
        find.setAvatarFileName(fileName);
        find.setAvatarContent(content);
        em.merge(find);
    }

    @Override
    public byte[] getAvatar(Long user_id) throws ServiceException {
        Profile find = em.find(Profile.class, user_id);
        if (find == null) {
            return null;
        }
        return find.getAvatarContent();
    }

    @Override
    public void removeAvatar(Long user_id) throws ServiceException {
        Profile find = em.find(Profile.class, user_id);
        if (find == null) {
            throw new ServiceException("User Profile doesn't exist: " + user_id);
        }
        find.setAvatarFileName("");
        find.setAvatarContent(null);
        em.merge(find);
    }

}
