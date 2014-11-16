/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.api;

import com.grogdj.grogshop.core.model.Matching;
import java.util.List;

/**
 *
 * @author salaboy
 */
public interface MatchingsService {
    Matching getMatching(Long matchingId);

    List<Matching> getAllMatchings();

    Long newMatching(Matching matching);

    void clearMatchings();

    Matching removeMatching(Long matchingId);
}
