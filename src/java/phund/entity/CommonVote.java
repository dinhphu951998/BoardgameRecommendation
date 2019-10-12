/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.entity;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.SqlResultSetMapping;


public class CommonVote {

    private int gameId;
    private int userId;
    private double userPoint;
    private int prefId;
    private double prefPoint;

    public CommonVote(int gameId, int userId, double userPoint, int prefId, double prefPoint) {
        this.gameId = gameId;
        this.userId = userId;
        this.userPoint = userPoint;
        this.prefId = prefId;
        this.prefPoint = prefPoint;
    }
    
    

    public CommonVote() {
    }

    /**
     * @return the gameId
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * @param gameId the gameId to set
     */
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the userPoint
     */
    public double getUserPoint() {
        return userPoint;
    }

    /**
     * @param userPoint the userPoint to set
     */
    public void setUserPoint(double userPoint) {
        this.userPoint = userPoint;
    }

    /**
     * @return the prefId
     */
    public int getPrefId() {
        return prefId;
    }

    /**
     * @param prefId the prefId to set
     */
    public void setPrefId(int prefId) {
        this.prefId = prefId;
    }

    /**
     * @return the prefPoint
     */
    public double getPrefPoint() {
        return prefPoint;
    }

    /**
     * @param prefPoint the prefPoint to set
     */
    public void setPrefPoint(double prefPoint) {
        this.prefPoint = prefPoint;
    }

}
