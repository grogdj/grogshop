/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.websocket.encoders;

import com.grogdj.grogshop.core.model.Matching;
import com.grogdj.grogshop.core.model.Tag;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author salaboy
 */
public class MatchingEncoder implements Encoder.Text<Matching> {

    @Override
    public String encode(Matching matching) throws EncodeException {

        JsonArray listingPriceRangeArray = Json.createArrayBuilder().add(matching.getListing().getPriceRange()[0]).add(matching.getListing().getPriceRange()[1]).build();
        JsonArray bidPriceRangeArray = Json.createArrayBuilder().add(matching.getBid().getPriceRange()[0]).add(matching.getBid().getPriceRange()[1]).build();

        JsonArrayBuilder listingTagsBuilder = Json.createArrayBuilder();
        for (Tag t : matching.getListing().getTags()) {
            listingTagsBuilder.add(Json.createObjectBuilder().add("name", t.getName()).build());
        }
        JsonArray listingTagsArray = listingTagsBuilder.build();

        JsonArrayBuilder bidTagsBuilder = Json.createArrayBuilder();
        for (Tag t : matching.getBid().getTags()) {
            bidTagsBuilder.add(Json.createObjectBuilder().add("name", t.getName()).build());
        }
        JsonArray bidTagsArray = bidTagsBuilder.build();

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("matchingId", matching.getId())
                .add("occurrence", matching.getOccurrence().getTime())
                .add("type", matching.getType())
                .add("status", matching.getStatus().name())
                .add("listing", Json.createObjectBuilder()
                        .add("id", matching.getListing().getId())
                        .add("userId", matching.getListing().getUserId())
                        .add("priceRange", listingPriceRangeArray)
                        .add("status", matching.getListing().getStatus().name())
                        .add("tags", listingTagsArray)
                        .build())
                .add("bid", Json.createObjectBuilder()
                        .add("id", matching.getBid().getId())
                        .add("userId", matching.getBid().getUserId())
                        .add("priceRange", bidPriceRangeArray)
                        .add("status", matching.getBid().getStatus().name())
                        .add("tags", bidTagsArray)
                        .build())
                .build();
        return jsonObject.toString();

    }

    @Override
    public void init(EndpointConfig ec) {

    }

    @Override
    public void destroy() {

    }

}
