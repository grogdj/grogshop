/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.grogshop.services.api.MatchingService;
import org.grogshop.services.api.NotificationsService;
import org.kie.api.KieBase;
import org.kie.api.cdi.KBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 *
 * @author salaboy
 */
@Singleton
public class MatchingServiceImpl implements MatchingService{

    @Inject
    NotificationsService notificationService;
    
    @Inject
    @KBase("matchingrules")
    KieBase kBase;
    
    private KieSession kieSession;
    
    public MatchingServiceImpl() {
    }

    @PostConstruct
    public void init(){
        kieSession = kBase.newKieSession();
        kieSession.setGlobal("notificationsService", notificationService);
    }
    
    @Override
    public void insert(Object o) {
        kieSession.insert(o);
        kieSession.fireAllRules();
    }

    
    
    @Override
    public void reset() {
        init();
    }

    public void retract(Object o) {
        FactHandle factHandle = kieSession.getFactHandle(o);
        kieSession.delete(factHandle);
        kieSession.fireAllRules();
    }

    
}
