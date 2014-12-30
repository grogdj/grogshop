/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.filters.auth;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;

import javax.security.auth.login.LoginException;
import org.grogshop.services.impl.UserServiceImpl;
import org.grogshop.services.util.GrogUtil;

/**
 *
 * @author grogdj
 */
@Singleton
public final class GrogAuthenticator {

    private static GrogAuthenticator authenticator = null;

    // An authentication token storage which stores <service_key, auth_token>.
    private final Map<String, String> authorizationTokensStorage = new HashMap();
    
    @Inject
    UserServiceImpl userService;
    

    private GrogAuthenticator() {


    }


    public String login(String serviceKey, String email, String password) throws LoginException {
        System.out.println("serviceKey: "+serviceKey);
        System.out.println("email: "+email);
        System.out.println("password: "+password);
        if (userService.existKey(serviceKey)) {
            
            String emailMatch = userService.getKey(serviceKey);
            System.out.println("emailMatch: "+emailMatch);
            if (emailMatch.equals(email) && userService.exist(email)) {

                String passwordMatch = userService.getByEmail(email).getPassword();
                System.out.println("passwordMatch: "+passwordMatch);
                if (passwordMatch.equals(GrogUtil.hash(password))) {

                    /**
                     *
                     * Once all params are matched, the authToken will be
                     *
                     * generated and will be stored in the
                     *
                     * authorizationTokensStorage. The authToken will be needed
                     *
                     * for every REST API invocation and is only valid within
                     *
                     * the login session
                     *
                     */
                    String authToken = UUID.randomUUID().toString();

                    authorizationTokensStorage.put(authToken, email);

                    return authToken;

                }

            }

        }

        throw new LoginException("Don't Come Here Again!");

    }

    /**
     *
     * The method that pre-validates if the client which invokes the REST API is
     *
     * from a authorized and authenticated source.
     *
     *
     *
     * @param serviceKey The service key
     *
     * @param authToken The authorization token generated after login
     *
     * @return TRUE for acceptance and FALSE for denied.
     *
     */
    public boolean isAuthTokenValid(String serviceKey, String authToken) {

        if (isServiceKeyValid(serviceKey)) {

            String usernameMatch1 = userService.getKey(serviceKey);

            if (authorizationTokensStorage.containsKey(authToken)) {

                String usernameMatch2 = authorizationTokensStorage.get(authToken);

                if (usernameMatch1.equals(usernameMatch2)) {

                    return true;

                }

            }

        }

        return false;

    }

    /**
     *
     * This method checks is the service key is valid
     *
     *
     *
     * @param serviceKey
     *
     * @return TRUE if service key matches the pre-generated ones in service key
     *
     * storage. FALSE for otherwise.
     *
     */
    public boolean isServiceKeyValid(String serviceKey) {

        return userService.existKey(serviceKey);

    }

    public void logout(String serviceKey, String authToken) throws GeneralSecurityException {

        if (userService.existKey(serviceKey)) {

            String usernameMatch1 = userService.getKey(serviceKey);

            if (authorizationTokensStorage.containsKey(authToken)) {

                String usernameMatch2 = authorizationTokensStorage.get(authToken);

                if (usernameMatch1.equals(usernameMatch2)) {

                    /**
                     *
                     * When a client logs out, the authentication token will be
                     *
                     * remove and will be made invalid.
                     *
                     */
                    authorizationTokensStorage.remove(authToken);

                    return;

                }

            }

        }

        throw new GeneralSecurityException("Invalid service key and authorization token match.");

    }

}
