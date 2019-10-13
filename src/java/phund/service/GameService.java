/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.util.List;
import phund.entity.Game;
import phund.entity.SuggestedGame;
import phund.entity.TrendGame;
import phund.entity.VotedGame;

/**
 *
 * @author PhuNDSE63159
 */
public interface GameService {

    List<TrendGame> getTrendGames(Integer offset, Integer fetchNext);

    List<SuggestedGame> getSuggestedGame(int userId, Integer offset, Integer fetchNext);

    List<VotedGame> getVotedGames(int userId, Integer offset, Integer fetch);

    void computeTrend();

    List<TrendGame> searchGames(String searchValue, Integer offset, Integer fetch);

}
