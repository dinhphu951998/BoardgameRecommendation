/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import phund.entity.User;
import phund.repository.UserRepository;
import phund.repository.UserRepositoryImp;
import phund.utils.HashGenerator;

/**
 *
 * @author PhuNDSE63159
 */
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    public UserServiceImp() {
        userRepository = new UserRepositoryImp();
    }

    @Override
    public User createAnonymousUser() throws NoSuchAlgorithmException {
        String userToken = HashGenerator.generateKey();
        User user = new User();
        user.setUserToken(userToken);
        userRepository.create(user);
        return user;
    }

    @Override
    public User getUserByIdAndUserToken(Integer id, String userToken) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("userToken", userToken);
        return userRepository.find("User.findByIdAndUserToken", params);
    }

}
