/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.jpaclass;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author PhuNDSE63159
 */
@Embeddable
public class UserBasedPointPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "UserId")
    private int userId;
    @Basic(optional = false)
    @Column(name = "PrefId")
    private int prefId;

    public UserBasedPointPK() {
    }

    public UserBasedPointPK(int userId, int prefId) {
        this.userId = userId;
        this.prefId = prefId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        hash += (int) userId;
        hash += (int) prefId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserBasedPointPK)) {
            return false;
        }
        UserBasedPointPK other = (UserBasedPointPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.prefId != other.prefId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phund.jpaclass.UserBasedPointPK[ userId=" + userId + ", prefId=" + prefId + " ]";
    }
    
}
