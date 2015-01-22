/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Interest;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.InterestsService;
import org.grogshop.services.endpoints.api.ShopInterestsService;
import org.grogshop.services.exceptions.ServiceException;
import org.hibernate.validator.constraints.NotEmpty;

@ApplicationScoped
public class ShopInterestsServiceImpl implements ShopInterestsService {

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

    @Override
    public Response newInterest(@NotNull @NotEmpty @FormParam("interest") String interest) throws ServiceException {
        tagsService.newInterest(interest);
        return Response.ok().build();
    }
    
}
