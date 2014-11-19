/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.websocket;

import javax.inject.Inject;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.grogshop.services.api.NotificationsService;
import org.grogshop.services.websocket.decoders.MatchingDecoder;
import org.grogshop.services.websocket.decoders.NotificationDecoder;
import org.grogshop.services.websocket.encoders.MatchingEncoder;
import org.grogshop.services.websocket.encoders.NotificationEncoder;

/**
 *
 * @author salaboy
 */
@ServerEndpoint( value = "/shop", 
        encoders = {NotificationEncoder.class, MatchingEncoder.class}, 
        decoders = {NotificationDecoder.class, MatchingDecoder.class}
)
public class NotificationsWSEndpoint {

    @Inject
    NotificationsService notificationService;

    @OnMessage
    public void onMessage(String message, Session client) {
        notificationService.setActiveSessions(client.getOpenSessions());
    }

}
