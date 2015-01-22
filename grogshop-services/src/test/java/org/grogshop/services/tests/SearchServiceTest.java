/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.tests;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.core.Response;
import org.grogshop.services.endpoints.api.ShopAuthenticationService;
import org.grogshop.services.exceptions.ServiceException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class SearchServiceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar")
                
                .addPackages(true, "org.grogshop.services")
                
                .addPackages(true, "com.grogdj.grogshop.core")
                .addAsManifestResource("test-persistence.xml", "persistence.xml")
                

                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    public SearchServiceTest() {
    }
    
    
    
    
    @Produces  
    public EntityManager produceEntityManager(){
        return Persistence.createEntityManagerFactory("primary").createEntityManager();
    }
    
    @Inject
    private ShopAuthenticationService usersService;
  

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void hello() throws ServiceException {
        
        
        Response registerUser = usersService.registerUser("grogdj@gmail.com", "asdasd");
        Assert.assertNotNull(registerUser);
        

    }
}
