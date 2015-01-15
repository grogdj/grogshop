/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Club;
import com.grogdj.grogshop.core.model.Interest;
import com.grogdj.grogshop.core.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.ClubsService;
import org.grogshop.services.api.InterestsService;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
@Singleton
public class ClubsServiceImpl implements ClubsService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    private final static Logger log = Logger.getLogger(ClubsServiceImpl.class.getName());

    @Inject
    private InterestsService interestsService;
    
    @PostConstruct
    private void init() {
        try {
            List<String> tags = new ArrayList<String>();
            tags.add("food");
            tags.add("fun");
            tags.add("healty");
            newClub("cooking club", "this is a new cooking club", "cooking", tags , new Long(1), "cooking.jpg");
            tags = new ArrayList<String>();
            tags.add("stars");
            tags.add("galaxy");
            tags.add("apollo");
            newClub("astronomy for everyone", "astronomy club for everybody", "astronomy", tags , new Long(1), "astrology.jpg");
            tags = new ArrayList<String>();
            tags.add("old");
            tags.add("classic");
            tags.add("cars");
            newClub("My good old cars", "about classic cars and stuff", "old cars", tags , new Long(1), "oldcars.jpg");
            tags = new ArrayList<String>();
            tags.add("puppies");
            tags.add("dogs");
            tags.add("cats");
            newClub("My small pets", "all pets allowed", "pets", tags , new Long(1), "pets.jpg");
            tags = new ArrayList<String>();
            tags.add("arcade");
            tags.add("play");
            tags.add("games");
            newClub("Pacman & cia", "for all the gamers", "videogames", tags , new Long(1), "videogames.jpg");
            tags = new ArrayList<String>();
            tags.add("graphic");
            tags.add("visual");
            tags.add("design");
            newClub("Designers United", "let the style be with you", "design", tags , new Long(1), "design.jpg");
            tags = new ArrayList<String>();
            tags.add("old");
            tags.add("fashion");
            tags.add("furniture");
            newClub("Antiques", "more than 100 year old items", "antiques", tags , new Long(1), "antiques.jpg");
            
        } catch (ServiceException ex) {
            Logger.getLogger(ClubsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Long newClub(String name, String description, String interest, List<String> tags, Long user_id, String image) throws ServiceException {
        User founder = em.find(User.class, user_id);
        if(founder == null){
            throw new ServiceException("Club cannot be created because there is no user with id: "+user_id);
        }
        Interest interestObject = interestsService.get(interest);
        Club club = new Club(name, description, interestObject, tags, founder, image);
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
