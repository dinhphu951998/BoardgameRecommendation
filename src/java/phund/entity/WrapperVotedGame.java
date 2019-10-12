
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
public class WrapperVotedGame implements Serializable {

    private List<VotedGame> games;

    public WrapperVotedGame(List<VotedGame> games) {
        this.games = games;
    }

    public WrapperVotedGame() {
    }

    @XmlElement
    public List<VotedGame> getGames() {
        return games;
    }

    public void setGames(List<VotedGame> games) {
        this.games = games;
    }

}
