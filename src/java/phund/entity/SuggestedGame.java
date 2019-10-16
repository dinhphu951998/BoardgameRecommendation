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

    private Integer id;
    private String title;
    private String thumbnail;
    private Double matchingPercent;

    public SuggestedGame() {
    }

    public SuggestedGame(Integer id, String title, String thumbnail, Double matchingPercent) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.matchingPercent = matchingPercent;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @return the matchingPercent
     */
    public Double getMatchingPercent() {
        return Double.valueOf(String.format("%.2f", this.matchingPercent));
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param thumbnail the thumbnail to set
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @param matchingPercent the matchingPercent to set
     */
    public void setMatchingPercent(Double matchingPercent) {
        this.matchingPercent = matchingPercent;
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
