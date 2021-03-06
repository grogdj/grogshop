/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.impl;

import com.grogdj.grogshop.core.model.Club;
import com.grogdj.grogshop.core.model.Comment;
import com.grogdj.grogshop.core.model.Item;
import com.grogdj.grogshop.core.model.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.grogshop.services.api.CommentsService;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
@ApplicationScoped
public class CommentsServiceImpl implements CommentsService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    private final static Logger log = Logger.getLogger(CommentsServiceImpl.class.getName());

    @PostConstruct
    private void init() {

    }

    @Override
    public List<Comment> getAllComments() throws ServiceException {
        return em.createNamedQuery("Comment.getAll", Comment.class).getResultList();
    }

    @Override
    public Comment getById(Long commentId) throws ServiceException {
        return em.find(Comment.class, commentId);
    }

    @Override
    public Long newComment(Long userId, Long clubId, Long itemId, String text ) throws ServiceException {
        User user = em.find(User.class, userId);
        
        
        Club club = em.find(Club.class, clubId);
        
        Item item = em.find(Item.class, itemId);
        
        Comment comment = new Comment(user, club, item, text);
        em.persist(comment);
        log.log(Level.INFO, "Comment {0} created with id {1}", new Object[]{text, comment.getId()});
        return comment.getId();
    }
    
   
    
    

    @Override
    public void removeComment(Long commentId) throws ServiceException {
        Comment find = em.find(Comment.class, commentId);
        if (find == null) {
            throw new ServiceException("Comment  doesn't exist: " + commentId);
        }
        em.remove(find);
    }
    
    

    @Override
    public List<Comment> getAllCommentsByClub(Long clubId) throws ServiceException {
        return em.createNamedQuery("Comment.getAllByClub", Comment.class).setParameter("clubId", clubId).getResultList();
    }
    
    @Override
    public List<Comment> getAllCommentsByItem(Long itemId) throws ServiceException {
        return em.createNamedQuery("Comment.getAllByItem", Comment.class).setParameter("itemId", itemId).getResultList();
    }

   
}
