/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.service;

import phund.repository.GameRepository;
import phund.repository.GameRepositoryImp;

/**
 *
 * @author PhuNDSE63159
 */
public class GameServiceImp implements GameService {

    private GameRepository gameRepository = null;

    public GameServiceImp() {
        gameRepository = new GameRepositoryImp();
    }

}
