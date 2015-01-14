/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Item;
import com.grogdj.grogshop.core.model.Profile;
import com.grogdj.grogshop.core.model.User;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.ItemsService;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
@Singleton
public class ItemsServiceImpl implements ItemsService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    private final static Logger log = Logger.getLogger(ItemsServiceImpl.class.getName());

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
    public Long newItem(Long userId, Long clubId, String name, String description, List<String> interests, BigDecimal price) throws ServiceException {
        User find = em.find(User.class, userId);
        if (find == null) {
            throw new ServiceException("User  doesn't exist: " + userId);
        }
        Item item = new Item(userId,find.getEmail(), clubId, name, description, interests, price);
        em.persist(item);
        log.log(Level.INFO, "Item {0} created with id {1}", new Object[]{name, item.getId()});
        return item.getId();
    }

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
