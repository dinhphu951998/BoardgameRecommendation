/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import java.util.List;
import phund.entity.CommonUserVote;
import phund.entity.CommonVote;
import phund.entity.Vote;
import phund.entity.VotePK;

/**
 *
 * @author PhuNDSE63159
 */
public interface VoteRepository extends BaseRepository<Vote, VotePK> {

    List<CommonVote> getCommonVotes(int userId, int prefId);

    int countVote(int userId);

    List<CommonUserVote> getCommonUserVotes(int gameId, int prefId);
}
