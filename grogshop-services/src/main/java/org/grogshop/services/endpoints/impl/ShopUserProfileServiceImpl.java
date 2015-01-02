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
    
    private final static Logger log =  Logger.getLogger( ShopUserProfileServiceImpl.class.getName() );

    public static final String UPLOADED_FILE_PARAMETER_NAME = "file";
    public static final String UPLOAD_DIR = "/tmp";

    public ShopUserProfileServiceImpl() {

    }

    @Override
    public Response exist(String email) throws ServiceException {
        return Response.ok(profileService.exist(email)).build();
    }

    @Override
    public Response newProfile(String email) throws ServiceException {
        if (!profileService.exist(email)) {
            profileService.newProfile(new Profile(email));
            return Response.ok().build();
        }
        throw new ServiceException("Profile for " + email + " already exists");
    }

    @Override
    public Response uploadFile(MultipartFormDataInput input) throws ServiceException {
        log.info(">>>> sit back - starting file upload...");

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
