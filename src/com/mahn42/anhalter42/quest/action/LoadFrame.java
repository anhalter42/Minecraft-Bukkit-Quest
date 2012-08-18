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
public class LoadFrame extends Action {
    public int index;
    public BlockPosition pos = new BlockPosition(0,0,0); // relative pos
    public BlockArea.BlockAreaPlaceMode mode = BlockArea.BlockAreaPlaceMode.full;
    
    @Override
    public void execute() {
        BlockPosition lPos = quest.edge1.clone();
        lPos.add(pos);
        quest.frames.toList(index, quest.syncList, lPos, false, false, false, false, mode);
    }
}
