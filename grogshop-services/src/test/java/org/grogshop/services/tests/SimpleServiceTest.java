/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.tests;

import com.grogdj.grogshop.core.model.Item;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.ProfilesService;
import org.grogshop.services.endpoints.api.PublicShopClubsService;
import org.grogshop.services.endpoints.api.ShopAuthenticationService;
import org.grogshop.services.endpoints.api.ShopClubMembershipsService;
import org.grogshop.services.endpoints.api.ShopClubsService;
import org.grogshop.services.endpoints.impl.PublicShopClubsServiceImpl;
import org.grogshop.services.exceptions.ServiceException;
import org.grogshop.services.filters.auth.GrogAuthenticator;
import org.grogshop.services.impl.ProfilesServiceImpl;
import org.grogshop.services.util.GrogUtil;
//import org.hibernate.search.jpa.FullTextEntityManager;
//import org.hibernate.search.jpa.Search;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class SimpleServiceTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(GrogUtil.class)
                .addPackage(Item.class.getPackage())
                .addPackage(PublicShopClubsService.class.getPackage())
                .addPackage(PublicShopClubsServiceImpl.class.getPackage())
                .addPackage(ProfilesService.class.getPackage())
                .addPackage(ProfilesServiceImpl.class.getPackage())
               
                .addClass(GrogAuthenticator.class)
                .addClass(ServiceException.class)
                .addAsResource("users.xml", "META-INF/users.xml")
                .addAsResource("interests.xml", "META-INF/interests.xml")
                .addAsResource("servicekey.xml", "META-INF/servicekey.xml")
                .addAsResource("clubs.xml", "META-INF/clubs.xml")
                .addAsResource("clubmemberships.xml", "META-INF/clubmemberships.xml")
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    public SimpleServiceTest() {
    }

    @PersistenceContext(name = "primary")
    @Produces
    EntityManager em;

    @Inject
    private ShopAuthenticationService authService;

    @Inject
    private ShopClubsService clubService;

    @Inject
    private ShopClubMembershipsService membershipsService;

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

//    @Test
//    public void registerAndLoginTest() throws ServiceException {
//
//        Response newUser = authService.registerUser("grogdj@gmail.com", "asdasd");
//        Assert.assertNotNull(newUser);
//        MultivaluedMap<String, String> headers = new MultivaluedHashMap<String, String>();
//        headers.add("service_key", "webkey:grogdj@gmail.com");
//        HttpHeaders httpHeaders = new ResteasyHttpHeaders(headers);
//
//        Response loginResponse = authService.login(httpHeaders, "grogdj@gmail.com", "asdasd");
//
//        Assert.assertEquals(Response.Status.OK.getStatusCode(), loginResponse.getStatus());
//        JsonReader jsonReader = Json.createReader(new StringReader((String) loginResponse.getEntity()));
//        JsonObject object = jsonReader.readObject();
//        jsonReader.close();
//
//        String authToken = object.getString("auth_token");
//        String email = object.getString("email");
//        String serviceKey = object.getString("service_key");
//        Long userId = Long.getLong(object.getString("user_id"));
//        Assert.assertNotNull(authToken);
//        Assert.assertEquals("grogdj@gmail.com", email);
//        Assert.assertTrue(serviceKey.contains(email));
//        Assert.assertNotSame(0, userId);
//
//    }

//    @Test
//    public void simpleCreateClubAndJoin() throws ServiceException {
//        Response newUser = authService.registerUser("grogdj@gmail.com", "asdasd");
//        Assert.assertNotNull(newUser);
//        MultivaluedMap<String, String> headers = new MultivaluedHashMap<String, String>();
//        headers.add("service_key", "webkey:grogdj@gmail.com");
//        HttpHeaders httpHeaders = new ResteasyHttpHeaders(headers);
//
//        Response loginResponse = authService.login(httpHeaders, "grogdj@gmail.com", "asdasd");
//
//        Assert.assertEquals(Response.Status.OK.getStatusCode(), loginResponse.getStatus());
//        JsonReader jsonReader = Json.createReader(new StringReader((String) loginResponse.getEntity()));
//        JsonObject object = jsonReader.readObject();
//        jsonReader.close();
//
//        String authToken = object.getString("auth_token");
//        String email = object.getString("email");
//        String serviceKey = object.getString("service_key");
//        Long userId = Long.getLong(object.getString("user_id"));
//        Assert.assertNotNull(authToken);
//        Assert.assertEquals("grogdj@gmail.com", email);
//        Assert.assertTrue(serviceKey.contains(email));
//        Assert.assertNotSame(0, userId);
//
//        Response newClubResponse = clubService.newClub("My First Club", "This is my first club description",
//                "sports", "sports,first,club", userId, "", 51.5033630, -0.1276250);
//        jsonReader = Json.createReader(new StringReader((String) newClubResponse.getEntity()));
//
//        object = jsonReader.readObject();
//        jsonReader.close();
//
//        Long clubId = Long.getLong(object.getString("club_id"));
//
//        Response joinResponse = membershipsService.create(clubId, userId);
//
//        Response allMembers = membershipsService.getAllMembers(clubId);
//
//        Assert.assertNotNull(allMembers);
//    }
    @Test
    public void searchInitialTest() {
//        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
//        Assert.assertNotNull(fullTextEntityManager);
    }

}
