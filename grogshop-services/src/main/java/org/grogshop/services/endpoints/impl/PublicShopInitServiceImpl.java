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

            List<Long> foodAndDrinkClubsIds = initializeFoodAndDrinkClubs("food & drink", grogdjId, "foodAndDrink/");
            List<Long> travelClubsIds = initializeTravelClubs("travel", grogdjId, "travel/");
            List<Long> sportsClubsIds = initializeSportsClubs("sports", grogdjId, "sports/");

//            membershipsService.createMembership(cookingId, grogdjId);
//            membershipsService.createMembership(antiquesId, grogdjId);

        } catch (Exception ex) {
            Logger.getLogger(InterestsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    private List<Long> initializeTravelClubs(String clubInterest, Long userId, String imageDir) throws ServiceException {
        List<Long> clubsIds = new ArrayList<Long>();
        List<String> tags = new ArrayList<String>();
        tags.add("tag here");
        Long luxuryId = clubsService.newClub("luxury", "description here", clubInterest, tags, userId, imageDir + "luxury" + ".jpg");
        clubsIds.add(luxuryId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long dealsId = clubsService.newClub("deals", "description here", clubInterest, tags, userId, imageDir  + "deals" + ".jpg");
        clubsIds.add(dealsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long campingId = clubsService.newClub("camping", "description here", clubInterest, tags, userId, imageDir  + "camping" + ".jpg");
        clubsIds.add(campingId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long cityId = clubsService.newClub("city", "description here", clubInterest, tags, userId, imageDir + "city" + ".jpg");
        clubsIds.add(cityId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long sunAndBeachId = clubsService.newClub("sun & beach", "description here", clubInterest, tags, userId, imageDir + "sunAndBeach" + ".jpg");
        clubsIds.add(sunAndBeachId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long familyId = clubsService.newClub("family", "description here", clubInterest, tags, userId, imageDir + "family" + ".jpg");
        clubsIds.add(familyId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long over55sId = clubsService.newClub("over 55s", "description here", clubInterest, tags, userId, imageDir   + "over55s" + ".jpg");
        clubsIds.add(over55sId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long romanceId = clubsService.newClub("romance", "description here", clubInterest, tags, userId, imageDir  + "romance" + ".jpg");
        clubsIds.add(romanceId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long selfCateringId = clubsService.newClub("self catering", "description here", clubInterest, tags, userId, imageDir  + "selfCatering" + ".jpg");
        clubsIds.add(selfCateringId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long activeId = clubsService.newClub("active", "description here", clubInterest, tags, userId, imageDir  + "active" + ".jpg");
        clubsIds.add(activeId);
         tags = new ArrayList<String>();
        tags.add("tag here");
        Long backpackingId = clubsService.newClub("backpacking", "description here", clubInterest, tags, userId, imageDir  + "backpacking" + ".jpg");
        clubsIds.add(backpackingId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long USAId = clubsService.newClub("USA", "description here", clubInterest, tags, userId, imageDir  + "USA" + ".jpg");
        clubsIds.add(USAId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long australiaId = clubsService.newClub("australia", "description here", clubInterest, tags, userId, imageDir + "australia" + ".jpg");
        clubsIds.add(australiaId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long asiaId = clubsService.newClub("asia", "description here", clubInterest, tags, userId, imageDir  + "asia" + ".jpg");
        clubsIds.add(asiaId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long africaId = clubsService.newClub("africa", "description here", clubInterest, tags, userId, imageDir  + "asia" + ".jpg");
        clubsIds.add(africaId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long southAmericaId = clubsService.newClub("south america", "description here", clubInterest, tags, userId, imageDir  + "southAmerica" + ".jpg");
        clubsIds.add(southAmericaId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long europeId = clubsService.newClub("europe", "description here", clubInterest, tags, userId, imageDir + "europe" + ".jpg");
        clubsIds.add(europeId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long walkingAndHikingId = clubsService.newClub("walking & hiking", "description here", clubInterest, tags, userId, imageDir  + "walkingAndHiking" + ".jpg");
        clubsIds.add(walkingAndHikingId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long snowAndSkiId = clubsService.newClub("snow & ski", "description here", clubInterest, tags, userId, imageDir  + "snowAndSki" + ".jpg");
        clubsIds.add(snowAndSkiId);
        
        
        return clubsIds;
    }
    
    private List<Long> initializeSportsClubs(String clubInterest, Long userId, String imageDir) throws ServiceException {
        List<Long> clubsIds = new ArrayList<Long>();
        List<String> tags = new ArrayList<String>();
        tags.add("tag here");
        Long eventsId = clubsService.newClub("events", "description here", clubInterest, tags, userId, imageDir + "events" + ".jpg");
        clubsIds.add(eventsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long soccerId = clubsService.newClub("soccer", "description here", clubInterest, tags, userId, imageDir + "soccer" + ".jpg");
        clubsIds.add(soccerId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long tennisId = clubsService.newClub("tennis", "description here", clubInterest, tags, userId, imageDir + "tennis" + ".jpg");
        clubsIds.add(tennisId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long rugbyId = clubsService.newClub("rugby", "description here", clubInterest, tags, userId, imageDir + "rugby" + ".jpg");
        clubsIds.add(rugbyId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long americanFootballId = clubsService.newClub("american football", "description here", clubInterest, tags, userId, imageDir + "americanFootball" + ".jpg");
        clubsIds.add(americanFootballId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long basketballId = clubsService.newClub("basketball", "description here", clubInterest, tags, userId, imageDir + "basketball" + ".jpg");
        clubsIds.add(basketballId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long baseballId = clubsService.newClub("baseball", "description here", clubInterest, tags, userId, imageDir + "baseball" + ".jpg");
        clubsIds.add(baseballId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long golfId = clubsService.newClub("golf", "description here", clubInterest, tags, userId, imageDir + "golf" + ".jpg");
        clubsIds.add(golfId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long swimmingId = clubsService.newClub("swimming", "description here", clubInterest, tags, userId, imageDir + "swimming" + ".jpg");
        clubsIds.add(swimmingId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long winterSportsId = clubsService.newClub("winter sports", "description here", clubInterest, tags, userId, imageDir + "winterSports" + ".jpg");
        clubsIds.add(winterSportsId);
        
        return clubsIds;
    }

    private List<Long> initializeFoodAndDrinkClubs(String clubInterest, Long userId, String imageDir) throws ServiceException {
        List<Long> clubsIds = new ArrayList<Long>();
        List<String> tags = new ArrayList<String>();
        tags.add("tag here");
        Long recipesId = clubsService.newClub("recipes", "description here", clubInterest, tags, userId, imageDir + "recipes" + ".jpg");
        clubsIds.add(recipesId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long wineId = clubsService.newClub("wine", "description here", clubInterest, tags, userId, imageDir + "wine" + ".jpg");
        clubsIds.add(wineId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long healthyId = clubsService.newClub("healthy", "description here", clubInterest, tags, userId, imageDir + "healthy" + ".jpg");
        clubsIds.add(healthyId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long restaurantsId = clubsService.newClub("restaurants", "description here", clubInterest, tags, userId, imageDir + "restaurants" + ".jpg");
        clubsIds.add(restaurantsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long pubsId = clubsService.newClub("pubs", "description here", clubInterest, tags, userId, imageDir + "pubs" + ".jpg" );
        clubsIds.add(pubsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long cocktailsId = clubsService.newClub("cocktails", "description here", clubInterest, tags, userId, imageDir + "cocktails" + ".jpg");
        clubsIds.add(cocktailsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long beerId = clubsService.newClub("beer", "description here", clubInterest, tags, userId, imageDir + "beer" + ".jpg");
        clubsIds.add(beerId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long asianId = clubsService.newClub("asian", "description here", clubInterest, tags, userId, imageDir + "asian" + ".jpg");
        clubsIds.add(asianId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long indianId = clubsService.newClub("indian", "description here", clubInterest, tags, userId, imageDir + "indian" + ".jpg");
        clubsIds.add(indianId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long mediterraneanId = clubsService.newClub("mediterranean", "description here", clubInterest, tags, userId, imageDir + "mediterranean" + ".jpg");
        clubsIds.add(mediterraneanId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long frenchId = clubsService.newClub("french", "description here", clubInterest, tags, userId, imageDir + "french" + ".jpg");
        clubsIds.add(frenchId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long fishId = clubsService.newClub("fish", "description here", clubInterest, tags, userId, imageDir + "fish" + ".jpg");
        clubsIds.add(fishId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long vegetarianId = clubsService.newClub("vegetarian", "description here", clubInterest, tags, userId, imageDir + "vegetarian" + ".jpg");
        clubsIds.add(vegetarianId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long freeFromId = clubsService.newClub("free from", "description here", clubInterest, tags, userId, imageDir + "freeFrom" + ".jpg");
        clubsIds.add(freeFromId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long coffeeId = clubsService.newClub("coffee", "description here", clubInterest, tags, userId, imageDir + "coffee" + ".jpg");
        clubsIds.add(coffeeId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long teaId = clubsService.newClub("tea", "description here", clubInterest, tags, userId, imageDir + "tea" + ".jpg");
        clubsIds.add(teaId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long spiritsId = clubsService.newClub("spirits", "description here", clubInterest, tags, userId, imageDir + "spirits" + ".jpg");
        clubsIds.add(spiritsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long desertsId = clubsService.newClub("deserts", "description here", clubInterest, tags, userId, imageDir + "deserts" + ".jpg");
        clubsIds.add(desertsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long sportsFoodId = clubsService.newClub("sports food", "description here", clubInterest, tags, userId, imageDir+ "sportsFood" + ".jpg" );
        clubsIds.add(sportsFoodId);
        
        
        
        return clubsIds;
        
        
    }

}
