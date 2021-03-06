/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;


import com.grogdj.grogshop.core.model.Comment;
import java.util.List;
import org.grogshop.services.exceptions.ServiceException;

/**
 *
 * @author grogdj
 */
public interface CommentsService {

    List<Comment> getAllCommentsByClub(Long clubId) throws ServiceException;
    
    List<Comment> getAllCommentsByItem(Long itemId) throws ServiceException;

    Long newComment(Long userId, Long clubId, Long itemId, String text) throws ServiceException;

    Comment getById(Long commentId) throws ServiceException;

    void removeComment(Long commentId) throws ServiceException;
    
    List<Comment> getAllComments() throws ServiceException;
    
}
