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
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PostConstruct
    private void init() {
        try {
            newInterest("music", "music.jpg");
            newInterest("art", "art.jpg");
            newInterest("science", "science.jpg");
            newInterest("sports", "sports.jpg");
            newInterest("cars", "cars.jpg");
            newInterest("cooking", "cooking.jpg");
            newInterest("design", "design.jpg");
            newInterest("health", "health.jpg");
            newInterest("antiques", "antiques.jpg");
            newInterest("clothes", "clothes.jpg");
            newInterest("astrology", "astrology.jpg");
            newInterest("gardening", "gardening.jpg");
            newInterest("infusions", "infusions.jpg");
            newInterest("oldcars", "oldcars.jpg");
            newInterest("pets", "pets.jpg");
            newInterest("photography", "photography.jpg");
            newInterest("radio", "radio.jpg");
            newInterest("sailing", "sailing.jpg");
            newInterest("videogames", "videogames.jpg");
            newInterest("writing", "writing.jpg");

        } catch (ServiceException ex) {
            Logger.getLogger(InterestsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
