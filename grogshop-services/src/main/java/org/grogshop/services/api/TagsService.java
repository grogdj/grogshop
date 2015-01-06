/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import com.grogdj.grogshop.core.model.Tag;
import java.util.List;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
public interface TagsService {
    
    List<Tag> getAllTags();
    
    void newTag(String tag) throws ServiceException;
    
    void newTag(String tag, String imagePath) throws ServiceException;
    
}
