/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Club;
import com.grogdj.grogshop.core.model.Item;
import com.grogdj.grogshop.core.model.Matching;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.MatchingsService;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
@ApplicationScoped
public class MatchingsServiceImpl implements MatchingsService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    private final static Logger log = Logger.getLogger(MatchingsServiceImpl.class.getName());


    @PostConstruct
    private void init() {

    }

    public List<Matching> getAllMatchings() throws ServiceException {
        return em.createNamedQuery("Matching.getAll", Matching.class).getResultList();
    }

    public List<Matching> getAllItemsByItem(Long itemId) throws ServiceException {
        return null;
    }

    public Long newMatching(Long clubId, Long itemId, Long itemMatchedId) throws ServiceException {
        Club club = em.find(Club.class, clubId);
        if (club == null) {
            throw new ServiceException("Club  doesn't exist: " + clubId);
        }
        
        Item item = em.find(Item.class, itemId);
        if (item == null) {
            throw new ServiceException("Item  doesn't exist: " + itemId);
        }
        
        Item itemMatched = em.find(Item.class, itemMatchedId);
        if (itemMatched == null) {
            throw new ServiceException("Item matched doesn't exist: " + itemMatchedId);
        }
        
        
        Matching matching = new Matching(club, item, itemMatched);
        em.persist(matching);
        log.log(Level.INFO, "Matching {0} created (club: {1})for item id {2} and {3}", new Object[]{ matching.getId(),club, item,itemMatched });
        return  matching.getId();
    }
    
    public Long newMatching(Matching matching) throws ServiceException {
        em.persist(matching);
        return matching.getId();
    }

    public Matching getById(Long matchingId) throws ServiceException {
        return null;
    }

    public void removeMatching(Long matchingId) throws ServiceException {
        
    }

   

   

}
