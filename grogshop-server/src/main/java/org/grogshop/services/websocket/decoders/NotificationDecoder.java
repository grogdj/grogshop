/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.websocket.decoders;

import com.grogdj.grogshop.core.model.Notification;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author salaboy
 */
public class NotificationDecoder implements Decoder.Text<Notification> {

    @Override
    public Notification decode(String jsonMessage) throws DecodeException {

        JsonObject jsonObject = Json
                .createReader(new StringReader(jsonMessage)).readObject();
        Notification notification = new Notification(jsonObject.getString("userId"), jsonObject.getString("message"));
        notification.setId(jsonObject.getJsonNumber("notificationId").longValue());
        return notification;

    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            // Check if incoming message is valid JSON
            Json.createReader(new StringReader(jsonMessage)).readObject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig ec) {
        
    }

    @Override
    public void destroy() {
        
    }
}
