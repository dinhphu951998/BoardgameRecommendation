/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.entity;

import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PhuNDSE63159
 */
@XmlRootElement
public class SuggestedGame {

    private double similarity;
    private double prefPoint;
    private Integer matchingPercent;
    private String title;
    private String thumbnail;
    private Integer id;

    public SuggestedGame(double similarity, double prefPoint, int id, String title, String thumbnail) {
        this.similarity = similarity;
        this.prefPoint = prefPoint;
        this.matchingPercent = matchingPercent;
        this.title = title;
        this.thumbnail = thumbnail;
        this.id = id;
    }

    public SuggestedGame() {
    }

    /**
     * @return the similarity
     */
    @XmlElement
    public double getSimilarity() {
        return similarity;
    }

    /**
     * @param similarity the similarity to set
     */
    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    /**
     * @return the prefPoint
     */
    @XmlElement
    public double getPrefPoint() {
        return prefPoint;
    }

    /**
     * @param prefPoint the prefPoint to set
     */
    public void setPrefPoint(double prefPoint) {
        this.prefPoint = prefPoint;
    }

    /**
     * @return the matchingPercent
     */
    @XmlElement
    public int getMatchingPercent() {
        return matchingPercent;
    }

    /**
     * @param matchingPercent the matchingPercent to set
     */
    public void setMatchingPercent(int matchingPercent) {
        this.matchingPercent = matchingPercent;
    }

    /**
     * @return the title
     */
    @XmlElement
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the thumbnail
     */
    @XmlElement
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @param thumbnail the thumbnail to set
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @return the id
     */
    @XmlElement
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SuggestedGame)) {
            return false;
        }
        SuggestedGame other = (SuggestedGame) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

}
