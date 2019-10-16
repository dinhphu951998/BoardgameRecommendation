/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import phund.entity.Vote;
import phund.repository.VoteRepository;
import phund.repository.VoteRepositoryImp;
import java.util.Map;
import phund.entity.CommonUserVote;
import phund.entity.Game;
import phund.entity.ItemBasedPoint;
import phund.entity.ItemBasedPointPK;
import phund.repository.GameRepository;
import phund.repository.GameRepositoryImp;
import phund.repository.ItemBasedRepository;
import phund.repository.ItemBasedRepositoryImp;
import phund.repository.UserRepository;
import phund.repository.UserRepositoryImp;

/**
 *
 * @author PhuNDSE63159
 */
public class ItemBasedServiceImp implements ItemBasedService {

    private final int DEFAULT_FETCH = 100;

    private VoteRepository voteRepository;
    private ItemBasedRepository itemBasedRepository;
    private GameRepository gameRepository;
    private UserRepository userRepository;

    private List<ItemBasedPoint> result;

    public ItemBasedServiceImp() {
        voteRepository = new VoteRepositoryImp();
        itemBasedRepository = new ItemBasedRepositoryImp();
        gameRepository = new GameRepositoryImp();
        userRepository = new UserRepositoryImp();

        result = new ArrayList<>();
    }

    public void computeAll() {
        int offset = 0;
        int fetch = DEFAULT_FETCH;

        List<Game> games = gameRepository.findManyAll("Game.findGameVoteNotEmpty", null);
        List<ItemBasedPointPK> computedGameIds = new ArrayList<>();

//        while (games != null && !games.isEmpty()) {
        for (Game game : games) {
            for (Game pref : games) {
                int gameId = game.getId();
                int prefId = pref.getId();
                ItemBasedPointPK gamePK = new ItemBasedPointPK(gameId, prefId);
                if (gameId != prefId
                        && !computedGameIds.contains(gamePK)) {

                    computedGameIds.add(gamePK);
                    ItemBasedPointPK prefPK = new ItemBasedPointPK(prefId, gameId);
                    computedGameIds.add(prefPK);
                    double sim = computeSimilarityBetweenItems(gameId, prefId);

                    if (sim != 0) {
                        result.add(new ItemBasedPoint(gamePK, sim));
                        result.add(new ItemBasedPoint(prefPK, sim));
                    }

                }//endif gameId != prefId
            }//end for each pref
        }//end for each game
        
        
//        offset += fetch;
//        games = gameRepository.findMany("Game.findAll", null, offset, fetch);
//        }//end while games not empty

        if (!result.isEmpty()) {
            itemBasedRepository.createOrUpdateRange(result);
        }
    }

    private double computeSimilarityBetweenItems(int gameId, int prefId) {
        List<CommonUserVote> commonUserVotes = voteRepository.getCommonUserVotes(gameId, prefId);
        if (commonUserVotes == null || commonUserVotes.isEmpty()) {
            return 0;
        }

        double sum = 0;
        for (CommonUserVote vote : commonUserVotes) {
            double point1 = vote.getGamePoint();
            double point2 = vote.getPrefPoint();
            sum += Math.pow(point1 - point2, 2);
        }

        return 1 / (1 + sum);
    }

}
