/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Tag;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.TagsService;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
@Singleton
public class TagsServiceImpl implements TagsService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    private final static Logger log = Logger.getLogger(TagsServiceImpl.class.getName());

    @PostConstruct
    private void init() {
        try {
            newTag("music", "music.jpg");
            newTag("art", "art.jpg");
            newTag("science", "science.jpg");
            newTag("sports", "sports.jpg");
            newTag("cars", "cars.jpg");
            newTag("cooking", "cooking.jpg");
            newTag("design", "design.jpg");
            newTag("health", "health.jpg");
        } catch (ServiceException ex) {
            Logger.getLogger(TagsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void newTag(String tag) throws ServiceException {
        em.persist(new Tag(tag));
        log.log(Level.INFO, "Tag {0} created", new Object[]{tag});

    }

    @Override
    public void newTag(String tag, String imagePath) throws ServiceException {
        em.persist(new Tag(tag, imagePath));
        log.log(Level.INFO, "Tag {0} - {1} created", new Object[]{tag, imagePath});

    }

    @Override
    public List<Tag> getAllTags() {
        return em.createNamedQuery("Tag.getAll", Tag.class).getResultList();
    }

}
