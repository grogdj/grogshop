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
import org.grogshop.services.util.GrogUtil;

/**
 *
 * @author grogdj
 */
@Singleton
public class UserServiceImpl {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public UserServiceImpl() {
    }

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

    public boolean exist(String email) {
        return (getByEmail(email) != null);
    }

    public User getByEmail(String email) {
        try {
            return em.createNamedQuery("User.getByEmail", User.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    public List<User> getAllUsers() {
        return em.createNamedQuery("User.getAll", User.class).getResultList();
    }

    public String generateApplicationKey(String email) {
        String key = email + ":" + UUID.randomUUID().toString();
        System.out.println("Generating Key: " + key);
        em.persist(new ServiceKey(key, email));
        return key;
    }

    public String generateWebKey(String email) {
        String key = "webkey:" + email;
        System.out.println("Generating Key: " + key);
        em.persist(new ServiceKey(key, email));
        return key;
    }

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

    public boolean existKey(String serviceKey) {
        return (em.createNamedQuery("ServiceKey.getByKey", ServiceKey.class).setParameter("key", serviceKey).getResultList().size() > 0);
    }

    List<String> getAllKeysByEmail(String email) {
        System.out.println("Email getAllKeysByEmail: " + email);
        List<ServiceKey> resultList = em.createNamedQuery("ServiceKey.getByEmail", ServiceKey.class).setParameter("email", email).getResultList();
        System.out.println("keys size: " + resultList.size());
        List<String> keys = new ArrayList<String>(resultList.size());
        for (ServiceKey k : resultList) {
            keys.add(k.getKey());
        }
        return keys;
    }
}
