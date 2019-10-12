/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import phund.constant.Constant;
import phund.entity.User;
import phund.entity.Vote;
import phund.entity.VotePK;
import phund.repository.VoteRepository;
import phund.repository.VoteRepositoryImp;
import java.util.Map;
import java.util.stream.Collectors;
import phund.entity.Game;

/**
 *
 * @author PhuNDSE63159
 */
public class VoteServiceImp implements VoteService {

    private VoteRepository voteRepository;

    public VoteServiceImp() {
        voteRepository = new VoteRepositoryImp();
    }

    @Override
    public void voteGame(int gameId, int userId, double point) {
        Vote v = new Vote(userId, gameId);
        v.setPoint(point);
        v.setTime(Calendar.getInstance().getTime());
        voteRepository.createOrUpdate(v);
    }

    @Override
    public void voteManyGame(List<Vote> votes, int userId) {
//        List<Vote> updateVote = new ArrayList<>();
//        List<Vote> createVote = new ArrayList<>();
//
//        for (Vote vote : votes) {
//            vote.getVotePK().setUserId(userId);
//            boolean exist = voteRepository.exist(vote.getVotePK());
//            if (exist) {
//                updateVote.add(vote);
//            } else {
//                createVote.add(vote);
//            }
//        }
//
//        if (!updateVote.isEmpty()) {
//            voteRepository.updateRange(updateVote);
//        }
//        if (!createVote.isEmpty()) {;
//            voteRepository.createRange(createVote);
//        }
        Calendar calendar = Calendar.getInstance();
        for (Vote vote : votes) {
            vote.getVotePK().setUserId(userId);
            vote.setTime(calendar.getTime());
        }
        voteRepository.createOrUpdateRange(votes);
    }

//    public List<Game> getVotedGame(int userId) {
//        List<Game> games = null;
//        Map<String, Object> params = new HashMap();
//        params.put("userId", userId);
//        List<Vote> votes = voteRepository.findMany("Vote.findByUserId", params, null, null);
//        if (votes != null && !votes.isEmpty()) {
//            games = votes.stream().map(v -> v.getGame()).collect(Collectors.toList());
//        }
//        return games;
//    }

}
