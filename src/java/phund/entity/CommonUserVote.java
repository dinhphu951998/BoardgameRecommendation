/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.entity;

/**
 *
 * @author PhuNDSE63159
 */
public class CommonUserVote {

    private int userId;
    private int gameId;
    private double gamePoint;
    private int prefId;
    private double prefPoint;

    public CommonUserVote(int userId, int gameId, double gamePoint, int prefId, double prefPoint) {
        this.userId = userId;
        this.gameId = gameId;
        this.gamePoint = gamePoint;
        this.prefId = prefId;
        this.prefPoint = prefPoint;
    }

    public CommonUserVote() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public double getGamePoint() {
        return gamePoint;
    }

    public void setGamePoint(double gamePoint) {
        this.gamePoint = gamePoint;
    }

    public int getPrefId() {
        return prefId;
    }

    public void setPrefId(int prefId) {
        this.prefId = prefId;
    }

    public double getPrefPoint() {
        return prefPoint;
    }

    public void setPrefPoint(double prefPoint) {
        this.prefPoint = prefPoint;
    }

}
