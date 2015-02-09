/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.websocket;

import java.util.List;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.grogshop.services.api.NotificationsService;
import org.grogshop.services.exceptions.ServiceException;
import org.grogshop.services.websocket.decoders.NotificationDecoder;
import org.grogshop.services.websocket.encoders.NotificationEncoder;

/**
 *
 * @author grogdj
 */
@ServerEndpoint(value = "/shop",
        encoders = {NotificationEncoder.class},
        decoders = {NotificationDecoder.class}
)
public class NotificationsWSEndpoint {

    @Inject
    NotificationsService notificationService;

    @OnOpen
    public void onOpen(Session client) {
        List<String> emails = client.getRequestParameterMap().get("email");
        System.out.println("OnOpen  Web Socket: " + emails.get(0));
        notificationService.addNewSession(emails.get(0), client);
    }

    @OnMessage
    public void onMessage(String message, Session client) {
        System.out.println("OnMessage  Web Socket: " + message);
    }

    @OnClose
    public void onClose(Session client) throws ServiceException {
        System.out.println("OnClose  Web Socket: ");
        notificationService.removeSession(client);

    }

}
