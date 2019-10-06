/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import phund.entity.Game;
import phund.entity.Image;

/**
 *
 * @author PhuNDSE63159
 */
public class ImageRepositoryImp extends BaseRepositoryImp<Image, Integer> implements ImageRepository {

    public ImageRepositoryImp() {
        super(Image.class);
    }

}
