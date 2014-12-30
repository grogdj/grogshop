/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.grogshop.services.filters.auth.GrogAuthenticator;
import org.grogshop.services.filters.auth.GrogHTTPHeaderNames;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 *
 * @author grogdj
 */
@Path("/auth")
@Stateless
public class ShopUserServiceImpl {

    @Inject
    UserServiceImpl userService;

    @Inject
    GrogAuthenticator authenticator;
    
    
    public static final String UPLOADED_FILE_PARAMETER_NAME = "file";
    public static final String UPLOAD_DIR = "/tmp";
    private String data;
    

    public ShopUserServiceImpl() {

    }
    
    

    @POST
    @Path("/register")
    @Produces({MediaType.APPLICATION_JSON})
    public String registerUser(@FormParam("email") String email, @FormParam("password") String password) {
        return userService.registerUser(new User(email, password));
    }

    @POST
    @Path("/newkey")
    @Produces({MediaType.APPLICATION_JSON})
    public String newKey(@FormParam("email") String email) {
        return userService.generateApplicationKey(email);
    }

    @GET
    @Path("/allkeys")
    @Produces({MediaType.APPLICATION_JSON})
    public List<String> getAllKeys(@QueryParam("email") String email) {
        return userService.getAllKeysByEmail(email);
    }
    
    @GET
    @Path("/loadkey")
    @Produces({MediaType.APPLICATION_JSON})
    public String getKey(@QueryParam("email") String email) {
        return userService.getAllKeysByEmail(email).get(0);
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
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

    
    
    
    @POST
    @Path("/logout")
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

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    
    

    @Path("/upload")
    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadFile(MultipartFormDataInput input) {
      System.out.println(">>>> sit back - starting file upload...");

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        for(String k: uploadForm.keySet()){
            System.out.println("Key: "+k);
        }
        List<InputPart> inputParts = uploadForm.get(UPLOADED_FILE_PARAMETER_NAME);

        for (InputPart inputPart : inputParts){
            MultivaluedMap<String, String> headers = inputPart.getHeaders();
            String filename = getFileName(headers);

            try{
                InputStream inputStream = inputPart.getBody(InputStream.class,null);

                byte [] bytes = IOUtils.toByteArray(inputStream);

                System.out.println(">>> File '{"+filename+"}' has been read, size: #{"+ bytes.length+"} bytes");
                writeFile(bytes, "/tmp/" + filename);
            } catch (IOException e) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        }
        return Response.status(Response.Status.OK).build();
    }

    /**
     * Buil filename local to the server.
     * @param filename
     * @return
     */
    private String getServerFilename(String filename) {
        return UPLOAD_DIR + "/"+filename;
    }

    private void writeFile(byte[] content, String filename) throws IOException {
        System.out.println(">>> writing "+content.length+" bytes to: "+filename );
        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();
        System.out.println(">>> writing complete: "+filename );
    }

    /**
     * Extract filename from HTTP heaeders.
     * @param headers
     * @return
     */
    private String getFileName(MultivaluedMap<String, String> headers) {
        String[] contentDisposition = headers.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = sanitizeFilename(name[1]);
                return finalFileName;
            }
        }
        return "unknown";
    }

    private String sanitizeFilename(String s) {
        return s.trim().replaceAll("\"", "");
    }

    
    
    
}
