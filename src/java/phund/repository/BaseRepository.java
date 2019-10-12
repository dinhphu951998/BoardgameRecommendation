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
    
    void createRange(List<T> entities);
    
    T update(T entity);
    
    T find(String namedQuery, 
            Map<String, Object> parameters);
    
    List findMany(String namedQuery, Map<String, Object> parameters, 
            Integer offset, Integer fetchNext);
    
    List findMany(String namedQuery, Class type, Map<String, Object> parameters, 
            Integer offset, Integer fetchNext);
    
    void updateRange(List<T> entities);
    
    boolean exist(Object Pkey);
    
    void createOrUpdateRange(List<T> entities);
    
    void createOrUpdate(T entity);
}