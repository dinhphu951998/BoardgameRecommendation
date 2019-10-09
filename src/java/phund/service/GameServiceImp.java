/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.util.List;
import phund.entity.Game;
import phund.repository.GameRepository;
import phund.repository.GameRepositoryImp;

/**
 *
 * @author PhuNDSE63159
 */
public class GameServiceImp implements GameService {

    private GameRepository gameRepository = null;
    private final int DEFAULT_FETCH = 40;

    public GameServiceImp() {
        gameRepository = new GameRepositoryImp();
    }

    @Override
    public List<Game> getTrendGames(Integer offset, Integer fetchNext) {
        if (offset == null) {
            offset = 0;
        }
        if (fetchNext == null || fetchNext <= DEFAULT_FETCH) {
            fetchNext = DEFAULT_FETCH;
        }
        String namedQuery = "Game.findByRatingPoint";
        return gameRepository.findMany(namedQuery, null, offset, fetchNext);
    }

}
