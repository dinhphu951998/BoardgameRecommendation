/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.util.List;
import phund.entity.Game;

/**
 *
 * @author PhuNDSE63159
 */
public interface GameService {
    
    List<Game> getTrendGames(Integer offset, Integer fetchNext);
    
}
