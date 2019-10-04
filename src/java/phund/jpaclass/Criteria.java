/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.jpaclass;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "Criteria")
@NamedQueries({
    @NamedQuery(name = "Criteria.findAll", query = "SELECT c FROM Criteria c")
    , @NamedQuery(name = "Criteria.findById", query = "SELECT c FROM Criteria c WHERE c.id = :id")
    , @NamedQuery(name = "Criteria.findByMinAge", query = "SELECT c FROM Criteria c WHERE c.minAge = :minAge")
    , @NamedQuery(name = "Criteria.findByMaxAge", query = "SELECT c FROM Criteria c WHERE c.maxAge = :maxAge")
    , @NamedQuery(name = "Criteria.findByMinTime", query = "SELECT c FROM Criteria c WHERE c.minTime = :minTime")
    , @NamedQuery(name = "Criteria.findByMaxTime", query = "SELECT c FROM Criteria c WHERE c.maxTime = :maxTime")
    , @NamedQuery(name = "Criteria.findByMinPlayer", query = "SELECT c FROM Criteria c WHERE c.minPlayer = :minPlayer")
    , @NamedQuery(name = "Criteria.findByMaxPlayer", query = "SELECT c FROM Criteria c WHERE c.maxPlayer = :maxPlayer")})
public class Criteria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "MinAge")
    private Integer minAge;
    @Column(name = "MaxAge")
    private Integer maxAge;
    @Column(name = "MinTime")
    private Integer minTime;
    @Column(name = "MaxTime")
    private Integer maxTime;
    @Column(name = "MinPlayer")
    private Integer minPlayer;
    @Column(name = "MaxPlayer")
    private Integer maxPlayer;
    @JoinColumn(name = "GameId", referencedColumnName = "Id")
    @ManyToOne
    private Game gameId;

    public Criteria() {
    }

    public Criteria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinTime() {
        return minTime;
    }

    public void setMinTime(Integer minTime) {
        this.minTime = minTime;
    }

    public Integer getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }

    public Integer getMinPlayer() {
        return minPlayer;
    }

    public void setMinPlayer(Integer minPlayer) {
        this.minPlayer = minPlayer;
    }

    public Integer getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(Integer maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public Game getGameId() {
        return gameId;
    }

    public void setGameId(Game gameId) {
        this.gameId = gameId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Criteria)) {
            return false;
        }
        Criteria other = (Criteria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phund.jpaclass.Criteria[ id=" + id + " ]";
    }
    
}
