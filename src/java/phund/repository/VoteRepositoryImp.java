/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import phund.entity.Vote;
import phund.entity.VotePK;

/**
 *
 * @author PhuNDSE63159
 */
public class VoteRepositoryImp extends BaseRepositoryImp<Vote, VotePK>
        implements VoteRepository {

    public VoteRepositoryImp() {
        super(Vote.class);
    }

}
