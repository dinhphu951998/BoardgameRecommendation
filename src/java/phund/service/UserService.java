/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.security.NoSuchAlgorithmException;
import phund.entity.User;

/**
 *
 * @author PhuNDSE63159
 */
public interface UserService {

    User createAnonymousUser() throws NoSuchAlgorithmException;

    User getUserByIdAndUserToken(Integer id, String userToken);

}
