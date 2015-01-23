/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import com.grogdj.grogshop.core.model.Item;
import java.math.BigDecimal;
import java.util.List;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
public interface ItemsService {

    List<Item> getAllItems() throws ServiceException;
    
    List<Item> getAllItemsByClub(Long clubId) throws ServiceException;

    Long newItem(Long userId, Long clubId, String type, String name, String description,  
            List<String> tags, BigDecimal minPrice,  BigDecimal maxPrice) throws ServiceException;
    
    Long newItem(Long userId, Long clubId, String type, String name, String description, 
            List<String> tags, BigDecimal minPrice, BigDecimal maxPrice, String image) throws ServiceException;

    Item getById(Long item_id) throws ServiceException;

    void updateItemImage(Long item_id, String fileName, byte[] content) throws ServiceException;
    
    byte[] getItemImage(Long item_id) throws ServiceException;
    
    void removeItem(Long item_id) throws ServiceException;
    
}
