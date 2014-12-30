/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.websocket.decoders;

import com.grogdj.grogshop.core.model.ClubMembership;
import com.grogdj.grogshop.core.model.Listing;
import com.grogdj.grogshop.core.model.Matching;
import com.grogdj.grogshop.core.model.Tag;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author grogdj
 */
public class MatchingDecoder implements Decoder.Text<Matching> {

    @Override
    public Matching decode(String jsonMessage) throws DecodeException {

        JsonObject jsonObject = Json
                .createReader(new StringReader(jsonMessage)).readObject();
        
        JsonObject jsonObjectListing = jsonObject.getJsonObject("listing");
        Listing listing = new Listing();
        listing.setId(jsonObjectListing.getJsonNumber("id").longValue());
        JsonArray jsonArrayListingPriceRange = jsonObjectListing.getJsonArray("priceRange");
        Double[] priceRangeListing = new Double[]{jsonArrayListingPriceRange.getJsonNumber(0).doubleValue(), jsonArrayListingPriceRange.getJsonNumber(1).doubleValue()};
        listing.setPriceRange(priceRangeListing);
        listing.setUserId(jsonObjectListing.getString("userId"));
        listing.setStatus(Listing.ListingStatus.valueOf(jsonObjectListing.getString("status")));
        JsonArray jsonArrayListingTags = jsonObjectListing.getJsonArray("tags");
        List<Tag> listingTags = new ArrayList<Tag>(jsonArrayListingTags.size());
        for(JsonValue jsv : jsonArrayListingTags){
            listingTags.add(new Tag(jsv.toString()));
        }
        listing.setTags(listingTags);
        
        
        ClubMembership membership = new ClubMembership();
        JsonObject jsonObjectMembership = jsonObject.getJsonObject("membership");
        membership.setId(jsonObjectMembership.getJsonNumber("id").longValue());
        JsonArray jsonArrayMembershipPriceRange = jsonObjectMembership.getJsonArray("priceRange");
        Double[] priceRangeMembership = new Double[]{jsonArrayMembershipPriceRange.getJsonNumber(0).doubleValue(), jsonArrayMembershipPriceRange.getJsonNumber(1).doubleValue()};
        membership.setPriceRange(priceRangeMembership);
        membership.setUserId(jsonObjectMembership.getString("userId"));
        //membership.setStatus(Bid.BidStatus.valueOf(jsonObjectBid.getString("status")));
        JsonArray jsonArrayMembershipTags = jsonObjectMembership.getJsonArray("tags");
        List<Tag> membershipTags = new ArrayList<Tag>(jsonArrayMembershipTags.size());
        for(JsonValue jsv : jsonArrayMembershipTags){
            membershipTags.add(new Tag(jsv.toString()));
        }
        membership.setTags(membershipTags);

        
        Matching matching = new Matching();
        matching.setId(jsonObject.getJsonNumber("matchingId").longValue());
        matching.setMembership(membership);
        matching.setListing(listing);
        matching.setOccurrence(new Date(jsonObject.getJsonNumber("occurrence").longValue()));
        matching.setStatus(Matching.MatchingStatus.valueOf(jsonObject.getString("status")));
        matching.setType(jsonObject.getString("type"));
        
        return matching;

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
