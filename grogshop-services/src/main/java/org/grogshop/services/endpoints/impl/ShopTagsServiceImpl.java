/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Tag;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.TagsService;
import org.grogshop.services.endpoints.api.ShopTagsService;
import org.grogshop.services.exceptions.ServiceException;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Stateless
public class ShopTagsServiceImpl implements ShopTagsService {

    @Inject
    private TagsService tagsService;

    @Override
    public List<Tag> getAllTags() {
        return tagsService.getAllTags();
    }

    @Override
    public Response newTag(@NotNull @Email @NotEmpty @FormParam("tag") String tag) throws ServiceException {
        tagsService.newTag(tag);

        return Response.ok().build();
    }

}
