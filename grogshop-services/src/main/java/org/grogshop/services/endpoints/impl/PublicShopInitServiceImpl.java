/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import com.grogdj.grogshop.core.model.Interest;
import com.grogdj.grogshop.core.model.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    private String server_url = "localhost";

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
            interests.add(interestsService.get("sports"));
            interests.add(interestsService.get("antique"));
            profilesService.setInterests(grogdjId, interests);

            profilesService.create(ezeId);
            interests = new ArrayList<Interest>();
            interests.add(interestsService.get("sports"));
            interests.add(interestsService.get("antique"));
            interests.add(interestsService.get("music"));
            profilesService.setInterests(ezeId, interests);

            Map<String, Long> foodAndDrinkClubsMap = initializeFoodAndDrinkClubs("food & drink", grogdjId, "foodAndDrink/");
            Map<String, Long> travelClubsMap = initializeTravelClubs("travel", grogdjId, "travel/");
            Map<String, Long> sportsClubsMap = initializeSportsClubs("sports", grogdjId, "sports/");
            
            
            Long soccerId = sportsClubsMap.get("soccer");
            List<String> itemTags = new ArrayList<String>();
            itemTags.add("sports");
            itemTags.add("soccer");
            itemsService.newItem(grogdjId, soccerId,"POST", "Diadora Boots", "Buy Diadora Soccer Boots", 
                    itemTags, new BigDecimal(100), new BigDecimal(100),"http://"+server_url+"/static/img/public-images/sports/soccer/"+"buyDiadoraSoccerBoot"+".jpg");
            itemsService.newItem(grogdjId, soccerId,"POST", "Umbro Boots (Size 8)", "Buy Umbro Soccer Boot (Size 8)", 
                    itemTags, new BigDecimal(100),new BigDecimal(100), "http://"+server_url+"/static/img/public-images/sports/soccer/"+"buyFootballBootsUmbroSize8"+".jpg");
            itemsService.newItem(grogdjId, soccerId,"POST", "Fuse Ball table", "For sale a brand new Fuse Ball Table ", 
                    itemTags, new BigDecimal(100),new BigDecimal(100), "http://"+server_url+"/static/img/public-images/sports/soccer/"+"buyFuseballTable"+".jpg");
            itemsService.newItem(grogdjId, soccerId,"POST", "Jako Soccer Ball", "Buy a Jacko Soccer Ball", 
                    itemTags, new BigDecimal(100),new BigDecimal(100), "http://"+server_url+"/static/img/public-images/sports/soccer/"+"buyJakoSoccerBall"+".jpg");
            itemsService.newItem(grogdjId, soccerId,"POST", "Real Madrid Shirt", "Buy a Real Madrid Shirt", 
                    itemTags, new BigDecimal(100),new BigDecimal(100), "http://"+server_url+"/static/img/public-images/sports/soccer/"+"buyRealMadridShirt"+".jpg");
            itemsService.newItem(grogdjId, soccerId,"POST", "Medium England Shirt", "For sale a Medium Retro England Shirt", 
                    itemTags, new BigDecimal(100),new BigDecimal(100), "http://"+server_url+"/static/img/public-images/sports/soccer/"+"buyRetroEnglandFootballShirtMedium"+".jpg");
            itemsService.newItem(grogdjId, soccerId,"POST", "Soccer Ball", "Used Soccer Ball", 
                    itemTags, new BigDecimal(100),new BigDecimal(100), "http://"+server_url+"/static/img/public-images/sports/soccer/"+"buySoccerBall"+".jpg");
            itemsService.newItem(grogdjId, soccerId,"POST", "Real Madrid Stadium Tour Tickets", "For sale a couple of Real Madrid Stadium Tour Tickets", 
                    itemTags, new BigDecimal(100),new BigDecimal(100), "http://"+server_url+"/static/img/public-images/sports/soccer/"+"buyStadiumTourTicketsRealMadrid"+".jpg");
            itemsService.newItem(grogdjId, soccerId, "POST","New Players Required ", "for a 5 aside team match in Acton", 
                    itemTags, new BigDecimal(100),new BigDecimal(100), "http://"+server_url+"/static/img/public-images/sports/soccer/"+"soccer5AsideTeamNeedsNewPlayers"+".jpg");
            
//            membershipsService.createMembership(cookingId, grogdjId);
//            membershipsService.createMembership(antiquesId, grogdjId);

        } catch (Exception ex) {
            Logger.getLogger(InterestsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    private Map<String, Long> initializeTravelClubs(String clubInterest, Long userId, String imageDir) throws ServiceException {
        Map<String, Long> clubsMap = new HashMap<String, Long>();
        List<String> tags = new ArrayList<String>();
        tags.add("tag here");
        Long luxuryId = clubsService.newClub("luxury", "description here", clubInterest, tags, userId, imageDir + "luxury" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("luxury", luxuryId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long dealsId = clubsService.newClub("deals", "description here", clubInterest, tags, userId, imageDir  + "deals" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("deals", dealsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long campingId = clubsService.newClub("camping", "description here", clubInterest, tags, userId, imageDir  + "camping" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("camping", campingId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long cityId = clubsService.newClub("city", "description here", clubInterest, tags, userId, imageDir + "city" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("city", cityId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long sunAndBeachId = clubsService.newClub("sun & beach", "description here", clubInterest, tags, userId, imageDir + "sunAndBeach" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("sun & beach", sunAndBeachId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long familyId = clubsService.newClub("family", "description here", clubInterest, tags, userId, imageDir + "family" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("family", familyId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long over55sId = clubsService.newClub("over 55s", "description here", clubInterest, tags, userId, imageDir   + "over55s" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("over 55s", over55sId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long romanceId = clubsService.newClub("romance", "description here", clubInterest, tags, userId, imageDir  + "romance" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("romance", romanceId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long selfCateringId = clubsService.newClub("self catering", "description here", clubInterest, tags, userId, imageDir  + "selfCatering" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("self catering", selfCateringId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long activeId = clubsService.newClub("active", "description here", clubInterest, tags, userId, imageDir  + "active" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("active", activeId);
         tags = new ArrayList<String>();
        tags.add("tag here");
        Long backpackingId = clubsService.newClub("backpacking", "description here", clubInterest, tags, userId, imageDir  + "backpacking" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("backpacking", backpackingId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long USAId = clubsService.newClub("USA", "description here", clubInterest, tags, userId, imageDir  + "USA" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("USA", USAId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long australiaId = clubsService.newClub("australia", "description here", clubInterest, tags, userId, imageDir + "australia" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("australia", australiaId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long asiaId = clubsService.newClub("asia", "description here", clubInterest, tags, userId, imageDir  + "asia" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("asia", asiaId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long africaId = clubsService.newClub("africa", "description here", clubInterest, tags, userId, imageDir  + "africa" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("africa", africaId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long southAmericaId = clubsService.newClub("south america", "description here", clubInterest, tags, userId, imageDir  + "southAmerica" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("south america", southAmericaId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long europeId = clubsService.newClub("europe", "description here", clubInterest, tags, userId, imageDir + "europe" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("europe", europeId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long walkingAndHikingId = clubsService.newClub("walking & hiking", "description here", clubInterest, tags, userId, imageDir  + "walkingAndHiking" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("walking & hiking", walkingAndHikingId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long snowAndSkiId = clubsService.newClub("snow & ski", "description here", clubInterest, tags, userId, imageDir  + "snowAndSki" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("snow & ski", snowAndSkiId);
        
        
        return clubsMap;
    }
    
    private Map<String, Long> initializeSportsClubs(String clubInterest, Long userId, String imageDir) throws ServiceException {
        Map<String, Long> clubsMap = new HashMap<String, Long>();
        List<String> tags = new ArrayList<String>();
        tags.add("tag here");
        Long eventsId = clubsService.newClub("events", "description here", clubInterest, tags, userId, imageDir + "events" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("events", eventsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long soccerId = clubsService.newClub("soccer", "description here", clubInterest, tags, userId, imageDir + "soccer" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("soccer", soccerId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long tennisId = clubsService.newClub("tennis", "description here", clubInterest, tags, userId, imageDir + "tennis" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("tennis",tennisId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long rugbyId = clubsService.newClub("rugby", "description here", clubInterest, tags, userId, imageDir + "rugby" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("rugby", rugbyId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long americanFootballId = clubsService.newClub("american football", "description here", clubInterest, tags, userId, imageDir + "americanFootball" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("american football", americanFootballId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long basketballId = clubsService.newClub("basketball", "description here", clubInterest, tags, userId, imageDir + "basketball" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("basketball", basketballId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long baseballId = clubsService.newClub("baseball", "description here", clubInterest, tags, userId, imageDir + "baseball" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("baseball", baseballId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long golfId = clubsService.newClub("golf", "description here", clubInterest, tags, userId, imageDir + "golf" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("golf", golfId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long swimmingId = clubsService.newClub("swimming", "description here", clubInterest, tags, userId, imageDir + "swimming" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("swimming", swimmingId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long winterSportsId = clubsService.newClub("winter sports", "description here", clubInterest, tags, userId, imageDir + "winterSports" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("winter sports", winterSportsId);
        
        return clubsMap;
    }

    private Map<String, Long> initializeFoodAndDrinkClubs(String clubInterest, Long userId, String imageDir) throws ServiceException {
        Map<String, Long> clubsMap = new HashMap<String, Long>();
        List<String> tags = new ArrayList<String>();
        tags.add("tag here");
        Long recipesId = clubsService.newClub("recipes", "description here", clubInterest, tags, userId, imageDir + "recipes" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("recipes", recipesId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long wineId = clubsService.newClub("wine", "description here", clubInterest, tags, userId, imageDir + "wine" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("wine", wineId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long healthyId = clubsService.newClub("healthy", "description here", clubInterest, tags, userId, imageDir + "healthy" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("healthy", healthyId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long restaurantsId = clubsService.newClub("restaurants", "description here", clubInterest, tags, userId, imageDir + "restaurants" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("restaurants", restaurantsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long pubsId = clubsService.newClub("pubs", "description here", clubInterest, tags, userId, imageDir + "pubs" + ".jpg" , 51.5033630,-0.1276250);
        clubsMap.put("pubs", pubsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long cocktailsId = clubsService.newClub("cocktails", "description here", clubInterest, tags, userId, imageDir + "cocktails" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("cocktails", cocktailsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long beerId = clubsService.newClub("beer", "description here", clubInterest, tags, userId, imageDir + "beer" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("beer", beerId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long asianId = clubsService.newClub("asian", "description here", clubInterest, tags, userId, imageDir + "asian" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("asian", asianId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long indianId = clubsService.newClub("indian", "description here", clubInterest, tags, userId, imageDir + "indian" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("indian", indianId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long mediterraneanId = clubsService.newClub("mediterranean", "description here", clubInterest, tags, userId, imageDir + "mediterranean" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("mediterranean", mediterraneanId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long frenchId = clubsService.newClub("french", "description here", clubInterest, tags, userId, imageDir + "french" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("french", frenchId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long fishId = clubsService.newClub("fish", "description here", clubInterest, tags, userId, imageDir + "fish" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("fish", fishId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long vegetarianId = clubsService.newClub("vegetarian", "description here", clubInterest, tags, userId, imageDir + "vegetarian" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("vegetarian", vegetarianId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long freeFromId = clubsService.newClub("free from", "description here", clubInterest, tags, userId, imageDir + "freeFrom" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("free from", freeFromId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long coffeeId = clubsService.newClub("coffee", "description here", clubInterest, tags, userId, imageDir + "coffee" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("coffee", coffeeId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long teaId = clubsService.newClub("tea", "description here", clubInterest, tags, userId, imageDir + "tea" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("tea", teaId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long spiritsId = clubsService.newClub("spirits", "description here", clubInterest, tags, userId, imageDir + "spirits" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("spirits", spiritsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long desertsId = clubsService.newClub("deserts", "description here", clubInterest, tags, userId, imageDir + "deserts" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("deserts", desertsId);
        tags = new ArrayList<String>();
        tags.add("tag here");
        Long sportsFoodId = clubsService.newClub("sports food", "description here", clubInterest, tags, userId, imageDir+ "sportsFood" + ".jpg", 51.5033630,-0.1276250);
        clubsMap.put("sports food", sportsFoodId);
        
        
        
        return clubsMap;
        
        
    }

}
