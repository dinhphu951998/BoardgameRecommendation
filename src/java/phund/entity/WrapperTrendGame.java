/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.entity;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PhuNDSE63159
 */
@XmlRootElement(name = "board-game")
public class WrapperTrendGame implements Serializable {

    private List<TrendGame> games;

    public WrapperTrendGame(List<TrendGame> games) {
        this.games = games;
    }

    public WrapperTrendGame() {
    }

    @XmlElement
    public List<TrendGame> getGames() {
        return games;
    }

    public void setGames(List<TrendGame> games) {
        this.games = games;
    }

}
