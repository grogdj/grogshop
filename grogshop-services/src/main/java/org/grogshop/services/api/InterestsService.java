/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import com.grogdj.grogshop.core.model.Interest;
import java.util.List;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
public interface InterestsService {
    
    List<Interest> getAllInterests() throws ServiceException;
    
    Interest get(String interest) throws ServiceException;
    
    void newInterest(String interest) throws ServiceException;
    
    void newInterest(String interest, String imagePath) throws ServiceException;
    
}
