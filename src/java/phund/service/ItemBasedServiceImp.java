/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import phund.entity.Vote;
import phund.repository.VoteRepository;
import phund.repository.VoteRepositoryImp;
import java.util.Map;
import phund.entity.CommonUserVote;
import phund.entity.CommonVote;
import phund.entity.Game;
import phund.entity.ItemBasedPoint;
import phund.entity.ItemBasedPointPK;
import phund.entity.Prediction;
import phund.entity.PredictionPK;
import phund.entity.User;
import phund.repository.GameRepository;
import phund.repository.GameRepositoryImp;
import phund.repository.ItemBasedRepository;
import phund.repository.ItemBasedRepositoryImp;
import phund.repository.PredictionRepository;
import phund.repository.PredictionRepositoryImp;
import phund.repository.UserRepository;
import phund.repository.UserRepositoryImp;

/**
 *
 * @author PhuNDSE63159
 */
public class ItemBasedServiceImp implements ItemBasedService {

    private final int FETCH_RECORD = 100;

    private VoteRepository voteRepository;
    private ItemBasedRepository itemBasedRepository;
    private GameRepository gameRepository;
    private UserRepository userRepository;
    private PredictionRepository predictionRepository;

    private Map<Integer, Map<Integer, Double>> itemMap;
    private List<ItemBasedPoint> result;

    public ItemBasedServiceImp() {
        voteRepository = new VoteRepositoryImp();
        itemBasedRepository = new ItemBasedRepositoryImp();
        gameRepository = new GameRepositoryImp();
        userRepository = new UserRepositoryImp();
        predictionRepository = new PredictionRepositoryImp();

        itemMap = new HashMap<>();
        result = new ArrayList<>();
    }

    public void computeAll() {
        int offset = 0;
        int fetch = FETCH_RECORD;

        List<Game> games = gameRepository.findMany("Game.findAll", null, offset, fetch);
        List<ItemBasedPointPK> computedGameIds = new ArrayList<>();

        while (games != null && !games.isEmpty()) {
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
            offset += fetch;
            games = gameRepository.findMany("Game.findAll", null, offset, fetch);
        }//end while games not empty

        if (!result.isEmpty()) {
            itemBasedRepository.createOrUpdateRange(result);
        }
        computePrediction();
    }

    public void computePrediction() {
        List<Prediction> predictions = new LinkedList<>();
        Map<String, Object> param = new HashMap<>();
        List<User> users = userRepository.findManyAll("User.findAll", null);
        for (User user : users) {
            param.put("userId", user.getId());
            List<Vote> votes = voteRepository.findManyAll("Vote.findByUserId", param);
            if (votes != null && !votes.isEmpty()) {
                List<Game> notVoteGames = gameRepository.findManyAll("Game.findNotVotedGame", null);
                for (Game notVoteGame : notVoteGames) {
                    Double predictRatePoint = predict(user.getId(), notVoteGame.getId(), votes);
                    if (predictRatePoint != null) {
                        Prediction prediction = 
                                new Prediction(new PredictionPK(user.getId(), notVoteGame.getId()), predictRatePoint);
                        predictions.add(prediction);
                    }
                }
            }
        }
        if(!predictions.isEmpty()){
            predictionRepository.createOrUpdateRange(predictions);
        }
    }

    private Double predict(int userId, int gameId, List<Vote> votes) {
        double predictPoint = 0;
        double totalRatingPoint = 0;
        double totalSim = 0;
        for (Vote vote : votes) {
            ItemBasedPoint itemBased
                    = itemBasedRepository.findById(new ItemBasedPointPK(gameId, vote.getVotePK().getGameId()));
            if (itemBased != null) {
                double sim = itemBased.getSimilarity();
                double point = vote.getPoint();
                totalRatingPoint += sim * point;
                totalSim += sim;
            }
        }
        if (totalSim == 0) {
            return null;
        }
        predictPoint = totalRatingPoint / totalSim;
        return predictPoint;
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
