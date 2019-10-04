/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import phund.jpaclass.Account;
import phund.repository.AccountRepositoryImp;
import phund.repository.AccountRepository;

/**
 *
 * @author PhuNDSE63159
 */
public class AccountServiceImp implements AccountService{
 
    private AccountRepository accountRepository;

    public AccountServiceImp() {
        accountRepository = new AccountRepositoryImp();
    }
    
    @Override
    public Account login(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }
    
}
