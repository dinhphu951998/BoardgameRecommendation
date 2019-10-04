/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import phund.jpaclass.Account;

/**
 *
 * @author PhuNDSE63159
 */
public interface AccountService {
    Account login(String username, String password);
}
