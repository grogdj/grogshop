/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Club;
import com.grogdj.grogshop.core.model.Item;
import com.grogdj.grogshop.core.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.io.IOUtils;
import org.grogshop.services.api.ItemsService;
import org.grogshop.services.api.RulesService;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
@ApplicationScoped
public class ItemsServiceImpl implements ItemsService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    private final static Logger log = Logger.getLogger(ItemsServiceImpl.class.getName());
    
    
    @Inject
    private RulesService rulesService;
    
    @PostConstruct
    private void init() {

    }

    @Override
    public List<Item> getAllItems() throws ServiceException {
        return em.createNamedQuery("Item.getAll", Item.class).getResultList();
    }

    @Override
    public Item getById(Long item_id) throws ServiceException {
        return em.find(Item.class, item_id);
    }

    @Override
    public Long newItem(Long userId, Long clubId, String type, String name, 
            String description, List<String> tags, BigDecimal minPrice , BigDecimal maxPrice) throws ServiceException {
        User user = em.find(User.class, userId);
        if (user == null) {
            throw new ServiceException("User  doesn't exist: " + userId);
        }
        
        Club club = em.find(Club.class, clubId);
        if (club == null) {
            throw new ServiceException("Club  doesn't exist: " + clubId);
        }
        Item item = new Item(user, club, type, name, description, tags, minPrice, maxPrice);
        em.persist(item);
        log.log(Level.INFO, "Item {0} created with id {1}", new Object[]{name, item.getId()});
        rulesService.insert(item);
        return item.getId();
    }
    
    @Override
    public Long newItem(Long userId, Long clubId, String type, String name, 
            String description, List<String> tags, BigDecimal minPrice , BigDecimal maxPrice, String image) throws ServiceException {
        Long newItem = newItem(userId, clubId, type, name, description, tags, minPrice, maxPrice);
        byte[] bytes = null;
        try {
            InputStream inputStream = new URL(image).openStream();

            bytes = IOUtils.toByteArray(inputStream);
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ItemsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateItemImage(newItem, image, bytes);
        return newItem;
    }
    
    

    @Override
    public void removeItem(Long item_id) throws ServiceException {
        Item find = em.find(Item.class, item_id);
        if (find == null) {
            throw new ServiceException("Item  doesn't exist: " + item_id);
        }
        em.remove(find);
    }
    
    

    @Override
    public List<Item> getAllItemsByClub(Long clubId) throws ServiceException {
        return em.createNamedQuery("Item.getAllByClub", Item.class).setParameter("clubId", clubId).getResultList();
    }
    
    @Override
    public List<Item> getAllItemsByUser(Long userId) throws ServiceException {
        return em.createNamedQuery("Item.getAllByUser", Item.class).setParameter("userId", userId).getResultList();
    }

    @Override
    public void updateItemImage(Long item_id, String fileName, byte[] content) throws ServiceException {
        Item find = em.find(Item.class, item_id);
        if (find == null) {
            throw new ServiceException("Item doesn't exist: " + item_id);
        }
        find.setImageFileName(fileName);
        find.setImageContent(content);
        em.merge(find);
    }

    @Override
    public byte[] getItemImage(Long item_id) throws ServiceException {
        Item find = em.find(Item.class, item_id);
        if (find == null) {
            throw new ServiceException("Item doesn't exist: " + item_id);
        }
        return find.getImageContent();
    }

}
