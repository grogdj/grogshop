/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import com.grogdj.grogshop.core.model.Tag;
import java.util.List;

/**
 *
 * @author salaboy
 */
public interface TagsService {
    List<Tag> getAllTags();

    List<Tag> getTopTags(int amount);
    
    void newTag(Tag tag);
    
    void clearTags();
}
