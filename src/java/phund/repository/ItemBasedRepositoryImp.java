/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import java.io.Serializable;
import phund.entity.ItemBasedPoint;
import phund.entity.ItemBasedPointPK;

/**
 *
 * @author PhuNDSE63159
 */
public class ItemBasedRepositoryImp extends BaseRepositoryImp<ItemBasedPoint, ItemBasedPointPK>
        implements ItemBasedRepository{
    
    public ItemBasedRepositoryImp() {
        super(ItemBasedPoint.class);
    }
    
}
