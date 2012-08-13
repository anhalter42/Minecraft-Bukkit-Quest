/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.framework.BlockPosition;

/**
 *
 * @author andre
 */
public class LoadFrame extends Action {
    public enum Mode {
        full,
        mixed,
        reverse
    }
    public int index;
    public BlockPosition pos = new BlockPosition(0,0,0); // relative pos
    public Mode mode = Mode.full;
    
    public void execute() {
        BlockPosition lPos = quest.edge1.clone();
        lPos.add(pos);
        switch (mode) {
            case full:
                quest.frames.toList(index, quest.syncList, lPos);
                break;
            case mixed:
                quest.frames.toListMixed(index, quest.syncList, lPos);
                break;
            case reverse:
                //TODO: reverse
                quest.frames.toList(index, quest.syncList, lPos);
                break;
        }
    }
}
