/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import phund.entity.Prediction;
import phund.entity.PredictionPK;

/**
 *
 * @author PhuNDSE63159
 */
public class PredictionRepositoryImp 
        extends BaseRepositoryImp<Prediction, PredictionPK> implements PredictionRepository {

    public PredictionRepositoryImp() {
        super(Prediction.class);
    }

}
