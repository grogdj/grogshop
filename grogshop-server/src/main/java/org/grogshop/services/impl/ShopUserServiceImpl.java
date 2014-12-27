/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author salaboy
 */
@Path("/users")
public class ShopUserServiceImpl {

    @PersistenceContext
    EntityManager em;
    
    @POST
    @Path("/register")
    @Consumes({MediaType.APPLICATION_JSON})
    public String registerUser(User user) {
        if (getByEmail(user.getEmail()) != null) {
            return "User already registered with email: " + user.getEmail();
        }
        em.persist(user);
        return "User " + user.getEmail() + " registered";
    }

    public User getByEmail(String email) {
        try {
            return em.createNamedQuery("User.getByEmail", User.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }
    
    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getAllUsers(){
        return em.createNamedQuery("User.getAll", User.class).getResultList();
    }
}
