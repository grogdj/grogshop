/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Profile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.grogshop.services.api.ProfileService;
import org.grogshop.services.endpoints.api.ShopUserProfileService;
import org.grogshop.services.exceptions.ServiceException;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 *
 * @author grogdj
 */
@Stateless
public class ShopUserProfileServiceImpl implements ShopUserProfileService {

    @Inject
    private ProfileService profileService;

    private final static Logger log = Logger.getLogger(ShopUserProfileServiceImpl.class.getName());

    public static final String UPLOADED_FILE_PARAMETER_NAME = "file";
    public static final String UPLOAD_DIR = "/tmp";

    public ShopUserProfileServiceImpl() {

    }

    @Override
    public Response get(@PathParam("id") Long user_id) throws ServiceException {
        Profile p = profileService.getById(user_id);
        if(p == null){
            throw new ServiceException("Profile for " + user_id + " doesn't exists");
        }
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        jsonObjBuilder.add("bio", (p.getIntroduction()==null)?"":p.getIntroduction());
        jsonObjBuilder.add("location", (p.getPostcode()==null)?"":p.getPostcode());
        jsonObjBuilder.add("username", (p.getRealname()==null)?"":p.getRealname());

        JsonObject jsonObj = jsonObjBuilder.build();
        return Response.ok(jsonObj.toString()).build();
    }

    @Override
    public Response exist(@NotNull @FormParam("user_id") Long user_id) throws ServiceException {
        return Response.ok(profileService.exist(user_id)).build();
    }

    @Override
    public Response create(@NotNull @FormParam("user_id") Long user_id) throws ServiceException {
        if (!profileService.exist(user_id)) {
            profileService.create(user_id);
            return Response.ok().build();
        }
        throw new ServiceException("Profile for " + user_id + " already exists");
    }
    
    @Override
    public Response update(@NotNull @PathParam("user_id") Long user_id, 
            @FormParam("username") String username, 
            @FormParam("location") String location, 
            @FormParam("bio") String bio ) throws ServiceException {
            profileService.update(user_id, username, location, bio);
            return Response.ok().build();
        
    }

    @Override
    public Response getInterests(@NotNull @PathParam("user_id") Long user_id) throws ServiceException {
        String interests = profileService.getInterests(user_id);
        String[] interestArray = interests.split(",");
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for(String s : interestArray){
            jsonArrayBuilder.add(s);
        }
        JsonArray build = jsonArrayBuilder.build();
        return Response.ok(build.toString()).build();
    }

    public Response setInterests(@NotNull @PathParam("user_id") Long user_id, @FormParam("interests") String interests) throws ServiceException {
        profileService.setInterests(user_id, interests);
        return Response.ok().build();  
    }
    
    @Override
    public Response uploadFile(@NotNull @PathParam("id") Long user_id, MultipartFormDataInput input) throws ServiceException {
        log.info(">>>> sit back - starting file upload for user_id..."+user_id);

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get(UPLOADED_FILE_PARAMETER_NAME);

        for (InputPart inputPart : inputParts) {
            MultivaluedMap<String, String> headers = inputPart.getHeaders();
            String filename = getFileName(headers);

            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                log.log(Level.INFO, ">>> File '''{'{0}'}''' has been read, size: #'{'{1}'}' bytes", new Object[]{filename, bytes.length});
                writeFile(bytes, "/tmp/" + filename);
            } catch (IOException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        }
        return Response.ok().build();
    }

    /**
     * Buil filename local to the server.
     *
     * @param filename
     * @return
     */
    private String getServerFilename(String filename) {
        return UPLOAD_DIR + "/" + filename;
    }

    private void writeFile(byte[] content, String filename) throws IOException {
        log.log(Level.INFO, ">>> writing {0} bytes to: {1}", new Object[]{content.length, filename});
        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();
        log.log(Level.INFO, ">>> writing complete: {0}", filename);
    }

    /**
     * Extract filename from HTTP heaeders.
     *
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
