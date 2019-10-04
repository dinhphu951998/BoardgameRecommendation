/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.jpaclass;

import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "ItemBasedPoint")
@NamedQueries({
    @NamedQuery(name = "ItemBasedPoint.findAll", query = "SELECT i FROM ItemBasedPoint i")
    , @NamedQuery(name = "ItemBasedPoint.findByItemId", query = "SELECT i FROM ItemBasedPoint i WHERE i.itemBasedPointPK.itemId = :itemId")
    , @NamedQuery(name = "ItemBasedPoint.findByPrefId", query = "SELECT i FROM ItemBasedPoint i WHERE i.itemBasedPointPK.prefId = :prefId")
    , @NamedQuery(name = "ItemBasedPoint.findBySimilarity", query = "SELECT i FROM ItemBasedPoint i WHERE i.similarity = :similarity")})
public class ItemBasedPoint implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItemBasedPointPK itemBasedPointPK;
    @Basic(optional = false)
    @Column(name = "Similarity")
    private double similarity;
    @JoinColumn(name = "ItemId", referencedColumnName = "Id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Game game;
    @JoinColumn(name = "PrefId", referencedColumnName = "Id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Game game1;

    public ItemBasedPoint() {
    }

    public ItemBasedPoint(ItemBasedPointPK itemBasedPointPK) {
        this.itemBasedPointPK = itemBasedPointPK;
    }

    public ItemBasedPoint(ItemBasedPointPK itemBasedPointPK, double similarity) {
        this.itemBasedPointPK = itemBasedPointPK;
        this.similarity = similarity;
    }

    public ItemBasedPoint(int itemId, int prefId) {
        this.itemBasedPointPK = new ItemBasedPointPK(itemId, prefId);
    }

    public ItemBasedPointPK getItemBasedPointPK() {
        return itemBasedPointPK;
    }

    public void setItemBasedPointPK(ItemBasedPointPK itemBasedPointPK) {
        this.itemBasedPointPK = itemBasedPointPK;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame1() {
        return game1;
    }

    public void setGame1(Game game1) {
        this.game1 = game1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemBasedPointPK != null ? itemBasedPointPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemBasedPoint)) {
            return false;
        }
        ItemBasedPoint other = (ItemBasedPoint) object;
        if ((this.itemBasedPointPK == null && other.itemBasedPointPK != null) || (this.itemBasedPointPK != null && !this.itemBasedPointPK.equals(other.itemBasedPointPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phund.jpaclass.ItemBasedPoint[ itemBasedPointPK=" + itemBasedPointPK + " ]";
    }
    
}
