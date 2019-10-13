/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author PhuNDSE63159
 */
@Entity
@Table(name = "Game", catalog = "BoardgameRecommendation", schema = "dbo")
@XmlRootElement
@XmlType(name = "game", propOrder = {
    "id",
    "title",
    "category",
    "thumbnail",
    "description",
    "link",
    "images",
    "minAge",
    "maxAge",
    "minTime",
    "maxTime",
    "minPlayer",
    "maxPlayer",
    "ratingPoint"
})
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g")
    , @NamedQuery(name = "Game.findAllGetTitle", query = "SELECT g.id, g.title FROM Game g")
    , @NamedQuery(name = "Game.findById", query = "SELECT g FROM Game g WHERE g.id = :id")
    , @NamedQuery(name = "Game.findByTitle", query = "SELECT g FROM Game g WHERE g.title = :title")
    , @NamedQuery(name = "Game.findByCategory", query = "SELECT g FROM Game g WHERE g.category = :category")
    , @NamedQuery(name = "Game.findByThumbnail", query = "SELECT g FROM Game g WHERE g.thumbnail = :thumbnail")
    , @NamedQuery(name = "Game.findByDescription", query = "SELECT g FROM Game g WHERE g.description = :description")
    , @NamedQuery(name = "Game.findByLink", query = "SELECT g FROM Game g WHERE g.link = :link")
    , @NamedQuery(name = "Game.findByMinAge", query = "SELECT g FROM Game g WHERE g.minAge = :minAge")
    , @NamedQuery(name = "Game.findByMaxAge", query = "SELECT g FROM Game g WHERE g.maxAge = :maxAge")
    , @NamedQuery(name = "Game.findByMinTime", query = "SELECT g FROM Game g WHERE g.minTime = :minTime")
    , @NamedQuery(name = "Game.findByMaxTime", query = "SELECT g FROM Game g WHERE g.maxTime = :maxTime")
    , @NamedQuery(name = "Game.findByMinPlayer", query = "SELECT g FROM Game g WHERE g.minPlayer = :minPlayer")
    , @NamedQuery(name = "Game.findByMaxPlayer", query = "SELECT g FROM Game g WHERE g.maxPlayer = :maxPlayer")
    , @NamedQuery(name = "Game.findByRatingPoint",
            query = "SELECT g.id, g.title, g.thumbnail, g.ratingPoint FROM Game g ORDER BY g.ratingPoint desc")
    , @NamedQuery(name = "Game.findGameVoteNotEmpty", query = "SELECT g FROM Game g join g.votes v group by g having count(v.votePK.userId) > 0")
    , @NamedQuery(name = "Game.findVotedGame",
            query = "SELECT g.id, g.title, g.thumbnail, v.point FROM Game g join g.votes v where v.votePK.userId = :userId")})

@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "SuggestedGame",
            classes = {
                @ConstructorResult(targetClass = SuggestedGame.class,
                        columns = {
                            @ColumnResult(name = "similarity", type = Double.class)
                            ,@ColumnResult(name = "prefPoint", type = Double.class)
                            ,@ColumnResult(name = "id", type = Integer.class)
                            ,@ColumnResult(name = "title", type = String.class)
                            ,@ColumnResult(name = "thumbnail", type = String.class),})
            }
    )
    ,
    @SqlResultSetMapping(
            name = "VotedGame",
            classes = {
                @ConstructorResult(targetClass = VotedGame.class,
                        columns = {
                            @ColumnResult(name = "id", type = Integer.class)
                            ,@ColumnResult(name = "title", type = String.class)
                            ,@ColumnResult(name = "thumbnail", type = String.class)
                            ,@ColumnResult(name = "point", type = Double.class)})
            }
    )
    ,
    @SqlResultSetMapping(
            name = "TrendGame",
            classes = {
                @ConstructorResult(targetClass = TrendGame.class,
                        columns = {
                            @ColumnResult(name = "id", type = Integer.class)
                            ,@ColumnResult(name = "title", type = String.class)
                            ,@ColumnResult(name = "thumbnail", type = String.class)
                            ,@ColumnResult(name = "ratingPoint", type = Double.class)})
            }
    )
})

@XmlAccessorType(XmlAccessType.NONE)
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "Title", length = 100)
    private String title;
    @Column(name = "Category", length = 255)
    private String category;
    @Column(name = "Thumbnail", length = 255)
    private String thumbnail;
    @Column(name = "Description", length = 2147483647)
    private String description;
    @Column(name = "Link", length = 255)
    private String link;
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
    @Column(name = "RatingPoint")
    private Double ratingPoint;

    @OneToMany(mappedBy = "game")
    private Collection<Vote> votes;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "gameId")
    private Collection<Image> images;

    public Game() {
    }

    public Game(Integer id) {
        this.id = id;
    }

    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @XmlElement
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @XmlElement
    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    @XmlElement
    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    @XmlElement
    public Integer getMinTime() {
        return minTime;
    }

    public void setMinTime(Integer minTime) {
        this.minTime = minTime;
    }

    @XmlElement
    public Integer getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }

    @XmlElement
    public Integer getMinPlayer() {
        return minPlayer;
    }

    public void setMinPlayer(Integer minPlayer) {
        this.minPlayer = minPlayer;
    }

    @XmlElement
    public Integer getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(Integer maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    @XmlElement
    public Double getRatingPoint() {
        return ratingPoint;
    }

    public void setRatingPoint(Double ratingPoint) {
        this.ratingPoint = ratingPoint;
    }

    @XmlTransient
    public Collection<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Collection<Vote> voteCollection) {
        this.votes = voteCollection;
    }

    @XmlElement
    @XmlTransient
    public Collection<Image> getImages() {
        return images;
    }

    public void setImages(Collection<Image> imageCollection) {
        this.images = imageCollection;
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
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phund.entity.Game[ id=" + id + " ]";
    }

}
