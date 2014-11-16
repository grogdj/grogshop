/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.grogshop.services.api.MatchingsService;
import org.grogshop.services.api.RulesService;
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
public class RulesServiceImpl implements RulesService{

    @Inject
    NotificationsService notificationService;
    
    @Inject
    MatchingsService matchingsService;
    
    @Inject
    @KBase("matchingrules")
    KieBase kBase;
    
    private KieSession kieSession;
    
    public RulesServiceImpl() {
    }

    @PostConstruct
    public void init(){
        kieSession = kBase.newKieSession();
        kieSession.setGlobal("notificationsService", notificationService);
        kieSession.setGlobal("matchingsService", matchingsService);
    }
    
    @Override
    public void insert(Object o) {
        System.out.println(">> Matching Service inserted: "+o);
        kieSession.insert(o);
        kieSession.fireAllRules();
    }

    
    
    @Override
    public void reset() {
        System.out.println(">> Matching Service init!" );
        init();
    }

    public void retract(Object o) {
        System.out.println(">> Matching Service retracted: "+o);
        FactHandle factHandle = kieSession.getFactHandle(o);
        kieSession.delete(factHandle);
        kieSession.fireAllRules();
    }

    
}
