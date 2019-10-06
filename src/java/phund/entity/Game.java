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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PhuNDSE63159
 */
@Entity
@Table(name = "Game", catalog = "BoardgameRecommendation", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g")
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
    , @NamedQuery(name = "Game.findByMaxPlayer", query = "SELECT g FROM Game g WHERE g.maxPlayer = :maxPlayer")})
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Collection<Vote> voteCollection;
    @OneToMany(mappedBy = "gameId", cascade = CascadeType.PERSIST)
    private Collection<Image> images;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Collection<ItemBasedPoint> itemBasedPointCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game1")
    private Collection<ItemBasedPoint> itemBasedPointCollection1;

    public Game() {
    }

    public Game(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    @XmlTransient
    public Collection<Vote> getVoteCollection() {
        return voteCollection;
    }

    public void setVoteCollection(Collection<Vote> voteCollection) {
        this.voteCollection = voteCollection;
    }

    public Collection<Image> getImages() {
        return images;
    }

    public void setImages(Collection<Image> imageCollection) {
        this.images = imageCollection;
    }

    @XmlTransient
    public Collection<ItemBasedPoint> getItemBasedPointCollection() {
        return itemBasedPointCollection;
    }

    public void setItemBasedPointCollection(Collection<ItemBasedPoint> itemBasedPointCollection) {
        this.itemBasedPointCollection = itemBasedPointCollection;
    }

    @XmlTransient
    public Collection<ItemBasedPoint> getItemBasedPointCollection1() {
        return itemBasedPointCollection1;
    }

    public void setItemBasedPointCollection1(Collection<ItemBasedPoint> itemBasedPointCollection1) {
        this.itemBasedPointCollection1 = itemBasedPointCollection1;
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
        return "phund.entity.Game[ id=" + id + ", images  = " + images.size() + "]";
    }

}
