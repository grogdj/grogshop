/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.ClubMembership;
import com.grogdj.grogshop.core.model.Tag;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.grogshop.services.api.ClubMembershipsService;
import org.grogshop.services.api.TagsService;

/**
 *
 * @author salaboy
 */
@Singleton
public class ClubMembershipsServiceImpl implements ClubMembershipsService {

    @Inject
    private TagsService tagsService;

    private Set<ClubMembership> memberships;

    public ClubMembershipsServiceImpl() {
        this.memberships = new HashSet<ClubMembership>();
    }

    public ClubMembership getClubMembership(Long clubMembershipId) {
        for (ClubMembership m : this.memberships) {
            if (m.getId().equals(clubMembershipId)) {
                return m;
            }
        }
        return null;
    }

    public List<ClubMembership> getClubMemberships(String userId) {
        List<ClubMembership> userMemberships = new ArrayList<ClubMembership>();
        for (ClubMembership m : memberships) {
            if (m.getUserId().equals(userId)) {
                userMemberships.add(m);
            }
        }
        return userMemberships;
    }

    public Long joinClub(String userId, ClubMembership clubMembership) {
        if (clubMembership != null) {
            clubMembership.setUserId(userId);
            for (Tag t : clubMembership.getTags()) {
                tagsService.newTag(t);
            }
            
            this.memberships.add(clubMembership);
            return clubMembership.getId();
        } else {
            System.out.println("> ClubMembership cannot be null :( ");
        }
        return -1l;
    }

    public void clearClubMemberships() {
        this.memberships.clear();
    }

    public ClubMembership removeClubMembership(Long clubId) {
        ClubMembership remove = null;
        for (ClubMembership m : this.memberships) {
            if (m.getId().equals(clubId)) {
                remove = m;
            }
        }
        if (remove != null) {
            this.memberships.remove(remove);
            return remove;
        }
        return null;
    }

    public void updateClubMembership(ClubMembership clubMembership) {
        if (clubMembership != null) {

            for (Tag t : clubMembership.getTags()) {
                tagsService.newTag(t);
            }
            this.memberships.add(clubMembership);
        } else {
            System.out.println("> ClubMembership cannot be null :( ");
        }

    }

}
