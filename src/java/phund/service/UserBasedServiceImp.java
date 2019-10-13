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
import phund.entity.CommonVote;
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

    private final int FETCH_RECORD = 100;

    public UserBasedServiceImp() {
        userBasedRepository = new UserBasedRepositoryImp();
        voteRepository = new VoteRepositoryImp();
        userRepository = new UserRepositoryImp();

        userMap = new HashMap<>();
        result = new ArrayList<>();
    }

    private double computeSimilarityBetweenUsers(int userId, int prefId) {
        List<CommonVote> commonVotes = voteRepository.getCommonVotes(userId, prefId);
        if (commonVotes == null || commonVotes.isEmpty()) {
            return 0;
        }

        double sum = 0;
        for (CommonVote vote : commonVotes) {
            double point1 = vote.getUserPoint();
            double point2 = vote.getPrefPoint();
            sum += Math.pow(point1 - point2, 2);
        }

        return 1 / (1 + sum);
    }

    public void computeAll() {
        int offset = 0;
        int fetch = FETCH_RECORD;

        List<User> users = userRepository.findMany("User.findAll", null, offset, fetch);
        List<UserBasedPointPK> computedUserIds = new ArrayList<>();

        while (users != null && !users.isEmpty()) {
            for (User user : users) {
                for (User pref : users) {
                    int userId = user.getId();
                    int prefId = pref.getId();
                    UserBasedPointPK userPK = new UserBasedPointPK(userId, prefId);
                    if (userId != prefId
                            && !computedUserIds.contains(userPK)) {

                        computedUserIds.add(userPK);
                        UserBasedPointPK prefPK = new UserBasedPointPK(prefId, userId);
                        computedUserIds.add(prefPK);
                        double sim = computeSimilarityBetweenUsers(userId, prefId);

                        if (sim != 0) {
                            result.add(new UserBasedPoint(userPK, sim));
                            result.add(new UserBasedPoint(prefPK, sim));
                        }

                    }//end if userId != prefId
                }//end if for pref
            }//end if for user

            offset += fetch;
            users = userRepository.findMany("User.findAll", null, offset, fetch);
        }

        if (!result.isEmpty()) {
            userBasedRepository.createOrUpdateRange(result);
        }

    }

//    public void compute() {
//        List<Vote> votes = voteRepository.findMany("Vote.findAll", null, null, 100);
//
//        //init data set
//        for (Vote vote : votes) {
//            int userId = vote.getVotePK().getUserId();
//            Map<Integer, Double> tmp = userMap.getOrDefault(userId, new HashMap<>());
//            tmp.put(vote.getVotePK().getGameId(), vote.getPoint());
//            userMap.put(userId, tmp);
//        }
//
//        //start compare
//        for (Map.Entry<Integer, Map<Integer, Double>> userEntry : userMap.entrySet()) {
//            Integer userId = userEntry.getKey();
//
//            for (Map.Entry<Integer, Map<Integer, Double>> entry : userMap.entrySet()) {
//                Integer prefId = entry.getKey();
//                if (userId != prefId) {
//                    double sim = computeSimilarityBetweenUsers(userId, prefId);
//                    UserBasedPoint userBasedPoint = new UserBasedPoint(new UserBasedPointPK(userId, prefId), sim);
//                    result.add(userBasedPoint);
//                }
//            }
//        }
//
//        List<UserBasedPoint> updatePoint = new ArrayList<>();
//        List<UserBasedPoint> createPoint = new ArrayList<>();
//
//        for (UserBasedPoint userBasedPoint : result) {
//            boolean exist = userBasedRepository.exist(userBasedPoint);
//            if (exist) {
//                updatePoint.add(userBasedPoint);
//            } else {
//                createPoint.add(userBasedPoint);
//            }
//        }
//
//        userBasedRepository.createRange(createPoint);
//        userBasedRepository.updateRange(createPoint);
//
//    }
}
