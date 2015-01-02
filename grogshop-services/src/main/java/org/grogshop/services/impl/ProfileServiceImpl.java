/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Profile;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.ProfileService;

/**
 *
 * @author salaboy
 */
@Singleton
public class ProfileServiceImpl implements ProfileService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Override
    public boolean exist(String email) {
        return (getByEmail(email) != null);
    }

    @Override
    public Profile getByEmail(String email) {
        try {
            return em.createNamedQuery("Profile.getByEmail", Profile.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }
    @Override
    public String newProfile(Profile profile){
        em.persist(profile);
        return "Profile created";
    }
}
