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
public class WrapperSuggestedGame implements Serializable {

    private List<SuggestedGame> games;

    public WrapperSuggestedGame(List<SuggestedGame> games) {
        this.games = games;
    }

    public WrapperSuggestedGame() {
    }

    @XmlElement
    public List<SuggestedGame> getGames() {
        return games;
    }

    public void setGames(List<SuggestedGame> games) {
        this.games = games;
    }

}
