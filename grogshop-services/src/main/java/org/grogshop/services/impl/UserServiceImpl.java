/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.ServiceKey;
import com.grogdj.grogshop.core.model.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.grogshop.services.api.UserService;
import org.grogshop.services.exceptions.ServiceException;
import org.grogshop.services.util.GrogUtil;

/**
 *
 * @author grogdj
 */
@Singleton
public class UserServiceImpl implements UserService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Resource
    UserTransaction ut;

    private final static Logger log = Logger.getLogger(UserServiceImpl.class.getName());

    public UserServiceImpl() {
    }

    @PostConstruct
    private void init() {

        try {
            ut.begin();
            newUser(new User("salaboy@gmail.com", "asdasd"));
            newUser(new User("eze@asd.asd", "123123"));
            ut.commit();

        } catch (Exception ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void newUser(User user) throws ServiceException {
        if (getByEmail(user.getEmail()) != null) {
            throw new ServiceException("User already registered with email: " + user.getEmail(), false);
        }
        user.setPassword(GrogUtil.hash(user.getPassword()));
        em.persist(user);
        String key = generateWebKey(user.getEmail());
        log.log(Level.INFO, "User {0} registered. Service Key: {1}", new Object[]{user.getEmail(), key});

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
        log.log(Level.INFO, "Generating Key: {0}", key);
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
