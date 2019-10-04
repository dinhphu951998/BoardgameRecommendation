/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;

/**
 *
 * @author PhuNDSE63159
 */
public interface BaseRepository<T, PK extends Serializable> {
    
    T findById(PK primaryKey);
    
    void delete(T entity);
    
    T create(T entity);
    
    T update(T entity);
    
    Object find(String namedQuery, 
            Map<String, Object> parameters,
            boolean isCollection);
    
}