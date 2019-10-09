/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import phund.entity.User;

/**
 *
 * @author PhuNDSE63159
 */
public class UserRepositoryImp extends BaseRepositoryImp<User, Integer> 
        implements UserRepository {

    public UserRepositoryImp() {
        super(User.class);
    }

}
