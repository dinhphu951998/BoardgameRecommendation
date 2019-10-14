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
import javax.validation.constraints.NotNull;

/**
 *
 * @author PhuNDSE63159
 */
@Embeddable
public class PredictionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "UserId", nullable = false)
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ItemId", nullable = false)
    private int itemId;

    public PredictionPK() {
    }

    public PredictionPK(int userId, int itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) itemId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PredictionPK)) {
            return false;
        }
        PredictionPK other = (PredictionPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.itemId != other.itemId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phund.entity.PredictionPK[ userId=" + userId + ", itemId=" + itemId + " ]";
    }
    
}
