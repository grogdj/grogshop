/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.MatchingsService;
import org.grogshop.services.endpoints.api.ShopMatchingsService;
import org.grogshop.services.exceptions.ServiceException;

@Stateless
public class ShopMatchingsServiceImpl implements ShopMatchingsService {

    @Inject
    private MatchingsService matchingsService;

   

    private final static Logger log = Logger.getLogger(ShopMatchingsServiceImpl.class.getName());

    public Response getAllMatchings() throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Response getAllMatchingsByItem(Long itemId) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Response get(Long item_id) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

}
