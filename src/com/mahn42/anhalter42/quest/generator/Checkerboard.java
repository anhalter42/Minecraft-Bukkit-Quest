/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.generator;

import com.mahn42.anhalter42.quest.GeneratorBase;
import com.mahn42.framework.BlockArea;
import com.mahn42.framework.BlockArea.BlockAreaItem;
import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.SyncBlockList;
import com.mahn42.framework.WoolColors;
import java.util.Random;
import org.bukkit.Material;

/**
 *
 * @author andre
 */
public class Checkerboard extends GeneratorBase {

    // META
    public Material blackMaterial = Material.WOOL;
    public byte blackMaterialData = WoolColors.black;
    public Material whiteMaterial = Material.WOOL;
    public byte whiteMaterialData = WoolColors.white;
    public int chanceForError = 20;
    public int blockSize = 1;
    
    @Override
    public void initialize(BlockPosition aFrom, BlockPosition aTo) {
        super.initialize(aFrom, aTo);
    }

    @Override
    public void execute(SyncBlockList aSyncList) {
        Random lRnd = new Random();
        for(int x=0;x<width;x++) {
            for(int y=0;y<height;y++) {
                for(int z=0;z<depth;z++) {
                    if (lRnd.nextInt(100) < chanceForError) {
                        BlockAreaItem lItem = area.get(x, y, z);
                        lItem.id = Material.AIR.getId();
                        lItem.data = (byte)0;
                    } else {
                        if (((x/blockSize) % 1) == 0 && ((z/blockSize) % 1) == 0 && ((y/blockSize) % 1) == 0) {
                            BlockAreaItem lItem = area.get(x, y, z);
                            lItem.id = blackMaterial.getId();
                            lItem.data = blackMaterialData;
                        } else {
                            BlockAreaItem lItem = area.get(x, y, z);
                            lItem.id = whiteMaterial.getId();
                            lItem.data = whiteMaterialData;
                        }
                    }
                }
            }
        }
        area.toList(aSyncList, from, BlockArea.BlockAreaPlaceMode.full);
    }
}
