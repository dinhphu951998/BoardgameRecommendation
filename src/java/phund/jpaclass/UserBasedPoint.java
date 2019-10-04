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
@Table(name = "UserBasedPoint")
@NamedQueries({
    @NamedQuery(name = "UserBasedPoint.findAll", query = "SELECT u FROM UserBasedPoint u")
    , @NamedQuery(name = "UserBasedPoint.findByUserId", query = "SELECT u FROM UserBasedPoint u WHERE u.userBasedPointPK.userId = :userId")
    , @NamedQuery(name = "UserBasedPoint.findByPrefId", query = "SELECT u FROM UserBasedPoint u WHERE u.userBasedPointPK.prefId = :prefId")
    , @NamedQuery(name = "UserBasedPoint.findBySimilarity", query = "SELECT u FROM UserBasedPoint u WHERE u.similarity = :similarity")})
public class UserBasedPoint implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserBasedPointPK userBasedPointPK;
    @Basic(optional = false)
    @Column(name = "Similarity")
    private double similarity;
    @JoinColumn(name = "UserId", referencedColumnName = "Id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "PrefId", referencedColumnName = "Id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user1;

    public UserBasedPoint() {
    }

    public UserBasedPoint(UserBasedPointPK userBasedPointPK) {
        this.userBasedPointPK = userBasedPointPK;
    }

    public UserBasedPoint(UserBasedPointPK userBasedPointPK, double similarity) {
        this.userBasedPointPK = userBasedPointPK;
        this.similarity = similarity;
    }

    public UserBasedPoint(int userId, int prefId) {
        this.userBasedPointPK = new UserBasedPointPK(userId, prefId);
    }

    public UserBasedPointPK getUserBasedPointPK() {
        return userBasedPointPK;
    }

    public void setUserBasedPointPK(UserBasedPointPK userBasedPointPK) {
        this.userBasedPointPK = userBasedPointPK;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userBasedPointPK != null ? userBasedPointPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserBasedPoint)) {
            return false;
        }
        UserBasedPoint other = (UserBasedPoint) object;
        if ((this.userBasedPointPK == null && other.userBasedPointPK != null) || (this.userBasedPointPK != null && !this.userBasedPointPK.equals(other.userBasedPointPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phund.jpaclass.UserBasedPoint[ userBasedPointPK=" + userBasedPointPK + " ]";
    }
    
}
