/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author PhuNDSE63159
 */
@Embeddable
public class VotePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "UserId", nullable = false)
    private int userId;
    @Basic(optional = false)
    @Column(name = "GameId", nullable = false)
    private int gameId;

    public VotePK() {
    }

    public VotePK(int userId, int gameId) {
        this.userId = userId;
        this.gameId = gameId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) gameId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotePK)) {
            return false;
        }
        VotePK other = (VotePK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phund.entity.VotePK[ userId=" + userId + ", gameId=" + gameId + " ]";
    }
    
}
