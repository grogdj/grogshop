/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Club;
import com.grogdj.grogshop.core.model.Tag;
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

    }

    @Override
    public void newClub(String name, List<String> interests, String founderEmail) throws ServiceException {
        em.persist(new Club(name, interests, founderEmail));
        log.log(Level.INFO, "Club {0} created", new Object[]{name});

    }

    @Override
    public List<Club> getAllClubs() {
        return em.createNamedQuery("Club.getAll", Club.class).getResultList();
    }

}
