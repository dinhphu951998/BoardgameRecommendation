/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PhuNDSE63159
 */
@Entity
@Table(name = "UserBasedPoint", catalog = "BoardgameRecommendation", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserBasedPoint.findAll", query = "SELECT u FROM UserBasedPoint u")
    , @NamedQuery(name = "UserBasedPoint.findByPK", 
            query = "SELECT u FROM UserBasedPoint u "
                    + "WHERE u.userBasedPointPK.userId = :userId "
                    + "and u.userBasedPointPK.prefId = :prefId "
                    + "order by u.similarity desc ")
    , @NamedQuery(name = "UserBasedPoint.findByUserId", query = "SELECT u FROM UserBasedPoint u WHERE u.userBasedPointPK.userId = :userId")
    , @NamedQuery(name = "UserBasedPoint.findByPrefId", query = "SELECT u FROM UserBasedPoint u WHERE u.userBasedPointPK.prefId = :prefId")
    , @NamedQuery(name = "UserBasedPoint.findBySimilarity", query = "SELECT u FROM UserBasedPoint u WHERE u.similarity = :similarity")})
public class UserBasedPoint implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserBasedPointPK userBasedPointPK;
    @Basic(optional = false)
    @Column(name = "Similarity", nullable = false)
    private double similarity;

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
        return "phund.entity.UserBasedPoint[ userBasedPointPK=" + userBasedPointPK + " ]";
    }
    
}
