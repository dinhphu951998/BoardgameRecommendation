/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import phund.entity.Game;
import phund.entity.Vote;
import phund.repository.GameRepository;
import phund.repository.GameRepositoryImp;
import phund.repository.VoteRepository;
import phund.repository.VoteRepositoryImp;
import java.util.Map;
import java.util.stream.Collectors;
import phund.entity.ItemBasedPoint;
import phund.entity.SuggestedGame;
import phund.entity.TrendGame;
import phund.entity.VotedGame;
import phund.repository.ItemBasedRepository;
import phund.repository.ItemBasedRepositoryImp;

/**
 *
 * @author PhuNDSE63159
 */
public class GameServiceImp implements GameService {

    private GameRepository gameRepository = null;

    private final int DEFAULT_FETCH = 100;

    public GameServiceImp() {
        gameRepository = new GameRepositoryImp();
    }

    @Override
    public List<TrendGame> getTrendGames(Integer offset, Integer fetchNext) {
        if (offset == null || offset < 0) {
            offset = 0;
        }
        if (fetchNext == null || fetchNext <= DEFAULT_FETCH) {
            fetchNext = DEFAULT_FETCH;
        }
        List<TrendGame> trendGames = gameRepository.getTrendGames(offset, fetchNext);
        return trendGames;
    }

    public List<SuggestedGame> getSuggestedGame(int userId, Integer offset, Integer fetchNext) {
        if (offset == null || offset < 0) {
            offset = 0;
        }
        if (fetchNext == null || fetchNext <= DEFAULT_FETCH) {
            fetchNext = DEFAULT_FETCH;
        }
        List<SuggestedGame> games = gameRepository.getSuggestedGamesItemBased(userId, offset, fetchNext);
        return games;
    }

    public List<VotedGame> getVotedGames(int userId, Integer offset, Integer fetch) {
        if (offset == null || offset < 0) {
            offset = 0;
        }
        if (fetch == null || fetch < DEFAULT_FETCH) {
            fetch = DEFAULT_FETCH;
        }
        List<VotedGame> games = gameRepository.getVotedGame(userId, offset, fetch);
        return games;
    }

    public void computeTrend() {
        int offset = 0;
        int fetch = DEFAULT_FETCH;
        List<Game> games = gameRepository.findMany("Game.findGameVoteNotEmpty", null, offset, fetch);
        List<Game> updatedGame = new LinkedList<>();
        while (games != null && !games.isEmpty()) {
            for (Game game : games) {
                List<Vote> votes = new ArrayList<>(game.getVotes());
                int totalVote = votes.size();
                if (totalVote != 0) {
                    double totalPoint = votes.stream().mapToDouble(v -> v.getPoint()).sum();
                    double ratingPoint = totalPoint / totalVote;
                    game.setRatingPoint(ratingPoint);
                    updatedGame.add(game);
                }

            }
            offset += fetch;
            games = gameRepository.findMany("Game.findGameVoteNotEmpty", null, offset, fetch);
        }
        gameRepository.updateRange(updatedGame);
    }

    public List<TrendGame> searchGames(String searchValue, Integer offset, Integer fetch) {
        if (offset == null || offset < 0) {
            offset = 0;
        }
        if (fetch == null || fetch < DEFAULT_FETCH) {
            fetch = DEFAULT_FETCH;
        }
        return gameRepository.searchGames(searchValue, offset, fetch);
    }

    @Override
    public Game getGameById(Integer id) {
        return gameRepository.findById(id);

    }
}
