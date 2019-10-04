
package phund.jaxbclass;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for criteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="criteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="minAge" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="maxAge" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="minTime" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="maxTime" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="minPlayer" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="maxPlayer" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="gameId" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "criteria", propOrder = {
    "minAge",
    "maxAge",
    "minTime",
    "maxTime",
    "minPlayer",
    "maxPlayer"
})
public class Criteria {

    @XmlElement(required = true)
    protected BigInteger minAge;
    @XmlElement(required = true)
    protected BigInteger maxAge;
    @XmlElement(required = true)
    protected BigInteger minTime;
    @XmlElement(required = true)
    protected BigInteger maxTime;
    @XmlElement(required = true)
    protected BigInteger minPlayer;
    @XmlElement(required = true)
    protected BigInteger maxPlayer;
    @XmlAttribute(name = "id")
    protected BigInteger id;
    @XmlAttribute(name = "gameId")
    protected BigInteger gameId;

    /**
     * Gets the value of the minAge property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMinAge() {
        return minAge;
    }

    /**
     * Sets the value of the minAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMinAge(BigInteger value) {
        this.minAge = value;
    }

    /**
     * Gets the value of the maxAge property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxAge() {
        return maxAge;
    }

    /**
     * Sets the value of the maxAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxAge(BigInteger value) {
        this.maxAge = value;
    }

    /**
     * Gets the value of the minTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMinTime() {
        return minTime;
    }

    /**
     * Sets the value of the minTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMinTime(BigInteger value) {
        this.minTime = value;
    }

    /**
     * Gets the value of the maxTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxTime() {
        return maxTime;
    }

    /**
     * Sets the value of the maxTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxTime(BigInteger value) {
        this.maxTime = value;
    }

    /**
     * Gets the value of the minPlayer property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMinPlayer() {
        return minPlayer;
    }

    /**
     * Sets the value of the minPlayer property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMinPlayer(BigInteger value) {
        this.minPlayer = value;
    }

    /**
     * Gets the value of the maxPlayer property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxPlayer() {
        return maxPlayer;
    }

    /**
     * Sets the value of the maxPlayer property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxPlayer(BigInteger value) {
        this.maxPlayer = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the gameId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGameId() {
        return gameId;
    }

    /**
     * Sets the value of the gameId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGameId(BigInteger value) {
        this.gameId = value;
    }

}
