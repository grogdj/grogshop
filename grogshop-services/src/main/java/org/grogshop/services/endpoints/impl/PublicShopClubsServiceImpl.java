/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Club;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.ClubsService;
import org.grogshop.services.endpoints.api.PublicShopClubsService;
import org.grogshop.services.exceptions.ServiceException;

@Stateless
public class PublicShopClubsServiceImpl implements PublicShopClubsService {

    @Inject
    private ClubsService clubsService;

    @Override
    public Response getAllClubs() {
        List<Club> allClubs = clubsService.getAllClubs();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Club c : allClubs) {
            jsonObjectBuilder
                    .add("id", (c.getId() == null) ? "" : c.getId().toString())
                    .add("name", (c.getName() == null) ? "" : c.getName())
                    .add("interest", (c.getInterest()== null) ? "" : c.getInterest().getName())
                    .add("description", (c.getDescription()== null) ? "" : c.getDescription())
                    .add("founderEmail", (c.getFounder().getEmail() == null) ? "" : c.getFounder().getEmail())
                    .add("image", (c.getImage()== null) ? "" : c.getImage());
            
            if (c.getTags()!= null) {
                JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
                for (String s : c.getTags()) {
                    jsonArrayBuilderInterest.add(s);
                }
                jsonObjectBuilder.add("tags", jsonArrayBuilderInterest.build());
            }
            jsonArrayBuilder.add(jsonObjectBuilder);
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return Response.ok(jsonArray.toString()).build();

    }


    @Override
    public Response get(Long club_id) throws ServiceException {
        Club c = clubsService.getById(club_id);
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("id", (c.getId() == null) ? "" : c.getId().toString())
                .add("name", (c.getName() == null) ? "" : c.getName())
                .add("interest", (c.getInterest()== null) ? "" : c.getInterest().getName())
                .add("description", (c.getDescription()== null) ? "" : c.getDescription())
                .add("founderEmail", (c.getFounder().getEmail() == null) ? "" : c.getFounder().getEmail())
                .add("image", (c.getImage() == null) ? "" : c.getImage());
        if (c.getTags()!= null) {
            JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
            for (String s : c.getTags()) {
                jsonArrayBuilderInterest.add(s);
            }
            jsonObjectBuilder.add("tags", jsonArrayBuilderInterest.build());
        }
        JsonObject build = jsonObjectBuilder.build();
        return Response.ok(build.toString()).build();

    }

}
