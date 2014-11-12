/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Listing;
import com.grogdj.grogshop.core.model.Tag;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.TagsService;


@Path("/tags")
public class ShopTagsServiceImpl {
    @Inject
    private TagsService tagsService;
    
    @GET
    @Path("/all")
    @Produces({"application/json"})
    public List<Tag> getAllTags(){
        return tagsService.getAllTags();
    }
    
    @POST
    @Path("/new")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Response newTag(Tag tag) {
        tagsService.newTag(tag);
        
        return Response.ok().build();
    }

    @GET
    @Path("/clear")
    public void clearTags() {
        tagsService.clearTags();
    }
}
