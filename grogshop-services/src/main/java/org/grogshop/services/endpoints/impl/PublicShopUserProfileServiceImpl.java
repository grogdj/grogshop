/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Profile;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.grogshop.services.api.ProfilesService;
import org.grogshop.services.endpoints.api.PublicShopUserProfileService;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
@Stateless
public class PublicShopUserProfileServiceImpl implements PublicShopUserProfileService {

    @Inject
    private ProfilesService profileService;

    private final static Logger log = Logger.getLogger(PublicShopUserProfileServiceImpl.class.getName());

    public PublicShopUserProfileServiceImpl() {

    }

    @Override
    public Response get(@PathParam("id") Long user_id) throws ServiceException {
        Profile p = profileService.getById(user_id);
        if (p == null) {
            throw new ServiceException("Profile for " + user_id + " doesn't exists");
        }
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        jsonObjBuilder.add("bio", (p.getIntroduction() == null) ? "" : p.getIntroduction());
        jsonObjBuilder.add("location", (p.getPostcode() == null) ? "" : p.getPostcode());
        jsonObjBuilder.add("username", (p.getRealname() == null) ? "" : p.getRealname());

        JsonObject jsonObj = jsonObjBuilder.build();
        return Response.ok(jsonObj.toString()).build();
    }

    @Override
    public Response getAvatar(@NotNull @PathParam("id") Long user_id) throws ServiceException {

        byte[] tmp = profileService.getAvatar(user_id);
        final byte[] avatar;
        if (tmp != null && tmp.length > 0) {
            log.info("avatar found");
            avatar = tmp;
            return Response.ok().entity(new StreamingOutput() {
                @Override
                public void write(OutputStream output)
                        throws IOException, WebApplicationException {
                    output.write(avatar);
                    output.flush();
                }
            }).build();
        } else {
            try {
                log.info("avatar not found");
                return Response.temporaryRedirect(new URI("../static/img/public-images/default-avatar.png")).build();
            } catch (URISyntaxException ex) {
                Logger.getLogger(PublicShopUserProfileServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return Response.serverError().build();

    }

}
