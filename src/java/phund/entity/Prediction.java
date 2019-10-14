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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PhuNDSE63159
 */
@Entity
@Table(name = "Prediction", catalog = "BoardgameRecommendation", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prediction.findAll", query = "SELECT p FROM Prediction p")
    , @NamedQuery(name = "Prediction.findByUserId", query = "SELECT p FROM Prediction p WHERE p.predictionPK.userId = :userId")
    , @NamedQuery(name = "Prediction.findByItemId", query = "SELECT p FROM Prediction p WHERE p.predictionPK.itemId = :itemId")
    , @NamedQuery(name = "Prediction.findByPredictRatePoint", query = "SELECT p FROM Prediction p WHERE p.predictRatePoint = :predictRatePoint")})
public class Prediction implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PredictionPK predictionPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PredictRatePoint", nullable = false)
    private double predictRatePoint;
    @JoinColumn(name = "ItemId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Game game;
    @JoinColumn(name = "UserId", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Prediction() {
    }

    public Prediction(PredictionPK predictionPK) {
        this.predictionPK = predictionPK;
    }

    public Prediction(PredictionPK predictionPK, double predictRatePoint) {
        this.predictionPK = predictionPK;
        this.predictRatePoint = predictRatePoint;
    }

    public Prediction(int userId, int itemId) {
        this.predictionPK = new PredictionPK(userId, itemId);
    }

    public PredictionPK getPredictionPK() {
        return predictionPK;
    }

    public void setPredictionPK(PredictionPK predictionPK) {
        this.predictionPK = predictionPK;
    }

    public double getPredictRatePoint() {
        return predictRatePoint;
    }

    public void setPredictRatePoint(double predictRatePoint) {
        this.predictRatePoint = predictRatePoint;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (predictionPK != null ? predictionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prediction)) {
            return false;
        }
        Prediction other = (Prediction) object;
        if ((this.predictionPK == null && other.predictionPK != null) || (this.predictionPK != null && !this.predictionPK.equals(other.predictionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phund.entity.Prediction[ predictionPK=" + predictionPK + " ]";
    }
    
}
