/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import com.grogdj.grogshop.core.model.ClubMembership;
import java.util.List;

/**
 *
 * @author salaboy
 */
public interface ClubMembershipsService {

    ClubMembership getClubMembership(Long clubId);

    List<ClubMembership> getClubMemberships(String userId);

    Long joinClub(String userId, ClubMembership club);

    void updateClubMembership(ClubMembership club);
    
    void clearClubMemberships();

    ClubMembership removeClubMembership(Long clubId);

}
