/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author PhuNDSE63159
 */
@XmlRootElement
@XmlType(propOrder = {
    "id",
    "title",
    "thumbnail",
    "ratingPoint"
})
public class TrendGame implements Serializable {

    private Integer id;
    private String title;
    private String thumbnail;
    private Double ratingPoint;

    public TrendGame() {
    }

    public TrendGame(Integer id, String title, String thumbnail, Double ratingPoint) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.ratingPoint = ratingPoint;
    }

    /**
     * @return the id
     */
    @XmlElement
    public Integer getId() {
        return id;
    }

    /**
     * @return the title
     */
    @XmlElement
    public String getTitle() {
        return title;
    }

    /**
     * @return the thumbnail
     */
    @XmlElement
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @return the ratingPoint
     */
    @XmlElement
    public Double getRatingPoint() {
        if (ratingPoint != null) {
            return Double.parseDouble(String.format("%.1f", ratingPoint));
        }
        return ratingPoint;
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
     * @param ratingPoint the ratingPoint to set
     */
    public void setRatingPoint(Double ratingPoint) {
        this.ratingPoint = ratingPoint;
    }

}
