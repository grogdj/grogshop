/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Club;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.ClubsService;
import org.grogshop.services.endpoints.api.ShopClubsService;
import org.grogshop.services.exceptions.ServiceException;
import org.hibernate.validator.constraints.NotEmpty;

@Stateless
public class ShopClubsServiceImpl implements ShopClubsService {

    @Inject
    private ClubsService clubsService;

   
    
    
    @Override
    public Response getAllClubs() {
        List<Club> allClubs = clubsService.getAllClubs();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for(Club c : allClubs){
            jsonArrayBuilder.add(jsonObjectBuilder
                    .add("name", (c.getName()==null)?"":c.getName())
                    .add("interests", (c.getName()==null)?"":c.getName())
                    .add("founderEmail", (c.getFounderEmail()==null)?"":c.getFounderEmail()));
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return Response.ok(jsonArray).build();
        
    }

    @Override
    public Response newClub(@NotNull @NotEmpty @FormParam("name") String name, 
            @NotNull @NotEmpty @FormParam("interests") String interests, 
            @NotNull @NotEmpty @FormParam("founderEmail") String founderEmail) throws ServiceException {
        String[] interestArray = interests.split("'");
        if(interestArray != null){
            List<String> interestsList = new ArrayList<String>();
            for(String s : interestArray){
                interestsList.add(s);
            }
            clubsService.newClub(name, interestsList, founderEmail);
        
        }
        return Response.ok().build();
        
    }
    
    

}
