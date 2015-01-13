/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.ItemsService;
import org.grogshop.services.endpoints.api.ShopItemsService;
import org.grogshop.services.exceptions.ServiceException;
import org.hibernate.validator.constraints.NotEmpty;

@Stateless
public class ShopItemsServiceImpl implements ShopItemsService {

    @Inject
    private ItemsService itemsService;

    @Override
    public Response getAllItems() {
        List<Item> allItems = itemsService.getAllItems();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Item i : allItems) {
            jsonObjectBuilder
                    .add("id", (i.getId() == null) ? "" : i.getId().toString())
                    .add("user_id", (i.getUserId()== null) ? "" : i.getUserId().toString())
                    .add("club_id", (i.getClubId()== null) ? "" : i.getClubId().toString())
                    .add("name", (i.getName() == null) ? "" : i.getName())
                    .add("description", (i.getDescription() == null) ? "" : i.getDescription())
                    .add("price", (i.getPrice()== null) ? "" : i.getPrice().toString())
                    .add("image", (i.getImage() == null) ? "" : i.getImage());

            if (i.getTags() != null) {
                JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
                for (String s : i.getTags()) {
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
    public Response getAllItemsByClub(@PathParam("id") Long club_id) {
        List<Item> allItems = itemsService.getAllItemsByClub(club_id);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Item i : allItems) {
            jsonObjectBuilder
                    .add("id", (i.getId() == null) ? "" : i.getId().toString())
                    .add("user_id", (i.getUserId()== null) ? "" : i.getUserId().toString())
                    .add("club_id", (i.getClubId()== null) ? "" : i.getClubId().toString())
                    .add("name", (i.getName() == null) ? "" : i.getName())
                    .add("description", (i.getDescription() == null) ? "" : i.getDescription())
                    .add("price", (i.getPrice()== null) ? "" : i.getPrice().toString())
                    .add("image", (i.getImage() == null) ? "" : i.getImage());

            if (i.getTags() != null) {
                JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
                for (String s : i.getTags()) {
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
    public Response newItem(@NotNull @FormParam("user_id") Long userId,
            @NotNull @FormParam("club_id") Long clubId,
            @NotNull @NotEmpty @FormParam("name") String name,
            @NotNull @NotEmpty @FormParam("description") String description,
            @NotNull @NotEmpty @FormParam("tags") String tags,
            @NotNull @FormParam("price") String price) throws ServiceException {
        String[] interestArray = tags.split(",");
        List<String> interestsList = new ArrayList<String>();
        if (interestArray != null) {
            
            for (String s : interestArray) {
                interestsList.add(s);
            }
            

        }
        Long newItem = itemsService.newItem(userId, clubId, name, description, interestsList, new BigDecimal(price));
        return Response.ok(newItem).build();

    }

    @Override
    public Response get(@PathParam("id") Long item_id) throws ServiceException {
        Item i = itemsService.getById(item_id);
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("id", (i.getId() == null) ? "" : i.getId().toString())
                .add("user_id", (i.getUserId()== null) ? "" : i.getUserId().toString())
                .add("club_id", (i.getClubId()== null) ? "" : i.getClubId().toString())
                .add("name", (i.getName() == null) ? "" : i.getName())
                .add("description", (i.getDescription() == null) ? "" : i.getDescription())
                .add("price", (i.getPrice()== null) ? "" : i.getPrice().toString())
                .add("image", (i.getImage() == null) ? "" : i.getImage());
        if (i.getTags() != null) {
            JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
            for (String s : i.getTags()) {
                jsonArrayBuilderInterest.add(s);
            }
            jsonObjectBuilder.add("tags", jsonArrayBuilderInterest.build());
        }
        JsonObject build = jsonObjectBuilder.build();
        return Response.ok(build.toString()).build();

    }

}