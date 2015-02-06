/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Notification;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.NotificationsService;
import org.grogshop.services.endpoints.api.ShopNotificationsService;
import org.grogshop.services.exceptions.ServiceException;
import org.hibernate.validator.constraints.NotEmpty;

@Stateless
public class ShopNotificationsServiceImpl implements ShopNotificationsService {

    @Inject
    private NotificationsService notificationsService;

    @Override
    public Response getAllNotificationsByUser(@PathParam("id") Long userId) throws ServiceException {
        List<Notification> allNotifications = notificationsService.getAllNotificationsByUser(userId);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (Notification n : allNotifications) {
            jsonObjectBuilder
                    .add("id", (n.getId() == null) ? "" : n.getId().toString())
                    .add("message", (n.getMessage() == null) ? "" : n.getMessage())
                    .add("action", (n.getAction() == null) ? "" : n.getAction())
                    .add("type", (n.getType() == null) ? "" : n.getType().toString())
                    .add("date", (n.getNotificationDate() == null) ? "" : n.getNotificationDate().toString());

            jsonArrayBuilder.add(jsonObjectBuilder);
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        return Response.ok(jsonArray.toString()).build();

    }

    @Override
    public Response newNotification(@NotNull @FormParam("user_id") Long userId,
            @NotNull @NotEmpty @FormParam("message") String message,
            @NotNull @NotEmpty @FormParam("action") String action,
            @NotNull @NotEmpty @FormParam("type") String type) throws ServiceException {

        Long newNotification = notificationsService.newNotification(userId, message, action, type);
        return Response.ok(newNotification).build();

    }

}
