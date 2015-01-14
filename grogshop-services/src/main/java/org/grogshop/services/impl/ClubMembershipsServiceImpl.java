/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Club;
import com.grogdj.grogshop.core.model.ClubMembership;
import com.grogdj.grogshop.core.model.User;
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
    public void createMembership(Long club_id, Long user_id) throws ServiceException {
        Club club = em.find(Club.class, club_id);
        if(club  == null){
            throw new ServiceException("Coudn't create membership because: there is no club with id: "+club_id);
        }
        User user = em.find(User.class, user_id);
        if(user  == null){
            throw new ServiceException("Coudn't create membership because: there is no user with id: "+user_id);
        }
        ClubMembership clubMembership = new ClubMembership(club, user);
        em.persist(clubMembership);
        log.log(Level.INFO, "ClubMembership {0} - {1} created with id : {2}", new Object[]{club_id, user_id, clubMembership.getId()});

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
            userIds.add(cm.getUser().getId());
        }
        return userIds;
    }

    @Override
    public List<Long> get(Long user_id) {
        List<ClubMembership> resultList = em.createNamedQuery("ClubMembership.getAll", ClubMembership.class).setParameter("user_id", user_id).getResultList();
        List<Long> clubIds = new ArrayList<Long>(resultList.size());
        for(ClubMembership cm : resultList){
            clubIds.add(cm.getClub().getId());
        }
        return clubIds;
    }

    public void cancelMembership(Long club_id, Long user_id) throws ServiceException {
        try {
            ClubMembership singleResult = em.createNamedQuery("ClubMembership.get", ClubMembership.class)
                                            .setParameter("club_id", club_id)
                                            .setParameter("user_id", user_id).getSingleResult();
            em.remove(singleResult);
        }catch (NoResultException e) {
            throw new ServiceException("The Membership that you are trying to remove a non existing membership");
        } 
    }


}
