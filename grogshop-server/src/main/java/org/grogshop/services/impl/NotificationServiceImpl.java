/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Matching;
import com.grogdj.grogshop.core.model.Notification;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.websocket.EncodeException;
import org.grogshop.services.api.NotificationsService;
import javax.websocket.Session;

/**
 *
 * @author salaboy
 */
@Singleton
public class NotificationServiceImpl implements NotificationsService {

    private Set<Session> activeSessions;

    @Override
    public void notifyUser(String userId, String message) {
        //@TODO: I need to use the userId to identify the correct session
        System.out.println(">>> Notification: \tUser: " + userId + " - Message: " + message);
        for (Session peer : activeSessions) {
            try {
                peer.getBasicRemote().sendObject(new Notification(userId, message));
            } catch (IOException ex) {
                Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EncodeException ex) {
                Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void notifyMatching(String listingUserId, String bidUserId, Matching matching) {
        //@TODO: I need to use the userId to identify the correct session
        System.out.println(">>> Matching \t Listing User: " + listingUserId + " Bid User: " + bidUserId + " - Matching: " + matching);
        for (Session peer : activeSessions) {
            try {
                peer.getBasicRemote().sendObject(matching);
            } catch (IOException ex) {
                Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EncodeException ex) {
                Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setActiveSessions(Set<Session> activeSessions) {
        this.activeSessions = activeSessions;
    }

}
