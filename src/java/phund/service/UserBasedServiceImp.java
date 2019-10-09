/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import phund.entity.ItemBasedPoint;
import phund.entity.ItemBasedPointPK;
import phund.entity.User;
import phund.entity.UserBasedPoint;
import phund.entity.UserBasedPointPK;
import phund.entity.Vote;
import phund.repository.UserBasedRepository;
import phund.repository.UserBasedRepositoryImp;
import phund.repository.UserRepository;
import phund.repository.UserRepositoryImp;
import phund.repository.VoteRepository;
import phund.repository.VoteRepositoryImp;

/**
 *
 * @author PhuNDSE63159
 */
public class UserBasedServiceImp implements UserBasedService {

    private UserBasedRepository userBasedRepository;
    private VoteRepository voteRepository;
    private UserRepository userRepository;

    private Map<Integer, Map<Integer, Double>> userMap;
    private List<UserBasedPoint> result;
    private List<Vote> userVotedList, prefVotedList;

    private final int FETCH_RECORD = 100;

    public UserBasedServiceImp() {
        userBasedRepository = new UserBasedRepositoryImp();
        voteRepository = new VoteRepositoryImp();
        userRepository = new UserRepositoryImp();

        userMap = new HashMap<>();
        result = new ArrayList<>();
    }

    public void compute() {
        List<Vote> votes = voteRepository.findMany("Vote.findAll", null, null, 100);

        //init data set
        for (Vote vote : votes) {
            int userId = vote.getVotePK().getUserId();
            Map<Integer, Double> tmp = userMap.getOrDefault(userId, new HashMap<>());
            tmp.put(vote.getVotePK().getGameId(), vote.getPoint());
            userMap.put(userId, tmp);
        }

        //start compare
        for (Map.Entry<Integer, Map<Integer, Double>> userEntry : userMap.entrySet()) {
            Integer userId = userEntry.getKey();

            for (Map.Entry<Integer, Map<Integer, Double>> entry : userMap.entrySet()) {
                Integer prefId = entry.getKey();
                if (userId != prefId) {
                    double sim = computeSimilarityBetweenUsers(userId, prefId);
                    UserBasedPoint userBasedPoint = new UserBasedPoint(new UserBasedPointPK(userId, prefId), sim);
                    result.add(userBasedPoint);
                }
            }
        }

        List<UserBasedPoint> updatePoint = new ArrayList<>();
        List<UserBasedPoint> createPoint = new ArrayList<>();

        for (UserBasedPoint userBasedPoint : result) {
            boolean exist = userBasedRepository.exist(userBasedPoint);
            if (exist) {
                updatePoint.add(userBasedPoint);
            } else {
                createPoint.add(userBasedPoint);
            }
        }

        userBasedRepository.createRange(createPoint);
        userBasedRepository.updateRange(createPoint);

    }

    private double computeSimilarityBetweenUsers(int userId, int prefId) {
        List<Integer> gamesInCommon = new ArrayList<>();
        Map<Integer, Double> userVotedMap = null, prefVotedMap = null;

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        userVotedList = voteRepository.findMany("Vote.findByUserId", params, null, FETCH_RECORD);
        params.put("userId", prefId);
        prefVotedList = voteRepository.findMany("Vote.findByUserId", params, null, FETCH_RECORD);

        for (Vote vote : userVotedList) {
            if (userVotedMap == null) {
                userVotedMap = new HashMap<>();
            }
            userVotedMap.put(vote.getVotePK().getGameId(), vote.getPoint());
        }

        for (Vote vote : prefVotedList) {
            if (userVotedMap == null) {
                userVotedMap = new HashMap<>();
            }
            prefVotedMap.put(vote.getVotePK().getGameId(), vote.getPoint());
        }

        for (Map.Entry<Integer, Double> entry : userVotedMap.entrySet()) {
            int gameId = entry.getKey();
            if (prefVotedMap.containsKey(gameId)) {
                gamesInCommon.add(gameId);
            }
        }

        if (gamesInCommon.isEmpty()) {
            return 0;
        }

        double sum1 = 0;
        double sum2 = 0;
        for (Integer common : gamesInCommon) {
            sum1 += userVotedMap.get(common);
            sum2 += prefVotedMap.get(common);
        }

        double sum1Sq = 0;
        double sum2Sq = 0;
        for (Integer common : gamesInCommon) {
            sum1Sq += Math.pow(userVotedMap.get(common), 2);
            sum2Sq += Math.pow(prefVotedMap.get(common), 2);
        }

        double pSum = 0;
        for (Integer common : gamesInCommon) {
            pSum += userVotedMap.get(common) * prefVotedMap.get(common);
        }
        int n = gamesInCommon.size();
        double num = pSum - (sum1 * sum2) / n;
        double den = Math.sqrt((sum1Sq - Math.pow(sum1, 2) / n) * (sum2Sq - Math.pow(sum2, 2) / n));
        if (den == 0) {
            return 0;
        }

        return num / den;
    }

    public void computeAll() {
        int offset = 0;
        int fetch = FETCH_RECORD;

        List<User> users = userRepository.findMany("User.findAll", null, offset, fetch);
        List<Integer> calculatedUserId = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        while (!users.isEmpty()) {
            for (User user : users) {
                for (User pref : users) {
                    int userId = user.getId();
                    int prefId = pref.getId();
                    if (userId != prefId
                            && !calculatedUserId.contains(userId)
                            && !calculatedUserId.contains(prefId)) {

                        calculatedUserId.add(user.getId());
                        calculatedUserId.add(pref.getId());
                        double sim = computeSimilarityBetweenUsers(user.getId(), pref.getId());

                        result.add(new UserBasedPoint(new UserBasedPointPK(userId, prefId), sim));
                        result.add(new UserBasedPoint(new UserBasedPointPK(prefId, userId), sim));

                    }//end if userId != prefId
                }//end if for pref
            }//end if for user

            offset += fetch;
            fetch += FETCH_RECORD;
            params.put("ids", Arrays.asList(calculatedUserId));
            users = userRepository.findMany("User.findAllIdNotInList", params, offset, fetch);
        }

        if (!result.isEmpty()) {
            userBasedRepository.createOrUpdateRange(result);
        }

    }

}
