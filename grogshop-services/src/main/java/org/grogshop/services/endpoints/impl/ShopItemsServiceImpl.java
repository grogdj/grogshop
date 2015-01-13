/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Item;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.apache.commons.io.IOUtils;
import org.grogshop.services.api.ItemsService;
import org.grogshop.services.endpoints.api.ShopItemsService;
import org.grogshop.services.exceptions.ServiceException;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Stateless
public class ShopItemsServiceImpl implements ShopItemsService {

    @Inject
    private ItemsService itemsService;
    
    public static final String UPLOADED_FILE_PARAMETER_NAME = "file";
    
    private final static Logger log = Logger.getLogger(ShopItemsServiceImpl.class.getName());

    @Override
    public Response getAllItems() {
        List<Item> allItems = itemsService.getAllItems();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Item i : allItems) {
            jsonObjectBuilder
                    .add("id", (i.getId() == null) ? "" : i.getId().toString())
                    .add("user_id", (i.getUserId()== null) ? "" : i.getUserId().toString())
                    .add("user_email", (i.getUserEmail()== null) ? "" : i.getUserEmail())
                    .add("club_id", (i.getClubId()== null) ? "" : i.getClubId().toString())
                    .add("name", (i.getName() == null) ? "" : i.getName())
                    .add("description", (i.getDescription() == null) ? "" : i.getDescription())
                    .add("price", (i.getPrice()== null) ? "" : i.getPrice().toString());
                    

            if (i.getTags() != null) {
                JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
                for (String s : i.getTags()) {
                    jsonArrayBuilderInterest.add(s);
                }
                jsonObjectBuilder.add("tags", jsonArrayBuilderInterest.build().toString());
            }
            jsonArrayBuilder.add(jsonObjectBuilder);
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return Response.ok(jsonArray.toString()).build();

    }
    
    @Override
    public Response getAllItemsByClub(@PathParam("id") Long club_id) {
        List<Item> allItems = itemsService.getAllItemsByClub(club_id);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Item i : allItems) {
            jsonObjectBuilder
                    .add("id", (i.getId() == null) ? "" : i.getId().toString())
                    .add("user_id", (i.getUserId()== null) ? "" : i.getUserId().toString())
                    .add("user_email", (i.getUserEmail()== null) ? "" : i.getUserEmail())
                    .add("club_id", (i.getClubId()== null) ? "" : i.getClubId().toString())
                    .add("name", (i.getName() == null) ? "" : i.getName())
                    .add("description", (i.getDescription() == null) ? "" : i.getDescription())
                    .add("price", (i.getPrice()== null) ? "" : i.getPrice().toString());

            if (i.getTags() != null) {
                JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
                for (String s : i.getTags()) {
                    jsonArrayBuilderInterest.add(s);
                }
                jsonObjectBuilder.add("tags", jsonArrayBuilderInterest.build().toString());
            }
            jsonArrayBuilder.add(jsonObjectBuilder);
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return Response.ok(jsonArray.toString()).build();

    }

    @Override
    public Response newItem(@NotNull @FormParam("user_id") Long userId,
            @NotNull @FormParam("club_id") Long clubId,
            @NotNull @NotEmpty @FormParam("name") String name,
            @NotNull @NotEmpty @FormParam("description") String description,
            @NotNull @NotEmpty @FormParam("tags") String tags,
            @NotNull @FormParam("price") String price) throws ServiceException {
        String[] interestArray = tags.split(",");
        List<String> interestsList = new ArrayList<String>();
        if (interestArray != null) {
            
            for (String s : interestArray) {
                interestsList.add(s);
            }
            

        }
        Long newItem = itemsService.newItem(userId, clubId, name, description, interestsList, new BigDecimal(price));
        return Response.ok(newItem).build();

    }

    @Override
    public Response get(@PathParam("id") Long item_id) throws ServiceException {
        Item i = itemsService.getById(item_id);
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("id", (i.getId() == null) ? "" : i.getId().toString())
                .add("user_id", (i.getUserId()== null) ? "" : i.getUserId().toString())
                .add("user_email", (i.getUserEmail()== null) ? "" : i.getUserEmail())
                .add("club_id", (i.getClubId()== null) ? "" : i.getClubId().toString())
                .add("name", (i.getName() == null) ? "" : i.getName())
                .add("description", (i.getDescription() == null) ? "" : i.getDescription())
                .add("price", (i.getPrice()== null) ? "" : i.getPrice().toString());
        if (i.getTags() != null) {
            JsonArrayBuilder jsonArrayBuilderInterest = Json.createArrayBuilder();
            for (String s : i.getTags()) {
                jsonArrayBuilderInterest.add(s);
            }
            jsonObjectBuilder.add("tags", jsonArrayBuilderInterest.build().toString());
        }
        JsonObject build = jsonObjectBuilder.build();
        return Response.ok(build.toString()).build();

    }
    
     @Override
    public Response uploadItemImage(@NotNull @PathParam("id") Long item_id, MultipartFormDataInput input) throws ServiceException {
        log.info(">>>> sit back - starting file upload for user_id..." + item_id);

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get(UPLOADED_FILE_PARAMETER_NAME);

        for (InputPart inputPart : inputParts) {
            MultivaluedMap<String, String> headers = inputPart.getHeaders();
            String filename = getFileName(headers);

            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                log.log(Level.INFO, ">>> File '''{'{0}'}''' has been read, size: #'{'{1}'}' bytes", new Object[]{filename, bytes.length});
                itemsService.updateItemImage(item_id, filename, bytes);

            } catch (IOException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        }
        return Response.ok().build();
    }

    @Override
    public Response getItemImage(@NotNull @PathParam("id") Long item_id) throws ServiceException {
        final byte[] avatar = itemsService.getItemImage(item_id);
        return Response.ok().entity(new StreamingOutput() {
            @Override
            public void write(OutputStream output)
                    throws IOException, WebApplicationException {
                output.write(avatar);
                output.flush();
            }
        }).build();
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
