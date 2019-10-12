/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import phund.entity.CommonVote;
import phund.entity.UserBasedPoint;
import phund.entity.UserBasedPointPK;
import phund.utils.JPAUtils;

/**
 *
 * @author PhuNDSE63159
 */
public class UserBasedRepositoryImp
        extends BaseRepositoryImp<UserBasedPoint, UserBasedPointPK>
        implements UserBasedRepository {

    

    public UserBasedRepositoryImp() {
        super(UserBasedPoint.class);
    }

    
}
