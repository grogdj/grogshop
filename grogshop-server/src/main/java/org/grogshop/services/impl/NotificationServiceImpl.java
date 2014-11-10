/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import org.grogshop.services.api.NotificationsService;
import javax.websocket.Session;
/**
 *
 * @author salaboy
 */
@Singleton
public class NotificationServiceImpl implements NotificationsService{

    private Set<Session> activeSessions;
    
    @Override
    public void notifyUser(String userId, String message) {
        System.out.println(">>> \tUser: "+userId  + " - Message: "+ message);
        for (Session peer : activeSessions) {
            try {
                peer.getBasicRemote().sendText(">>> \tUser: "+userId  + " - Message: "+ message);
            } catch (IOException ex) {
                Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setActiveSessions(Set<Session> activeSessions){
        this.activeSessions = activeSessions;
    }
    
}
