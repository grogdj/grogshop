/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Interest;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.grogshop.services.api.InterestsService;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
@Singleton
public class InterestsServiceImpl implements InterestsService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    private final static Logger log = Logger.getLogger(InterestsServiceImpl.class.getName());
   

    @Override
    public void newInterest(String tag) throws ServiceException {
        em.persist(new Interest(tag));
        log.log(Level.INFO, "Interest {0} created", new Object[]{tag});

    }

    @Override
    public void newInterest(String tag, String imagePath) throws ServiceException {
        em.persist(new Interest(tag, imagePath));
        log.log(Level.INFO, "Interest {0} - {1} created", new Object[]{tag, imagePath});

    }

    @Override
    public List<Interest> getAllInterests() throws ServiceException {
        return em.createNamedQuery("Interest.getAll", Interest.class).getResultList();
    }

    public Interest get(String interest) throws ServiceException {
        return em.find(Interest.class, interest);
    }
    
    

}
