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
