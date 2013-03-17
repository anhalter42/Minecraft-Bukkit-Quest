/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.trait;

import com.mahn42.anhalter42.quest.QuestObject;
import com.mahn42.framework.BlockPosition;

/**
 *
 * @author andre
 */
public class QuestNPCPathItem extends QuestObject {
    // META
    public BlockPosition pos = new BlockPosition();
    
    // Runtime
    protected BlockPosition fpos = null;
    public BlockPosition getPosition() {
        if (fpos == null) {
            fpos = new BlockPosition(pos);
            fpos.add(quest.edge1);
        }
        return fpos;
    }
}
