/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.generator;

import com.mahn42.anhalter42.quest.GeneratorBase;
import com.mahn42.framework.BlockArea;
import com.mahn42.framework.SyncBlockList;
import java.util.Random;
import org.bukkit.Material;

/**
 *
 * @author andre
 */
public class Randomizer extends GeneratorBase{

    @Override
    public void execute(SyncBlockList aSyncList) {
        Random lRnd = new Random();
        area.clear(Material.AIR, (byte)0);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                for(int z = 0; z < depth; z++) {
                    Material lMat = null;
                    while (lMat == null) {
                        int lId = lRnd.nextInt(138);
                        lMat = Material.getMaterial(lId);
                        if (lMat != null && !lMat.isBlock()) {
                            lMat = null;
                        }
                    }
                    area.get(x, y, z).id = lMat.getId();
                }
            }
        }
        area.toList(aSyncList, from, BlockArea.BlockAreaPlaceMode.full);
    }
    
}
