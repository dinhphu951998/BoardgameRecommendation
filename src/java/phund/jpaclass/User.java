/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.jpaclass;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author PhuNDSE63159
 */
@Entity
@Table(name = "User")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id")
    , @NamedQuery(name = "User.findByUserToken", query = "SELECT u FROM User u WHERE u.userToken = :userToken")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "UserToken")
    private String userToken;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Vote> voteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<UserBasedPoint> userBasedPointCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user1")
    private Collection<UserBasedPoint> userBasedPointCollection1;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Collection<Vote> getVoteCollection() {
        return voteCollection;
    }

    public void setVoteCollection(Collection<Vote> voteCollection) {
        this.voteCollection = voteCollection;
    }

    public Collection<UserBasedPoint> getUserBasedPointCollection() {
        return userBasedPointCollection;
    }

    public void setUserBasedPointCollection(Collection<UserBasedPoint> userBasedPointCollection) {
        this.userBasedPointCollection = userBasedPointCollection;
    }

    public Collection<UserBasedPoint> getUserBasedPointCollection1() {
        return userBasedPointCollection1;
    }

    public void setUserBasedPointCollection1(Collection<UserBasedPoint> userBasedPointCollection1) {
        this.userBasedPointCollection1 = userBasedPointCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "phund.jpaclass.User[ id=" + id + " ]";
    }
    
}
