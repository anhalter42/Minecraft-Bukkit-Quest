/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.anhalter42.quest.QuestObject;
import com.mahn42.framework.BlockArea;
import com.mahn42.framework.BlockArea.BlockAreaItem;
import com.mahn42.framework.BlockPosition;
import java.util.ArrayList;
import org.bukkit.Material;

/**
 *
 * @author andre
 */
public class ReplaceBlocks extends Action {
    public class Mat extends QuestObject {
        public Material from;
        public byte fromData = (byte)0;
        public Material to;
        public byte toData = (byte)0;
    }
    
    // RUNTIME
    public BlockArea area;
    public int width;
    public int height;
    public int depth;
    
    // META
    public BlockPosition from = new BlockPosition();
    public BlockPosition to = new BlockPosition();
    public boolean cycle = false;
    public ArrayList<Mat> materials = new ArrayList<Mat>();
    
    public void setMaterialsFromSectionValue(Object aValue) {
        if (aValue instanceof ArrayList) {
            for(Object lItem : ((ArrayList)aValue)) {
                Mat lMat = new Mat();
                lMat.quest = quest;
                lMat.fromSectionValue(lItem);
                materials.add(lMat);
            }
        }
    }
    
    @Override
    public void initialize() {
        super.initialize();
        to.add(quest.edge1);
        from.add(quest.edge1);
        BlockPosition lWHD = from.getWHD(to);
        width = lWHD.x;
        height = lWHD.y;
        depth = lWHD.z;
        area = new BlockArea(width, height, depth);
    }
    
    @Override
    public void execute() {
        boolean lChanged = false;
        area.fromWorld(quest.world, from, false, false);
        for(int x=0; x<width; x++) {
            for(int y=0; y<height; y++) {
                for(int z=0; z<depth; z++) {
                    BlockAreaItem lItem = area.get(x, y, z);
                    for(Mat lMat : materials) {
                        if (lMat.from.getId() == lItem.id
                                && (lMat.fromData == 0 || lMat.fromData == lItem.data)) {
                            lChanged = true;
                            lItem.id = lMat.to.getId();
                            lItem.data = lMat.toData;
                            break;
                        }
                    }
                }
            }
        }
        if (lChanged) {
            area.toList(quest.syncList, from, BlockArea.BlockAreaPlaceMode.mixed);
        }
    }
}
