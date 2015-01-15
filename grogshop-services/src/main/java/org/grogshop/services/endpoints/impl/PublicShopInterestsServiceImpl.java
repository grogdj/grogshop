/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Interest;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.InterestsService;
import org.grogshop.services.endpoints.api.PublicShopInterestsService;
import org.grogshop.services.exceptions.ServiceException;

@Stateless
public class PublicShopInterestsServiceImpl implements PublicShopInterestsService {

    @Inject
    private InterestsService tagsService;

    
    @Override
    public Response getAllInterests() throws ServiceException {
        List<Interest> allTags = tagsService.getAllInterests();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for(Interest t : allTags){
            jsonArrayBuilder.add(jsonObjectBuilder.add("name", (t.getName()==null)?"":t.getName()).add("imagePath", (t.getImageURL()==null)?"":t.getImageURL()));
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return Response.ok(jsonArray).build();
        
    }

    
}
