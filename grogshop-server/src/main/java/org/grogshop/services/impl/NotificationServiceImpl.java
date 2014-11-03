/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import javax.inject.Singleton;
import org.grogshop.services.api.NotificationsService;

/**
 *
 * @author salaboy
 */
@Singleton
public class NotificationServiceImpl implements NotificationsService{

    @Override
    public void notifyUser(String userId, String message) {
        System.out.println(">>> \tUser: "+userId  + " - Message: "+ message);
    }
    
}
