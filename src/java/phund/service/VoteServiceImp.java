/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import phund.constant.Constant;
import phund.entity.User;
import phund.entity.Vote;
import phund.entity.VotePK;
import phund.repository.VoteRepository;
import phund.repository.VoteRepositoryImp;

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

        Vote v = voteRepository.findById(new VotePK(userId, gameId));
        if (v != null) {
            v.setPoint(point);
            voteRepository.update(v);
        } else {
            v = new Vote(userId, gameId);
            v.setPoint(point);
            voteRepository.create(v);
        }

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

        for (Vote vote : votes) {
            vote.getVotePK().setUserId(userId);
        }
        voteRepository.createOrUpdateRange(votes);
    }

}
