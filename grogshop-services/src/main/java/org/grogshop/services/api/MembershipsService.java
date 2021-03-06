/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import java.util.List;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
public interface MembershipsService {
    
    void createMembership(Long club_id, Long user_id) throws ServiceException;
    
    void cancelMembership(Long club_id, Long user_id) throws ServiceException;

    Long getMembersCount(Long club_id);

    List<Long> getAllMembers(Long club_id);

    List<Long> get(Long user_id);
}
