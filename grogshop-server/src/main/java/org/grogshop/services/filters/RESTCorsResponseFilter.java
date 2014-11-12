/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.filters;

import java.io.IOException;

import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;

import javax.ws.rs.container.ContainerResponseContext;

import javax.ws.rs.container.ContainerResponseFilter;

import javax.ws.rs.container.PreMatching;

import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class RESTCorsResponseFilter implements ContainerResponseFilter {

    private final static Logger log = Logger.getLogger(RESTCorsResponseFilter.class.getName());

    @Override

    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {

        log.info("Executing REST response filter");

        responseCtx.getHeaders().add("Access-Control-Allow-Origin", "*");

        responseCtx.getHeaders().add("Access-Control-Allow-Credentials", "true");

        responseCtx.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");

    }

}
