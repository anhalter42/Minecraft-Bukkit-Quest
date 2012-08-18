/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.framework.BlockPosition;
import org.bukkit.Material;

/**
 *
 * @author andre
 */
public class FillBlocks extends Action {
    // RUNTIME
    protected Material fMat = Material.AIR;
    
    // META
    public String material;
    public byte data = 0;
    public BlockPosition from = new BlockPosition();
    public BlockPosition to = new BlockPosition(-1,-1,-1);
    
    @Override
    public void initialize() {
        fMat = Material.getMaterial(material);
        if (fMat == null) {
            fMat = Material.getMaterial(Integer.parseInt(material));
        }
        if (to.x < 0) {
            to.cloneFrom(from);
        }
        BlockPosition lFrom = from.clone();
        BlockPosition lTo = to.clone();
        from = lFrom.getMinPos(lTo);
        to = lFrom.getMaxPos(lTo);
        from.add(quest.edge1);
        to.add(quest.edge1);
        super.initialize();
    }

    @Override
    public void execute() {
        for(int x = from.x; x <= to.x; x++) {
            for(int y = from.y; y <= to.y; y++) {
                for(int z = from.z; z <= to.z; z++) {
                    quest.syncList.add(new BlockPosition(x, y, z), fMat, data);
                }
            }
        }
    }
    
}
