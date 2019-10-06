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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PhuNDSE63159
 */
@Entity
@Table(name = "Game", catalog = "BoardgameRecommendation" , schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g")
    , @NamedQuery(name = "Game.findById", query = "SELECT g FROM Game g WHERE g.id = :id")
    , @NamedQuery(name = "Game.findByTitle", query = "SELECT g FROM Game g WHERE g.title = :title")
    , @NamedQuery(name = "Game.findByCategory", query = "SELECT g FROM Game g WHERE g.category = :category")
    , @NamedQuery(name = "Game.findByThumbnail", query = "SELECT g FROM Game g WHERE g.thumbnail = :thumbnail")
    , @NamedQuery(name = "Game.findByDescription", query = "SELECT g FROM Game g WHERE g.description = :description")
    , @NamedQuery(name = "Game.findByLink", query = "SELECT g FROM Game g WHERE g.link = :link")})

@XmlRootElement(name = "game")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id", nullable = false)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Collection<Vote> voteCollection;
    @OneToMany(mappedBy = "gameId")
    private Collection<Criteria> criteriaCollection;
    @OneToMany(mappedBy = "gameId")
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

    @XmlTransient
    public Collection<Vote> getVoteCollection() {
        return voteCollection;
    }

    public void setVoteCollection(Collection<Vote> voteCollection) {
        this.voteCollection = voteCollection;
    }

    @XmlTransient
    public Collection<Criteria> getCriteriaCollection() {
        return criteriaCollection;
    }

    public void setCriteriaCollection(Collection<Criteria> criteriaCollection) {
        this.criteriaCollection = criteriaCollection;
    }

    @XmlTransient
    public Collection<Image> getImageCollection() {
        return images;
    }

    public void setImageCollection(Collection<Image> imageCollection) {
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
        return "phund.entity.Game[ id=" + id + " ]";
    }
    
}
