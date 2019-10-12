/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PhuNDSE63159
 */
@XmlRootElement
public class VotedGame {

    private Integer id;
    private String title;
    private String thumbnail;
    private Double point;

    public VotedGame() {
    }

    public VotedGame(Integer id, String title, String thumbnail, Double point) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.point = point;
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
     * @return the point
     */
    @XmlElement
    public Double getPoint() {
        return point;
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
     * @param point the point to set
     */
    public void setPoint(Double point) {
        this.point = point;
    }

}
