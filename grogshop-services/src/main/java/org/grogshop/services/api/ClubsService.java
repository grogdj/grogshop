/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import com.grogdj.grogshop.core.model.Club;
import java.util.List;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
public interface ClubsService {
    
    List<Club> getAllClubs();
    
    Long newClub(String name, String description, String category, List<String> interests, String founderEmail, String image) throws ServiceException;

    Club getById(Long club_id);
    
    
}
