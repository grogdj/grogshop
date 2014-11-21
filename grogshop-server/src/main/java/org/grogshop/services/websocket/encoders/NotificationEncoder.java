/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.websocket.encoders;

import com.grogdj.grogshop.core.model.Notification;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author salaboy
 */
public class NotificationEncoder implements Encoder.Text<Notification> {

    @Override
    public String encode(Notification notification) throws EncodeException {

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("notificationId", notification.getId())
                .add("userId", notification.getUserId())
                .add("message", notification.getMessage())
                .add("type", notification.getType()).build();
        return jsonObject.toString();

    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("MessageEncoder - init method called");
    }

    @Override
    public void destroy() {
        System.out.println("MessageEncoder - destroy method called");
    }

}
