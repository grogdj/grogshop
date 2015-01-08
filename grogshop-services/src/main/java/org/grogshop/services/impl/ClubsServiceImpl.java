/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Club;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.ClubsService;
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

    @PostConstruct
    private void init() {
        try {
            List<String> tags = new ArrayList<String>();
            tags.add("food");
            tags.add("fun");
            tags.add("healty");
            newClub("cooking club", "this is a new cooking club", "cooking", tags , "grogdj@gmail.com", "cooking.jpg");
            tags = new ArrayList<String>();
            tags.add("stars");
            tags.add("galaxy");
            tags.add("apollo");
            newClub("astronomy for everyone", "astronomy club for everybody", "astronomy", tags , "esala212121@gmail.com", "astrology.jpg");
            tags = new ArrayList<String>();
            tags.add("old");
            tags.add("classic");
            tags.add("cars");
            newClub("My good old cars", "about classic cars and stuff", "old cars", tags , "thelongestttttttemail@gmail.com", "oldcars.jpg");
            tags = new ArrayList<String>();
            tags.add("puppies");
            tags.add("dogs");
            tags.add("cats");
            newClub("My small pets", "all pets allowed", "pets", tags , "anotherlongemailhere@gmail.com", "pets.jpg");
            tags = new ArrayList<String>();
            tags.add("arcade");
            tags.add("play");
            tags.add("games");
            newClub("Pacman & cia", "for all the gamers", "videogames", tags , "mrpacman@gmail.com", "videogames.jpg");
            tags = new ArrayList<String>();
            tags.add("graphic");
            tags.add("visual");
            tags.add("design");
            newClub("Designers United", "let the style be with you", "design", tags , "wearealldesigners@gmail.com", "design.jpg");
            tags = new ArrayList<String>();
            tags.add("old");
            tags.add("fashion");
            tags.add("furniture");
            newClub("Antiques", "more than 100 year old items", "antiques", tags , "oldisgood@gmail.com", "antiques.jpg");
            
        } catch (ServiceException ex) {
            Logger.getLogger(ClubsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void newClub(String name, String description, String category, List<String> interests, String founderEmail, String image) throws ServiceException {
        em.persist(new Club(name, description, category, interests, founderEmail, image));
        log.log(Level.INFO, "Club {0} created", new Object[]{name});

    }

    @Override
    public List<Club> getAllClubs() {
        return em.createNamedQuery("Club.getAll", Club.class).getResultList();
    }

    public Club getById(Long club_id) {
        return em.find(Club.class, club_id);
    }

}
