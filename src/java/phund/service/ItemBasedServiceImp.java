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
import phund.entity.ItemBasedPoint;
import phund.entity.ItemBasedPointPK;
import phund.repository.ItemBasedRepository;
import phund.repository.ItemBasedRepositoryImp;

/**
 *
 * @author PhuNDSE63159
 */
public class ItemBasedServiceImp implements ItemBasedService {

    private VoteRepository voteRepository;
    private ItemBasedRepository itemBasedRepository;
    
    private Map<Integer, Map<Integer, Double>> itemMap;
    private List<ItemBasedPoint> result;
    

    public ItemBasedServiceImp() {
        voteRepository = new VoteRepositoryImp();
        itemBasedRepository = new ItemBasedRepositoryImp();
        
        itemMap = new HashMap<>();
        result = new ArrayList<>();
    }

    public void compute() {
        List<Vote> votes = voteRepository.findMany("Vote.findAll", null, null, 100);
        
        //init data set
        for (Vote vote : votes) {
            int gameId = vote.getVotePK().getGameId();
            Map<Integer, Double> tmp = itemMap.getOrDefault(gameId, new HashMap<>());
            tmp.put(vote.getVotePK().getUserId(), vote.getPoint());
            itemMap.put(gameId, tmp);
        }

        //start compare
        for (Map.Entry<Integer, Map<Integer, Double>> itemEntry : itemMap.entrySet()) {
            Integer gameId = itemEntry.getKey();

            for (Map.Entry<Integer, Map<Integer, Double>> entry : itemMap.entrySet()) {
                Integer prefId = entry.getKey();
                if(gameId != prefId){
                    double sim = computeSimilarity(gameId, prefId);
                    ItemBasedPoint itemBasedPoint = new ItemBasedPoint(new ItemBasedPointPK(gameId, prefId), sim);
                    result.add(itemBasedPoint);
                }
            }
        }
        
        itemBasedRepository.createRange(result);

    }

    private double computeSimilarity(int gameId, int prefId) {
        List<Integer> commonUsers = new ArrayList<>();
        Map<Integer, Double> gameIdVoting = itemMap.get(gameId);
        Map<Integer, Double> prefIdVoting = itemMap.get(prefId);

        for (Map.Entry<Integer, Double> entry : gameIdVoting.entrySet()) {
            int userId = entry.getKey();
            Double point = entry.getValue();
            if (prefIdVoting.containsKey(userId)) {
                commonUsers.add(userId);
            }
        }

        if (commonUsers.size() == 0) {
            return 0;
        }

        double sum1 = 0;
        double sum2 = 0;
        for (Integer common : commonUsers) {
            sum1 += gameIdVoting.get(common);
            sum2 += prefIdVoting.get(common);
        }

        double sum1Sq = 0;
        double sum2Sq = 0;
        for (Integer common : commonUsers) {
            sum1Sq += Math.pow(gameIdVoting.get(common), 2);
            sum2Sq += Math.pow(prefIdVoting.get(common), 2);
        }

        double pSum = 0;
        for (Integer common : commonUsers) {
            pSum += gameIdVoting.get(common) * prefIdVoting.get(common);
        }
        int n = commonUsers.size();
        double num = pSum - (sum1 * sum2) / n;
        double den = Math.sqrt((sum1Sq - Math.pow(sum1, 2) / n) * (sum2Sq - Math.pow(sum2, 2) / n));
        if (den == 0) {
            return 0;
        }

        return num / den;
    }

}
