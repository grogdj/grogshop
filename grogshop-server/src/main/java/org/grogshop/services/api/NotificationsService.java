/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import java.util.Set;
import javax.websocket.Session;
/**
 *
 * @author salaboy
 */
public interface NotificationsService {
    void notifyUser(String userId, String message);
    void setActiveSessions(Set<Session> activeSessions);
}
