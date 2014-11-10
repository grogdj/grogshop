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

/**
 *
 * @author salaboy
 */
@ServerEndpoint("/shop")
public class NotificationsWSEndpoint {

    @Inject
    NotificationsService notificationService;
    
    @OnMessage
    public void onMessage(String message, Session client) {
        System.out.println("New Client here: "+message );
        notificationService.setActiveSessions(client.getOpenSessions());
    }
    
}
