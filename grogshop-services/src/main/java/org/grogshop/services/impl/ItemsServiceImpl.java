/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Item;
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
    public List<Item> getAllItems() {
        return em.createNamedQuery("Item.getAll", Item.class).getResultList();
    }

    @Override
    public Item getById(Long item_id) {
        return em.find(Item.class, item_id);
    }

    @Override
    public Long newItem(Long userId, Long clubId, String name, String description,  List<String> interests, BigDecimal price) throws ServiceException {
        Item item = new Item(userId, clubId, name, description, interests, price);
        em.persist(item);
        log.log(Level.INFO, "Item {0} created with id {1}", new Object[]{name, item.getId()});
        return item.getId();
    }

    @Override
    public List<Item> getAllItemsByClub(Long clubId) {
        return em.createNamedQuery("Item.getAllByClub", Item.class).setParameter("clubId", clubId).getResultList();
    }

}
