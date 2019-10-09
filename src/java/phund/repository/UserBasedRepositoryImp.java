/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import phund.entity.UserBasedPoint;
import phund.entity.UserBasedPointPK;

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
