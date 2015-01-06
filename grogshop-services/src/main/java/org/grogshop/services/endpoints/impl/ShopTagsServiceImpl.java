/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Tag;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.TagsService;
import org.grogshop.services.endpoints.api.ShopTagsService;
import org.grogshop.services.exceptions.ServiceException;
import org.hibernate.validator.constraints.NotEmpty;

@Stateless
public class ShopTagsServiceImpl implements ShopTagsService {

    @Inject
    private TagsService tagsService;

    @PostConstruct
    private void init() throws ServiceException{
        tagsService.newTag("music", "music.jpg");
        tagsService.newTag("art", "art.jpg");
        tagsService.newTag("science", "science.jpg");
        tagsService.newTag("sports", "sports.jpg");
        tagsService.newTag("cars", "cars.jpg");
        tagsService.newTag("cooking", "cooking.jpg");
        tagsService.newTag("design", "design.jpg");
        tagsService.newTag("health", "health.jpg");
        
        
    }
    
    
    @Override
    public Response getAllTags() {
        List<Tag> allTags = tagsService.getAllTags();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for(Tag t : allTags){
            jsonArrayBuilder.add(jsonObjectBuilder.add("name", (t.getName()==null)?"":t.getName()).add("imagePath", (t.getImageURL()==null)?"":t.getImageURL()));
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return Response.ok(jsonArray).build();
        
    }

    @Override
    public Response newTag(@NotNull @NotEmpty @FormParam("tag") String tag) throws ServiceException {
        tagsService.newTag(tag);
        return Response.ok().build();
    }
    
    

}
