/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.entity;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author PhuNDSE63159
 */
@XmlRootElement
@XmlType(propOrder = {
   "checkedGame",
    "duplicatedGame"
})
public class DuplicatedGame implements Serializable {

    private Game checkedGame;
    private Game duplicatedGame;

    public DuplicatedGame() {
    }

    public DuplicatedGame(Game game, Game duplicatedGame) {
        this.checkedGame = game;
        this.duplicatedGame = duplicatedGame;
    }

    /**
     * @return the checkedGame
     */
    public Game getCheckedGame() {
        return checkedGame;
    }

    /**
     * @return the duplicatedGame
     */
    public Game getDuplicatedGame() {
        return duplicatedGame;
    }

    /**
     * @param checkedGame the checkedGame to set
     */
    public void setCheckedGame(Game checkedGame) {
        this.checkedGame = checkedGame;
    }

    /**
     * @param duplicatedGame the duplicatedGame to set
     */
    public void setDuplicatedGame(Game duplicatedGame) {
        this.duplicatedGame = duplicatedGame;
    }

}
