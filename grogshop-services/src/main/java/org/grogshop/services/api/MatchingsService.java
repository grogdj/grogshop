/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;


import com.grogdj.grogshop.core.model.Matching;
import java.util.List;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
public interface MatchingsService {

    List<Matching> getAllMatchings() throws ServiceException;
    
    List<Matching> getAllItemsByItem(Long itemId) throws ServiceException;

    Long newMatching(Long clubId, Long itemId, Long itemMatchedId, String type) throws ServiceException;
    
    Long newMatching(Matching matching) throws ServiceException;
    
    Matching getById(Long matchingId) throws ServiceException;

    void removeMatching(Long matchingId) throws ServiceException;
    
}
