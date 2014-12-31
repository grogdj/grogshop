/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.filters.auth;

import java.io.IOException;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.grogshop.services.filters.auth.GrogAuthenticator;
import org.grogshop.services.filters.auth.GrogHTTPHeaderNames;

/**
 *
 * @author grogdj
 */
@Provider
@PreMatching
public class AuthRESTRequestFilter implements ContainerRequestFilter {

    private final static Logger log = Logger.getLogger(AuthRESTRequestFilter.class.getName());

    @Inject
    GrogAuthenticator grogAuthenticator;

    @Override

    public void filter(ContainerRequestContext requestCtx) throws IOException {

        String path = requestCtx.getUriInfo().getPath();

        log.info("Filtering request path: " + path);

        // IMPORTANT!!! First, Acknowledge any pre-flight test from browsers for this case before validating the headers (CORS stuff)
        if (requestCtx.getRequest().getMethod().equals("OPTIONS")) {

            requestCtx.abortWith(Response.status(Response.Status.OK).build());

            return;

        }

        if (!path.startsWith("/auth/register")) {

            // Then check is the service key exists and is valid.
            String serviceKey = requestCtx.getHeaderString(GrogHTTPHeaderNames.SERVICE_KEY);

            if (!grogAuthenticator.isServiceKeyValid(serviceKey)) {

                // Kick anyone without a valid service key
                requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

                return;

            }

            // For any pther methods besides login, the authToken must be verified
            if (!path.startsWith("/auth/login")) {

                String authToken = requestCtx.getHeaderString(GrogHTTPHeaderNames.AUTH_TOKEN);

                // if it isn't valid, just kick them out.
                if (!grogAuthenticator.isAuthTokenValid(serviceKey, authToken)) {

                    requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

                }

            }
        }

    }

}