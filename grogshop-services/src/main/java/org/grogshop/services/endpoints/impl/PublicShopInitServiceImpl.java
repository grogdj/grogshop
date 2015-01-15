/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.endpoints.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.ClubsService;
import org.grogshop.services.api.InterestsService;
import org.grogshop.services.endpoints.api.PublicShopInitService;
import org.grogshop.services.exceptions.ServiceException;
import org.grogshop.services.impl.InterestsServiceImpl;

@Stateless
public class PublicShopInitServiceImpl implements PublicShopInitService {

    @Inject
    private ClubsService clubsService;

    @Inject
    private InterestsService interestsService;

    public Response initApplication() throws ServiceException {
        try {
            interestsService.newInterest("cooking", "cooking.jpg");
            interestsService.newInterest("music", "music.jpg");
            interestsService.newInterest("art", "art.jpg");
            interestsService.newInterest("science", "science.jpg");
            interestsService.newInterest("sports", "sports.jpg");
            interestsService.newInterest("cars", "cars.jpg");
            interestsService.newInterest("design", "design.jpg");
            interestsService.newInterest("health", "health.jpg");
            interestsService.newInterest("antiques", "antiques.jpg");
            interestsService.newInterest("clothes", "clothes.jpg");
            interestsService.newInterest("astronomy", "astrology.jpg");
            interestsService.newInterest("gardening", "gardening.jpg");
            interestsService.newInterest("infusions", "infusions.jpg");
            interestsService.newInterest("old cars", "oldcars.jpg");
            interestsService.newInterest("pets", "pets.jpg");
            interestsService.newInterest("photography", "photography.jpg");
            interestsService.newInterest("radio", "radio.jpg");
            interestsService.newInterest("sailing", "sailing.jpg");
            interestsService.newInterest("video games", "videogames.jpg");
            interestsService.newInterest("writing", "writing.jpg");
            List<String> tags = new ArrayList<String>();
            tags.add("food");
            tags.add("fun");
            tags.add("healty");
            clubsService.newClub("cooking club", "this is a new cooking club", "cooking", tags, new Long(1), "cooking.jpg");
            tags = new ArrayList<String>();
            tags.add("stars");
            tags.add("galaxy");
            tags.add("apollo");
            clubsService.newClub("astronomy for everyone", "astronomy club for everybody", "astronomy", tags, new Long(1), "astrology.jpg");
            tags = new ArrayList<String>();
            tags.add("old");
            tags.add("classic");
            tags.add("cars");
            clubsService.newClub("My good old cars", "about classic cars and stuff", "old cars", tags, new Long(1), "oldcars.jpg");
            tags = new ArrayList<String>();
            tags.add("puppies");
            tags.add("dogs");
            tags.add("cats");
            clubsService.newClub("My small pets", "all pets allowed", "pets", tags, new Long(1), "pets.jpg");
            tags = new ArrayList<String>();
            tags.add("arcade");
            tags.add("play");
            tags.add("games");
            clubsService.newClub("Pacman & cia", "for all the gamers", "video games", tags, new Long(1), "videogames.jpg");
            tags = new ArrayList<String>();
            tags.add("graphic");
            tags.add("visual");
            tags.add("design");
            clubsService.newClub("Designers United", "let the style be with you", "design", tags, new Long(1), "design.jpg");
            tags = new ArrayList<String>();
            tags.add("old");
            tags.add("fashion");
            tags.add("furniture");
            clubsService.newClub("Antiques", "more than 100 year old items", "antiques", tags, new Long(1), "antiques.jpg");
        } catch (Exception ex) {
            Logger.getLogger(InterestsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }

}
