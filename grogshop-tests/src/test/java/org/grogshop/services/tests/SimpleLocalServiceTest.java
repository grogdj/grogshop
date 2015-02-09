/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.tests;

import com.grogdj.grogshop.core.model.User;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.grogshop.services.api.ClubsService;
import org.grogshop.services.api.InterestsService;
import org.grogshop.services.api.MembershipsService;
import org.grogshop.services.api.UsersService;
import org.grogshop.services.exceptions.ServiceException;

//import org.hibernate.search.jpa.FullTextEntityManager;
//import org.hibernate.search.jpa.Search;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
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
public class SimpleLocalServiceTest {

    @Deployment
    public static WebArchive createDeployment() {

        File[] libs = Maven.configureResolver()
                // .withRemoteRepo("jboss-repo", "https://repository.jboss.org/nexus/content/groups/public/", "default")
                .workOffline()
                .withMavenCentralRepo(false)
                .withClassPathResolution(true)
                .loadPomFromFile("pom.xml").importRuntimeDependencies().resolve("org.drools:drools-compiler",
                        "com.google.protobuf:protobuf-java")
                .withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addAsLibraries(libs)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    public SimpleLocalServiceTest() {
    }

    @PersistenceContext(name = "primary")
    @Produces
    EntityManager em;

    @Inject
    private UsersService usersService;

    @Inject
    private ClubsService clubsService;

    @Inject
    private MembershipsService membershipsService;

    @Inject
    private InterestsService interestService;

    @Inject
    private UserTransaction ut;

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
    public void newUserAndLoginTest() throws ServiceException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        ut.begin();
        Long newUser = usersService.newUser(new User("grogdj@gmail.com", "asdasd"));

        ut.commit();
        Assert.assertNotNull(newUser);
        ut.begin();
        interestService.newInterest("sports");
        ut.commit();
        List<String> tags = new ArrayList<String>();

        tags.add("sports");
        ut.begin();
        clubsService.newClub("My First Club", "This is my first club description",
                "sports", tags, newUser, "imagePathHere", 51.5033630, -0.1276250);
        ut.commit();

    }

//    @Test
//    public void simpleCreateClubAndJoin() throws ServiceException {
//       
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
//        Long userId = object.getJsonNumber("user_id").longValue();
//        Assert.assertNotNull(authToken);
//        Assert.assertEquals("grogdj@gmail.com", email);
//        Assert.assertTrue(serviceKey.contains(email));
//        Assert.assertNotSame(0, userId);
//
//        Response newClubResponse = clubsService.newClub("My First Club", "This is my first club description",
//                "sports", "sports,first,club", userId, "imagePathHere", 51.5033630, -0.1276250);
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
}
