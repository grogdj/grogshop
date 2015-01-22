/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Item;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.grogshop.services.api.ItemsService;
import org.grogshop.services.endpoints.api.PublicShopItemsService;
import org.grogshop.services.exceptions.ServiceException;

@Stateless
public class PublicShopItemsServiceImpl implements PublicShopItemsService {

    @Inject
    private ItemsService itemsService;

    public static final String UPLOADED_FILE_PARAMETER_NAME = "file";

    private final static Logger log = Logger.getLogger(PublicShopItemsServiceImpl.class.getName());

    @Override
    public Response getAllItems() throws ServiceException {
        List<Item> allItems = itemsService.getAllItems();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Item i : allItems) {
            jsonObjectBuilder
                    .add("id", (i.getId() == null) ? "" : i.getId().toString())
                    .add("user_id", (i.getUser().getId() == null) ? "" : i.getUser().getId().toString())
                    .add("user_email", (i.getUser().getEmail() == null) ? "" : i.getUser().getEmail())
                    .add("club_id", (i.getClub().getId() == null) ? "" : i.getClub().getId().toString())
                    .add("name", (i.getName() == null) ? "" : i.getName())
                    .add("description", (i.getDescription() == null) ? "" : i.getDescription())
                    .add("price", (i.getPrice() == null) ? "" : i.getPrice().toString());

            if (i.getTags() != null) {
                JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
                for (String s : i.getTags()) {
                    jsonArrayBuilderInterest.add(Json.createObjectBuilder().add("name", s));
                }
                jsonObjectBuilder.add("tags", jsonArrayBuilderInterest);
            }
            jsonArrayBuilder.add(jsonObjectBuilder);
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return Response.ok(jsonArray.toString()).build();

    }

    @Override
    public Response getAllItemsByClub(@PathParam("id") Long club_id) throws ServiceException {
        List<Item> allItems = itemsService.getAllItemsByClub(club_id);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Item i : allItems) {
            jsonObjectBuilder
                    .add("id", (i.getId() == null) ? "" : i.getId().toString())
                    .add("user_id", (i.getUser().getId() == null) ? "" : i.getUser().getId().toString())
                    .add("user_email", (i.getUser().getEmail() == null) ? "" : i.getUser().getEmail())
                    .add("club_id", (i.getClub().getId() == null) ? "" : i.getClub().getId().toString())
                    .add("name", (i.getName() == null) ? "" : i.getName())
                    .add("description", (i.getDescription() == null) ? "" : i.getDescription())
                    .add("price", (i.getPrice() == null) ? "" : i.getPrice().toString());

            if (i.getTags() != null) {
                JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
                for (String s : i.getTags()) {
                    jsonArrayBuilderInterest.add(Json.createObjectBuilder().add("name", s));
                }
                jsonObjectBuilder.add("tags", jsonArrayBuilderInterest);
            }
            jsonArrayBuilder.add(jsonObjectBuilder);
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return Response.ok(jsonArray.toString()).build();

    }

    @Override
    public Response get(@PathParam("id") Long item_id) throws ServiceException {
        Item i = itemsService.getById(item_id);
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("id", (i.getId() == null) ? "" : i.getId().toString())
                .add("user_id", (i.getUser().getId() == null) ? "" : i.getUser().getId().toString())
                    .add("user_email", (i.getUser().getEmail() == null) ? "" : i.getUser().getEmail())
                    .add("club_id", (i.getClub().getId() == null) ? "" : i.getClub().getId().toString())
                .add("name", (i.getName() == null) ? "" : i.getName())
                .add("description", (i.getDescription() == null) ? "" : i.getDescription())
                .add("price", (i.getPrice() == null) ? "" : i.getPrice().toString());
        if (i.getTags() != null) {
            JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
            for (String s : i.getTags()) {
                jsonArrayBuilderInterest.add(Json.createObjectBuilder().add("name", s));
            }
            jsonObjectBuilder.add("tags", jsonArrayBuilderInterest);
        }
        JsonObject build = jsonObjectBuilder.build();
        return Response.ok(build.toString()).build();

    }

    @Override
    public Response getItemImage(@NotNull @PathParam("id") Long item_id) throws ServiceException {
        final byte[] avatar = itemsService.getItemImage(item_id);
        return Response.ok().entity(new StreamingOutput() {
            @Override
            public void write(OutputStream output)
                    throws IOException, WebApplicationException {
                output.write(avatar);
                output.flush();
            }
        }).build();
    }

}
