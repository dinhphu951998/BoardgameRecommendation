/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import java.util.List;
import javax.persistence.Query;
import phund.entity.CommonUserVote;
import phund.entity.CommonVote;
import phund.entity.Vote;
import phund.entity.VotePK;
import phund.utils.JPAUtils;

/**
 *
 * @author PhuNDSE63159
 */
public class VoteRepositoryImp extends BaseRepositoryImp<Vote, VotePK>
        implements VoteRepository {

    private final String GET_COMMON_VOTES
            = "select v.GameId as 'GameId', v.UserId as 'UserId', v.Point as 'UserPoint', "
            + "v1.UserId as 'PrefId', v1.Point as 'PrefPoint' "
            + "from Vote v join Vote v1 on v.GameId = v1.GameId "
            + "where v.UserId = ?userId and v1.UserId = ?prefId ";

    private final String COUNT_VOTES = "SELECT count(*) FROM Vote v WHERE v.userId = ?userId";

    private final String GET_COMMON_USER_VOTE = 
            "select  v.UserId as 'UserId', v.GameId as 'GameId', v.Point as 'GamePoint', "
            + "v1.GameId as 'PrefId', v1.Point as 'PrefPoint' "
            + "from Vote v join Vote v1 on v.UserId = v1.UserId "
            + "where v.GameId = ?gameId and v1.GameId = ?prefId ";

    public VoteRepositoryImp() {
        super(Vote.class);
    }

    public List<CommonVote> getCommonVotes(int userId, int prefId) {
        em = JPAUtils.getEntityManager();
        try {
            Query query = em.createNativeQuery(GET_COMMON_VOTES, "CommonVote");
            query.setParameter("userId", userId);
            query.setParameter("prefId", prefId);

            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            closeConnection();
        }
        return null;
    }

    public int countVote(int userId) {
        em = JPAUtils.getEntityManager();
        try {
            Query query = em.createNativeQuery(COUNT_VOTES);
            query.setParameter("userId", userId);

            Integer result = (Integer) query.getSingleResult();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            closeConnection();
        }
        return 0;
    }

    public List<CommonUserVote> getCommonUserVotes(int gameId, int prefId) {
        em = JPAUtils.getEntityManager();
        try {
            Query query = em.createNativeQuery(GET_COMMON_USER_VOTE, "CommonUserVote");
            query.setParameter("gameId", gameId);
            query.setParameter("prefId", prefId);

            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            closeConnection();
        }
        return null;

    }

}
