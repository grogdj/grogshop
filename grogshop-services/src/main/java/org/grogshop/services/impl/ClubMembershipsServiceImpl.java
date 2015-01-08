/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.ClubMembership;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.ClubMembershipsService;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
@Singleton
public class ClubMembershipsServiceImpl implements ClubMembershipsService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    private final static Logger log = Logger.getLogger(ClubMembershipsServiceImpl.class.getName());

    @PostConstruct
    private void init() {
        
    }

    @Override
    public void joinClub(Long club_id, Long user_id) throws ServiceException {
        em.persist(new ClubMembership(club_id, user_id));
        log.log(Level.INFO, "ClubMembership {0} created", new Object[]{club_id, user_id});

    }

    @Override
    public Long getNroMembers(Long club_id) {
        try {
            return em.createNamedQuery("ClubMembership.countMembers", Long.class).setParameter("club_id", club_id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Long> getAllMembers(Long club_id) {
        List<ClubMembership> resultList = em.createNamedQuery("ClubMembership.getAllMembers", ClubMembership.class).setParameter("club_id", club_id).getResultList();
        List<Long> userIds = new ArrayList<Long>(resultList.size());
        for(ClubMembership cm : resultList){
            userIds.add(cm.getUserId());
        }
        return userIds;
    }

    @Override
    public List<Long> get(Long user_id) {
        List<ClubMembership> resultList = em.createNamedQuery("ClubMembership.getAll", ClubMembership.class).setParameter("user_id", user_id).getResultList();
        List<Long> clubIds = new ArrayList<Long>(resultList.size());
        for(ClubMembership cm : resultList){
            clubIds.add(cm.getClubId());
        }
        return clubIds;
    }


}
