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
    private VoteRepository voteRepository = null;
    private ItemBasedRepository itemBasedRepository = null;

    private final int BOUND_VOTE = 100;
    private final int DEFAULT_FETCH = 100;
    private final double MAX_RATING_POINT = 5;

    public GameServiceImp() {
        gameRepository = new GameRepositoryImp();
        voteRepository = new VoteRepositoryImp();
        itemBasedRepository = new ItemBasedRepositoryImp();
    }

    @Override
    public List<TrendGame> getTrendGames(Integer offset, Integer fetchNext) {
        if (offset == null) {
            offset = 0;
        }
        if (fetchNext == null || fetchNext <= DEFAULT_FETCH) {
            fetchNext = DEFAULT_FETCH;
        }
        List<TrendGame> trendGames = gameRepository.getTrendGames(offset, fetchNext);
        return trendGames;
    }

    public List<SuggestedGame> getSuggestedGame(int userId, Integer offset, Integer fetchNext) {
        if (offset == null) {
            offset = 0;
        }
        if (fetchNext == null || fetchNext <= DEFAULT_FETCH) {
            fetchNext = DEFAULT_FETCH;
        }
        List<SuggestedGame> games = null;
        Map<String, Object> params = new HashMap();
        params.put("userId", userId);
        int totalVote = voteRepository.countVote(userId);
        if (totalVote < BOUND_VOTE) {
            //apply item based
            games = gameRepository.getSuggestedGamesItemBased(userId, offset, fetchNext);
        } else {
            //apply user based
            games = gameRepository.getSuggestedGamesUserBased(userId, offset, fetchNext);
        }
        for (SuggestedGame game : games) {
            double sim = game.getSimilarity();
            double prefPoint = game.getPrefPoint();
            int percent = (int) (sim * prefPoint * 100 / MAX_RATING_POINT);
            game.setMatchingPercent(percent);
        }
        if (games != null && !games.isEmpty()) {
            games = games.stream().sorted(new Comparator<SuggestedGame>() {
                @Override
                public int compare(SuggestedGame o1, SuggestedGame o2) {
                    if (o1.getMatchingPercent() > o2.getMatchingPercent()) {
                        return -1;
                    } else if (o1.getMatchingPercent() < o2.getMatchingPercent()) {
                        return 1;
                    }
                    return 0;
                }
            }).distinct().collect(Collectors.toList());
        }

        return games;
    }

    public List<VotedGame> getVotedGames(int userId, Integer offset, Integer fetch) {
        if (offset == null || offset < 0) {
            offset = 0;
        }
        if (fetch == null || fetch < DEFAULT_FETCH) {
            fetch = DEFAULT_FETCH;
        }
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("userId", userId);
        List<VotedGame> games = gameRepository.getVotedGame(userId, offset, fetch);
        return games;
    }

    public void computeTrend() {
        int offset = 0;
        int fetch = DEFAULT_FETCH;
//        Map<String, Object> parameters = new HashMap<>();
        List<Game> games = gameRepository.findMany("Game.findGameVoteNotEmpty", null, offset, fetch);
        List<Game> updatedGame = new LinkedList<>();
        while (games != null && !games.isEmpty()) {
            for (Game game : games) {
                List<Vote> votes = new ArrayList<>(game.getVotes());
                int totalVote = votes.size();
                if (totalVote != 0) {
                    double totalPoint = votes.stream().mapToDouble(v -> v.getPoint()).sum();
                    double ratingPoint = totalPoint / totalVote;
                    game.setRatingPoint(totalPoint / totalVote);
                    updatedGame.add(game);
                }

            }
            offset += fetch;
//            fetch += DEFAULT_FETCH;
            games = gameRepository.findMany("Game.findGameVoteNotEmpty", null, offset, fetch);
        }
        gameRepository.updateRange(updatedGame);
    }

//    private List<SuggestedGame> getSuggestedGames(int userId) {
//        Map<String, Object> params = new HashMap();
//        params.put("userId", userId);
//        List<Vote> votes = voteRepository.findMany("Vote.findByUserId", params, null, null);
//        List<Integer> gameIdsVoted = votes.stream().map(v -> v.getVotePK().getGameId()).collect(Collectors.toList());
//        params.clear();
//        params.put("itemIds", gameIdsVoted);
//        List<ItemBasedPoint> comparedItems =
//                itemBasedRepository.findMany("ItemBasedPoint.findByItemIdInList", params, null, null);
//        
//        List<Integer> prefIds = comparedItems.stream().map(p -> p.getItemBasedPointPK().getPrefId())
//        
//    }
}
