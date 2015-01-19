/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Interest;
import com.grogdj.grogshop.core.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.MembershipsService;
import org.grogshop.services.api.ClubsService;
import org.grogshop.services.api.InterestsService;
import org.grogshop.services.api.ItemsService;
import org.grogshop.services.api.ProfilesService;
import org.grogshop.services.api.UsersService;
import org.grogshop.services.endpoints.api.PublicShopInitService;
import org.grogshop.services.exceptions.ServiceException;
import org.grogshop.services.impl.InterestsServiceImpl;

//http://localhost:8080/grogshop-server/rest/public/app/init
@Stateless
public class PublicShopInitServiceImpl implements PublicShopInitService {

    @Inject
    private ClubsService clubsService;

    @Inject
    private UsersService usersService;

    @Inject
    private InterestsService interestsService;

    @Inject
    private ProfilesService profilesService;

    @Inject
    private ItemsService itemsService;

    @Inject
    private MembershipsService membershipsService;

    public Response initApplication() throws ServiceException {
        try {
            Long grogdjId = usersService.newUser(new User("grogdj@gmail.com", "asdasd"));
            Long ezeId = usersService.newUser(new User("eze@asd.asd", "123123"));

            interestsService.newInterest("food & drink", "foodAndDrink.jpg");
            interestsService.newInterest("music", "music.jpg");
            interestsService.newInterest("film", "film.jpg");
            interestsService.newInterest("fashion", "fashion.jpg");
            interestsService.newInterest("photography", "photography.jpg");
            interestsService.newInterest("literature", "literature.jpg");
            interestsService.newInterest("animals & nature", "animalsAndNature.jpg");
            interestsService.newInterest("tech & gadgets", "techAndGadgets.jpg");
            interestsService.newInterest("antique", "antique.jpg");
            interestsService.newInterest("extreme", "extreme.jpg");
            interestsService.newInterest("motors", "motors.jpg");
            interestsService.newInterest("dating", "dating.jpg");
            interestsService.newInterest("home & garden", "homeAndGarden.jpg");
            interestsService.newInterest("sports", "sports.jpg");
            interestsService.newInterest("travel", "travel.jpg");
            interestsService.newInterest("kids", "kids.jpg");
            interestsService.newInterest("baby", "baby.jpg");
            interestsService.newInterest("art & design", "artAndDesign.jpg");
            interestsService.newInterest("business", "business.jpg");
            interestsService.newInterest("wedding", "wedding.jpg");
            interestsService.newInterest("hot now", "hotNow.jpg");
            interestsService.newInterest("community", "community.jpg");
            interestsService.newInterest("gaming", "gaming.jpg");
            interestsService.newInterest("health & lifestyle", "healthAndLifestyle.jpg");


            profilesService.create(grogdjId);
            List<Interest> interests = new ArrayList<Interest>();
            interests.add(interestsService.get("food & drink"));
            interests.add(interestsService.get("antique"));
            profilesService.setInterests(grogdjId, interests);

            profilesService.create(ezeId);
            interests = new ArrayList<Interest>();
            interests.add(interestsService.get("food & drink"));
            interests.add(interestsService.get("antique"));
            interests.add(interestsService.get("music"));
            profilesService.setInterests(ezeId, interests);

            List<Long> foodAndDrinkClubsIds = initializeFoodAndDrinkClubs("food & drink", grogdjId, "foodAndDrink.jpg");

//            membershipsService.createMembership(cookingId, grogdjId);
//            membershipsService.createMembership(antiquesId, grogdjId);

        } catch (Exception ex) {
            Logger.getLogger(InterestsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }

    private List<Long> initializeFoodAndDrinkClubs(String clubInterest, Long userId, String image) throws ServiceException {
        List<Long> clubsIds = new ArrayList<Long>();
        List<String> tags = new ArrayList<String>();
        tags.add("food");
        tags.add("fun");
        tags.add("healty");
        Long recipesId = clubsService.newClub("recipes", "this is a new cooking club", clubInterest, tags, userId, image);
        clubsIds.add(recipesId);
        tags = new ArrayList<String>();
        tags.add("stars");
        tags.add("galaxy");
        tags.add("apollo");
        Long wineId = clubsService.newClub("wine", "wine club description", clubInterest, tags, userId, image);
        clubsIds.add(wineId);
        tags = new ArrayList<String>();
        tags.add("old");
        tags.add("classic");
        tags.add("cars");
        Long healthyId = clubsService.newClub("healthy", "healthy food club description", clubInterest, tags, userId, image);
        clubsIds.add(healthyId);
        tags = new ArrayList<String>();
        tags.add("puppies");
        tags.add("dogs");
        tags.add("cats");
        Long restaurantsId = clubsService.newClub("restaurants", "all pets allowed", clubInterest, tags, userId, image);
        clubsIds.add(restaurantsId);
        tags = new ArrayList<String>();
        tags.add("arcade");
        tags.add("play");
        tags.add("games");
        Long pubsId = clubsService.newClub("pubs", "for all the gamers", "video games", tags, userId, image);
        clubsIds.add(pubsId);
        tags = new ArrayList<String>();
        tags.add("graphic");
        tags.add("visual");
        tags.add("design");
        
        return clubsIds;
        
        
    }

}
