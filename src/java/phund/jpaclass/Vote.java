/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.jpaclass;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author PhuNDSE63159
 */
@Entity
@Table(name = "Vote")
@NamedQueries({
    @NamedQuery(name = "Vote.findAll", query = "SELECT v FROM Vote v")
    , @NamedQuery(name = "Vote.findByUserId", query = "SELECT v FROM Vote v WHERE v.votePK.userId = :userId")
    , @NamedQuery(name = "Vote.findByGameId", query = "SELECT v FROM Vote v WHERE v.votePK.gameId = :gameId")
    , @NamedQuery(name = "Vote.findByPoint", query = "SELECT v FROM Vote v WHERE v.point = :point")})
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VotePK votePK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Point")
    private Double point;
    @JoinColumn(name = "GameId", referencedColumnName = "Id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Game game;
    @JoinColumn(name = "UserId", referencedColumnName = "Id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Vote() {
    }

    public Vote(VotePK votePK) {
        this.votePK = votePK;
    }

    public Vote(int userId, int gameId) {
        this.votePK = new VotePK(userId, gameId);
    }

    public VotePK getVotePK() {
        return votePK;
    }

    public void setVotePK(VotePK votePK) {
        this.votePK = votePK;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (votePK != null ? votePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vote)) {
            return false;
        }
        Vote other = (Vote) object;
        if ((this.votePK == null && other.votePK != null) || (this.votePK != null && !this.votePK.equals(other.votePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phund.jpaclass.Vote[ votePK=" + votePK + " ]";
    }
    
}
