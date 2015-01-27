/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.tests;

import com.grogdj.grogshop.core.model.Interest;
import com.grogdj.grogshop.core.model.Profile;
import com.grogdj.grogshop.core.model.ServiceKey;
import com.grogdj.grogshop.core.model.User;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.grogshop.services.api.ProfilesService;
import org.grogshop.services.api.UsersService;
import org.grogshop.services.endpoints.api.ShopAuthenticationService;
import org.grogshop.services.endpoints.impl.ShopAuthenticationServiceImpl;
import org.grogshop.services.exceptions.ServiceException;
import org.grogshop.services.filters.auth.GrogAuthenticator;
import org.grogshop.services.impl.ProfilesServiceImpl;
import org.grogshop.services.impl.UsersServiceImpl;
import org.grogshop.services.util.GrogUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.specimpl.ResteasyHttpHeaders;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
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
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
               .addClass(UsersService.class).addClass(UsersServiceImpl.class)
                .addClass(ServiceException.class)
                .addClass(User.class)
                .addClass(GrogUtil.class)
                .addClass(ShopAuthenticationServiceImpl.class)
                .addClass(ShopAuthenticationService.class)
                .addClass(ProfilesService.class)
                .addClass(ProfilesServiceImpl.class)
                .addClass(Profile.class)
                .addClass(GrogAuthenticator.class)
                .addClass(Interest.class)
                .addClass(ServiceKey.class)
                .addAsResource("users.xml", "META-INF/users.xml")
                .addAsResource("interests.xml", "META-INF/interests.xml")
                .addAsResource("servicekey.xml", "META-INF/servicekey.xml")
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    public SearchServiceTest() {
    }

    @PersistenceContext(name = "primary")
    @Produces
    EntityManager em;

    @Inject
    private ShopAuthenticationService authService;

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

        Response newUser = authService.registerUser("grogdj@gmail.com", "asdasd");
        Assert.assertNotNull(newUser);
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<String, String>();
        headers.add("service_key", "webkey:grogdj@gmail.com");
        HttpHeaders httpHeaders = new ResteasyHttpHeaders(headers);
        
        Response login = authService.login(httpHeaders, "grogdj@gmail.com", "asdasd");
        
        Assert.assertEquals(Response.Status.OK.getStatusCode(), login.getStatus());
        
    }
}
