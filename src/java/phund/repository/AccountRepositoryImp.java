/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Query;
import phund.entity.Account;

/**
 *
 * @author PhuNDSE63159
 */
public class AccountRepositoryImp extends BaseRepositoryImp<Account, String>
        implements AccountRepository {

    public AccountRepositoryImp() {
        super(Account.class);
    }

    @Override
    public Account findByUsernameAndPassword(String username, String password) {
        String namedQuery = "Account.findByUsernameAndPassword";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", password);
        return (Account) find(namedQuery, params, false);
    }

}
