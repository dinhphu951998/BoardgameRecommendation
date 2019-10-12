/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PhuNDSE63159
 */
@Entity
@Table(name = "Vote", catalog = "BoardgameRecommendation", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vote.findAll", query = "SELECT v FROM Vote v")
    , @NamedQuery(name = "Vote.findByUserId",
            query = "SELECT v FROM Vote v WHERE v.votePK.userId = :userId order by v.time desc")
    , @NamedQuery(name = "Vote.findByGameId",query = "SELECT v FROM Vote v WHERE v.votePK.gameId = :gameId order by v.time desc")
//    , @NamedQuery(name = "Vote.countByUserId",query = "SELECT count(v) FROM Vote v WHERE v.votePK.userId = :userId")
    , @NamedQuery(name = "Vote.findByPoint", query = "SELECT v FROM Vote v WHERE v.point = :point")})

@SqlResultSetMappings({
            @SqlResultSetMapping(
            name = "CommonVote",
            classes={
            @ConstructorResult(targetClass=CommonVote.class,
                columns={
                    @ColumnResult(name="gameId", type=Integer.class),
                    @ColumnResult(name="userId", type=Integer.class),
                    @ColumnResult(name="userPoint", type=Double.class),
                    @ColumnResult(name="prefId", type=Integer.class),
                    @ColumnResult(name="prefPoint", type=Double.class)
                })
            }),
            @SqlResultSetMapping(
            name = "CommonUserVote",
            classes={
            @ConstructorResult(targetClass=CommonUserVote.class,
                columns={
                    @ColumnResult(name="userId", type=Integer.class),
                    @ColumnResult(name="gameId", type=Integer.class),
                    @ColumnResult(name="gamePoint", type=Double.class),
                    @ColumnResult(name="prefId", type=Integer.class),
                    @ColumnResult(name="prefPoint", type=Double.class)
                })
            })
        }
)
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VotePK votePK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Point", precision = 53)
    private Double point;
    @JoinColumn(name = "GameId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Game game;
    @JoinColumn(name = "UserId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @Column(name = "Time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public Vote() {
    }

    public Vote(VotePK votePK) {
        this.votePK = votePK;
    }

    public Vote(int userId, int gameId) {
        this.votePK = new VotePK(userId, gameId);
    }

    @XmlElement
    public VotePK getVotePK() {
        return votePK;
    }

    public void setVotePK(VotePK votePK) {
        this.votePK = votePK;
    }

    @XmlElement
    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    @XmlTransient
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @XmlTransient
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
        return "phund.entity.Vote[ votePK=" + votePK + " ]";
    }

    @XmlElement
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
