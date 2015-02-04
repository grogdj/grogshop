/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Matching;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.MatchingsService;
import org.grogshop.services.endpoints.api.ShopMatchingsService;
import org.grogshop.services.exceptions.ServiceException;

@Stateless
public class ShopMatchingsServiceImpl implements ShopMatchingsService {

    @Inject
    private MatchingsService matchingsService;

    private final static Logger log = Logger.getLogger(ShopMatchingsServiceImpl.class.getName());

    public Response getAllMatchings() throws ServiceException {
        List<Matching> allMatchings = matchingsService.getAllMatchings();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Matching m : allMatchings) {
            jsonObjectBuilder
                    .add("id", (m.getId() == null) ? "" : m.getId().toString())
                    .add("club_id", (m.getClub() == null) ? "" : m.getClub().getId().toString())
                    .add("club_name", (m.getClub() == null) ? "" : m.getClub().getName());
            if (m.getItem() != null) {
                JsonObjectBuilder jsonItemObjectBuilder = Json.createObjectBuilder();
                jsonItemObjectBuilder.add("id", (m.getItem().getId() == null) ? "" : m.getItem().getId().toString())
                        .add("user_id", (m.getItem().getUser().getId() == null) ? "" : m.getItem().getUser().getId().toString())
                        .add("user_email", (m.getItem().getUser().getEmail() == null) ? "" : m.getItem().getUser().getEmail())
                        .add("club_id", (m.getItem().getClub().getId() == null) ? "" : m.getItem().getClub().getId().toString())
                        .add("type", (m.getItem().getType() == null) ? "" : m.getItem().getType().toString())
                        .add("name", (m.getItem().getName() == null) ? "" : m.getItem().getName())
                        .add("description", (m.getItem().getDescription() == null) ? "" : m.getItem().getDescription())
                        .add("hasImage", m.getItem().hasImage())
                        .add("minPrice", (m.getItem().getMinPrice() == null) ? "" : m.getItem().getMinPrice().toString())
                        .add("maxPrice", (m.getItem().getMaxPrice() == null) ? "" : m.getItem().getMaxPrice().toString());

                if (m.getItem().getTags() != null) {
                    JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
                    for (String s : m.getItem().getTags()) {
                        jsonArrayBuilderInterest.add(Json.createObjectBuilder().add("text", s));
                    }
                    jsonItemObjectBuilder.add("tags", jsonArrayBuilderInterest);
                }
                jsonObjectBuilder.add("item", jsonItemObjectBuilder);
            }
            if (m.getItemMatched() != null) {
                JsonObjectBuilder jsonItemMatchedObjectBuilder = Json.createObjectBuilder();
                jsonItemMatchedObjectBuilder.add("id", (m.getItemMatched().getId() == null) ? "" : m.getItemMatched().getId().toString())
                        .add("user_id", (m.getItemMatched().getUser().getId() == null) ? "" : m.getItemMatched().getUser().getId().toString())
                        .add("user_email", (m.getItemMatched().getUser().getEmail() == null) ? "" : m.getItemMatched().getUser().getEmail())
                        .add("club_id", (m.getItemMatched().getClub().getId() == null) ? "" : m.getItemMatched().getClub().getId().toString())
                        .add("type", (m.getItemMatched().getType() == null) ? "" : m.getItemMatched().getType().toString())
                        .add("name", (m.getItemMatched().getName() == null) ? "" : m.getItemMatched().getName())
                        .add("description", (m.getItemMatched().getDescription() == null) ? "" : m.getItemMatched().getDescription())
                        .add("hasImage", m.getItemMatched().hasImage())
                        .add("minPrice", (m.getItemMatched().getMinPrice() == null) ? "" : m.getItemMatched().getMinPrice().toString())
                        .add("maxPrice", (m.getItemMatched().getMaxPrice() == null) ? "" : m.getItemMatched().getMaxPrice().toString());

                if (m.getItemMatched().getTags() != null) {
                    JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
                    for (String s : m.getItemMatched().getTags()) {
                        jsonArrayBuilderInterest.add(Json.createObjectBuilder().add("text", s));
                    }
                    jsonItemMatchedObjectBuilder.add("tags", jsonArrayBuilderInterest);
                }
                jsonObjectBuilder.add("itemMatched", jsonItemMatchedObjectBuilder);
            }
            jsonObjectBuilder.add("since", m.getMatchedSince().toString());

            jsonArrayBuilder.add(jsonObjectBuilder);
        }

        JsonArray jsonArray = jsonArrayBuilder.build();
        return Response.ok(jsonArray.toString()).build();
    }

    public Response getAllMatchingsByItem(Long itemId) throws ServiceException {
        List<Matching> allMatchings = matchingsService.getAllItemsByItem(itemId);
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("item_id", itemId);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        if (allMatchings != null) {
            for (Matching m : allMatchings) {

                JsonObjectBuilder jsonItemMatchedObjectBuilder = Json.createObjectBuilder();
                if (m.getItemMatched() != null) {

                    jsonItemMatchedObjectBuilder.add("id", (m.getItemMatched().getId() == null) ? "" : m.getItemMatched().getId().toString())
                            .add("user_id", (m.getItemMatched().getUser().getId() == null) ? "" : m.getItemMatched().getUser().getId().toString())
                            .add("user_email", (m.getItemMatched().getUser().getEmail() == null) ? "" : m.getItemMatched().getUser().getEmail())
                            .add("club_id", (m.getItemMatched().getClub().getId() == null) ? "" : m.getItemMatched().getClub().getId().toString())
                            .add("type", (m.getItemMatched().getType() == null) ? "" : m.getItemMatched().getType().toString())
                            .add("name", (m.getItemMatched().getName() == null) ? "" : m.getItemMatched().getName())
                            .add("description", (m.getItemMatched().getDescription() == null) ? "" : m.getItemMatched().getDescription())
                            .add("hasImage", m.getItemMatched().hasImage())
                            .add("minPrice", (m.getItemMatched().getMinPrice() == null) ? "" : m.getItemMatched().getMinPrice().toString())
                            .add("maxPrice", (m.getItemMatched().getMaxPrice() == null) ? "" : m.getItemMatched().getMaxPrice().toString());

                    if (m.getItemMatched().getTags() != null) {
                        JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
                        for (String s : m.getItemMatched().getTags()) {
                            jsonArrayBuilderInterest.add(Json.createObjectBuilder().add("text", s));
                        }
                        jsonItemMatchedObjectBuilder.add("tags", jsonArrayBuilderInterest);
                    }

                }

                jsonArrayBuilder.add(jsonItemMatchedObjectBuilder);
            }
        }
        jsonObjectBuilder.add("machedItems", jsonArrayBuilder);
        JsonObject jsonObject = jsonObjectBuilder.build();
        return Response.ok(jsonObject.toString()).build();
    }

    public Response get(Long matchingId) throws ServiceException {
        Matching m = matchingsService.getById(matchingId);

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        jsonObjectBuilder
                .add("id", (m.getId() == null) ? "" : m.getId().toString())
                .add("club_id", (m.getClub() == null) ? "" : m.getClub().getId().toString())
                .add("club_name", (m.getClub() == null) ? "" : m.getClub().getName());
        if (m.getItem() != null) {
            JsonObjectBuilder jsonItemObjectBuilder = Json.createObjectBuilder();
            jsonItemObjectBuilder.add("id", (m.getItem().getId() == null) ? "" : m.getItem().getId().toString())
                    .add("user_id", (m.getItem().getUser().getId() == null) ? "" : m.getItem().getUser().getId().toString())
                    .add("user_email", (m.getItem().getUser().getEmail() == null) ? "" : m.getItem().getUser().getEmail())
                    .add("club_id", (m.getItem().getClub().getId() == null) ? "" : m.getItem().getClub().getId().toString())
                    .add("type", (m.getItem().getType() == null) ? "" : m.getItem().getType().toString())
                    .add("name", (m.getItem().getName() == null) ? "" : m.getItem().getName())
                    .add("description", (m.getItem().getDescription() == null) ? "" : m.getItem().getDescription())
                    .add("hasImage", m.getItem().hasImage())
                    .add("minPrice", (m.getItem().getMinPrice() == null) ? "" : m.getItem().getMinPrice().toString())
                    .add("maxPrice", (m.getItem().getMaxPrice() == null) ? "" : m.getItem().getMaxPrice().toString());

            if (m.getItem().getTags() != null) {
                JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
                for (String s : m.getItem().getTags()) {
                    jsonArrayBuilderInterest.add(Json.createObjectBuilder().add("text", s));
                }
                jsonItemObjectBuilder.add("tags", jsonArrayBuilderInterest);
            }
            jsonObjectBuilder.add("item", jsonItemObjectBuilder);
        }
        if (m.getItemMatched() != null) {
            JsonObjectBuilder jsonItemMatchedObjectBuilder = Json.createObjectBuilder();
            jsonItemMatchedObjectBuilder.add("id", (m.getItemMatched().getId() == null) ? "" : m.getItemMatched().getId().toString())
                    .add("user_id", (m.getItemMatched().getUser().getId() == null) ? "" : m.getItemMatched().getUser().getId().toString())
                    .add("user_email", (m.getItemMatched().getUser().getEmail() == null) ? "" : m.getItemMatched().getUser().getEmail())
                    .add("club_id", (m.getItemMatched().getClub().getId() == null) ? "" : m.getItemMatched().getClub().getId().toString())
                    .add("type", (m.getItemMatched().getType() == null) ? "" : m.getItemMatched().getType().toString())
                    .add("name", (m.getItemMatched().getName() == null) ? "" : m.getItemMatched().getName())
                    .add("description", (m.getItemMatched().getDescription() == null) ? "" : m.getItemMatched().getDescription())
                    .add("hasImage", m.getItemMatched().hasImage())
                    .add("minPrice", (m.getItemMatched().getMinPrice() == null) ? "" : m.getItemMatched().getMinPrice().toString())
                    .add("maxPrice", (m.getItemMatched().getMaxPrice() == null) ? "" : m.getItemMatched().getMaxPrice().toString());

            if (m.getItemMatched().getTags() != null) {
                JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
                for (String s : m.getItemMatched().getTags()) {
                    jsonArrayBuilderInterest.add(Json.createObjectBuilder().add("text", s));
                }
                jsonItemMatchedObjectBuilder.add("tags", jsonArrayBuilderInterest);
            }
            jsonObjectBuilder.add("itemMatched", jsonItemMatchedObjectBuilder);
        }
        jsonObjectBuilder.add("since", m.getMatchedSince().toString());

        return Response.ok(jsonObjectBuilder.build().toString()).build();
    }

}
