/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Club;
import com.grogdj.grogshop.core.model.Interest;
import com.grogdj.grogshop.core.model.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.ClubsService;
import org.grogshop.services.api.InterestsService;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
@ApplicationScoped
public class ClubsServiceImpl implements ClubsService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    private final static Logger log = Logger.getLogger(ClubsServiceImpl.class.getName());

    @Inject
    private InterestsService interestsService;

    public ClubsServiceImpl() {
    }

    

    @Override
    public Long newClub(String name, String description, String interest, List<String> tags, Long user_id, 
            String image, Double longitude, Double latitude) throws ServiceException {
        User founder = em.find(User.class, user_id);
        if(founder == null){
            throw new ServiceException("Club cannot be created because there is no user with id: "+user_id);
        }
        Interest interestObject = interestsService.get(interest);
        if(interestObject == null){
            throw new ServiceException("Club cannot be created because the interest doesn't exist : "+interest);
        }
        Club club = null;
        if(longitude != 0.0 || latitude != 0.0 ){
            club = new Club(name, description, interestObject, tags, founder, image, longitude, latitude);
        }else{
            club = new Club(name, description, interestObject, tags, founder, image);
        }
        em.persist(club);
        log.log(Level.INFO, "Club {0} created with id {1}", new Object[]{name, club.getId()});
        return club.getId();
    }

    @Override
    public List<Club> getAllClubs() {
        return em.createNamedQuery("Club.getAll", Club.class).getResultList();
    }

    @Override
    public Club getById(Long club_id) {
        return em.find(Club.class, club_id);
    }

    @Override
    public List<Club> getAllClubsByInterests(List<String> interestsList) {
        return em.createNamedQuery("Club.getAllByInterests", Club.class).setParameter("interests", interestsList).getResultList();
    }
    
    

}
