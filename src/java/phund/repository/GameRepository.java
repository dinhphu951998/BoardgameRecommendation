/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import java.util.List;
import phund.entity.Game;
import phund.entity.SuggestedGame;
import phund.entity.TrendGame;
import phund.entity.VotedGame;

/**
 *
 * @author PhuNDSE63159
 */
public interface GameRepository extends BaseRepository<Game, Integer> {

    List<SuggestedGame> getSuggestedGamesUserBased(int userId, Integer offset, Integer fetch);

    List<SuggestedGame> getSuggestedGamesItemBased(int userId, Integer offset, Integer fetch);

    List<VotedGame> getVotedGame(int userId, Integer offset, Integer fetch);
    
    List<TrendGame> getTrendGames(Integer offset, Integer fetch);
    
    List<TrendGame> searchGames(String searchValue, Integer offset, Integer fetch);
}
