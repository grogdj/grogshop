/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.grogshop.services.endpoints.api.ShopUserProfileService;
import org.grogshop.services.filters.auth.GrogAuthenticator;
import org.grogshop.services.impl.UserServiceImpl;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 *
 * @author grogdj
 */

@Stateless
public class ShopUserProfileServiceImpl implements ShopUserProfileService{

    @Inject
    UserServiceImpl userService;

    @Inject
    GrogAuthenticator authenticator;
    
    
    public static final String UPLOADED_FILE_PARAMETER_NAME = "file";
    public static final String UPLOAD_DIR = "/tmp";
    private String data;
    

    public ShopUserProfileServiceImpl() {

    }
    
    
    
    @Override
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
