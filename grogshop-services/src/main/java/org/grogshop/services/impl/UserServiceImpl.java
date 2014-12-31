/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.ServiceKey;
import com.grogdj.grogshop.core.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.UserService;
import org.grogshop.services.util.GrogUtil;

/**
 *
 * @author grogdj
 */
@Singleton
public class UserServiceImpl implements UserService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public UserServiceImpl() {
    }

    @Override
    public String registerUser(User user) {
        if (getByEmail(user.getEmail()) != null) {
            return "User already registered with email: " + user.getEmail();
        }
        user.setPassword(GrogUtil.hash(user.getPassword()));
        em.persist(user);
        String key = generateWebKey(user.getEmail());
        System.out.println("User " + user.getEmail() + " registered. Service Key: " + key);
        return "User " + user.getEmail() + " registered. Service Key: " + key;
    }

    @Override
    public boolean exist(String email) {
        return (getByEmail(email) != null);
    }

    @Override
    public User getByEmail(String email) {
        try {
            return em.createNamedQuery("User.getByEmail", User.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    private String generateWebKey(String email) {
        String key = "webkey:" + email;
        System.out.println("Generating Key: " + key);
        em.persist(new ServiceKey(key, email));
        return key;
    }

    @Override
    public String getKey(String serviceKey) {
        try {
            ServiceKey singleResult = em.createNamedQuery("ServiceKey.getByKey", ServiceKey.class).setParameter("key", serviceKey).getSingleResult();
            if (singleResult != null) {
                return singleResult.getEmail();
            }
        } catch (NoResultException e) {
            return "";
        }
        return "";
    }

    @Override
    public boolean existKey(String serviceKey) {
        return (em.createNamedQuery("ServiceKey.getByKey", ServiceKey.class).setParameter("key", serviceKey).getResultList().size() > 0);
    }

}
