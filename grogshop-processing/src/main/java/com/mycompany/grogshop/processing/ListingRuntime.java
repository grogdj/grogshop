/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.grogshop.processing;

import com.grogdj.grogshop.core.model.Bid;
import com.grogdj.grogshop.core.model.Listing;
import com.mycompany.grogshop.processing.util.NotificationUtils;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;

/**
 *
 * @author salaboy
 */
public class ListingRuntime extends Frame {

    @Inject
    @KSession("ksession1")
    KieSession kSession;

    private int screenWidth = 1000;
    private int screenHeight = 800;

    private Embedded embed;
    
    public ListingRuntime() {
        setSize(screenWidth, screenHeight);
        setLayout(new BorderLayout());
        embed = new Embedded(screenWidth, screenHeight);
        add(embed, BorderLayout.CENTER);
        
         // important to call this whenever embedding a PApplet.
        // It ensures that the animation thread is started and
        // that other internal variables are properly set.
        embed.init();
        setVisible(true);
    }
    
    @PostConstruct
    public void initProcessing(){
        NotificationUtils.setProcessingRuntime(embed);
    }

    public void addNewListing(Listing listing) {
        kSession.insert(listing);
        kSession.fireAllRules();
    }

    public void newBid(Bid bid) {
        kSession.insert(bid);
        kSession.fireAllRules();

    }

    public static void main(String[] args) {

        Weld w = new Weld();

        WeldContainer wc = w.initialize();
        ListingRuntime bean = wc.instance().select(ListingRuntime.class).get();

        Listing listing = new Listing("salaboy", 70);
        listing.addTag("Car");
        listing.addTag("PT Cruiser");
        listing.addTag("Blue");
        
        bean.addNewListing(listing);
        boolean run = true;
        while(run){

            Bid bid = new Bid("xxx", Math.random() * 100);
            bid.addTag("Car");
            bid.addTag("PT Cruiser");
            bid.addTag("Blue");

            bean.newBid(bid);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ListingRuntime.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        


        w.shutdown();
    }

}
