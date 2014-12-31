/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.User;
import java.security.GeneralSecurityException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.UserService;
import org.grogshop.services.endpoints.api.ShopAuthenticationService;
import org.grogshop.services.filters.auth.GrogAuthenticator;
import org.grogshop.services.filters.auth.GrogHTTPHeaderNames;

/**
 *
 * @author grogdj
 */
@Stateless
public class ShopAuthenticationServiceImpl implements ShopAuthenticationService {

    @Inject
    UserService userService;

    @Inject
    GrogAuthenticator authenticator;

    public ShopAuthenticationServiceImpl() {

    }

    @Override
    public String registerUser(@FormParam("email") String email, @FormParam("password") String password) {
        return userService.registerUser(new User(email, password));
    }

    @Override
    public Response login(
            @Context HttpHeaders httpHeaders,
            @FormParam("email") String email,
            @FormParam("password") String password) {

        String serviceKey = httpHeaders.getHeaderString(GrogHTTPHeaderNames.SERVICE_KEY);

        try {
            String authToken = authenticator.login(serviceKey, email, password);

            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add("email", email);
            jsonObjBuilder.add("service_key", serviceKey);
            jsonObjBuilder.add("auth_token", authToken);

            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder(Response.Status.OK).entity(jsonObj.toString()).build();

        } catch (final LoginException ex) {

            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();

            jsonObjBuilder.add("message", "Problem matching service key, username and password");

            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();

        }

    }

    @Override
    public Response logout(
            @Context HttpHeaders httpHeaders) {

        try {

            String serviceKey = httpHeaders.getHeaderString(GrogHTTPHeaderNames.SERVICE_KEY);

            String authToken = httpHeaders.getHeaderString(GrogHTTPHeaderNames.AUTH_TOKEN);

            authenticator.logout(serviceKey, authToken);

            return getNoCacheResponseBuilder(Response.Status.NO_CONTENT).build();

        } catch (final GeneralSecurityException ex) {

            return getNoCacheResponseBuilder(Response.Status.INTERNAL_SERVER_ERROR).build();

        }

    }

    private Response.ResponseBuilder getNoCacheResponseBuilder(Response.Status status) {

        CacheControl cc = new CacheControl();

        cc.setNoCache(true);

        cc.setMaxAge(-1);

        cc.setMustRevalidate(true);

        return Response.status(status).cacheControl(cc);

    }

}
