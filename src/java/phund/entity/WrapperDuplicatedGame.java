/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PhuNDSE63159
 */
@XmlRootElement
public class WrapperDuplicatedGame implements Serializable {

    private List<DuplicatedGame> games;

    public WrapperDuplicatedGame() {
        games = new ArrayList<>();
    }

    public WrapperDuplicatedGame(List<DuplicatedGame> games) {
        this.games = games;
    }

    public List<DuplicatedGame> getGames() {
        return games;
    }

    public void setGames(List<DuplicatedGame> games) {
        this.games = games;
    }

    public void add(Game game, Game duplicate){
        games.add(new DuplicatedGame(game, duplicate));
    }
    
    public int size(){
        return games.size();
    }
}
