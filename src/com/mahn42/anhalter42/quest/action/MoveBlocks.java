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
public class MoveBlocks extends Action {
    
    public class Mat extends QuestObject {
        public Material material = Material.AIR;
        public byte data = (byte)0;
    }
    
    // RUNTIME
    public BlockArea area;
    public int width;
    public int height;
    public int depth;
    
    // META
    public BlockPosition from = new BlockPosition();
    public BlockPosition to = new BlockPosition();
    public BlockPosition vector = new BlockPosition();
    public ArrayList<Mat> transparentMaterials = new ArrayList<Mat>();
    public boolean cycle = false;
    
    public void setTransparentMaterialsFromSectionValue(Object aObject) {
        if (aObject instanceof ArrayList) {
            for(Object lItem : (ArrayList)aObject) {
                Mat lMat = new Mat();
                lMat.quest = quest;
                lMat.fromSectionValue(lItem);
                transparentMaterials.add(lMat);
            }
        }
    }
    
    public boolean isTransparent(BlockAreaItem lItem) {
        boolean lResult = false;
        for(Mat lMat : transparentMaterials) {
            if (lMat.material.getId() == lItem.id && (lMat.data == (byte)0 || lMat.data == lItem.data)) {
                lResult = true;
                break;
            }
        }
        return lResult;
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
        area.fromWorld(quest.world, from, false, false);
        if (vector.x != 0 || vector.y != 0 || vector.z != 0) {
            for(int x=(vector.x < 0 ? 0 : width-1);(vector.x < 0 ? x<width : x>=0);x+=(vector.x < 0 ? 1 : -1)) {
                for(int y=(vector.y < 0 ? 0 : height-1);(vector.y < 0 ? y<height : y>=0);y+=(vector.y < 0 ? 1 : -1)) {
                    for(int z=(vector.z < 0 ? 0 : depth-1);(vector.z < 0 ? z<depth : z>=0);z+=(vector.z < 0 ? 1 : -1)) {
                        BlockPosition lToPos = new BlockPosition(x, y, z);
                        lToPos.add(vector);
                        //quest.log("x="+x+" y="+y+" z="+z+"  to "+lToPos);
                        if (lToPos.x >= 0 && lToPos.x < width
                                && lToPos.y >= 0 && lToPos.y < height
                                && lToPos.z >= 0 && lToPos.z < depth) {
                            BlockAreaItem lFrom = area.get(x, y, z);
                            if (!isTransparent(lFrom)) {
                                BlockAreaItem lTo = area.get(lToPos.x, lToPos.y, lToPos.z);
                                lTo.cloneFrom(lFrom);
                            }
                        }
                    }
                }
            }
        }
        area.toList(quest.syncList, from, BlockArea.BlockAreaPlaceMode.full);
    }
}
