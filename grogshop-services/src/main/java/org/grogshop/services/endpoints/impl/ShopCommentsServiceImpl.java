/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Comment;
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
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.CommentsService;
import org.grogshop.services.endpoints.api.ShopCommentsService;
import org.grogshop.services.exceptions.ServiceException;
import org.hibernate.validator.constraints.NotEmpty;

@Stateless
public class ShopCommentsServiceImpl implements ShopCommentsService {

    @Inject
    private CommentsService commentsService;

    

    private final static Logger log = Logger.getLogger(ShopCommentsServiceImpl.class.getName());

    @Override
    public Response getAllComments() throws ServiceException {
        List<Comment> allComments = commentsService.getAllComments();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Comment c : allComments) {
            jsonObjectBuilder
                    .add("id", (c.getId() == null) ? "" : c.getId().toString())
                    .add("user_id", (c.getUser().getId() == null) ? "" : c.getUser().getId().toString())
                    .add("user_email", (c.getUser().getEmail() == null) ? "" : c.getUser().getEmail())
                    .add("club_id", (c.getClub().getId() == null) ? "" : c.getClub().getId().toString())
                    .add("item_id", (c.getItem().getId() == null) ? "" : c.getItem().getId().toString())
                    .add("text", (c.getText()== null) ? "" : c.getText());

            
            jsonArrayBuilder.add(jsonObjectBuilder);
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return Response.ok(jsonArray.toString()).build();

    }

    @Override
    public Response getAllCommentsByClub(@PathParam("id") Long club_id) throws ServiceException {
        List<Comment> allComments = commentsService.getAllCommentsByClub(club_id);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Comment c : allComments) {
            jsonObjectBuilder
                    .add("id", (c.getId() == null) ? "" : c.getId().toString())
                    .add("user_id", (c.getUser().getId() == null) ? "" : c.getUser().getId().toString())
                    .add("user_email", (c.getUser().getEmail() == null) ? "" : c.getUser().getEmail())
                    .add("club_id", (c.getClub().getId() == null) ? "" : c.getClub().getId().toString())
                    .add("item_id", (c.getItem().getId() == null) ? "" : c.getItem().getId().toString())
                    .add("text", (c.getText()== null) ? "" : c.getText())
                    .add("date", (c.getSince()== null) ? "" : c.getSince().toString());

            
            jsonArrayBuilder.add(jsonObjectBuilder);
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return Response.ok(jsonArray.toString()).build();

    }
    
    @Override
    public Response getAllCommentsByItem(@PathParam("id") Long item_id) throws ServiceException {
        List<Comment> allComments = commentsService.getAllCommentsByItem(item_id);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Comment c : allComments) {
            jsonObjectBuilder
                    .add("id", (c.getId() == null) ? "" : c.getId().toString())
                    .add("user_id", (c.getUser().getId() == null) ? "" : c.getUser().getId().toString())
                    .add("user_email", (c.getUser().getEmail() == null) ? "" : c.getUser().getEmail())
                    .add("club_id", (c.getClub().getId() == null) ? "" : c.getClub().getId().toString())
                    .add("item_id", (c.getItem().getId() == null) ? "" : c.getItem().getId().toString())
                    .add("text", (c.getText()== null) ? "" : c.getText())
                    .add("date", (c.getSince()== null) ? "" : c.getSince().toString());

            
            jsonArrayBuilder.add(jsonObjectBuilder);
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return Response.ok(jsonArray.toString()).build();

    }

    @Override
    public Response newComment(@NotNull @FormParam("user_id") Long userId,
             @FormParam("club_id") Long clubId,
             @FormParam("item_id") Long itemId,
            @NotNull @NotEmpty @FormParam("text") String text) throws ServiceException {

        Long newComment = commentsService.newComment(userId, clubId, itemId, text);
        return Response.ok(newComment).build();

    }

    @Override
    public Response get(@PathParam("id") Long comment_id) throws ServiceException {
        Comment c = commentsService.getById(comment_id);
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder
                    .add("id", (c.getId() == null) ? "" : c.getId().toString())
                    .add("user_id", (c.getUser().getId() == null) ? "" : c.getUser().getId().toString())
                    .add("user_email", (c.getUser().getEmail() == null) ? "" : c.getUser().getEmail())
                    .add("club_id", (c.getClub().getId() == null) ? "" : c.getClub().getId().toString())
                    .add("item_id", (c.getItem().getId() == null) ? "" : c.getItem().getId().toString())
                    .add("text", (c.getText()== null) ? "" : c.getText())
                    .add("date", (c.getSince()== null) ? "" : c.getSince().toString());
        
        JsonObject build = jsonObjectBuilder.build();
        return Response.ok(build.toString()).build();

    }

    @Override
    public Response remove(Long comment_id) throws ServiceException {
        commentsService.removeComment(comment_id);
        return Response.ok().build();
    }

    
}
