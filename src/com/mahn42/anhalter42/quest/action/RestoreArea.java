/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.framework.BlockArea;
import com.mahn42.framework.BlockPosition;

/**
 *
 * @author andre
 */
public class RestoreArea extends Action {
    // META
    public String name;
    public BlockPosition to = new BlockPosition(-1,-1,-1);
    public boolean mirrorX = false;
    public boolean mirrorY = false;
    public boolean mirrorZ = false;
    public boolean swapXZ = false;
    public BlockArea.BlockAreaPlaceMode mode = BlockArea.BlockAreaPlaceMode.full;
    
    @Override
    public void initialize() {
        super.initialize();
        
    }
    
    @Override
    public void execute() {
        StoreArea.Item lItem = StoreArea.areas.get(name);
        if (lItem != null) {
            BlockPosition lPos;
            if (to.x == -1 && to.y == -1 && to.z == -1) {
                lPos = lItem.edge;
            } else {
                lPos = to;
            }
            lItem.area.toList(quest.syncList, lPos, mirrorX, mirrorZ, mirrorY, swapXZ, mode);
        }
    }
}
