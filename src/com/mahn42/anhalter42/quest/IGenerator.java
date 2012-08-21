/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.SyncBlockList;

/**
 *
 * @author andre
 */
public interface IGenerator {

    public void initialize(BlockPosition aFrom, BlockPosition aTo);

    public void execute(SyncBlockList aSyncList);
    
}
