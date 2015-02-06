/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Notification;
import com.grogdj.grogshop.core.model.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.NotificationsService;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
@ApplicationScoped
public class NotificationServiceImpl implements NotificationsService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    private final static Logger log = Logger.getLogger(NotificationServiceImpl.class.getName());

    @Override
    public Long newNotification(Long userId, String message, String action, String type) throws ServiceException {
        User user = em.find(User.class, userId);
        if (user == null) {
            throw new ServiceException("User  doesn't exist: " + userId);
        }
        
       
        Notification notification = new Notification(user, message, action, Notification.NotificationType.valueOf(type));
        em.persist(notification);
        log.log(Level.INFO, "Item {0} created with id {1}", new Object[]{message, notification.getId()});
        
        return notification.getId();
    }

    @Override
    public List<Notification> getAllNotificationsByUser(Long userId) {
        return em.createNamedQuery("Notification.getAllByUser", Notification.class).setParameter("userId", userId).getResultList();
    }

//    private Set<Session> activeSessions;
//
//    @Override
//    public void notifyUser(String userId, String message, String type) {
//        //@TODO: I need to use the userId to identify the correct session
//        System.out.println(">>> Notification: \tUser: " + userId + " - Message: " + message);
//        for (Session peer : activeSessions) {
//            try {
//                peer.getBasicRemote().sendObject(new Notification(userId, message, type));
//            } catch (IOException ex) {
//                Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (EncodeException ex) {
//                Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//
//
//    public void setActiveSessions(Set<Session> activeSessions) {
//        this.activeSessions = activeSessions;
//    }
}
