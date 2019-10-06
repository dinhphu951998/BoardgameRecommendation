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
public class ItemBasedPointPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ItemId", nullable = false)
    private int itemId;
    @Basic(optional = false)
    @Column(name = "PrefId", nullable = false)
    private int prefId;

    public ItemBasedPointPK() {
    }

    public ItemBasedPointPK(int itemId, int prefId) {
        this.itemId = itemId;
        this.prefId = prefId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getPrefId() {
        return prefId;
    }

    public void setPrefId(int prefId) {
        this.prefId = prefId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) itemId;
        hash += (int) prefId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemBasedPointPK)) {
            return false;
        }
        ItemBasedPointPK other = (ItemBasedPointPK) object;
        if (this.itemId != other.itemId) {
            return false;
        }
        if (this.prefId != other.prefId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phund.entity.ItemBasedPointPK[ itemId=" + itemId + ", prefId=" + prefId + " ]";
    }
    
}
