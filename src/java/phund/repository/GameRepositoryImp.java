/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import java.util.ArrayList;
import java.util.List;
import phund.entity.Game;
import phund.entity.Image;

/**
 *
 * @author PhuNDSE63159
 */
public class GameRepositoryImp extends BaseRepositoryImp<Game, Integer> implements GameRepository {

    public GameRepositoryImp() {
        super(Game.class);
    }

    @Override
    public void createRange(List<Game> entities) {
        for (Game game : entities) {
            addImage(game);
        }
        super.createRange(entities); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Game create(Game entity) {
        addImage(entity);
        return super.create(entity); //To change body of generated methods, choose Tools | Templates.
    }

    protected void addImage(Game game) {

        if (game.getImages() != null) {
            List<Image> images = new ArrayList<>(game.getImages());
            for (Image image : images) {
                image.setGameId(game);
            }
        }

    }

}
