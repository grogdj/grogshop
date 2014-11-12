/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Tag;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Singleton;
import org.grogshop.services.api.TagsService;

/**
 *
 * @author salaboy
 */
@Singleton
public class TagsServiceImpl implements TagsService{

    private Set<Tag> tags = new HashSet<Tag>();
    
    public List<Tag> getAllTags() {
        return new ArrayList<Tag>(this.tags);
    }
    
    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public void clearTags() {
        this.tags.clear();
    }

    public List<Tag> getTopTags(int amount) {
        return new ArrayList<Tag>(this.tags);
    }
    
}
