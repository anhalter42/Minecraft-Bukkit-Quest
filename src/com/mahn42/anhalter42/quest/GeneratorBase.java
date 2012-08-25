/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.BlockArea;
import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.SyncBlockList;

/**
 *
 * @author andre
 */
public class GeneratorBase extends QuestObject implements IGenerator {

    protected BlockPosition from;
    protected BlockPosition to;
    protected int width, height, depth;
    protected BlockArea area;

    @Override
    public void initialize(BlockPosition aFrom, BlockPosition aTo) {
        from = aFrom;
        to = aTo;
        BlockPosition lWHD = from.getWHD(to);
        width = lWHD.x;
        height = lWHD.y;
        depth = lWHD.z;
        area = new BlockArea(width, height, depth);
    }

    @Override
    public void execute(SyncBlockList aSyncList) {
    }
    
}
