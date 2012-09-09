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
    public BlockArea.BlockAreaPlaceMode blockPlaceMode = BlockArea.BlockAreaPlaceMode.full;
    
    @Override
    public void initialize(BlockPosition aFrom, BlockPosition aTo) {
        super.initialize(aFrom, aTo);
    }

    @Override
    public void execute(SyncBlockList aSyncList) {
        Random lRnd = new Random();
        for(int x=0;x<width;x+=blockSize) {
            for(int y=0;y<height;y+=blockSize) {
                for(int z=0;z<depth;z+=blockSize) {
                    int lId;
                    byte lData;
                    if (lRnd.nextInt(100) < chanceForError) {
                        lId = Material.AIR.getId();
                        lData = (byte)0;
                    } else {
                        if ((((x/blockSize) + (z/blockSize) + (y/blockSize)) % 2) == 0) {
                            lId = blackMaterial.getId();
                            lData = blackMaterialData;
                        } else {
                            lId = whiteMaterial.getId();
                            lData = whiteMaterialData;
                        }
                    }
                    for(int bx=0;bx<blockSize;bx++) {
                        for(int by=0;by<blockSize;by++) {
                            for(int bz=0;bz<blockSize;bz++) {
                                if (x+bx < width && y+by<height && z+bz<depth) {
                                    BlockAreaItem lItem = area.get(x+bx, y+by, z+bz);
                                    lItem.id = lId;
                                    lItem.data = lData;
                                }
                            }
                        }
                    }
                }
            }
        }
        area.toList(aSyncList, from, blockPlaceMode);
    }
}
